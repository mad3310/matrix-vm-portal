package com.letv.lcp.openstack.service.billing;

import com.letv.common.exception.MatrixException;
import com.letv.lcp.openstack.model.billing.ResourceLocator;

/**
 * Created by zhouxianguang on 2015/10/28.
 */
public interface IResourceDeleteService {
    void deleteResource(long userId, Iterable<ResourceLocator> resourceLocators) throws MatrixException;
}
