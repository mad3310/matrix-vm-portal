package com.letv.portal.service.cmdb;

import java.util.List;
import java.util.Set;

import com.letv.portal.model.cloudvm.lcp.CloudvmServerModel;
import com.letv.portal.model.order.OrderSub;


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
	  * @param groupId 组标识（一组云主机全部创建完成后才进行回调）   
	  * @throws 
	  * @author lisuxiao
	  * @date 2016年3月2日 下午3:04:38
	  */
	void saveVmInfo(String groupId);
	
}
