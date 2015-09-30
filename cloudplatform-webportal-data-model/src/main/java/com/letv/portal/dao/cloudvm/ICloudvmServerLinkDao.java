package com.letv.portal.dao.cloudvm;

import com.letv.common.dao.IBaseDao;
import com.letv.portal.model.cloudvm.CloudvmServerAddress;
import com.letv.portal.model.cloudvm.CloudvmServerLink;

import java.util.List;
import java.util.Map;

/**
 * Created by zhouxianguang on 2015/9/18.
 */
public interface ICloudvmServerLinkDao extends IBaseDao<CloudvmServerLink> {

    void deleteByRegionAndServerId(Map<String, Object> map);
}
