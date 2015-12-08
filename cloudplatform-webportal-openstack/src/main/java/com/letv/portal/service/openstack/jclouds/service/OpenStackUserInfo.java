package com.letv.portal.service.openstack.jclouds.service;

import com.letv.portal.service.openstack.OpenStackTenant;

/**
 * Created by zhouxianguang on 2015/11/3.
 */
public class OpenStackUserInfo {
    public final OpenStackTenant tenant;
    public final String sessionId;

    public OpenStackUserInfo(OpenStackTenant tenant, String sessionId) {
        this.tenant = tenant;
        this.sessionId = sessionId;
    }
}
