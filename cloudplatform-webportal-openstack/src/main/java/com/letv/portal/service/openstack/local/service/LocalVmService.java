package com.letv.portal.service.openstack.local.service;

import com.letv.common.paging.impl.Page;
import com.letv.portal.service.openstack.exception.OpenStackException;
import com.letv.portal.service.openstack.resource.VMResource;

/**
 * Created by zhouxianguang on 2015/9/30.
 */
public interface LocalVmService {
    VMResource get(String region, String vmId) throws OpenStackException;

    Page list(String region, String name, Integer currentPage, Integer recordsPerPage) throws OpenStackException;
}
