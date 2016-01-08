package com.letv.lcp.cloudvm.model.event;

/**
 * Created by zhouxianguang on 2015/10/19.
 */
public class VmCreateFailEvent {

    private String region;
    private Integer vmIndex;
    private String reason;
    private Object userData;

    public VmCreateFailEvent(String region, Integer vmIndex, String reason, Object userData) {
        this.region = region;
        this.vmIndex = vmIndex;
        this.reason = reason;
        this.userData = userData;
    }

    public String getRegion() {
        return region;
    }

    public Integer getVmIndex() {
        return vmIndex;
    }

    public Object getUserData() {
        return userData;
    }

    public String getReason() {
        return reason;
    }
}
