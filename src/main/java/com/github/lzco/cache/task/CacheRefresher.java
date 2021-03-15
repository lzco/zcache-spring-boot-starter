/**
 * Copyright 2021 the original author or authors.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.lzco.cache.task;

import com.github.lzco.cache.constant.CacheConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 缓存刷新器
 * @author lzc
 * @date 2021/03/11 16:00
 * See More: https://github.com/lzco, https://gitee.com/lzco
 */
public class CacheRefresher {

    @Resource(name = "ttlRedisTemplate")
    private RedisTemplate<String, Object> redisTemplate;

    @Resource
    private ThreadPoolExecutor cacheThreadPoolExecutor;

    private final Logger logger = LoggerFactory.getLogger(CacheRefresher.class);

    /**
     * 添加需要自动刷新的缓存
     * @param cacheInvocation 缓存调用对象
     * @author lzc
     * @date 2021/03/10 16:21
     */
    public void addCache(CacheInvocation cacheInvocation) {
        Object cache = redisTemplate.opsForHash().get(CacheConstant.REFRESH_KEY, cacheInvocation.getKey());
        CacheInvocation oldInvocation = (CacheInvocation) cache;
        if (oldInvocation != null && oldInvocation.getTtl() == cacheInvocation.getTtl()) {
            // 以key为准，如果过期时间一样，则视为完全一样，避免每次获取方法缓存都设置一次刷新参数的缓存
            return;
        }
        redisTemplate.opsForHash().put(CacheConstant.REFRESH_KEY, cacheInvocation.getKey(), cacheInvocation);
    }

    public void refresh() {
        Map<Object, Object> caches = redisTemplate.opsForHash().entries(CacheConstant.REFRESH_KEY);
        if (caches.size() == 0) {
            return;
        }
        for (Map.Entry<Object, Object> entry : caches.entrySet()) {
            try {
                final CacheInvocation cacheInvocation = (CacheInvocation) entry.getValue();
                if (cacheInvocation == null) {
                    redisTemplate.opsForHash().delete(CacheConstant.REFRESH_KEY, entry.getKey());
                    continue;
                }
                cacheThreadPoolExecutor.execute(() -> this.execute(cacheInvocation));
            } catch (ClassCastException e) {
                redisTemplate.opsForHash().delete(CacheConstant.REFRESH_KEY, entry.getKey());
            }
        }
    }

    private void execute(CacheInvocation cacheInvocation) {
        try {
            Class<?> targetClass = Class.forName(cacheInvocation.getTargetName());
            Object target = SpringContextUtil.getBean(targetClass);
            Method method = targetClass.getMethod(cacheInvocation.getMethodName(), cacheInvocation.getArgTypes());
            Object data = method.invoke(target, cacheInvocation.getArgs());
            if (cacheInvocation.getTtl() > 0) {
                redisTemplate.opsForValue().set(cacheInvocation.getKey(), data, cacheInvocation.getTtl(), TimeUnit.SECONDS);
            } else {
                redisTemplate.opsForValue().set(cacheInvocation.getKey(), data);
            }
        } catch (Exception e) {
            logger.error("CacheInvocation reflect fail", e);
        }
    }
}
