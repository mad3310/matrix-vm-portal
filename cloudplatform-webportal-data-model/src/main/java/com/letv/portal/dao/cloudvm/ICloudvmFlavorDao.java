package com.letv.portal.dao.cloudvm;

import java.util.List;

import com.letv.common.dao.IBaseDao;
import com.letv.portal.model.cloudvm.CloudvmFlavor;

/**
 * Created by zhouxianguang on 2015/9/18.
 */
public interface ICloudvmFlavorDao extends IBaseDao<CloudvmFlavor> {

	List<CloudvmFlavor> selectByClusterId(Long clusterId);
}
