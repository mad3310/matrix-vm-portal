package com.letv.portal.service.openstack.resource.manager;

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

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

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
}
