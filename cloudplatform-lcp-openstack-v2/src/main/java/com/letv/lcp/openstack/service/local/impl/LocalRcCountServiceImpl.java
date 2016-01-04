package com.letv.lcp.openstack.service.local.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.letv.lcp.openstack.service.local.ILocalRcCountService;
import com.letv.portal.model.cloudvm.CloudvmRcCount;
import com.letv.portal.model.cloudvm.CloudvmRcCountType;
import com.letv.portal.service.cloudvm.ICloudvmRcCountService;

/**
 * Created by zhouxianguang on 2015/10/29.
 */
@Service
public class LocalRcCountServiceImpl implements ILocalRcCountService {

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
        incRcCount(userId, tenantId, region, type, 1);
    }

    @Override
    public void incRcCount(long tenantId, String region, CloudvmRcCountType type) {
        incRcCount(tenantId, tenantId, region, type, 1);
    }

    @Override
    public void incRcCount(long userId, long tenantId, String region, CloudvmRcCountType type, long count) {
        CloudvmRcCount rcCount = cloudvmRcCountService.selectByType(tenantId, region, type);
        if (rcCount != null) {
            rcCount.setCount(rcCount.getCount() + count);
            rcCount.setUpdateUser(userId);
            cloudvmRcCountService.update(rcCount);
        } else {
            rcCount = new CloudvmRcCount();
            rcCount.setTenantId(tenantId);
            rcCount.setRegion(region);
            rcCount.setType(type);
            rcCount.setCount(count);
            rcCount.setCreateUser(userId);
            cloudvmRcCountService.insert(rcCount);
        }
    }

    @Override
    public void incRcCount(long tenantId, String region, CloudvmRcCountType type, long count) {
        incRcCount(tenantId, tenantId, region, type, count);
    }

    @Override
    public void decRcCount(long userId, long tenantId, String region, CloudvmRcCountType type) {
        decRcCount(userId, tenantId, region, type, 1);
    }

    @Override
    public void decRcCount(long userId, long tenantId, String region, CloudvmRcCountType type, long count) {
        CloudvmRcCount rcCount = cloudvmRcCountService.selectByType(tenantId, region, type);
        if (rcCount != null) {
            if (rcCount.getCount() > 0) {
                rcCount.setCount(rcCount.getCount() - count);
            }
            rcCount.setUpdateUser(userId);
            cloudvmRcCountService.update(rcCount);
        }
    }

    @Override
    public void decRcCount(long tenantId, String region, CloudvmRcCountType type) {
        decRcCount(tenantId, tenantId, region, type, 1);
    }

    @Override
    public void decRcCount(long tenantId, String region, CloudvmRcCountType type, long count) {
        decRcCount(tenantId, tenantId, region, type, count);
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
