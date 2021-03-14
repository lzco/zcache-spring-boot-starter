package com.github.lzco.cache.task;

import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import javax.annotation.Resource;

/**
 * 缓存任务
 * <p>之所以不用@Scheduled，是为了从外部配置文件设置定时周期</p>
 * @author lzc
 * @date 2021/03/11 11:24
 * See More: https://github.com/lzco, https://gitee.com/lzco
 */
public class CacheTask implements SchedulingConfigurer {

    @Resource
    private TaskProperties taskProperties;

    @Resource
    private CacheRefresher cacheRefresher;

    @Override
    public void configureTasks(ScheduledTaskRegistrar registrar) {
        if (taskProperties.getCron() != null && taskProperties.getCron().trim().length() > 0) {
            registrar.addCronTask(() -> cacheRefresher.refresh(), taskProperties.getCron());
        }
    }

}
