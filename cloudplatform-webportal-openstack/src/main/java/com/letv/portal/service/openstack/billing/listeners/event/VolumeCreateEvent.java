package com.letv.portal.service.openstack.billing.listeners.event;

/**
 * Created by zhouxianguang on 2015/10/16.
 */
public class VolumeCreateEvent {

    private String region;
    private String volumeId;
    private Integer volumeIndex;
    private Object userData;

    public VolumeCreateEvent(String region, String volumeId, Integer volumeIndex, Object userData) {
        this.region = region;
        this.volumeId = volumeId;
        this.volumeIndex = volumeIndex;
        this.userData = userData;
    }

    public String getRegion() {
        return region;
    }

    public String getVolumeId() {
        return volumeId;
    }

    public Integer getVolumeIndex() {
        return volumeIndex;
    }

    public Object getUserData() {
        return userData;
    }
}
