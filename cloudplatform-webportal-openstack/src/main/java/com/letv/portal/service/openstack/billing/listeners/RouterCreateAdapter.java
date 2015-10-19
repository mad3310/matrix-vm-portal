package com.letv.portal.service.openstack.billing.listeners;

import com.letv.portal.service.openstack.billing.listeners.event.RouterCreateEvent;

/**
 * Created by zhouxianguang on 2015/10/14.
 */
public abstract class RouterCreateAdapter implements RouterCreateListener {
    @Override
    public void routerCreated(String region, String routerId, int routerIndex, Object userData) throws Exception {}

    @Override
    public void routerCreated(RouterCreateEvent event) throws Exception{}
}
