package com.letv.lcp.cloudvm.service.network;

import com.letv.portal.enumeration.cloudvm.CloudvmNetworkStatusEnum;
import com.letv.portal.model.cloudvm.lcp.CloudvmPublicNetworkModel;


public interface INetworkDbService {
	
	CloudvmPublicNetworkModel createPublicNetwork(Long userId, Long tenantId, String region, Integer bandWidth, CloudvmNetworkStatusEnum status, String name);
	
	void updatePublicNetwork(Long id, Long userId, String networkInstanceId, String publicIp, String carrierName, CloudvmNetworkStatusEnum status);
	
	void updatePublicNetwork(Long id, Long userId, CloudvmNetworkStatusEnum status);
}
