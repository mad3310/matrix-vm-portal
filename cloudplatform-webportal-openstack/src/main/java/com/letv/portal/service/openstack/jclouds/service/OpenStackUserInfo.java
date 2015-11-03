package com.letv.portal.service.openstack.jclouds.service;

/**
 * Created by zhouxianguang on 2015/11/3.
 */
public class OpenStackUserInfo {
    private Long userId;
    private String sessionId;
    private String email;
    private String password;

    public OpenStackUserInfo(Long userId, String sessionId, String email, String password) {
        this.userId = userId;
        this.sessionId = sessionId;
        this.email = email;
        this.password = password;
    }

    public Long getUserId() {
        return userId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
