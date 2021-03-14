package com.github.lzco.cache.task;

import com.github.lzco.cache.constant.CacheConstant;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 任务信息配置
 * @author lzc
 * @date 2021/03/11 14:23
 * See More: https://github.com/lzco, https://gitee.com/lzco
 */
@ConfigurationProperties(prefix = CacheConstant.TASK_PREFIX)
public class TaskProperties {

    private String enabled = "true";

    private String cron;

    private int poolSize = 2;

    private int queueSize = 32;

    public String getEnabled() {
        return enabled;
    }

    public void setEnabled(String enabled) {
        this.enabled = enabled;
    }

    public String getCron() {
        return cron;
    }

    public void setCron(String cron) {
        this.cron = cron;
    }

    public int getPoolSize() {
        return poolSize;
    }

    public void setPoolSize(int poolSize) {
        this.poolSize = poolSize;
    }

    public int getQueueSize() {
        return queueSize;
    }

    public void setQueueSize(int queueSize) {
        this.queueSize = queueSize;
    }
}
