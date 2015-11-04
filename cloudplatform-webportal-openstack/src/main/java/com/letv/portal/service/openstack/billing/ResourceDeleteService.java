package com.letv.portal.service.openstack.billing;

import com.letv.common.exception.MatrixException;
import com.letv.portal.service.openstack.resource.FlavorResource;
import com.letv.portal.service.openstack.resource.VolumeTypeResource;

import java.util.Map;

/**
 * Created by zhouxianguang on 2015/10/28.
 */
public interface ResourceDeleteService {
    void deleteResource(long userId, Iterable<ResourceLocator> resourceLocators) throws MatrixException;
}
