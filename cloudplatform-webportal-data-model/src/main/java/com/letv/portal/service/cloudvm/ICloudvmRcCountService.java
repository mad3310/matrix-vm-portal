package com.letv.portal.service.cloudvm;

import com.letv.portal.model.cloudvm.CloudvmRcCount;
import com.letv.portal.model.cloudvm.CloudvmRcCountType;
import com.letv.portal.service.IBaseService;

import java.util.List;

/**
 * Created by zhouxianguang on 2015/10/29.
 */
public interface ICloudvmRcCountService extends IBaseService<CloudvmRcCount> {
    CloudvmRcCount selectByType(long tenantId, String region, CloudvmRcCountType type);
}
