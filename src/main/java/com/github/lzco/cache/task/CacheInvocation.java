package com.github.lzco.cache.task;

import java.io.Serializable;

/**
 * 缓存调用信息
 * @author lzc
 * @date 2021/03/09 16:52
 * See More: https://github.com/lzco, https://gitee.com/lzco
 */
public class CacheInvocation implements Serializable {

    private static final long serialVersionUID = 4622181904589844690L;

    private final String targetName;

    private final String methodName;

    private final Class<?>[] argTypes;

    private final Object[] args;

    private final String key;

    private final long ttl;

    public CacheInvocation(String targetName, String methodName, Class<?>[] argTypes, Object[] args, String key, long ttl) {
        this.targetName = targetName;
        this.methodName = methodName;
        this.argTypes = argTypes;
        this.args = args;
        this.key = key;
        this.ttl = ttl;
    }

    public String getTargetName() {
        return targetName;
    }

    public String getMethodName() {
        return methodName;
    }

    public Class<?>[] getArgTypes() {
        return argTypes;
    }

    public Object[] getArgs() {
        return args;
    }

    public String getKey() {
        return key;
    }

    public long getTtl() {
        return ttl;
    }
}
