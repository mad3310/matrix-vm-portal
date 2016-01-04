package com.letv.lcp.openstack.service.base;

import com.letv.lcp.openstack.exception.OpenStackException;
import com.letv.lcp.openstack.service.session.IOpenStackSession;


/**
 * Created by zhouxianguang on 2015/6/8.
 */
public interface IOpenStackService {

    IOpenStackSession createSession(long userVoUserId, String email,
                                   String userName);
    IOpenStackSession createSession(long userId);

    String getOpenStackTenantNameFromMatrixUser(long userVoUserId, String email);

    boolean isUserExists(String tenantName, String password) throws OpenStackException;

    void registerUser(String tenantName, String password, String email) throws OpenStackException;

    void registerUserIfNotExists(String tenantName, String password, String email) throws OpenStackException;

    void registerAndInitUserIfNotExists(String tenantName, String password, String email) throws OpenStackException;

//    OpenStackSession createSessionForSync(long userVoUserId) throws OpenStackException;
}
