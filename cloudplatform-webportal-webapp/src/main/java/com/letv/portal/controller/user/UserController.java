package com.letv.portal.controller.user;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.letv.common.exception.OauthException;
import com.letv.common.result.ResultObject;
import com.letv.common.session.Session;
import com.letv.common.util.CookieUtil;
import com.letv.common.util.HttpsClient;
import com.letv.portal.service.openstack.OpenStackService;
import com.mysql.jdbc.StringUtils;


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
	@Autowired
	private OpenStackService openStackService;
	
	private final static Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@RequestMapping(method=RequestMethod.GET)   
	public @ResponseBody ResultObject account(HttpServletRequest request,HttpServletResponse response,ResultObject obj) throws Exception{
		Session session = (Session) request.getSession().getAttribute(Session.USER_SESSION_REQUEST_ATTRIBUTE);
		Cookie ucCookie = CookieUtil.getCookieByName(request, Session.UC_COOKIE_KEY);
		
		if(session == null && ucCookie !=null) {
			session = getUserdetailinfo(ucCookie.getValue(),request);
		}
		
		if(session == null ) {
			obj.setResult(0);
		} else {
			obj.setData(session.getUserVo());
		}
		
		return obj;
	}
	
	private Session getUserdetailinfo(String ucCookieId,HttpServletRequest request) throws Exception{
		Map<String, Object> userdetailinfo = this.getUserdetailinfo(ucCookieId);
		if(null == userdetailinfo || userdetailinfo.isEmpty()||null == userdetailinfo.get("id"))
			return null;
		
		Long userId = Long.valueOf((Integer) userdetailinfo.get("id"));
		String username = (String) userdetailinfo.get("contacts");
		String email = (String) userdetailinfo.get("email");
		String mobile = (String) userdetailinfo.get("mobile");
		Integer mobileStatus = (Integer) userdetailinfo.get("mobileStatus");
		
		Session session = new Session(userId);
		session.setUserName(username);
		session.setEmail(email);
		session.setMobile(mobile);
		
		session.setUserVo(new UserVo(userId,username,email,mobile,mobileStatus));
		
		session.setOpenStackSession(openStackService.createSession(email,email,username));
		return session;
	}
	
	private Map<String,Object> getUserdetailinfo(String ucCookieId) {
		StringBuffer buffer = new StringBuffer();
		buffer.append(UC_AUTH_HTTP).append("/user/userInfo.do?sessionId=").append(ucCookieId);
		String result = HttpsClient.sendXMLDataByGet(buffer.toString(),1000,2000);
		if(StringUtils.isNullOrEmpty(result))
			throw new OauthException("getUserdetailinfo connection timeout");
		Map<String,Object> resultMap = this.transResult(result);
		return resultMap;
	}
	
	private Map<String,Object> transResult(String result){
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
