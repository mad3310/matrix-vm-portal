package com.letv.portal.service.cloudvm;

import java.util.List;

import com.letv.portal.model.cloudvm.CloudvmCluster;
import com.letv.portal.service.common.IBaseService;

public interface ICloudvmClusterService extends IBaseService<CloudvmCluster> {
	
	List<CloudvmCluster> selectByRegionId(Long regionId);
}
