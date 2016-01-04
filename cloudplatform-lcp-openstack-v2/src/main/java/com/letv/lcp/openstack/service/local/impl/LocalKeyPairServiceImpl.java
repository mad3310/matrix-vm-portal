package com.letv.lcp.openstack.service.local.impl;

import java.util.LinkedList;
import java.util.List;

import org.jclouds.openstack.nova.v2_0.domain.KeyPair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.letv.common.exception.ValidateException;
import com.letv.common.paging.impl.Page;
import com.letv.lcp.openstack.exception.OpenStackException;
import com.letv.lcp.openstack.model.keypair.KeyPairResource;
import com.letv.lcp.openstack.model.keypair.impl.LocalKeyPairResource;
import com.letv.lcp.openstack.service.local.ILocalKeyPairService;
import com.letv.lcp.openstack.service.local.ILocalRegionService;
import com.letv.portal.model.cloudvm.CloudvmKeyPair;
import com.letv.portal.service.cloudvm.ICloudvmKeyPairService;

/**
 * Created by zhouxianguang on 2015/10/21.
 */
@Service
public class LocalKeyPairServiceImpl implements ILocalKeyPairService {

    @Autowired
    private ICloudvmKeyPairService cloudvmKeyPairService;
    @Autowired
    private ILocalRegionService localRegionService;

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
    public boolean delete(long tenantId, String region, String name) {
        CloudvmKeyPair cloudvmKeyPair = cloudvmKeyPairService.getByName(tenantId, region, name);
        if (cloudvmKeyPair != null) {
            cloudvmKeyPairService.delete(cloudvmKeyPair);
            return true;
        }
        return false;
    }

    @Override
    public int count(long tenantId, String region) {
        return cloudvmKeyPairService.selectCountByName(tenantId, region, null);
    }

}
