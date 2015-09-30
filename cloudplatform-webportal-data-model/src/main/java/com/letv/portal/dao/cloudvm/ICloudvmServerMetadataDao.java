package com.letv.portal.dao.cloudvm;

import com.letv.common.dao.IBaseDao;
import com.letv.portal.model.cloudvm.CloudvmServerAddress;
import com.letv.portal.model.cloudvm.CloudvmServerMetadata;

import java.util.Map;

/**
 * Created by zhouxianguang on 2015/9/18.
 */
public interface ICloudvmServerMetadataDao extends IBaseDao<CloudvmServerMetadata> {

    void deleteByRegionAndServerId(Map<String, Object> paras);
}
