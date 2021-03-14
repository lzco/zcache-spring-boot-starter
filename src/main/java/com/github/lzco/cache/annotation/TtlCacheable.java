package com.github.lzco.cache.annotation;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * 带ttl的缓存注解
 *
 * @author lzc
 * @date 2021/03/08 9:53
 * See More: https://github.com/lzco, https://gitee.com/lzco
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
@Cacheable
public @interface TtlCacheable {

    @AliasFor("cacheNames")
    String[] value() default {};

    @AliasFor("value")
    String[] cacheNames() default {};

    /**
     * key值，默认方法名（加参数值）
     */
    String key() default "method.name + (args.length==0?'':('-'+ args))";

    String keyGenerator() default "";

    String cacheManager() default "";

    String cacheResolver() default "ttlRedisCacheResolver";

    String condition() default "";

    String unless() default "";

    boolean sync() default false;

    long ttl() default -1;

    boolean autoRefreshWithoutUnless() default false;
}
