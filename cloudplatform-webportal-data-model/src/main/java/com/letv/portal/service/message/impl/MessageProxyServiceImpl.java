package com.letv.portal.service.message.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.letv.common.session.SessionServiceImpl;
import com.letv.common.util.CommonUtil;
import com.letv.common.util.HttpClient;
import com.letv.portal.constant.Constant;
import com.letv.portal.model.message.Message;
import com.letv.portal.service.message.IMessageProxyService;

public class MessageProxyServiceImpl implements IMessageProxyService{
	
	private final static Logger logger = LoggerFactory.getLogger(MessageProxyServiceImpl.class);
	
	@Value("${uc.auth.api.http}")
	private String UC_AUTH_API_HTTP;
	@Autowired
	public SessionServiceImpl sessionService;

	@Override
	public Map<String,Object> saveMessage(Message msg) {
		return saveMessage(this.sessionService.getSession().getUserId(), msg);
	}
	
	private Map<String,Object> saveMessage(Long userId, Message msg) {
		StringBuffer buffer = new StringBuffer();
		Map<String, String> map = CommonUtil.convertBeanToMap(msg);
		buffer.append(UC_AUTH_API_HTTP).append("/saveMessage.do?userId=").append(userId);
		logger.info("getUnReadMessage url:{}",buffer.toString());
		String result = HttpClient.post(buffer.toString(), map, 1000, 2000, null, null);
		return analyzeRestServiceResult(result, buffer.toString());
	}
	
	@SuppressWarnings("unchecked")
	private Map<String, Object> analyzeRestServiceResult(String result, String url) {
		Map<String, Object> map = transResult(result);
		Map<String,Object> meta = (Map<String, Object>) map.get("meta");
		
		boolean isSucess = Constant.PYTHON_API_RESPONSE_SUCCESS.equals(String.valueOf(meta.get("code")));
		if(isSucess) {
			map.put("result", true);
			
		} else {
			map.put("result", false);
			map.put("message", meta.get("message"));
		}
		map.put("url", url);
		return map;
	}
	
	
	@SuppressWarnings("unchecked")
	private Map<String,Object> transResult(String result){
		if(StringUtils.isEmpty(result))
			return null;
		ObjectMapper resultMapper = new ObjectMapper();
		Map<String,Object> jsonResult = new HashMap<String,Object>();
		try {
			jsonResult = resultMapper.readValue(result, Map.class);
		}catch (Exception e) {
			logger.error("转换string到map出现异常：", e);
		}
		return jsonResult;
	}
	
}
	
