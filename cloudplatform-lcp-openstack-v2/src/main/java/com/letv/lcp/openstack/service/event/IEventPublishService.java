package com.letv.lcp.openstack.service.event;

import com.letv.lcp.openstack.model.billing.ResourceLocator;

/**
 * Created by zhouxianguang on 2015/11/6.
 */
public interface IEventPublishService {
    void onDelete(ResourceLocator locator);
}
