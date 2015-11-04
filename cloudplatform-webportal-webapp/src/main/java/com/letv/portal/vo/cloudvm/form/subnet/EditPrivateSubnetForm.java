package com.letv.portal.vo.cloudvm.form.subnet;

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
    @Pattern(regexp = "^[a-zA-Zu4e00-u9fa5][^@/:=\\\\\"<>\\{\\[\\]\\}\\s]{2,128}$", message = "名称须为2-128个字符，以大小写字母或中文开头，不支持字符@/:=\\\"<>{[]}和空格")
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
