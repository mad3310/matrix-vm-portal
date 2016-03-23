package com.letv.portal.dao.cloudvm;

import java.util.List;
import java.util.Map;

import com.letv.common.dao.IBaseDao;
import com.letv.portal.model.cloudvm.CloudvmImage;

/**
 * Created by zhouxianguang on 2015/9/18.
 */
public interface ICloudvmImageDao extends IBaseDao<CloudvmImage> {
    List<CloudvmImage> selectByMapForSync(Map<String, Object> map);

    List<CloudvmImage> selectAllImageOrVmSnapshot(Map<String, Object> paras);
    
    List<CloudvmImage> selectByClusterId(Long clusterId);
}
