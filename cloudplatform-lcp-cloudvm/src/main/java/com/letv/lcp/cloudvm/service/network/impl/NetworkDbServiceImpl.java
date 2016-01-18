package com.letv.lcp.cloudvm.service.network.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.letv.lcp.cloudvm.service.network.INetworkDbService;
import com.letv.portal.enumeration.cloudvm.CloudvmNetworkStatusEnum;
import com.letv.portal.model.cloudvm.lcp.CloudvmPublicNetworkModel;
import com.letv.portal.service.lcp.ICloudvmPublicNetworkService;

@Service("networkDbService")
public class NetworkDbServiceImpl implements INetworkDbService {

    @Autowired
    private ICloudvmPublicNetworkService cloudvmPublicNetworkService;
    
    public CloudvmPublicNetworkModel createPublicNetwork(Long userId, Long tenantId, String region, Integer bandWidth, CloudvmNetworkStatusEnum status, String name) {
    	CloudvmPublicNetworkModel networkModel = new CloudvmPublicNetworkModel();
    	networkModel.setCreateUser(userId);
    	networkModel.setTenantId(tenantId);
    	networkModel.setName(name);
    	networkModel.setRegion(region);
    	networkModel.setStatus(status);
    	networkModel.setBandWidth(bandWidth);
    	
        cloudvmPublicNetworkService.insert(networkModel);
        return networkModel;
    }

	@Override
	public void updatePublicNetwork(Long id, Long userId, String networkInstanceId, String publicIp,
			String carrierName, CloudvmNetworkStatusEnum status) {
		CloudvmPublicNetworkModel networkModel = new CloudvmPublicNetworkModel();
		networkModel.setId(id);
		networkModel.setNetworkInstanceId(networkInstanceId);
		networkModel.setUpdateUser(userId);
		networkModel.setPublicIp(publicIp);
		networkModel.setCarrierName(carrierName);
		networkModel.setStatus(status);
		cloudvmPublicNetworkService.update(networkModel);
	}

	@Override
	public void updatePublicNetwork(Long id, Long userId,
			CloudvmNetworkStatusEnum status) {
		updatePublicNetwork(id, userId, null, null, null, status);
	}

}
