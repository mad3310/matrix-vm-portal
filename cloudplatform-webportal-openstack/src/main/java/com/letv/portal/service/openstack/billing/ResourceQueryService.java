package com.letv.portal.service.openstack.billing;

import com.letv.common.exception.MatrixException;
import com.letv.portal.service.openstack.resource.*;

import java.util.List;
import java.util.Map;

/**
 * Created by zhouxianguang on 2015/10/28.
 */
public interface ResourceQueryService {
    FlavorResource getFlavor(long userId, String region, String flavorId) throws MatrixException;

    VolumeTypeResource getVolumeType(long userId, String region, String volumeTypeId) throws MatrixException;

    Map<ResourceLocator, BillingResource> getResources(long userId, Iterable<ResourceLocator> resourceLocators) throws MatrixException;
}
