package com.letv.portal.service.openstack.local.service;

import com.letv.common.paging.impl.Page;
import com.letv.portal.service.openstack.exception.OpenStackException;

/**
 * Created by zhouxianguang on 2015/10/23.
 */
public interface LocalImageService {
    Page list(String region, String name, Integer currentPage, Integer recordsPerPage) throws OpenStackException;
}
