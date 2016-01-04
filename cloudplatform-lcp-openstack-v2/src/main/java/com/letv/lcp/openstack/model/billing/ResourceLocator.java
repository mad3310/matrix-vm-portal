package com.letv.lcp.openstack.model.billing;

import com.google.common.base.Objects;

/**
 * Created by zhouxianguang on 2015/9/21.
 */
public class ResourceLocator {
    private String region;
    private String id;
    private Class<? extends BillingResource> type;

    public ResourceLocator() {
    }

    public ResourceLocator(String region, String id) {
        this.region = region;
        this.id = id;
    }

    public ResourceLocator(String region, String id, Class<? extends BillingResource> type) {
        this.region = region;
        this.id = id;
        this.type = type;
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

    public Class<? extends BillingResource> getType() {
        return type;
    }

    public void setType(Class<? extends BillingResource> type) {
        this.type = type;
    }

    public String region() {
        return this.region;
    }

    public String id() {
        return this.id;
    }

    public ResourceLocator region(final String region) {
        this.region = region;
        return this;
    }

    public ResourceLocator id(final String id) {
        this.id = id;
        return this;
    }

    public Class<? extends BillingResource> type() {
        return this.type;
    }

    public ResourceLocator type(final Class<? extends BillingResource> type) {
        this.type = type;
        return this;
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
                .add("type", type)
                .toString();
    }
}
