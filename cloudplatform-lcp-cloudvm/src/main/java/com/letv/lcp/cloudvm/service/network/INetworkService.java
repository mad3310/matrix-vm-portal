package com.letv.lcp.cloudvm.service.network;

import java.util.Map;

import com.letv.lcp.cloudvm.listener.FloatingIpCreateListener;
import com.letv.lcp.cloudvm.listener.RouterCreateListener;
import com.letv.lcp.cloudvm.model.network.FloatingIpCreateConf;
import com.letv.lcp.cloudvm.model.network.NetworkModel;
import com.letv.lcp.cloudvm.model.network.RouterCreateConf;
import com.letv.lcp.cloudvm.service.resource.IResourceService;

public interface INetworkService extends IResourceService<NetworkModel> {
	
	String createFloatingIp(Long userId, FloatingIpCreateConf floatingIpCreateConf, 
			FloatingIpCreateListener listener, Object listenerUserData, Map<String, Object> params);
	
	void rollBackFloatingIpWithCreateVmFail(Map<String, Object> params);
	
	String createRouter(Long userId, RouterCreateConf routerCreateConf, RouterCreateListener listener, Object listenerUserData);
	
	String createSubnetPorts(Map<String, Object> params);
	
	void rollBackSubnetPortsWithCreateVmFail(Map<String, Object> params);
}
