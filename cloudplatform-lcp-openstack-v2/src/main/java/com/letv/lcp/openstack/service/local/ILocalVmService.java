package com.letv.lcp.openstack.service.local;

import com.letv.common.paging.impl.Page;
import com.letv.lcp.openstack.exception.OpenStackException;
import com.letv.lcp.openstack.model.compute.VMResource;


/**
 * Created by zhouxianguang on 2015/9/30.
 */
public interface ILocalVmService {
    VMResource get(String region, String vmId) throws OpenStackException;

    Page list(String region, String name, Integer currentPage, Integer recordsPerPage) throws OpenStackException;
}
