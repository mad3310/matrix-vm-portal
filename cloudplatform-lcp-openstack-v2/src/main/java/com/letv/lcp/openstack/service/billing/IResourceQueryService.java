package com.letv.lcp.openstack.service.billing;

import java.util.Map;

import com.letv.common.exception.MatrixException;
import com.letv.lcp.openstack.model.billing.BillingResource;
import com.letv.lcp.openstack.model.billing.ResourceLocator;
import com.letv.lcp.openstack.model.compute.FlavorResource;
import com.letv.lcp.openstack.model.storage.VolumeTypeResource;

/**
 * Created by zhouxianguang on 2015/10/28.
 */
public interface IResourceQueryService {
    FlavorResource getFlavor(long userId, String region, String flavorId) throws MatrixException;

    VolumeTypeResource getVolumeType(long userId, String region, String volumeTypeId) throws MatrixException;

    Map<ResourceLocator, BillingResource> getResources(long userId, Iterable<ResourceLocator> resourceLocators) throws MatrixException;
}
