package com.letv.lcp.openstack.service.local;

import java.util.List;

import com.letv.lcp.openstack.model.network.LocalSubnetOptionResource;

/**
 * Created by zhouxianguang on 2015/11/25.
 */
public interface ILocalSubnetOptionService {
    List<LocalSubnetOptionResource> list();
}
