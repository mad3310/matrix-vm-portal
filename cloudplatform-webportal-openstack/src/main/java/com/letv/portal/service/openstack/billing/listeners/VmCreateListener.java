package com.letv.portal.service.openstack.billing.listeners;

import com.letv.portal.service.openstack.billing.listeners.event.VmCreateEvent;

/**
 * Created by zhouxianguang on 2015/9/22.
 */
public interface VmCreateListener {
    void vmCreated(VmCreateEvent event) throws Exception;
}
