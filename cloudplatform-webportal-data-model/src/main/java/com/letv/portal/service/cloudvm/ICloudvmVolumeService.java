package com.letv.portal.service.cloudvm;

import com.letv.common.paging.impl.Page;
import com.letv.portal.model.cloudvm.CloudvmVolume;
import com.letv.portal.service.IBaseService;

import java.util.List;

/**
 * Created by zhouxianguang on 2015/9/18.
 */
public interface ICloudvmVolumeService extends IBaseService<CloudvmVolume> {

    CloudvmVolume selectByVolumeId(long tenantId, String region, String volumeId);

    int selectByTenantIdCount(long tenantId);

    List<CloudvmVolume> selectForSync(Long minId, Page page);

    List<CloudvmVolume> selectByName(long tenantId, String region, String name, Page page);

    int countByName(long tenantId, String region, String name);
}
