package com.letv.portal.service.openstack.billing.listeners.event;

/**
 * Created by zhouxianguang on 2015/10/16.
 */
public class VmCreateEvent {

    private String region;
    private String vmId;
    private Integer vmIndex;
    private Object userData;

    public VmCreateEvent(String region, String vmId, Integer vmIndex, Object userData) {
        this.region = region;
        this.vmId = vmId;
        this.vmIndex = vmIndex;
        this.userData = userData;
    }

    public String getRegion() {
        return region;
    }

    public String getVmId() {
        return vmId;
    }

    public Integer getVmIndex() {
        return vmIndex;
    }

    public Object getUserData() {
        return userData;
    }
}
