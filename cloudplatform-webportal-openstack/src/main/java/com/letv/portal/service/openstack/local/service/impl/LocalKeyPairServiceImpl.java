package com.letv.portal.service.openstack.local.service.impl;

import com.letv.common.exception.ValidateException;
import com.letv.common.paging.impl.Page;
import com.letv.portal.model.cloudvm.CloudvmKeyPair;
import com.letv.portal.service.cloudvm.ICloudvmKeyPairService;
import com.letv.portal.service.openstack.exception.OpenStackException;
import com.letv.portal.service.openstack.local.resource.LocalKeyPairResource;
import com.letv.portal.service.openstack.local.service.LocalKeyPairService;
import com.letv.portal.service.openstack.local.service.LocalRegionService;
import com.letv.portal.service.openstack.resource.KeyPairResource;
import org.jclouds.openstack.nova.v2_0.domain.KeyPair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by zhouxianguang on 2015/10/21.
 */
@Service
public class LocalKeyPairServiceImpl implements LocalKeyPairService {

    @Autowired
    private ICloudvmKeyPairService cloudvmKeyPairService;
    @Autowired
    private LocalRegionService localRegionService;

    @Override
    public Page list(long tenantId, String region, String name, Integer currentPage, Integer recordsPerPage) throws OpenStackException {
        localRegionService.get(region);

        Page page = null;
        if (currentPage != null && recordsPerPage != null) {
            if (currentPage <= 0) {
                throw new ValidateException("当前页数不能小于或等于0");
            }
            if (recordsPerPage <= 0) {
                throw new ValidateException("每页记录数不能小于或等于0");
            }
            page = new Page(currentPage, recordsPerPage);
        }
        List<CloudvmKeyPair> cloudvmKeyPairs = cloudvmKeyPairService.selectByName(tenantId, region, name, page);
        List<KeyPairResource> keyPairResources = new LinkedList<KeyPairResource>();
        for (CloudvmKeyPair cloudvmKeyPair : cloudvmKeyPairs) {
            keyPairResources.add(new LocalKeyPairResource(cloudvmKeyPair));
        }

        if (page == null) {
            page = new Page();
        }
        page.setTotalRecords(cloudvmKeyPairService.selectCountByName(tenantId, region, name));
        page.setData(keyPairResources);
        return page;
    }

    @Override
    public CloudvmKeyPair create(long userId, long tenantId, String region, KeyPair keyPair) {
        CloudvmKeyPair cloudvmKeyPair = new CloudvmKeyPair();
        cloudvmKeyPair.setCreateUser(userId);
        cloudvmKeyPair.setTenantId(tenantId);
        cloudvmKeyPair.setRegion(region);
        cloudvmKeyPair.setName(keyPair.getName());
        cloudvmKeyPair.setFingerprint(keyPair.getFingerprint());
        cloudvmKeyPairService.insert(cloudvmKeyPair);
        return cloudvmKeyPair;
    }

    @Override
    public void delete(long tenantId, String region, String name) {
        CloudvmKeyPair cloudvmKeyPair = cloudvmKeyPairService.getByName(tenantId, region, name);
        if (cloudvmKeyPair != null) {
            cloudvmKeyPairService.delete(cloudvmKeyPair);
        }
    }

}
