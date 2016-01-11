package com.letv.lcp.cloudvm.model.event;

import java.util.EventObject;

/**
 * Created by zhouxianguang on 2015/10/16.
 */
public class FloatingIpCreateEvent extends EventObject{
	private static final long serialVersionUID = 1L;
	
	private String region;
    private String floatingIpId;
    private Integer floatingIpIndex;
    private String name;
    private Object userData;

    public FloatingIpCreateEvent(Object source, String region, String floatingIpId, Integer floatingIpIndex, String name, Object userData) {
        super(source);
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
