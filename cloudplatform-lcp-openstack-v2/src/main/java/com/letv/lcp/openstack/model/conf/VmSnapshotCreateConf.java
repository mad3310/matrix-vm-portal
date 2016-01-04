package com.letv.lcp.openstack.model.conf;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

import com.letv.lcp.openstack.constants.ValidationRegex;

/**
 * Created by zhouxianguang on 2015/10/19.
 */
public class VmSnapshotCreateConf {
    private String region;
    private String vmId;
    private String name;

    public VmSnapshotCreateConf() {
    }

    @NotBlank
    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    @NotBlank
    public String getVmId() {
        return vmId;
    }

    public void setVmId(String vmId) {
        this.vmId = vmId;
    }

    @Pattern(regexp = ValidationRegex.name, message = ValidationRegex.nameMessage)
    @NotBlank
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
