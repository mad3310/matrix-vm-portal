package com.letv.portal.service.openstack.billing.listeners;

import com.letv.portal.service.openstack.billing.listeners.event.VmCreateEvent;
import com.letv.portal.service.openstack.billing.listeners.event.VmCreateFailEvent;

/**
 * Created by zhouxianguang on 2015/9/22.
 */
public interface VmCreateListener {
    void vmCreated(VmCreateEvent event) throws Exception;
    void vmCreateFailed(VmCreateFailEvent event) throws Exception;
}
