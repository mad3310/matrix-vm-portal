package com.letv.portal.service.openstack.util.function;

/**
 * Created by zhouxianguang on 2015/10/30.
 */
public interface Function1<T, T1> {
    T apply(T1 p1) throws Exception;
}
