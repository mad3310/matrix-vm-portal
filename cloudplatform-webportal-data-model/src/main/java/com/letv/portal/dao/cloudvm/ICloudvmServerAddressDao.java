package com.letv.portal.dao.cloudvm;

import com.letv.common.dao.IBaseDao;
import com.letv.portal.model.cloudvm.CloudvmServer;
import com.letv.portal.model.cloudvm.CloudvmServerAddress;

import java.util.Map;

/**
 * Created by zhouxianguang on 2015/9/18.
 */
public interface ICloudvmServerAddressDao extends IBaseDao<CloudvmServerAddress> {

    void deleteByRegionAndServerId(Map<String, Object> paras);
}
