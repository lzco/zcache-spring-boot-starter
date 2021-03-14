package com.github.lzco.cache.configuration;

import com.github.lzco.cache.constant.CacheConstant;
import com.github.lzco.cache.core.TtlRedisCacheManager;
import com.github.lzco.cache.core.TtlRedisCacheResolver;
import com.github.lzco.cache.task.CacheRefresher;
import com.github.lzco.cache.task.CacheTask;
import com.github.lzco.cache.task.SpringContextUtil;
import com.github.lzco.cache.task.TaskProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.CustomizableThreadFactory;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 缓存自动配置
 *
 * @author lzc
 * @date 2021/03/08 9:42
 * See More: https://github.com/lzco, https://gitee.com/lzco
 */
@Configuration
@EnableCaching
@EnableScheduling
@EnableConfigurationProperties(TaskProperties.class)
public class CacheAutoConfiguration {

    @Resource
    RedisConnectionFactory redisConnectionFactory;
    @Resource
    private TaskProperties taskProperties;

    @Bean
    @ConditionalOnMissingBean(name = "ttlRedisCacheWriter")
    public RedisCacheWriter ttlRedisCacheWriter() {
        return RedisCacheWriter.nonLockingRedisCacheWriter(redisConnectionFactory);
    }

    @Bean
    @ConditionalOnMissingBean(name = "ttlRedisCacheConfiguration")
    public RedisCacheConfiguration ttlRedisCacheConfiguration() {
        return RedisCacheConfiguration.defaultCacheConfig();
    }

    @Bean
    @ConditionalOnMissingBean(name = "ttlRedisCacheManager")
    public TtlRedisCacheManager ttlRedisCacheManager(RedisCacheWriter ttlRedisCacheWriter,
                                                     RedisCacheConfiguration ttlRedisCacheConfiguration) {
        return new TtlRedisCacheManager(ttlRedisCacheWriter, ttlRedisCacheConfiguration, new HashMap<>(16));
    }

    @Bean
    @ConditionalOnMissingBean(name = "ttlRedisCacheResolver")
    public TtlRedisCacheResolver ttlRedisCacheResolver(TtlRedisCacheManager ttlRedisCacheManager) {
        return new TtlRedisCacheResolver(ttlRedisCacheManager);
    }

    @Bean
    @ConditionalOnMissingBean(name = "ttlRedisTemplate")
    public RedisTemplate<String, Object> ttlRedisTemplate() {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.setKeySerializer(RedisSerializer.string());
        redisTemplate.setValueSerializer(RedisSerializer.java());
        redisTemplate.setHashKeySerializer(RedisSerializer.string());
        redisTemplate.setHashValueSerializer(RedisSerializer.java());
        return redisTemplate;
    }

    @Bean
    @ConditionalOnClass(TaskProperties.class)
    @ConditionalOnProperty(prefix = CacheConstant.TASK_PREFIX, value = "enabled", havingValue = "true", matchIfMissing = true)
    public CacheTask cacheTask() {
        return new CacheTask();
    }

    @Bean("cacheRefresher")
    public CacheRefresher cacheRefresher() {
        return new CacheRefresher();
    }

    @Bean("cacheThreadPoolExecutor")
    @ConditionalOnMissingBean(value = {ThreadPoolExecutor.class, ExecutorService.class})
    public ThreadPoolExecutor cacheThreadPoolExecutor() {
        int poolSize = 2;
        int maxPoolSize = 16;
        if (taskProperties.getPoolSize() > 0) {
            poolSize = taskProperties.getPoolSize();
        }
        if (poolSize > maxPoolSize) {
            poolSize = maxPoolSize;
        }
        int queueSize = 32;
        int maxQueueSize = 1024;
        if (taskProperties.getQueueSize() > 0) {
            queueSize = taskProperties.getQueueSize();
        }
        if (queueSize > maxQueueSize) {
            queueSize = maxQueueSize;
        }
        CustomizableThreadFactory threadFactory = new CustomizableThreadFactory(CacheConstant.THREAD_FACTORY);
        return new ThreadPoolExecutor(poolSize, poolSize, 0, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(queueSize), threadFactory);
    }

    @Bean("springContextUtil")
    public SpringContextUtil springContextUtil() {
        return new SpringContextUtil();
    }

}
