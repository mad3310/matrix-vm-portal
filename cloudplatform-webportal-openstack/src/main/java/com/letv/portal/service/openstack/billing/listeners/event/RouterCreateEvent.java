package com.letv.portal.service.openstack.billing.listeners.event;

/**
 * Created by zhouxianguang on 2015/10/16.
 */
public class RouterCreateEvent {

    private String region;
    private String routerId;
    private Integer routerIndex;
    private Object userData;

    public RouterCreateEvent(String region, String routerId, Integer routerIndex, Object userData) {
        this.region = region;
        this.routerId = routerId;
        this.routerIndex = routerIndex;
        this.userData = userData;
    }

    public String getRegion() {
        return region;
    }

    public String getRouterId() {
        return routerId;
    }

    public Integer getRouterIndex() {
        return routerIndex;
    }

    public Object getUserData() {
        return userData;
    }
}
