package com.letv.portal.service.openstack.cronjobs.impl.cache;

import com.google.common.base.Objects;

import java.io.Closeable;

/**
 * Created by zhouxianguang on 2015/10/8.
 */
public class SyncLocalApiCacheKey {
    private long userId;
    private Class<? extends Closeable> apiType;

    public SyncLocalApiCacheKey() {
    }

    public SyncLocalApiCacheKey(long userId, Class<? extends Closeable> apiType) {
        this.userId = userId;
        this.apiType = apiType;
    }

    public Class<? extends Closeable> getApiType() {
        return apiType;
    }

    public void setApiType(Class<? extends Closeable> apiType) {
        this.apiType = apiType;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SyncLocalApiCacheKey that = (SyncLocalApiCacheKey) o;
        return Objects.equal(userId, that.userId) &&
                Objects.equal(apiType, that.apiType);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(userId, apiType);
    }

    @Override
    public String toString() {
        return "SyncLocalApiCacheKey{" +
                "userId=" + userId +
                ", apiType=" + apiType +
                '}';
    }
}
