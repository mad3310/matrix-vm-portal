package com.letv.portal.service.openstack.billing.listeners;

/**
 * Created by zhouxianguang on 2015/10/14.
 */
public abstract class RouterCreateAdapter implements RouterCreateListener {
    @Override
    public void routerCreated(String region, String routerId, int routerIndex, Object userData) throws Exception {}
}
