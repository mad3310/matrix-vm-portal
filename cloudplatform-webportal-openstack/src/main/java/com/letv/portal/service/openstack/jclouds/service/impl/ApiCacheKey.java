package com.letv.portal.service.openstack.jclouds.service.impl;

import com.google.common.base.Objects;

import java.io.Closeable;

/**
 * Created by zhouxianguang on 2015/10/8.
 */
public class ApiCacheKey {
    private long userId;
    private String sessionId;
    private Class<? extends Closeable> apiType;

    public ApiCacheKey() {
    }

    public ApiCacheKey(long userId, String sessionId, Class<? extends Closeable> apiType) {
        this.userId = userId;
        this.sessionId = sessionId;
        this.apiType = apiType;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
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
        ApiCacheKey that = (ApiCacheKey) o;
        return Objects.equal(userId, that.userId) &&
                Objects.equal(sessionId, that.sessionId) &&
                Objects.equal(apiType, that.apiType);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(userId, sessionId, apiType);
    }

    @Override
    public String toString() {
        return "ApiCacheKey{" +
                "userId=" + userId +
                ", sessionId='" + sessionId + '\'' +
                ", apiType=" + apiType +
                '}';
    }
}
