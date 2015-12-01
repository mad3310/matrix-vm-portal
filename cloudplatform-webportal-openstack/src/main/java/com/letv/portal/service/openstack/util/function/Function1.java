package com.letv.portal.service.openstack.util.function;

/**
 * Created by zhouxianguang on 2015/10/30.
 */
public interface Function1<T1, R> {
    R apply(T1 v1) throws Exception;
}
