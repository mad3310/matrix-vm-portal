package com.letv.portal.dao.cloudvm;

import com.letv.common.dao.IBaseDao;
import com.letv.portal.model.cloudvm.CloudvmImageLink;

import java.util.Map;

/**
 * Created by zhouxianguang on 2015/9/18.
 */
public interface ICloudvmImageLinkDao extends IBaseDao<CloudvmImageLink> {

    void deleteByRegionAndImageId(Map<String, Object> map);
}
