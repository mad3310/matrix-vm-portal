package com.letv.portal.service.openstack.local.service;

import com.letv.common.paging.impl.Page;
import com.letv.portal.model.cloudvm.CloudvmKeyPair;
import com.letv.portal.service.openstack.exception.OpenStackException;
import org.jclouds.openstack.nova.v2_0.domain.KeyPair;

/**
 * Created by zhouxianguang on 2015/11/2.
 */
public interface LocalKeyPairService {
    Page list(long tenantId, String region, String name, Integer currentPage, Integer recordsPerPage) throws OpenStackException;

    CloudvmKeyPair create(long userId, long tenantId, String region, KeyPair keyPair);

    void delete(long tenantId, String region, String name);
}
