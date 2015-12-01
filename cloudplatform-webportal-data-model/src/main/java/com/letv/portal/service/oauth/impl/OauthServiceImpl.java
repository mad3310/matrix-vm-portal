package com.letv.portal.service.oauth.impl;

import com.letv.common.exception.OauthException;
import com.letv.common.session.SessionServiceImpl;
import com.letv.common.util.HttpsClient;
import com.letv.common.util.StringUtil;
import com.letv.mms.cache.ICacheService;
import com.letv.mms.cache.factory.CacheFactory;
import com.letv.portal.service.oauth.IOauthService;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;


/**Program Name: OauthLoginApi <br>
 * Description:  提供基础的oauth api服务<br>
 * @author name: howie <br>
 * Written Date: 2015年7月15日 <br>
 * Modified By: <br>
 * Modified Date: <br>
 */
@Service("oauthService")
public class OauthServiceImpl  implements IOauthService {

	public final static Logger logger = LoggerFactory.getLogger(OauthServiceImpl.class);
	public final static int OAUTH_API_RETRY_COUNT = 3;
	private final static String OAUTH_REDIRECT_KEY_SECRET = "&redirect_uri=http://www.baidu.com&app_key=chenle&app_secret=newpasswd";

	@Autowired
	public SessionServiceImpl sessionService;

	private ICacheService<?> cacheService = CacheFactory.getCache();


	@Value("${oauth.auth.http}")
	public String OAUTH_AUTH_HTTP;
	@Value("${oauth.token.cache.expire}")
	public long OAUTH_TOKEN_CACHE_EXPIRE;

	public String retryOauthApi(String result,String url){
		int i = 1;
		while(StringUtils.isEmpty(result) && i<=OAUTH_API_RETRY_COUNT) {
			result = HttpsClient.sendXMLDataByGet(url, 1000, 1000);
			i++;
		}
		return result;
	}

	@Override
	public String getAccessToken(String clientId,String clientSecret,String code) {
		if(StringUtils.isEmpty(clientId) || StringUtils.isEmpty(code))
			return null;
		StringBuffer buffer = new StringBuffer();
		buffer.append(OAUTH_AUTH_HTTP).append("/accesstoken?grant_type=authorization_code&code=").append(code).append("&client_id=").append(clientId).append("&client_secret=").append(clientSecret).append(OAUTH_REDIRECT_KEY_SECRET);
		logger.debug("getAccessToken :" + buffer.toString());
		String result = HttpsClient.sendXMLDataByGet(buffer.toString(), 1000, 1000);
		retryOauthApi(result,buffer.toString());
		logger.debug("getAccessToken result:" + result);
		Map<String,Object> resultMap = this.transResult(result);
		if(StringUtils.isEmpty(result))
			return null;
		return (String) resultMap.get("access_token");
	}

	@Override
	public Map<String,Object> getUserdetailinfo(String accessToken) {
		if(StringUtils.isEmpty(accessToken))
			return null;
		StringBuffer buffer = new StringBuffer();
		buffer.append(OAUTH_AUTH_HTTP).append("/userdetailinfo?access_token=").append(accessToken).append(OAUTH_REDIRECT_KEY_SECRET);
		logger.debug("getUserdetailinfo :" + buffer.toString());
		String result = HttpsClient.sendXMLDataByGet(buffer.toString(), 1000, 1000);
		retryOauthApi(result, buffer.toString());
		logger.debug("getUserdetailinfo result:" + result);
		Map<String,Object> resultMap = this.transResult(result);
		return resultMap;
	}

	@Override
	public String getAuthorize(String clientId) {
		if(StringUtils.isEmpty(clientId))
			return null;
		StringBuffer buffer = new StringBuffer();
		buffer.append(OAUTH_AUTH_HTTP).append("/authorizenoredirect?&response_type=code&client_id=").append(clientId).append(OAUTH_REDIRECT_KEY_SECRET);
		logger.debug("getAuthorize :" + buffer.toString());
		String result = HttpsClient.sendXMLDataByGet(buffer.toString(), 1000, 1000);
		retryOauthApi(result, buffer.toString());
		logger.debug("getAuthorize result:" + result);
		Map<String,Object> resultMap = this.transResult(result);
		if(null == resultMap)
			return null;
		return (String) resultMap.get("code");
	}

	@Override
	public Map<String, Object> getUserdetailinfo(String clientId, String clientSecret) {
		Map<String,Object> userdetailinfo = (Map<String, Object>) this.cacheService.get(clientId, null);
		if(null == userdetailinfo)
			userdetailinfo = getUserdetailinfo(getAccessToken(clientId, clientSecret, getAuthorize(clientId)));
		this.cacheService.set(clientId, userdetailinfo, OAUTH_TOKEN_CACHE_EXPIRE);
		return userdetailinfo;
	}

	public Map<String,Object> transResult(String result){
		if(StringUtils.isEmpty(result))
			return null;
		ObjectMapper resultMapper = new ObjectMapper();
		Map<String,Object> jsonResult;
		try {
			jsonResult = resultMapper.readValue(result, Map.class);
		}catch (Exception e) {
			logger.error("login failed when transResult:{}",e.getMessage());
			return null;
		}
		return jsonResult;
	}
}