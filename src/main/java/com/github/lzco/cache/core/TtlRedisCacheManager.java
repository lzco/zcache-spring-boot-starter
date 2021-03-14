package com.github.lzco.cache.core;

import org.springframework.data.redis.cache.RedisCache;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;

import java.util.Map;

/**
 * 自定义带ttl的redis缓存管理器
 *
 * @author lzc
 * @date 2021/03/08 16:25
 * See More: https://github.com/lzco, https://gitee.com/lzco
 */
public class TtlRedisCacheManager extends RedisCacheManager {

    private final RedisCacheConfiguration defaultCacheConfiguration;

    public TtlRedisCacheManager(RedisCacheWriter cacheWriter, RedisCacheConfiguration defaultCacheConfiguration, Map<String, RedisCacheConfiguration> initialCacheConfigurations) {
        super(cacheWriter, defaultCacheConfiguration, initialCacheConfigurations);
        this.defaultCacheConfiguration = defaultCacheConfiguration;
    }

    @Override
    public RedisCache createRedisCache(String name, RedisCacheConfiguration cacheConfig) {
        return super.createRedisCache(name, cacheConfig);
    }

    public RedisCacheConfiguration getCacheConfiguration() {
        return RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(defaultCacheConfiguration.getTtl())
                .serializeKeysWith(defaultCacheConfiguration.getKeySerializationPair())
                .serializeValuesWith(defaultCacheConfiguration.getValueSerializationPair())
                .withConversionService(defaultCacheConfiguration.getConversionService())
                .computePrefixWith(defaultCacheConfiguration::getKeyPrefixFor);
    }
}
