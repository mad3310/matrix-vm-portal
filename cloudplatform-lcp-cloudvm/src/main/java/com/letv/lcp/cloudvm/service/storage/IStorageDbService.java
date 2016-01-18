package com.letv.lcp.cloudvm.service.storage;

import com.letv.portal.enumeration.cloudvm.CloudvmVolumeStatusEnum;
import com.letv.portal.enumeration.cloudvm.CloudvmVolumeTypeEnum;
import com.letv.portal.model.cloudvm.lcp.CloudvmStorageModel;


public interface IStorageDbService {
	
	CloudvmStorageModel create(Long userId, Long tenantId, String region, Integer size, 
			CloudvmVolumeTypeEnum volumeType, CloudvmVolumeStatusEnum status, String name);
	
	void updateStorage(Long id, Long userId, String storageInstanceId, CloudvmVolumeStatusEnum status);
	
	void updateStorage(Long id, Long userId, Long serverId, CloudvmVolumeStatusEnum status);
	
}
