package com.github.lzco.cache.clone;

import org.springframework.cache.Cache;

import java.lang.reflect.Method;
import java.util.Collection;

/**
 * clone from org.springframework.cache.interceptor.CacheExpressionRootObject
 * @author lzc
 * @date 2021/03/10 14:08
 * See More: https://github.com/lzco, https://gitee.com/lzco
 */
public class CacheExpressionRootObject {

    private final Collection<? extends Cache> caches;
    private final Method method;
    private final Object[] args;
    private final Object target;
    private final Class<?> targetClass;

    public CacheExpressionRootObject(Collection<? extends Cache> caches, Method method, Object[] args, Object target, Class<?> targetClass) {
        this.method = method;
        this.target = target;
        this.targetClass = targetClass;
        this.args = args;
        this.caches = caches;
    }

    public Collection<? extends Cache> getCaches() {
        return this.caches;
    }

    public Method getMethod() {
        return this.method;
    }

    public String getMethodName() {
        return this.method.getName();
    }

    public Object[] getArgs() {
        return this.args;
    }

    public Object getTarget() {
        return this.target;
    }

    public Class<?> getTargetClass() {
        return this.targetClass;
    }
}
