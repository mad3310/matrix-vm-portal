package com.letv.lcp.cloudvm.model.event;

import java.util.EventObject;
import java.util.List;

import com.letv.lcp.cloudvm.model.task.VmCreateContext;

/**
 * Created by zhouxianguang on 2015/10/16.
 */
public class VolumeCreateEvent extends EventObject {
	private static final long serialVersionUID = 1L;
	
	private String region;
    private String volumeId;
    private Integer volumeIndex;
    private String name;
    private Object userData;
    private List<VmCreateContext> contexts;

    public VolumeCreateEvent(Object source, String region, String volumeId, Integer volumeIndex, String name, 
    		Object userData, List<VmCreateContext> contexts) {
        super(source);
    	this.region = region;
        this.volumeId = volumeId;
        this.volumeIndex = volumeIndex;
        this.name = name;
        this.userData = userData;
        this.contexts = contexts;
    }

    public List<VmCreateContext> getContexts() {
		return contexts;
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

    public String getName() {
        return name;
    }

    public Object getUserData() {
        return userData;
    }
}
