package com.letv.lcp.cloudvm.model.event;

import java.util.EventObject;

/**
 * Created by zhouxianguang on 2015/10/16.
 */
public class RouterCreateEvent extends EventObject{

	private static final long serialVersionUID = 1L;
	
	private String region;
    private String routerId;
    private Integer routerIndex;
    private String name;
    private Object userData;

    public RouterCreateEvent(Object source, String region, String routerId, Integer routerIndex, String name, Object userData) {
        super(source);
    	this.region = region;
        this.routerId = routerId;
        this.routerIndex = routerIndex;
        this.name = name;
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

    public String getName() {
        return name;
    }

    public Object getUserData() {
        return userData;
    }
}
