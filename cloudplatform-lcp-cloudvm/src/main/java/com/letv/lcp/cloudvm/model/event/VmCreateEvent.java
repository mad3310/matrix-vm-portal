package com.letv.lcp.cloudvm.model.event;

import java.util.EventObject;
import java.util.List;

import com.letv.lcp.cloudvm.model.task.VmCreateContext;

/**
 * Created by zhouxianguang on 2015/10/16.
 */
public class VmCreateEvent extends EventObject {

	private static final long serialVersionUID = 1362308281709224255L;
	private String region;
    private String vmId;
    private Integer vmIndex;
    private String name;
    private Object userData;
    private String volumeId;
    private String floatingIpId;
    private List<VmCreateContext> contexts;
    

    public VmCreateEvent(Object source, String region, String vmId, Integer vmIndex, String name, Object userData, List<VmCreateContext> contexts) {
    	super(source);
        this.region = region;
        this.vmId = vmId;
        this.vmIndex = vmIndex;
        this.name = name;
        this.userData = userData;
        this.contexts = contexts;
    }

    
    public List<VmCreateContext> getContexts() {
		return contexts;
	}

	public String getVolumeId() {
        return volumeId;
    }

    public void setVolumeId(String volumeId) {
        this.volumeId = volumeId;
    }

    public String getFloatingIpId() {
        return floatingIpId;
    }

    public void setFloatingIpId(String floatingIpId) {
        this.floatingIpId = floatingIpId;
    }

    public String getRegion() {
        return region;
    }

    public String getVmId() {
        return vmId;
    }

    public Integer getVmIndex() {
        return vmIndex;
    }

    public String getName() {
        return name;
    }

    public Object getUserData() {
        return userData;
    }
}
