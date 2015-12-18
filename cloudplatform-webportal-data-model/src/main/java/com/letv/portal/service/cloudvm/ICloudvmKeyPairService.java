package com.letv.portal.service.cloudvm;

import com.letv.common.paging.impl.Page;
import com.letv.portal.model.cloudvm.CloudvmKeyPair;
import com.letv.portal.service.common.IBaseService;

import java.util.List;

/**
 * Created by zhouxianguang on 2015/9/18.
 */
public interface ICloudvmKeyPairService extends IBaseService<CloudvmKeyPair> {

    CloudvmKeyPair getByName(long tenantId, String region, String name);

    List<CloudvmKeyPair> selectByName(long tenantId, String region, String name, Page page);

    int selectCountByName(long tenantId, String region, String name);
}
