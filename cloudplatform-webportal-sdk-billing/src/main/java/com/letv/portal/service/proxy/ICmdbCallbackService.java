package com.letv.portal.service.proxy;

import com.letv.portal.model.cloudvm.lcp.CloudvmServerModel;


/**
 * cmdb回调
 * @author lisuxiao
 *
 */
public interface ICmdbCallbackService {
	
	/**
	  * @Title: getPhysicalHostNameByServerInfo
	  * @Description: 根据虚机信息获取该物理机的主机名
	  * @param serverModel    
	  * @throws 
	  * @author lisuxiao
	  * @date 2016年3月2日 下午1:45:27
	  */
	String getPhysicalHostNameByServerInfo(CloudvmServerModel serverModel); 
	
	/**
	  * @Title: saveVmInfo
	  * @Description: 调用cmdb接口完成虚机信息录入cmdb
	  * @param serverInstanceId void   
	  * @throws 
	  * @author lisuxiao
	  * @date 2016年3月2日 下午3:04:38
	  */
	void saveVmInfo(String serverInstanceId);
	
}
