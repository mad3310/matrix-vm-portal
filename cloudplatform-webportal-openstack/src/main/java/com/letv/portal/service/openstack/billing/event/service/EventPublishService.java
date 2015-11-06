package com.letv.portal.service.openstack.billing.event.service;

import com.letv.portal.service.openstack.billing.ResourceLocator;

/**
 * Created by zhouxianguang on 2015/11/6.
 */
public interface EventPublishService {
    void onDelete(ResourceLocator locator);
}
