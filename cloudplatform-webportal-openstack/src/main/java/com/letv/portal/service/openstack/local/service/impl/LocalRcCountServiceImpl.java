package com.letv.portal.service.openstack.local.service.impl;

import com.letv.portal.model.cloudvm.CloudvmRcCount;
import com.letv.portal.model.cloudvm.CloudvmRcCountType;
import com.letv.portal.service.cloudvm.ICloudvmRcCountService;
import com.letv.portal.service.openstack.local.service.LocalRcCountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by zhouxianguang on 2015/10/29.
 */
@Service
public class LocalRcCountServiceImpl implements LocalRcCountService {

    @Autowired
    private ICloudvmRcCountService cloudvmRcCountService;

    @Override
    public long getRcCount(long tenantId, String region, CloudvmRcCountType type) {
        CloudvmRcCount rcCount = cloudvmRcCountService.selectByType(tenantId, region, type);
        if (rcCount != null) {
            return rcCount.getCount();
        }
        return 0;
    }

    @Override
    public void incRcCount(long userId, long tenantId, String region, CloudvmRcCountType type) {
        CloudvmRcCount rcCount = cloudvmRcCountService.selectByType(tenantId, region, type);
        if (rcCount != null) {
            rcCount.setCount(rcCount.getCount() + 1);
            rcCount.setUpdateUser(userId);
            cloudvmRcCountService.update(rcCount);
        } else {
            rcCount = new CloudvmRcCount();
            rcCount.setTenantId(tenantId);
            rcCount.setRegion(region);
            rcCount.setType(type);
            rcCount.setCount(1L);
            rcCount.setCreateUser(userId);
            cloudvmRcCountService.insert(rcCount);
        }
    }

    @Override
    public void decRcCount(long userId, long tenantId, String region, CloudvmRcCountType type) {
        CloudvmRcCount rcCount = cloudvmRcCountService.selectByType(tenantId, region, type);
        if (rcCount != null) {
            rcCount.setCount(rcCount.getCount() - 1);
            rcCount.setUpdateUser(userId);
            cloudvmRcCountService.update(rcCount);
        }
    }
}
