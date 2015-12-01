package com.letv.portal.service.oauth.impl;

import com.letv.common.util.HttpsClient;
import com.letv.portal.service.oauth.IUcService;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service("ucService")
public class UcServiceImpl implements IUcService {
	public final static Logger logger = LoggerFactory.getLogger(UcServiceImpl.class);

	@Value("${uc.auth.api.http}")
	private String UC_AUTH_API_HTTP;


	@Override
	public Map<String,Object> getUnReadMessage(Long userId) {
		StringBuffer buffer = new StringBuffer();
		buffer.append(UC_AUTH_API_HTTP).append("/message/unReadMessage.do?userId=").append(userId);
		logger.info("getUnReadMessage url:{}",buffer.toString());
		String result = HttpsClient.sendXMLDataByGet(buffer.toString(), 1000, 2000);
		Map<String,Object> resultMap = this.transResult(result);
		return resultMap;
	}
	@Override
	public Map<String,Object> getUserByUserId(Long userId) {
		StringBuffer buffer = new StringBuffer();
		buffer.append(UC_AUTH_API_HTTP).append("/user/userInfoById.do?userId=").append(userId);
		logger.info("getUserDetailInfo url:{}",buffer.toString());
		String result = HttpsClient.sendXMLDataByGet(buffer.toString(), 1000, 2000);
		Map<String,Object> resultMap = this.transResult(result);
		return resultMap;
	}

	@Override
	public Long getUcIdByOauthId(String oauthId) {
		StringBuffer buffer = new StringBuffer();
		buffer.append(UC_AUTH_API_HTTP).append("/user/getUseridByOauthUuid.do?uuid=").append(oauthId);
		String result = HttpsClient.sendXMLDataByGet(buffer.toString(), 1000, 2000);
		Map<String,Object> resultMap = this.transResult(result);
		if(null == resultMap || resultMap.isEmpty())
			return null;
		return Long.valueOf((Integer) resultMap.get("userid"));
	}

	private Map<String,Object> transResult(String result){
		if(StringUtils.isEmpty(result))
			return null;
		ObjectMapper resultMapper = new ObjectMapper();
		Map<String,Object> jsonResult = new HashMap<String,Object>();
		try {
			jsonResult = resultMapper.readValue(result, Map.class);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return jsonResult;
	}

}