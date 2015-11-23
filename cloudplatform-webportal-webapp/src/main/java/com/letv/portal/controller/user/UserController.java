package com.letv.portal.controller.user;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.letv.common.result.ResultObject;
import com.letv.common.session.Session;
import com.letv.common.util.CookieUtil;
import com.letv.common.util.HttpsClient;


/**Program Name: UserController <br>
 * Description:  <br>
 * @author name: liuhao1 <br>
 * Written Date: 2015年9月16日 <br>
 * Modified By: <br>
 * Modified Date: <br>
 */
@Controller
@RequestMapping("/user")
public class UserController {
	
	@Value("${uc.auth.http}")
	private String UC_AUTH_HTTP;
	@Value("${uc.auth.api.http}")
	private String UC_AUTH_API_HTTP;
	
	private final static Logger logger = LoggerFactory.getLogger(UserController.class);
	
	/**Methods Name: userInfo <br>
	 * Description: 根据当前session获取用户信息<br>
	 * @author name: liuhao1
	 * @param request
	 * @param response
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method=RequestMethod.GET)   
	public @ResponseBody ResultObject userInfo(HttpServletRequest request,HttpServletResponse response,ResultObject obj) throws Exception{
		Cookie ucCookie = CookieUtil.getCookieByName(request, Session.UC_COOKIE_KEY);
		if(ucCookie == null) {
			obj.setResult(0);
			return obj;
		}
		Map<String, Object> userdetailinfo = this.getUserByCookieId(ucCookie.getValue());
		if(userdetailinfo == null || userdetailinfo.isEmpty()) {
			obj.setResult(0);
			return obj;
		}
		obj.setData(userdetailinfo);
		return obj;
	}
	
	/**Methods Name: userInfo <br>
	 * Description: 根据userId，获取用户信息<br>
	 * @author name: liuhao1
	 * @param userId
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/{userId}",method=RequestMethod.GET)   
	public @ResponseBody ResultObject userInfo(@PathVariable Long userId,ResultObject obj) throws Exception{
		if(null ==userId) {
			obj.setResult(0);
			return obj;
		}
		Map<String, Object> userdetailinfo = this.getUserByUserId(userId);
		if(userdetailinfo == null || userdetailinfo.isEmpty()) {
			obj.setResult(0);
			return obj;
		}
		obj.setData(userdetailinfo);
		return obj;
	}
	@RequestMapping(value="/message/un/{userId}",method=RequestMethod.GET)   
	public @ResponseBody ResultObject unReadMessage(@PathVariable Long userId,ResultObject obj) throws Exception{
		if(null ==userId) {
			obj.setResult(0);
			return obj;
		}
		Map<String, Object> unReadMessage = this.getUnReadMessage(userId);
		if(unReadMessage == null || unReadMessage.isEmpty()) {
			obj.setResult(0);
			return obj;
		}
		obj.setData(unReadMessage.get("totalCount"));
		return obj;
	}
	
	private Map<String,Object> getUnReadMessage(Long userId) {
		StringBuffer buffer = new StringBuffer();
		buffer.append(UC_AUTH_API_HTTP).append("/unReadMessage.do?userId=").append(userId);
		logger.info("getUnReadMessage url:{}",buffer.toString());
		String result = HttpsClient.sendXMLDataByGet(buffer.toString(),1000,2000);
		Map<String,Object> resultMap = this.transResult(result);
		return resultMap;
	}
	
	private Map<String,Object> getUserByUserId(Long userId) {
		StringBuffer buffer = new StringBuffer();
		buffer.append(UC_AUTH_API_HTTP).append("/userInfoById.do?userId=").append(userId);
		logger.info("getUserDetailInfo url:{}",buffer.toString());
		String result = HttpsClient.sendXMLDataByGet(buffer.toString(),1000,2000);
		Map<String,Object> resultMap = this.transResult(result);
		return resultMap;
	}
	
	private Map<String,Object> getUserByCookieId(String ucCookieId) {
		StringBuffer buffer = new StringBuffer();
		buffer.append(UC_AUTH_HTTP).append("/user/userInfo.do?sessionId=").append(ucCookieId);
		String result = HttpsClient.sendXMLDataByGet(buffer.toString(),1000,2000);
		Map<String,Object> resultMap = this.transResult(result);
		return resultMap;
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
