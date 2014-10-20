package com.letv.portal.zabbixPush;

import com.letv.portal.model.ZabbixPushModel;

 
public interface IZabbixPushService{
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
	public Boolean createContainerPushZabbixInfo(ZabbixPushModel zabbixPushModel); 
	
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
