package com.letv.portal.service.openstack.billing;

import com.letv.common.exception.MatrixException;
import com.letv.portal.service.openstack.resource.FlavorResource;
import com.letv.portal.service.openstack.resource.VolumeTypeResource;

/**
 * Created by zhouxianguang on 2015/10/28.
 */
public interface ResourceQueryService {
    FlavorResource getFlavor(long userId, String region, String flavorId) throws MatrixException;
    VolumeTypeResource getVolumeType(long userId, String region, String volumeTypeId) throws MatrixException;
}
