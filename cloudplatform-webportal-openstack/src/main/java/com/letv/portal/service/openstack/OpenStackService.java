package com.letv.portal.service.openstack;

import com.letv.portal.service.openstack.exception.OpenStackException;

/**
 * Created by zhouxianguang on 2015/6/8.
 */
public interface OpenStackService {

    OpenStackSession createSession(String userId) throws OpenStackException;

}
