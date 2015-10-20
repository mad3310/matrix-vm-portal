package com.letv.portal.service.openstack.billing.listeners.event;

/**
 * Created by zhouxianguang on 2015/10/16.
 */
public class VolumeCreateFailEvent {

    private String region;
    private Integer volumeIndex;
    private String reason;
    private Object userData;

    public VolumeCreateFailEvent(String region, Integer volumeIndex, String reason, Object userData) {
        this.region = region;
        this.volumeIndex = volumeIndex;
        this.reason = reason;
        this.userData = userData;
    }

    public String getRegion() {
        return region;
    }

    public String getReason() {
        return reason;
    }

    public Integer getVolumeIndex() {
        return volumeIndex;
    }

    public Object getUserData() {
        return userData;
    }
}
