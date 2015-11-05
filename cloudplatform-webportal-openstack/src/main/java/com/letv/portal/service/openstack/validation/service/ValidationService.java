package com.letv.portal.service.openstack.validation.service;

import com.letv.portal.service.openstack.exception.OpenStackException;

/**
 * Created by zhouxianguang on 2015/11/5.
 */
public interface ValidationService {
    <T> void validate(T var1, Class... var2) throws OpenStackException;
}
