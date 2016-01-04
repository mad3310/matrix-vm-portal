package com.letv.lcp.cloudvm.model.network;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

import com.letv.lcp.cloudvm.constants.ValidationRegex;

/**
 * Created by zhouxianguang on 2015/10/19.
 */
public class RouterCreateConf {
    private String region;
    private String name;
    private Boolean enablePublicNetworkGateway;
    private String publicNetworkId;

    public RouterCreateConf() {
    }

    @NotBlank
    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
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

    public String getPublicNetworkId() {
        return publicNetworkId;
    }

    public void setPublicNetworkId(String publicNetworkId) {
        this.publicNetworkId = publicNetworkId;
    }

	@Override
	public String toString() {
		return "RouterCreateConf [region=" + region + ", name=" + name
				+ ", enablePublicNetworkGateway=" + enablePublicNetworkGateway
				+ ", publicNetworkId=" + publicNetworkId + "]";
	}
    
}
