package com.letv.portal.service.openstack.local.service.impl;

import com.letv.portal.model.cloudvm.CloudvmRcCount;
import com.letv.portal.model.cloudvm.CloudvmRcCountType;
import com.letv.portal.service.cloudvm.ICloudvmRcCountService;
import com.letv.portal.service.openstack.local.service.LocalRcCountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public void incRcCount(long tenantId, String region, CloudvmRcCountType type) {
        
    }

    @Override
    public void incRcCount(long userId, long tenantId, String region, CloudvmRcCountType type, long count) {

    }

    @Override
    public void incRcCount(long tenantId, String region, CloudvmRcCountType type, long count) {

    }

    @Override
    public void decRcCount(long userId, long tenantId, String region, CloudvmRcCountType type) {
        CloudvmRcCount rcCount = cloudvmRcCountService.selectByType(tenantId, region, type);
        if (rcCount != null) {
            if (rcCount.getCount() > 0) {
                rcCount.setCount(rcCount.getCount() - 1);
            }
            rcCount.setUpdateUser(userId);
            cloudvmRcCountService.update(rcCount);
        }
    }

    @Override
    public void decRcCount(long userId, long tenantId, String region, CloudvmRcCountType type, long count) {

    }

    @Override
    public void decRcCount(long tenantId, String region, CloudvmRcCountType type) {

    }

    @Override
    public void decRcCount(long tenantId, String region, CloudvmRcCountType type, long count) {

    }

    @Override
    public Map<CloudvmRcCountType, Long> getRcCount(long tenantId, String region) {
        List<CloudvmRcCount> cloudvmRcCountList = cloudvmRcCountService.selectByRegion(tenantId, region);
        Map<CloudvmRcCountType, Long> typeToCount = new HashMap<CloudvmRcCountType, Long>();
        for (CloudvmRcCountType type : CloudvmRcCountType.values()) {
            typeToCount.put(type, 0L);
        }
        for (CloudvmRcCount cloudvmRcCount : cloudvmRcCountList) {
            typeToCount.put(cloudvmRcCount.getType(), cloudvmRcCount.getCount());
        }
        return typeToCount;
    }
}
