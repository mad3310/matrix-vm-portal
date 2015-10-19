package com.letv.portal.service.openstack.resource.manager;

/**
 * Created by zhouxianguang on 2015/10/19.
 */
public class VmSnapshotCreateConf {
    private String region;
    private String vmId;
    private String name;

    public VmSnapshotCreateConf() {
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getVmId() {
        return vmId;
    }

    public void setVmId(String vmId) {
        this.vmId = vmId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
