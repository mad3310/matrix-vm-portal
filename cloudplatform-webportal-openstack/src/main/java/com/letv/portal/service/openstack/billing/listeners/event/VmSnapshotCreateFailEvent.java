package com.letv.portal.service.openstack.billing.listeners.event;

/**
 * Created by zhouxianguang on 2015/10/19.
 */
public class VmSnapshotCreateFailEvent {

    private String region;
    private String reason;
    private Object userData;

    public VmSnapshotCreateFailEvent(String region, String reason, Object userData) {
        this.region = region;
        this.reason = reason;
        this.userData = userData;
    }

    public String getRegion() {
        return region;
    }

    public String getReason() {
        return reason;
    }

    public Object getUserData() {
        return userData;
    }
}
