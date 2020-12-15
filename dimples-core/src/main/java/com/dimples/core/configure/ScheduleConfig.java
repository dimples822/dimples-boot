package com.dimples.core.configure;

import com.dimples.core.thread.DimplesThreadPoolExecutor;

import org.springframework.boot.autoconfigure.batch.BatchProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import java.lang.reflect.Method;

/**
 * @author zhongyj <1126834403@qq.com><br/>
 * @date 2020/12/15
 */
@Configuration
public class ScheduleConfig implements SchedulingConfigurer {
    @SuppressWarnings("NullableProblems")
    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        Method[] methods = BatchProperties.Job.class.getMethods();
        int defaultPoolSize = 3;
        int corePoolSize = 0;
        if (methods.length > 0) {
            for (Method method : methods) {
                Scheduled annotation = method.getAnnotation(Scheduled.class);
                if (annotation != null) {
                    corePoolSize++;
                }
            }
            if (defaultPoolSize > corePoolSize) {
                corePoolSize = defaultPoolSize;
            }
        }
        taskRegistrar.setScheduler(DimplesThreadPoolExecutor.newScheduledThreadPool("Schedule", corePoolSize));
    }
}
