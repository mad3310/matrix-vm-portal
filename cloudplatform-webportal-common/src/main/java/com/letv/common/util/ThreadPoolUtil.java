package com.letv.common.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 线程池工具类 线程数不设上限，池中线程60秒未使用则自动销毁， 适合执行时间短但执行频繁的异步任务
 * 
 * @author chen liu
 */
public final class ThreadPoolUtil {
    private static ExecutorService executorService = Executors.newCachedThreadPool();

    private ThreadPoolUtil() {
    }

    /**
     * 异步执行线程任务
     * 
     * @param task task
     */
    public static void execute(Runnable task) {
        executorService.execute(task);
    }

}
