package com.letv.portal.service.openstack.billing.listeners.event;

/**
 * Created by zhouxianguang on 2015/10/19.
 */
public class VmSnapshotCreateEvent {

    private String region;
    private String vmSnapshotId;
    private Object userData;

    public VmSnapshotCreateEvent(String region, String vmSnapshotId, Object userData) {
        this.region = region;
        this.vmSnapshotId = vmSnapshotId;
        this.userData = userData;
    }

    public String getRegion() {
        return region;
    }

    public String getVmSnapshotId() {
        return vmSnapshotId;
    }

    public Object getUserData() {
        return userData;
    }
}
