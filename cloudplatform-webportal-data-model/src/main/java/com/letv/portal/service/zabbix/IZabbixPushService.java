package com.letv.portal.service.zabbix;

import java.util.List;

import com.letv.portal.model.cloudvm.lcp.CloudvmServerModel;
import com.letv.portal.model.zabbix.ZabbixPushModel;

 
public interface IZabbixPushService{
	
	/**
	 * 批量添加云主机监控
	 */
	public Boolean createMultiCloudvmPushZabbixInfo(List<CloudvmServerModel> models);
	
	/**
	 * 删除云主机监控
	 */
	public Boolean deleteCloudvmPushZabbixInfo(CloudvmServerModel model);
	/**
	 * 批量删除云主机监控
	 */
	public Boolean deleteMutilCloudvmPushZabbixInfo(List<CloudvmServerModel> models);
	/**
	 * Methods Name: loginZabbix <br>
	 * Description:登陆zabbix系统<br>
	 * @author name: wujun
	 * @param zabbixPushModel
	 * @return
	 */
	public String loginZabbix(); 
	/**
	 * Methods Name: createContainerPushZabbixInfo <br>
	 * Description: 创建container时向zabbix系统推送信息<br>
	 * @author name: wujun
	 * @param zabbixPushModel
	 * @return
	 */
	public String pushZabbixInfo(ZabbixPushModel zabbixPushModel); 
	
	/**
	 * Methods Name: sendFixedInfo <br>
	 * Description: 向zabbix系统发送固资信息<br>
	 * @author name: wujun
	 * @throws Exception 
	 */
	public String sendZabbixInfo(Object object) throws Exception;
	/**
	 * Methods Name: receviceFixedInfo <br>
	 * Description: 接受zabbix系统的固资信息<br>
	 * @author name: wujun
	 */
	public String receviceZabbixInfo(ZabbixPushModel zabbixPushModel)throws Exception;
}
