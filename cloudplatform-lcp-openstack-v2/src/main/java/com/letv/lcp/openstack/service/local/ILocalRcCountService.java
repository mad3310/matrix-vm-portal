package com.letv.lcp.openstack.service.local;

import java.util.Map;

import com.letv.portal.model.cloudvm.CloudvmRcCountType;

/**
 * Created by zhouxianguang on 2015/10/29.
 */
public interface ILocalRcCountService {
    long getRcCount(long tenantId, String region, CloudvmRcCountType type);

    void incRcCount(long userId, long tenantId, String region, CloudvmRcCountType type);

    void incRcCount(long tenantId, String region, CloudvmRcCountType type);

    void incRcCount(long userId, long tenantId, String region, CloudvmRcCountType type, long count);

    void incRcCount(long tenantId, String region, CloudvmRcCountType type, long count);

    void decRcCount(long userId, long tenantId, String region, CloudvmRcCountType type);

    void decRcCount(long userId, long tenantId, String region, CloudvmRcCountType type, long count);

    void decRcCount(long tenantId, String region, CloudvmRcCountType type);

    void decRcCount(long tenantId, String region, CloudvmRcCountType type, long count);

    Map<CloudvmRcCountType, Long> getRcCount(long tenantId, String region);
}
