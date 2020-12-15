package com.dimples.core.thread;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;

import lombok.extern.slf4j.Slf4j;

/**
 * 构建线程池
 *
 * @author zhongyj <1126834403@qq.com><br/>
 * @date 2020/2/24
 */
@Slf4j
public class DimplesThreadPoolExecutor {

    private static DimplesThreadProperties threadProperties = new DimplesThreadProperties();

    private static ThreadPoolExecutor mExecutor;

    private static ThreadPoolExecutor newPoolExecutor;

    private static ScheduledExecutorService scheduledExecutor;

    private DimplesThreadPoolExecutor() {
    }

    /**
     * 初始化ThreadPoolExecutor
     * 双重检查加锁,只有在第一次实例化的时候才启用同步机制,提高了性能
     */
    public static ThreadPoolExecutor initThreadPoolExecutor(String threadPre) {
        if (mExecutor == null || mExecutor.isShutdown() || mExecutor.isTerminated()) {
            synchronized (DimplesThreadPoolExecutor.class) {
                if (mExecutor == null || mExecutor.isShutdown() || mExecutor.isTerminated()) {
                    BlockingQueue<Runnable> workQueue = new LinkedBlockingDeque<>();
                    ThreadFactory threadFactory = new DimplesThreadFactory(threadPre);
                    RejectedExecutionHandler handler = new ThreadPoolExecutor.CallerRunsPolicy();

                    mExecutor = new ThreadPoolExecutor(threadProperties.getCorePoolSize(),
                            threadProperties.getMaxPoolSize(),
                            threadProperties.getKeepAliveSeconds(),
                            threadProperties.getUnit(),
                            workQueue,
                            threadFactory,
                            handler);
                }
            }
        }
        return mExecutor;
    }

    /**
     * 创建新的线程池，不可以滥用，否则会创建很多的线程池
     *
     * @param threadPre 线程池前缀
     * @return ThreadPoolExecutor
     */
    public static ThreadPoolExecutor newThreadPoolExecutor(String threadPre) {
        return newThreadPoolExecutor(threadPre, 0);
    }

    public static ThreadPoolExecutor newThreadPoolExecutor(String threadPre, int corePoolSize) {
        createThreadPool(threadPre, corePoolSize);
        return newPoolExecutor;
    }

    private static void createThreadPool(String threadPre, int corePoolSize) {
        BlockingQueue<Runnable> workQueue = new LinkedBlockingDeque<>();
        ThreadFactory threadFactory = new DimplesThreadFactory(threadPre);
        RejectedExecutionHandler handler = new ThreadPoolExecutor.CallerRunsPolicy();
        if (corePoolSize == 0) {
            corePoolSize = threadProperties.getCorePoolSize();
        }

        newPoolExecutor = new ThreadPoolExecutor(corePoolSize,
                threadProperties.getMaxPoolSize(),
                threadProperties.getKeepAliveSeconds(),
                threadProperties.getUnit(),
                workQueue,
                threadFactory,
                handler);
    }

    /**
     * 任务线程池
     *
     * @param threadPre    线程池前缀
     * @param corePoolSize 线程池核心数
     * @return 任务线程池
     */
    public static ScheduledExecutorService newScheduledThreadPool(String threadPre, int corePoolSize) {
        if (scheduledExecutor == null || scheduledExecutor.isShutdown() || scheduledExecutor.isTerminated()) {
            synchronized (DimplesThreadPoolExecutor.class) {
                if (scheduledExecutor == null || scheduledExecutor.isShutdown() || scheduledExecutor.isTerminated()) {
                    ThreadFactory threadFactory = new DimplesThreadFactory(threadPre);
                    scheduledExecutor = new ScheduledThreadPoolExecutor(corePoolSize, threadFactory);
                }
            }
        }
        return scheduledExecutor;
    }


}
















