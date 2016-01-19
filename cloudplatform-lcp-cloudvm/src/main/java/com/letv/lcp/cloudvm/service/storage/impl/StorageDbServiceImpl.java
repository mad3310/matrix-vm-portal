package com.letv.lcp.cloudvm.service.storage.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.letv.lcp.cloudvm.service.storage.IStorageDbService;
import com.letv.portal.enumeration.cloudvm.CloudvmVolumeStatusEnum;
import com.letv.portal.enumeration.cloudvm.CloudvmVolumeTypeEnum;
import com.letv.portal.model.cloudvm.lcp.CloudvmStorageModel;
import com.letv.portal.service.lcp.ICloudvmStorageService;

@Service("storageDbService")
public class StorageDbServiceImpl implements IStorageDbService {

    @Autowired
    private ICloudvmStorageService cloudvmStorageService;
    
    public CloudvmStorageModel create(Long userId, Long tenantId, String region, Integer size, 
    		CloudvmVolumeTypeEnum volumeType, CloudvmVolumeStatusEnum volumeStatus, String name) {
        CloudvmStorageModel storageModel = new CloudvmStorageModel();
        storageModel.setCreateUser(userId);
        storageModel.setTenantId(tenantId);
        storageModel.setRegion(region);
        storageModel.setStorageType(volumeType);
        storageModel.setName(name);
        storageModel.setSize(size);
        storageModel.setStatus(volumeStatus);
        cloudvmStorageService.insert(storageModel);
        return storageModel;
    }

	@Override
	public void updateStorage(Long id, Long userId, String storageInstanceId,
			CloudvmVolumeStatusEnum status) {
		updateStorage(id, userId, storageInstanceId, status, null);
	}
	
	private void updateStorage(Long id, Long userId, String storageInstanceId,
			CloudvmVolumeStatusEnum status, Long serverId) {
		CloudvmStorageModel storageModel = new CloudvmStorageModel();
		storageModel.setId(id);
		storageModel.setStorageInstanceId(storageInstanceId);
		storageModel.setUpdateUser(userId);
		storageModel.setStatus(status);
		storageModel.setServerId(serverId);
		cloudvmStorageService.update(storageModel);
	}

	@Override
	public void updateStorage(Long id, Long userId, Long serverId, CloudvmVolumeStatusEnum status) {
		updateStorage(id, userId, null, status, serverId);
	}


}
