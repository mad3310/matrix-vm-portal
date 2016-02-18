package com.letv.lcp.cloudvm.service.compute;

import com.letv.portal.enumeration.cloudvm.CloudvmServerStatusEnum;
import com.letv.portal.model.cloudvm.lcp.CloudvmServerModel;


public interface IComputeDbService {
	
	CloudvmServerModel createServer(Long userId, Long tenantId, String region, Integer size, 
			CloudvmServerStatusEnum status, String name, String imageInstanceId);
	
	void updateServer(Long id, Long userId, Integer cpu, Integer ram, String serverInstanceId, 
			CloudvmServerStatusEnum status, String privateIp);
	
	void updateServer(Long id, Long userId, Long publicNetworkId);
	
	/**
	  * @Title: saveCloudvmServerOperator
	  * @Description: TODO
	  * @param userId 当前申请人
	  * @param serverId 云主机id
	  * @param operatorId 操作人id  
	  * @throws 
	  * @author lisuxiao
	  * @date 2016年2月18日 下午12:02:16
	  */
	void saveCloudvmServerOperator(Long userId, Long serverId, Long operatorId);
}
