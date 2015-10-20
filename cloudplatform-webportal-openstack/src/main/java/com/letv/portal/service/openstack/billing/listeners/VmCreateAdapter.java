package com.letv.portal.service.openstack.billing.listeners;

import com.letv.portal.service.openstack.billing.listeners.event.VmCreateEvent;
import com.letv.portal.service.openstack.billing.listeners.event.VmCreateFailEvent;

/**
 * Created by zhouxianguang on 2015/10/14.
 */
public abstract class VmCreateAdapter implements VmCreateListener {
    @Override
    public void vmCreated(VmCreateEvent event) throws Exception {}

    @Override
    public void vmCreateFailed(VmCreateFailEvent event) throws Exception {}
}
