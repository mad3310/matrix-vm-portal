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

    public String email;
    public final long userId;//lcp用户id(使用表WEBPORTAL_CLOUDVM_REGION中id)
    public final String tenantName;//对应openstack中的用户名
    public String projectName;//对应openstack中的租户
    public final String password;//对应openstack中的密码
    public final String jcloudsCredentialsIdentity;
    public String openStackTenantId;//openstack用户id


    public OpenStackTenant(long userId, String email) {
        this.userId = userId;
        this.email = email;
        OpenStackServiceGroup openStackServiceGroup = OpenStackServiceImpl.getOpenStackServiceGroup();
        this.tenantName = openStackServiceGroup.getOpenStackService().getOpenStackTenantNameFromMatrixUser(userId, email);
        this.password = openStackServiceGroup.getPasswordService().userIdToPassword(tenantName);
        this.jcloudsCredentialsIdentity = OpenStackServiceImpl.createCredentialsIdentity(tenantName);
    }
    
    public OpenStackTenant(long userId, String tenantName, String password, String projectName) {
    	this.userId = userId;
        this.tenantName = tenantName;
        this.password = password;
        this.projectName = projectName;
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
