package com.letv.portal.service.openstack;

import com.letv.portal.service.openstack.exception.OpenStackException;
import com.letv.portal.service.openstack.impl.OpenStackUser;

/**
 * Created by zhouxianguang on 2015/6/8.
 */
public interface OpenStackService {

    OpenStackSession createSession(long userVoUserId, String userId, String email,
                                   String userName) throws OpenStackException;

    boolean isUserExists(String email, String password) throws OpenStackException;

    void registerUser(String email, String password) throws OpenStackException;

    void registerUserIfNotExists(String email, String password) throws OpenStackException;

    OpenStackUser registerAndInitUserIfNotExists(long userVoUserId, String userName, String email, String password) throws OpenStackException;

//    OpenStackSession createSessionForSync(long userVoUserId) throws OpenStackException;
}
