package com.letv.lcp.cloudvm.model.event;

/**
 * Created by zhouxianguang on 2015/10/19.
 */
public class RouterCreateFailEvent {

    private String region;
    private Integer routerIndex;
    private String reason;
    private Object userData;

    public RouterCreateFailEvent(String region, Integer routerIndex, String reason, Object userData) {
        this.region = region;
        this.routerIndex = routerIndex;
        this.reason = reason;
        this.userData = userData;
    }

    public String getRegion() {
        return region;
    }

    public Integer getRouterIndex() {
        return routerIndex;
    }

    public String getReason() {
        return reason;
    }

    public Object getUserData() {
        return userData;
    }
}
