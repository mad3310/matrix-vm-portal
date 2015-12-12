package com.letv.portal.service.message;

import com.letv.portal.model.message.Message;


public interface IMessageProxyService {
	
	/**
	  * @Title: saveMessage
	  * @Description: 调用用户中心保存消息通知
	  * @param msg
	  * @throws 
	  * @author lisuxiao
	  * @date 2015年10月23日 下午4:44:22
	  */
	void saveMessage(final Message msg);
	void saveMessage(Long userId, final Message msg);

}
