package com.letv.lcp.cloudvm.service.network;

import com.letv.lcp.cloudvm.listener.FloatingIpCreateListener;
import com.letv.lcp.cloudvm.listener.RouterCreateListener;
import com.letv.lcp.cloudvm.model.network.FloatingIpCreateConf;
import com.letv.lcp.cloudvm.model.network.NetworkModel;
import com.letv.lcp.cloudvm.model.network.RouterCreateConf;
import com.letv.lcp.cloudvm.service.resource.IResourceService;

public interface INetworkService extends IResourceService<NetworkModel> {
	
	void createFloatingIp(long userId, FloatingIpCreateConf floatingIpCreateConf, FloatingIpCreateListener listener, Object listenerUserData);
	
	void createRouter(long userId, RouterCreateConf routerCreateConf, RouterCreateListener listener, Object listenerUserData);

}
