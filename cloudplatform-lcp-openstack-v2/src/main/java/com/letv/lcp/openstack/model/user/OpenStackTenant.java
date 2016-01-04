package com.letv.lcp.openstack.model.user;

import java.io.Serializable;

import com.google.common.base.Objects;
import com.letv.lcp.openstack.service.base.OpenStackServiceGroup;
import com.letv.lcp.openstack.service.base.impl.OpenStackServiceImpl;

/**
 * Created by zhouxianguang on 2015/12/8.
 */
public class OpenStackTenant implements Serializable {

    private static final long serialVersionUID = -5937930998423002260L;

    public final String email;
    public final long userId;
    public final String tenantName;
    public final String password;
    public final String jcloudsCredentialsIdentity;

//    public OpenStackTenant(String tenantName) {
//        this(tenantName, null, null);
//    }
//
//    public OpenStackTenant(String tenantName, Long userId, String email) {
//        this.tenantName = tenantName;
//        this.userId = userId;
//        this.email = email;
//        OpenStackServiceGroup openStackServiceGroup = OpenStackServiceImpl.getOpenStackServiceGroup();
//        this.password = openStackServiceGroup.getPasswordService().userIdToPassword(tenantName);
//        this.jcloudsCredentialsIdentity = OpenStackServiceImpl.createCredentialsIdentity(tenantName);
//    }

    public OpenStackTenant(long userId, String email) {
        this.userId = userId;
        this.email = email;
        OpenStackServiceGroup openStackServiceGroup = OpenStackServiceImpl.getOpenStackServiceGroup();
        this.tenantName = openStackServiceGroup.getOpenStackService().getOpenStackTenantNameFromMatrixUser(userId, email);
        this.password = openStackServiceGroup.getPasswordService().userIdToPassword(tenantName);
        this.jcloudsCredentialsIdentity = OpenStackServiceImpl.createCredentialsIdentity(tenantName);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OpenStackTenant that = (OpenStackTenant) o;
        return Objects.equal(tenantName, that.tenantName);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(tenantName);
    }
}
