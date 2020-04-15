package com.dimples.core.thread;

import java.util.concurrent.ExecutorCompletionService;

/**
 * @author zhongyj <1126834403@qq.com><br/>
 * @date 2020/2/24
 */
public class DimplesTaskExecutor<T> {

    private ExecutorCompletionService<T> completionService;

    public DimplesTaskExecutor() {
        this.completionService = new ExecutorCompletionService<>(DimplesThreadPoolExecutor.initThreadPoolExecutor());
    }

    public ExecutorCompletionService<T> getCompletionService(){
        return completionService;
    }
}
