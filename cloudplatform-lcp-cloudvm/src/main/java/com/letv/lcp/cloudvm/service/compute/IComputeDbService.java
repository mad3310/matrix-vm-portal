package com.letv.lcp.cloudvm.service.compute;

import com.letv.portal.enumeration.cloudvm.CloudvmServerStatusEnum;
import com.letv.portal.model.cloudvm.lcp.CloudvmServerModel;


public interface IComputeDbService {
	
	CloudvmServerModel createServer(Long userId, Long tenantId, String region, Integer size, 
			CloudvmServerStatusEnum status, String name, String imageInstanceId);
	
	void updateServer(Long id, Long userId, Integer cpu, Integer ram, String serverInstanceId, 
			CloudvmServerStatusEnum status, String privateIp);
	
	void updateServer(Long id, Long userId, Long publicNetworkId);
}
