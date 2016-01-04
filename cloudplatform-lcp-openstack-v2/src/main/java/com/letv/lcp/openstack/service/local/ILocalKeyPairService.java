package com.letv.lcp.openstack.service.local;

import org.jclouds.openstack.nova.v2_0.domain.KeyPair;

import com.letv.common.paging.impl.Page;
import com.letv.lcp.openstack.exception.OpenStackException;
import com.letv.portal.model.cloudvm.CloudvmKeyPair;

/**
 * Created by zhouxianguang on 2015/11/2.
 */
public interface ILocalKeyPairService {
    Page list(long tenantId, String region, String name, Integer currentPage, Integer recordsPerPage) throws OpenStackException;

    CloudvmKeyPair create(long userId, long tenantId, String region, KeyPair keyPair);

    boolean delete(long tenantId, String region, String name);

    int count(long tenantId, String region);
}
