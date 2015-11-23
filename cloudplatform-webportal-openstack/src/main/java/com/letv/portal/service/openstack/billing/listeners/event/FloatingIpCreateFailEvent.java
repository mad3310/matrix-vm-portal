package com.letv.portal.service.openstack.billing.listeners.event;

/**
 * Created by zhouxianguang on 2015/10/19.
 */
public class FloatingIpCreateFailEvent {

    private String region;
    private Integer floatingIpIndex;
    private String reason;
    private Object userData;

    public FloatingIpCreateFailEvent(String region, Integer floatingIpIndex, String reason, Object userData) {
        this.region = region;
        this.floatingIpIndex = floatingIpIndex;
        this.reason = reason;
        this.userData = userData;
    }

    public String getRegion() {
        return region;
    }

    public Integer getFloatingIpIndex() {
        return floatingIpIndex;
    }

    public String getReason() {
        return reason;
    }

    public Object getUserData() {
        return userData;
    }
}
