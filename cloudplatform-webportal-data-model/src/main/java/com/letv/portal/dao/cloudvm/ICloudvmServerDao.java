package com.letv.portal.dao.cloudvm;

import com.letv.common.dao.IBaseDao;
import com.letv.portal.model.cloudvm.CloudvmServer;

import java.util.List;
import java.util.Map;

/**
 * Created by zhouxianguang on 2015/9/18.
 */
public interface ICloudvmServerDao extends IBaseDao<CloudvmServer> {
    List<CloudvmServer> selectByMapForSync(Map<String, Object> map);
}
