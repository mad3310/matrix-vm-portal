package com.letv.portal.service.openstack.util;

/**
 * Created by zhouxianguang on 2015/10/30.
 */
public interface Function<T> {
    T apply() throws Exception;
}
