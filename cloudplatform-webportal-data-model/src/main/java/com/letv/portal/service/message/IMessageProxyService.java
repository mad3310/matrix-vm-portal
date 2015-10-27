package com.letv.portal.service.message;

import java.util.Map;

import com.letv.portal.model.message.Message;


public interface IMessageProxyService {
	
	/**
	  * @Title: saveMessage
	  * @Description: 调用用户中心保存消息通知
	  * @param msg
	  * @return Map<String,Object>   
	  * @throws 
	  * @author lisuxiao
	  * @date 2015年10月23日 下午4:44:22
	  */
	Map<String,Object> saveMessage(Message msg);
	Map<String,Object> saveMessage(Long userId, Message msg);

}
