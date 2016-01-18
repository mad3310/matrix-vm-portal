package com.letv.portal.vo.cloudvm.form.router;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

import com.letv.lcp.cloudvm.constants.ValidationRegex;

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
    @Pattern(regexp = ValidationRegex.name, message = ValidationRegex.nameMessage)
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
