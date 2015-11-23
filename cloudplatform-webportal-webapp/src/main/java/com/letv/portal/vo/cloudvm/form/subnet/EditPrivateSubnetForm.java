package com.letv.portal.vo.cloudvm.form.subnet;

import com.letv.portal.service.openstack.util.constants.ValidationRegex;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * Created by zhouxianguang on 2015/11/4.
 */
public class EditPrivateSubnetForm {
    private String region;
    private String subnetId;
    private String name;
    private String gatewayIp;

    @NotBlank
    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    @NotBlank
    public String getSubnetId() {
        return subnetId;
    }

    public void setSubnetId(String subnetId) {
        this.subnetId = subnetId;
    }

    @NotBlank
    @Pattern(regexp = ValidationRegex.name, message = ValidationRegex.nameMessage)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @NotNull
    public String getGatewayIp() {
        return gatewayIp;
    }

    public void setGatewayIp(String gatewayIp) {
        this.gatewayIp = gatewayIp;
    }
}
