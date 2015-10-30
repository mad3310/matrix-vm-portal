package com.letv.portal.service.openstack.util;

import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import com.letv.common.exception.MatrixException;
import com.letv.portal.service.openstack.exception.OpenStackException;
import com.letv.portal.service.openstack.util.function.Function1;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;

/**
 * Created by zhouxianguang on 2015/10/30.
 */
public class ThreadUtil {
    private static final ListeningExecutorService executorService = MoreExecutors.listeningDecorator(Executors.newCachedThreadPool());

    public static void asyncExec(Runnable task) {
        executorService.submit(task);
    }

    public static void concurrentRunAndWait(Runnable currentThreadTask, Runnable... otherTasks) {
        ListenableFuture[] futures = new ListenableFuture[otherTasks.length];
        for (int i = 0; i < otherTasks.length; i++) {
            futures[i] = executorService.submit(otherTasks[i]);
        }

        currentThreadTask.run();

        try {
            Futures.successfulAsList(futures).get();
        } catch (Exception e) {
            throw new MatrixException("后台错误", e);
        }
    }

    public static void concurrentRun(Runnable... tasks) {
        for (Runnable task : tasks) {
            executorService.submit(task);
        }
    }

    public static <T> List<T> concurrentFilter(List<T> list, final Function1<T, T> filter) throws OpenStackException {
        try {
            if (list.isEmpty()) {
                return list;
            }
            if (list.size() == 1) {
                List<T> newList = new LinkedList<T>();
                T element = filter.apply(list.get(0));
                if (element != null) {
                    newList.add(element);
                }
                return newList;
            }

            List<ListenableFuture<Ref<T>>> futures = new LinkedList<ListenableFuture<Ref<T>>>();
            for (int i = 1; i < list.size(); i++) {
                final T oldElement = list.get(i);
                futures.add(executorService.submit(new Callable<Ref<T>>() {
                    @Override
                    public Ref<T> call() throws Exception {
                        return new Ref<T>(filter.apply(oldElement));
                    }
                }));
            }

            T firstNewElement = filter.apply(list.get(0));

            List<Ref<T>> otherNewElementRefs = Futures.successfulAsList(futures).get();

            List<T> newList = new LinkedList<T>();
            if (firstNewElement != null) {
                newList.add(firstNewElement);
            }
            for (Ref<T> newElementRef : otherNewElementRefs) {
                T newElement = newElementRef.get();
                if (newElement != null) {
                    newList.add(newElement);
                }
            }
            return newList;
        } catch (Exception ex){
            ExceptionUtil.throwException(ex);
        }
        return new LinkedList<T>();
    }
}
