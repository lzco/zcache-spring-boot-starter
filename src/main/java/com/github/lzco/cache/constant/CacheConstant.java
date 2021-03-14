package com.github.lzco.cache.constant;

/**
 * 常量
 * @author lzc
 * @date 2021/03/10 16:07
 * See More: https://github.com/lzco, https://gitee.com/lzco
 */
public final class CacheConstant {

    private CacheConstant() {}

    public static final String PROJECT_CONSTANT = "z-cache";

    public static final String REFRESH_KEY = PROJECT_CONSTANT + "::refresh";

    public static final String TASK_PREFIX = PROJECT_CONSTANT + ".task";

    public static final String THREAD_FACTORY = PROJECT_CONSTANT + "-thread-pool-";

}
