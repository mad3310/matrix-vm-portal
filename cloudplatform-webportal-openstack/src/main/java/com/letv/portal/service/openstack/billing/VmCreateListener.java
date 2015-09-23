package com.letv.portal.service.openstack.billing;

/**
 * Created by zhouxianguang on 2015/9/22.
 */
public interface VmCreateListener {
    void vmCreated(String region, String vmId, int vmIndex, Object userData);
}
