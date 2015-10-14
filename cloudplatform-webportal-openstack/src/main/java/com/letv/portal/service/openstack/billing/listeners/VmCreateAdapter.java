package com.letv.portal.service.openstack.billing.listeners;

/**
 * Created by zhouxianguang on 2015/10/14.
 */
public abstract class VmCreateAdapter implements VmCreateListener {
    @Override
    public void vmCreated(String region, String vmId, int vmIndex, Object userData) throws Exception {}
}
