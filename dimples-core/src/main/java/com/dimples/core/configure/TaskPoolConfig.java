package com.dimples.core.configure;

import com.dimples.core.thread.DimplesThreadPoolExecutor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.concurrent.Executor;

/**
 * springboot的异步线程池配置
 * 使用：@Async("taskExecutor")
 *
 * @author zhongyj <1126834403@qq.com><br/>
 * @date 2020/4/15
 */
@EnableAsync
@Configuration
public class TaskPoolConfig {

    @Bean("taskExecutor")
    public Executor taskExecutor() {
        return DimplesThreadPoolExecutor.initThreadPoolExecutor();
    }

}
