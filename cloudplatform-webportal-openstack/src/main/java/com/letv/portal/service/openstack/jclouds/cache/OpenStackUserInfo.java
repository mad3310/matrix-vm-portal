package com.letv.portal.service.openstack.jclouds.cache;

/**
 * Created by zhouxianguang on 2015/11/9.
 */
public class OpenStackUserInfo {
    private String userId;
    private String password;

    public OpenStackUserInfo(String userId, String password) {
        this.userId = userId;
        this.password = password;
    }

    public String getUserId() {
        return userId;
    }

    public String getPassword() {
        return password;
    }
}