package com.letv.portal.service.message.impl;

import java.util.HashMap;
import java.util.Map;

import com.letv.common.util.HttpClient;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;

import com.letv.common.session.SessionServiceImpl;
import com.letv.common.util.ExceptionEmailServiceUtil;
import com.letv.common.util.HttpsClient;
import com.letv.portal.model.message.Message;
import com.letv.portal.service.IUserService;
import com.letv.portal.service.message.IMessageProxyService;

@Service("messageProxyService")
public class MessageProxyServiceImpl implements IMessageProxyService{
	
	private final static Logger logger = LoggerFactory.getLogger(MessageProxyServiceImpl.class);
	
	@Value("${uc.auth.api.http}")
	private String UC_AUTH_API_HTTP;
	@Autowired
	public SessionServiceImpl sessionService;
	@Autowired
	public IUserService userService;
	@Autowired
    private TaskExecutor threadPoolTaskExecutor;
	@Autowired
    ExceptionEmailServiceUtil exceptionEmailServiceUtil;

	@Override
	public void saveMessage(final Message msg) {
		 saveMessage(this.sessionService.getSession().getUserId(), msg);
	}
	
	
	private void analyzeRestServiceResult(String result, String url, Map<String, String> params) {
		Map<String, Object> map = transResult(result);
		
		if(map.get("id")==null && 1!=(Integer)map.get("id")) {
			map.put("result", true);
			exceptionEmailServiceUtil.sendErrorEmail("用户中心接口调用失败", 
					"保存消息通知失败，返回结果:"+result+";传入参数："+params.toString(), url);
		}
	}
	
	
	@SuppressWarnings("unchecked")
	private Map<String,Object> transResult(String result){
		Map<String,Object> jsonResult = new HashMap<String,Object>();
		if(StringUtils.isEmpty(result))
			return jsonResult;
		ObjectMapper resultMapper = new ObjectMapper();
		try {
			jsonResult = resultMapper.readValue(result, Map.class);
		}catch (Exception e) {
			logger.error("转换string到map出现异常：", e);
		}
		return jsonResult;
	}


	@Override
	public void saveMessage(final Long userId, final Message msg) {
		this.threadPoolTaskExecutor.execute(new Runnable() {
			@Override
			public void run() {
				StringBuffer buffer = new StringBuffer();
				Map<String, String> map = new HashMap<String, String>();
				map.put("msgTitle", msg.getMsgTitle());
				map.put("msgContent", msg.getMsgContent());
				map.put("msgStatus", msg.getMsgStatus());
				map.put("msgType", msg.getMsgType());
				Long ucId = userService.getUcIdByUserId(userId);
				buffer.append(UC_AUTH_API_HTTP).append("/message/pubMessage.do?userid=").append(ucId);
				//buffer.append("http://10.150.146.171/uc-http-api/pubMessage.do?userid=").append(userId);
				logger.info("saveMessage url:{}",buffer.toString());
				String result = HttpClient.post(buffer.toString(), map, 1000, 2000, null, null);
				logger.info("保存消息:"+map.toString());
				analyzeRestServiceResult(result, buffer.toString(), map);
			}
		});
		
		
	}
	
}
	
