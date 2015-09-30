package com.letv.portal.service.openstack.local.query.service;

import com.letv.portal.service.openstack.exception.OpenStackException;
import com.letv.portal.service.openstack.resource.Region;

/**
 * Created by zhouxianguang on 2015/9/30.
 */
public interface RegionQueryService {
    Region get(String code) throws OpenStackException;
}
