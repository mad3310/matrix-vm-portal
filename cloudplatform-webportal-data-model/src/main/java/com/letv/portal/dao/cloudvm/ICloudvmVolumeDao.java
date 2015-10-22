package com.letv.portal.dao.cloudvm;

import com.letv.common.dao.IBaseDao;
import com.letv.portal.model.cloudvm.CloudvmVolume;

import java.util.List;
import java.util.Map;

/**
 * Created by zhouxianguang on 2015/9/18.
 */
public interface ICloudvmVolumeDao extends IBaseDao<CloudvmVolume> {
    List<CloudvmVolume> selectByMapForSync(Map<String, Object> map);
}
