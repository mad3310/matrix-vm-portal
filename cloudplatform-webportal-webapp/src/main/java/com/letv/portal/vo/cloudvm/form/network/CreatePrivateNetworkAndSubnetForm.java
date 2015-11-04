package com.letv.portal.vo.cloudvm.form.network;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * Created by zhouxianguang on 2015/11/4.
 */
public class CreatePrivateNetworkAndSubnetForm {
    private String region;
    private String networkName;
    private String subnetName;
    private String cidr;
    private Boolean autoGatewayIp;
    private String gatewayIp;

    @NotBlank
    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    @NotBlank
    @Pattern(regexp = "^[a-zA-Zu4e00-u9fa5][^@/:=\\\\\"<>\\{\\[\\]\\}\\s]{2,128}$", message = "名称须为2-128个字符，以大小写字母或中文开头，不支持字符@/:=\\\"<>{[]}和空格")
    public String getNetworkName() {
        return networkName;
    }

    public void setNetworkName(String networkName) {
        this.networkName = networkName;
    }

    @NotBlank
    @Pattern(regexp = "^[a-zA-Zu4e00-u9fa5][^@/:=\\\\\"<>\\{\\[\\]\\}\\s]{2,128}$", message = "名称须为2-128个字符，以大小写字母或中文开头，不支持字符@/:=\\\"<>{[]}和空格")
    public String getSubnetName() {
        return subnetName;
    }

    public void setSubnetName(String subnetName) {
        this.subnetName = subnetName;
    }

    @NotBlank
    public String getCidr() {
        return cidr;
    }

    public void setCidr(String cidr) {
        this.cidr = cidr;
    }

    @NotNull
    public Boolean getAutoGatewayIp() {
        return autoGatewayIp;
    }

    public void setAutoGatewayIp(Boolean autoGatewayIp) {
        this.autoGatewayIp = autoGatewayIp;
    }

    @NotNull
    public String getGatewayIp() {
        return gatewayIp;
    }

    public void setGatewayIp(String gatewayIp) {
        this.gatewayIp = gatewayIp;
    }
}
