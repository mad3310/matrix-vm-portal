package com.letv.portal.service.openstack.billing.listeners.event;

/**
 * Created by zhouxianguang on 2015/10/16.
 */
public class FloatingIpCreateEvent {

    private String region;
    private String floatingIpId;
    private Integer floatingIpIndex;
    private String name;
    private Object userData;

    public FloatingIpCreateEvent(String region, String floatingIpId, Integer floatingIpIndex, String name, Object userData) {
        this.region = region;
        this.floatingIpId = floatingIpId;
        this.floatingIpIndex = floatingIpIndex;
        this.name = name;
        this.userData = userData;
    }

    public String getRegion() {
        return region;
    }

    public String getFloatingIpId() {
        return floatingIpId;
    }

    public Integer getFloatingIpIndex() {
        return floatingIpIndex;
    }

    public String getName() {
        return name;
    }

    public Object getUserData() {
        return userData;
    }
}
