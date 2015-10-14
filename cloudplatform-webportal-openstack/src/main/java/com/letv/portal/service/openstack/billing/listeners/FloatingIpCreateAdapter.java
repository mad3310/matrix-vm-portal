package com.letv.portal.service.openstack.billing.listeners;

/**
 * Created by zhouxianguang on 2015/10/14.
 */
public abstract class FloatingIpCreateAdapter implements FloatingIpCreateListener {
    @Override
    public void floatingIpCreated(String region, String floatingIpId, int floatingIpIndex, Object userData) throws Exception {}
}
