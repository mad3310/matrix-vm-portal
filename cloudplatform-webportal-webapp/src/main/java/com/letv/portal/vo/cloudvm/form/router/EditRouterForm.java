package com.letv.portal.vo.cloudvm.form.router;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * Created by zhouxianguang on 2015/11/4.
 */
public class EditRouterForm {
    private String region;
    private String routerId;
    private String name;
    private Boolean enablePublicNetworkGateway;
    private String publicNetworkId;

    @NotBlank
    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    @NotBlank
    public String getRouterId() {
        return routerId;
    }

    public void setRouterId(String routerId) {
        this.routerId = routerId;
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
    public Boolean getEnablePublicNetworkGateway() {
        return enablePublicNetworkGateway;
    }

    public void setEnablePublicNetworkGateway(Boolean enablePublicNetworkGateway) {
        this.enablePublicNetworkGateway = enablePublicNetworkGateway;
    }

    @NotNull
    public String getPublicNetworkId() {
        return publicNetworkId;
    }

    public void setPublicNetworkId(String publicNetworkId) {
        this.publicNetworkId = publicNetworkId;
    }
}
