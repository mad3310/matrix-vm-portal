package com.letv.lcp.cloudvm.service.network;

import java.util.Map;

import com.letv.lcp.cloudvm.listener.FloatingIpCreateListener;
import com.letv.lcp.cloudvm.listener.RouterCreateListener;
import com.letv.lcp.cloudvm.model.network.FloatingIpCreateConf;
import com.letv.lcp.cloudvm.model.network.NetworkModel;
import com.letv.lcp.cloudvm.model.network.RouterCreateConf;
import com.letv.lcp.cloudvm.service.resource.IResourceService;

public interface INetworkService extends IResourceService<NetworkModel> {
	
	/**
	  * @Title: createFloatingIp
	  * @Description: 创建公网IP
	  * @param userId
	  * @param floatingIpCreateConf
	  * @param listener
	  * @param listenerUserData
	  * @param params
	  * @return String   
	  * @throws 
	  * @author lisuxiao
	  * @date 2016年1月11日 下午6:51:49
	  */
	String createFloatingIp(Long userId, FloatingIpCreateConf floatingIpCreateConf, 
			FloatingIpCreateListener listener, Object listenerUserData, Map<String, Object> params);
	
	/**
	  * @Title: rollBackFloatingIpWithCreateVmFail
	  * @Description: 云主机创建中的公网ip创建失败回滚
	  * @param params void   
	  * @throws 
	  * @author lisuxiao
	  * @date 2016年1月11日 下午6:51:59
	  */
	void rollBackFloatingIpWithCreateVmFail(Map<String, Object> params);
	
	/**
	  * @Title: createRouter
	  * @Description: 创建路由
	  * @param userId
	  * @param routerCreateConf
	  * @param listener
	  * @param listenerUserData
	  * @return String   
	  * @throws 
	  * @author lisuxiao
	  * @date 2016年1月11日 下午6:52:23
	  */
	String createRouter(Long userId, RouterCreateConf routerCreateConf, RouterCreateListener listener, Object listenerUserData);
	
	/**
	  * @Title: createSubnetPorts
	  * @Description: 创建子网
	  * @param params
	  * @return String   
	  * @throws 
	  * @author lisuxiao
	  * @date 2016年1月11日 下午6:52:32
	  */
	String createSubnetPorts(Map<String, Object> params);
	
	/**
	  * @Title: rollBackSubnetPortsWithCreateVmFail
	  * @Description: 云主机创建中的子网创建失败回滚
	  * @param params void   
	  * @throws 
	  * @author lisuxiao
	  * @date 2016年1月11日 下午6:52:47
	  */
	void rollBackSubnetPortsWithCreateVmFail(Map<String, Object> params);
}
