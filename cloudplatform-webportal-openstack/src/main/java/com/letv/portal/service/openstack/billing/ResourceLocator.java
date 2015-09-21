package com.letv.portal.service.openstack.billing;

/**
 * Created by zhouxianguang on 2015/9/21.
 */
public class ResourceLocator {
    private String region;
    private String id;

    public ResourceLocator() {
    }

    public ResourceLocator(String region, String id) {
        this.region = region;
        this.id = id;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
