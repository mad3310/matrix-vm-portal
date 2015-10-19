package com.letv.portal.service.openstack.resource.manager;

/**
 * Created by zhouxianguang on 2015/10/19.
 */
public class FloatingIpCreateConf {
    private String region;
    private String name;
    private String publicNetworkId;
    private Integer bandWidth;
    private Integer count;

    public FloatingIpCreateConf() {
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

    public String getPublicNetworkId() {
        return publicNetworkId;
    }

    public void setPublicNetworkId(String publicNetworkId) {
        this.publicNetworkId = publicNetworkId;
    }

    public Integer getBandWidth() {
        return bandWidth;
    }

    public void setBandWidth(Integer bandWidth) {
        this.bandWidth = bandWidth;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
