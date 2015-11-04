package com.letv.portal.service.openstack.billing.impl;

import com.letv.common.exception.MatrixException;
import com.letv.portal.service.openstack.billing.ResourceDeleteService;
import com.letv.portal.service.openstack.billing.ResourceLocator;
import org.springframework.stereotype.Service;

/**
 * Created by zhouxianguang on 2015/10/28.
 */
@Service
public class ResourceDeleteServiceImpl implements ResourceDeleteService {

    @Override
    public void deleteResource(long userId, Iterable<ResourceLocator> resourceLocators) throws MatrixException {

    }
}
