package com.letv.portal.service.openstack.billing.listeners.event;

/**
 * Created by zhouxianguang on 2015/10/16.
 */
public class VmCreateEvent {

    private String region;
    private String vmId;
    private Integer vmIndex;
    private Object userData;
    private String volumeId;
    private String floatingIpId;

    public VmCreateEvent(String region, String vmId, Integer vmIndex, Object userData) {
        this.region = region;
        this.vmId = vmId;
        this.vmIndex = vmIndex;
        this.userData = userData;
    }

    public String getVolumeId() {
        return volumeId;
    }

    public void setVolumeId(String volumeId) {
        this.volumeId = volumeId;
    }

    public String getFloatingIpId() {
        return floatingIpId;
    }

    public void setFloatingIpId(String floatingIpId) {
        this.floatingIpId = floatingIpId;
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
