package com.letv.portal.dao.cloudvm;

import java.util.List;

import com.letv.common.dao.IBaseDao;
import com.letv.portal.model.cloudvm.CloudvmCluster;

public interface ICloudvmClusterDao extends IBaseDao<CloudvmCluster> {
	List<CloudvmCluster> selectByRegionId(Long regionId);
}
