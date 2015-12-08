package com.letv.portal.service.openstack;

import com.letv.portal.service.openstack.exception.OpenStackException;
import com.letv.portal.service.openstack.impl.OpenStackUser;
import org.jclouds.openstack.neutron.v2.NeutronApi;

/**
 * Created by zhouxianguang on 2015/6/8.
 */
public interface OpenStackService {

    OpenStackSession createSession(long userVoUserId, String email,
                                   String userName);

    String getOpenStackTenantNameFromMatrixUser(long userVoUserId, String email);

    boolean isUserExists(String tenantName, String password) throws OpenStackException;

    void registerUser(String tenantName, String password, String email) throws OpenStackException;

    void registerUserIfNotExists(String tenantName, String password, String email) throws OpenStackException;

    void registerAndInitUserIfNotExists(String tenantName, String password, String email) throws OpenStackException;

//    OpenStackSession createSessionForSync(long userVoUserId) throws OpenStackException;
}
