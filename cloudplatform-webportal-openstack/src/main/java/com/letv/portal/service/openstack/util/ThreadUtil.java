package com.letv.portal.service.openstack.util;

import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import com.letv.portal.service.openstack.exception.OpenStackException;
import com.letv.portal.service.openstack.exception.TimeoutException;
import com.letv.portal.service.openstack.util.function.Function0;
import com.letv.portal.service.openstack.util.function.Function1;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by zhouxianguang on 2015/10/30.
 */
public class ThreadUtil {
    private static final ListeningExecutorService executorService = MoreExecutors.listeningDecorator(Executors.newCachedThreadPool());

//    public static void asyncExec(Runnable task) {
//        executorService.submit(task);
//    }

    public static void asyncExec(final Function0<Void> task) {
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    task.apply();
                } catch (Exception ex) {
                    ExceptionUtil.logAndEmail(ex);
                }
            }
        });
    }

    private static <T> List<T> getResultsOfFutures(
            List<ListenableFuture<T>> futures) throws InterruptedException,
            ExecutionException, java.util.concurrent.TimeoutException {
        List<T> results = new LinkedList<T>();
        for (ListenableFuture<T> future : futures) {
            results.add(future.get(0L, TimeUnit.SECONDS));
        }
        return results;
    }

//    @Deprecated
//    public static void concurrentRunAndWait(Runnable currentThreadTask, Runnable... otherTasks) {
//        ListenableFuture<?>[] futures = new ListenableFuture[otherTasks.length];
//        for (int i = 0; i < otherTasks.length; i++) {
//            futures[i] = executorService.submit(otherTasks[i]);
//        }
//
//        currentThreadTask.run();
//
//        try {
//            Futures.successfulAsList(futures).get();
//        } catch (Exception e) {
//            throw new MatrixException("后台错误", e);
//        }
//    }

    public static <T> List<Ref<T>> concurrentRunAndWait(Function0<T> currentThreadTask, Function0<T>... otherTasks) throws OpenStackException {
        return concurrentRunAndWait(null, currentThreadTask, otherTasks);
    }

    public static <T> List<Ref<T>> concurrentRunAndWait(Timeout timeout, Function0<T> currentThreadTask, Function0<T>... otherTasks) throws OpenStackException {
        try {
            List<ListenableFuture<Ref<T>>> futures = new LinkedList<ListenableFuture<Ref<T>>>();
            for (int i = 0; i < otherTasks.length; i++) {
                final Function0<T> task = otherTasks[i];
                futures.add(executorService.submit(new Callable<Ref<T>>() {
                    @Override
                    public Ref<T> call() throws Exception {
                        return new Ref<T>(task.apply());
                    }
                }));
            }

            T firstResult = currentThreadTask.apply();

            ListenableFuture<List<Ref<T>>> listFuture = Futures.successfulAsList(futures);
            if (timeout != null) {
                listFuture.get(timeout.time(), timeout.unit());
            } else {
                listFuture.get();
            }
            List<Ref<T>> otherResultRefs = getResultsOfFutures(futures);

            List<Ref<T>> resultList = new LinkedList<Ref<T>>();
            resultList.add(new Ref<T>(firstResult));
            if (otherResultRefs != null) {
                for (Ref<T> resultRef : otherResultRefs) {
                    if (resultRef != null) {
                        resultList.add(resultRef);
                    } else {
                        resultList.add(new Ref<T>());
                    }
                }
            } else {
                for (int i = 0; i < otherTasks.length; i++) {
                    resultList.add(new Ref<T>());
                }
            }
            return resultList;
        } catch (ExecutionException ex) {
            ExceptionUtil.throwException(ExceptionUtil.getCause(ex));
        } catch (Exception ex) {
            ExceptionUtil.throwException(ex);
        }
        List<Ref<T>> resultList = new LinkedList<Ref<T>>();
        for (int i = 0; i <= otherTasks.length; i++) {
            resultList.add(new Ref<T>());
        }
        return resultList;
    }

//    public static void concurrentRun(Runnable... tasks) {
//        for (Runnable task : tasks) {
//            executorService.submit(task);
//        }
//    }

    public static void concurrentRun(Function0<Void>... tasks) {
        for (final Function0<Void> task : tasks) {
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    try {
                        task.apply();
                    } catch (Exception ex) {
                        ExceptionUtil.logAndEmail(ex);
                    }
                }
            });
        }
    }

    public static <PT, RT> List<RT> concurrentFilter(List<PT> list, final Function1<PT, RT> filter) throws OpenStackException {
        return concurrentFilter(list, filter, null);
    }

    public static <PT, RT> List<RT> concurrentFilter(List<PT> list, final Function1<PT, RT> filter, Timeout timeout) throws OpenStackException {
        try {
            if (list.isEmpty()) {
                return new LinkedList<RT>();
            }
            if (list.size() == 1) {
                List<RT> newList = new LinkedList<RT>();
                RT element = filter.apply(list.get(0));
                if (element != null) {
                    newList.add(element);
                }
                return newList;
            }

            List<ListenableFuture<Ref<RT>>> futures = new LinkedList<ListenableFuture<Ref<RT>>>();
            for (int i = 1; i < list.size(); i++) {
                final PT oldElement = list.get(i);
                futures.add(executorService.submit(new Callable<Ref<RT>>() {
                    @Override
                    public Ref<RT> call() throws Exception {
                        return new Ref<RT>(filter.apply(oldElement));
                    }
                }));
            }

            RT firstNewElement = filter.apply(list.get(0));

            ListenableFuture<List<Ref<RT>>> listFuture = Futures.successfulAsList(futures);
            if (timeout != null) {
                listFuture.get(timeout.time(), timeout.unit());
            } else {
                listFuture.get();
            }
            List<Ref<RT>> otherNewElementRefs = getResultsOfFutures(futures);

            List<RT> newList = new LinkedList<RT>();
            if (firstNewElement != null) {
                newList.add(firstNewElement);
            }
            if (otherNewElementRefs != null) {
                for (Ref<RT> newElementRef : otherNewElementRefs) {
                    RT newElement = newElementRef.get();
                    if (newElement != null) {
                        newList.add(newElement);
                    }
                }
            }
            return newList;
        } catch (ExecutionException ex) {
            ExceptionUtil.throwException(ExceptionUtil.getCause(ex));
        } catch (Exception ex) {
            ExceptionUtil.throwException(ex);
        }
        return new LinkedList<RT>();
    }

    public static void waiting(Function0<Boolean> checker)
            throws OpenStackException {
        waiting(checker, null, null);
    }

    public static void waiting(Function0<Boolean> checker, Timeout timeout)
            throws OpenStackException {
        waiting(checker, timeout, null);
    }

    public static void waiting(Function0<Boolean> checker, Long sleepTime)
            throws OpenStackException {
        waiting(checker, null, sleepTime);
    }

    public static void waiting(Function0<Boolean> checker, Timeout timeout, Long sleepTime)
            throws OpenStackException {
        try {
            Long timeoutMillis = null;
            if (timeout != null) {
                timeoutMillis = TimeUnit.MILLISECONDS.convert(timeout.time(), timeout.unit());
            }

            if (sleepTime == null || sleepTime <= 0) {
                sleepTime = 1000L;
            }

            long beginTime = System.currentTimeMillis();
            while (checker.apply()) {
                Thread.sleep(sleepTime);
                if (timeoutMillis != null) {
                    long timeInterval = System.currentTimeMillis() - beginTime;
                    if (timeInterval > timeoutMillis) {
                        throw new TimeoutException(timeout, timeInterval);
                    }
                }
            }
        } catch (Exception e) {
            ExceptionUtil.throwException(e);
        }
    }
}
