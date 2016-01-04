package com.letv.lcp.openstack.service.validation;

import com.letv.lcp.openstack.exception.OpenStackException;

/**
 * Created by zhouxianguang on 2015/11/5.
 */
public interface IValidationService {
    <T> void validate(T var1, Class... var2) throws OpenStackException;
}
