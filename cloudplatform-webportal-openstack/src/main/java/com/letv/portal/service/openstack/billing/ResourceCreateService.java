package com.letv.portal.service.openstack.billing;

import com.letv.common.exception.MatrixException;

import java.util.List;

/**
 * Created by zhouxianguang on 2015/9/21.
 */
public interface ResourceCreateService {
    List<ResourceLocator> createVm(long userId, String reqParaJson) throws MatrixException;
}
