package com.letv.portal.service.openstack.billing;

import com.letv.common.exception.MatrixException;
import com.letv.portal.service.openstack.resource.FlavorResource;

import java.util.List;

/**
 * Created by zhouxianguang on 2015/9/21.
 */
public interface ResourceCreateService {
    List<ResourceLocator> createVm(long userId, String reqParaJson) throws MatrixException;

    FlavorResource getFlavor(long userId, String region, String flavorId) throws MatrixException;
}
