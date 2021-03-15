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
