package com.letv.lcp.cloudvm.service.compute.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.letv.lcp.cloudvm.service.compute.IComputeDbService;
import com.letv.portal.enumeration.cloudvm.CloudvmServerStatusEnum;
import com.letv.portal.model.cloudvm.CloudvmImage;
import com.letv.portal.model.cloudvm.lcp.CloudvmServerModel;
import com.letv.portal.model.cloudvm.lcp.CloudvmServerOperatorModel;
import com.letv.portal.service.cloudvm.ICloudvmImageService;
import com.letv.portal.service.lcp.ICloudvmServerOperatorService;
import com.letv.portal.service.lcp.ICloudvmServerService;

@Service("computeDbService")
public class ComputeDbServiceImpl implements IComputeDbService {

    @Autowired
    private ICloudvmServerService cloudvmServerService;
    @Autowired
    private ICloudvmImageService cloudvmImageService;
    @Autowired
	private ICloudvmServerOperatorService cloudvmServerOperatorService;
    
    public CloudvmServerModel createServer(Long userId, Long tenantId, String region, Integer size, CloudvmServerStatusEnum status, 
    		String name, String imageInstanceId) {
    	//通过镜像id获取数据库中保存的id
    	CloudvmImage image = cloudvmImageService.getImageOrVmSnapshot(region, imageInstanceId);
    	CloudvmServerModel serverModel = new CloudvmServerModel();
    	serverModel.setCreateUser(userId);
    	serverModel.setTenantId(tenantId);
    	serverModel.setName(name);
    	serverModel.setRegion(region);
    	serverModel.setStatus(status);
    	serverModel.setStorage(size);
    	serverModel.setImageId(image==null?null:image.getId());
    	cloudvmServerService.insert(serverModel);
        return serverModel;
    }

	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public void updateServer(Long id, Long userId, Integer cpu, Integer ram,
			String serverInstanceId, CloudvmServerStatusEnum status,
			String privateIp) {
		updateServer(id, userId, cpu, ram, serverInstanceId, status, privateIp, null);
	}
	
	private void updateServer(Long id, Long userId, Integer cpu, Integer ram,
			String serverInstanceId, CloudvmServerStatusEnum status,
			String privateIp, Long publicNetworkId) {
		CloudvmServerModel serverModel = new CloudvmServerModel();
		serverModel.setId(id);
		serverModel.setUpdateUser(userId);
		serverModel.setCpu(cpu);
		serverModel.setRam(ram);
		serverModel.setServerInstanceId(serverInstanceId);
		serverModel.setStatus(status);
		serverModel.setPrivateIp(privateIp);
		serverModel.setPublicNetworkId(publicNetworkId);
		cloudvmServerService.update(serverModel);
	}

	@Override
	public void updateServer(Long id, Long userId, Long publicNetworkId) {
		updateServer(id, userId, null, null, null, null, null, publicNetworkId);
	}

	@Override
	public void saveCloudvmServerOperator(Long userId, Long serverId, Long operatorId) {
		CloudvmServerOperatorModel operator = new CloudvmServerOperatorModel();
		operator.setServerId(serverId);
		operator.setUserId(operatorId);
		operator.setCreateUser(userId);
		cloudvmServerOperatorService.insert(operator);
	}

}
