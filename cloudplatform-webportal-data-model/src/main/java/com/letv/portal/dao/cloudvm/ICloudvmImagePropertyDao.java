package com.letv.portal.dao.cloudvm;

import com.letv.common.dao.IBaseDao;
import com.letv.portal.model.cloudvm.CloudvmImageProperty;

import java.util.Map;

/**
 * Created by zhouxianguang on 2015/9/18.
 */
public interface ICloudvmImagePropertyDao extends IBaseDao<CloudvmImageProperty> {
    void deleteByRegionAndImageId(Map<String, Object> paras);
}
