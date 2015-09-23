package com.letv.portal.service.openstack.billing;

import com.letv.common.exception.MatrixException;
import com.letv.portal.service.openstack.resource.FlavorResource;

/**
 * Created by zhouxianguang on 2015/9/21.
 */
public interface ResourceCreateService {
    void createVm(long userId, String reqParaJson, VmCreateListener listener, Object listenerUserData) throws MatrixException;

    FlavorResource getFlavor(long userId, String region, String flavorId) throws MatrixException;
}
