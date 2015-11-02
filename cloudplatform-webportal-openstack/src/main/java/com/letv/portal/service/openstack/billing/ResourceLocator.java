package com.letv.portal.service.openstack.billing;

import com.google.common.base.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResourceLocator that = (ResourceLocator) o;
        return Objects.equal(region, that.region) &&
                Objects.equal(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(region, id);
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("region", region)
                .add("id", id)
                .toString();
    }
}
