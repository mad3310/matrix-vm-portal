package com.letv.portal.service.openstack.billing.listeners;

import com.letv.portal.service.openstack.billing.listeners.event.FloatingIpCreateEvent;
import com.letv.portal.service.openstack.billing.listeners.event.FloatingIpCreateFailEvent;

/**
 * Created by zhouxianguang on 2015/10/14.
 */
public abstract class FloatingIpCreateAdapter implements FloatingIpCreateListener {
    @Override
    public void floatingIpCreated(String region, String floatingIpId, int floatingIpIndex, Object userData) throws Exception {}

    @Override
    public void floatingIpCreated(FloatingIpCreateEvent event) throws Exception {}

    @Override
    public void floatingIpCreateFailed(FloatingIpCreateFailEvent event) throws Exception {}
}
