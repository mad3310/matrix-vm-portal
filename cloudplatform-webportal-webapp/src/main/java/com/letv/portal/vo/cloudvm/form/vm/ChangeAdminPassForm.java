package com.letv.portal.vo.cloudvm.form.vm;

import com.letv.portal.service.openstack.util.constants.ValidationRegex;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * Created by zhouxianguang on 2015/11/4.
 */
public class ChangeAdminPassForm {
    private String region;
    private String vmId;
    private String adminPass;

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

    @Pattern(regexp = ValidationRegex.password, message = ValidationRegex.passwordMessage)
    @NotBlank
    public String getAdminPass() {
        return adminPass;
    }

    public void setAdminPass(String adminPass) {
        this.adminPass = adminPass;
    }
}
