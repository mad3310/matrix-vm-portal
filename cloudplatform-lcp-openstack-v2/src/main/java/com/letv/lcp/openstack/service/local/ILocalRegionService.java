package com.letv.lcp.openstack.service.local;

import com.letv.lcp.openstack.exception.OpenStackException;
import com.letv.lcp.openstack.model.region.Region;

/**
 * Created by zhouxianguang on 2015/9/30.
 */
public interface ILocalRegionService {
    Region get(String code) throws OpenStackException;
}
