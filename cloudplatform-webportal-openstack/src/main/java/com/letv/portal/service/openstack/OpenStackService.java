package com.letv.portal.service.openstack;

import com.letv.portal.service.openstack.exception.OpenStackException;

/**
 * Created by zhouxianguang on 2015/6/8.
 */
public interface OpenStackService {

    OpenStackSession createSession(long userVoUserId, String userId, String email,
                                   String userName) throws OpenStackException;

//    OpenStackSession createSessionForSync(long userVoUserId) throws OpenStackException;
}
