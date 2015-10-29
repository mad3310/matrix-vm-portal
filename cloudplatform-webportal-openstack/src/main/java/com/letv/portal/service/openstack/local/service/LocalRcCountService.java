package com.letv.portal.service.openstack.local.service;

import com.letv.portal.model.cloudvm.CloudvmRcCountType;

/**
 * Created by zhouxianguang on 2015/10/29.
 */
public interface LocalRcCountService {
    long getRcCount(long tenantId, String region, CloudvmRcCountType type);

    void incRcCount(long userId, long tenantId, String region, CloudvmRcCountType type);

    void decRcCount(long userId, long tenantId, String region, CloudvmRcCountType type);
}
