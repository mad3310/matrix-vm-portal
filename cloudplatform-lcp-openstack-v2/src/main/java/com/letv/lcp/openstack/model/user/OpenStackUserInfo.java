package com.letv.lcp.openstack.model.user;

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
