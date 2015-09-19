package com.letv.portal.interceptor;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.letv.common.exception.OauthException;
import com.letv.common.result.ResultObject;
import com.letv.common.session.Executable;
import com.letv.common.session.Session;
import com.letv.common.session.SessionServiceImpl;
import com.letv.common.util.CookieUtil;
import com.letv.common.util.HttpsClient;
import com.letv.common.util.IpUtil;
import com.letv.portal.controller.user.UserVo;
import com.letv.portal.model.UserLogin;
import com.letv.portal.proxy.ILoginProxy;
import com.letv.portal.service.openstack.OpenStackService;
import com.mysql.jdbc.StringUtils;

/**
 * 处理session超时的拦截器
 */
@Component
public class SessionTimeoutInterceptor  implements HandlerInterceptor{
	private final static Logger logger = LoggerFactory.getLogger(SessionTimeoutInterceptor.class);
	
	@Autowired(required=false)
	private SessionServiceImpl sessionService;
	
	@Autowired
	private ILoginProxy loginProxy;
	
	@Autowired
	private OpenStackService openStackService;
	
	@Value("${uc.auth.http}")
	private String UC_AUTH_HTTP;
	
	public String[] allowUrls;
	
	public void setAllowUrls(String[] allowUrls) {
		this.allowUrls = allowUrls;
	}
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
			Object arg2) throws Exception {
		if(allowUrl(request))
			return true;
		
		Session session = (Session) request.getSession().getAttribute(Session.USER_SESSION_REQUEST_ATTRIBUTE);
		Cookie ucCookie = CookieUtil.getCookieByName(request, Session.UC_COOKIE_KEY);
		
		if(session == null && ucCookie !=null) {
			session = getUserdetailinfo(ucCookie.getValue(),request);
		}
		if(ucCookie == null ) {
			return toLogin(request, response);
		} else {
			return pass(session,request);
		}
	}
	
	/**Methods Name: getUserdetailinfo <br>
	 * Description: <br>
	 * @author name: liuhao1
	 * @param ucCookieId
	 * @param request
	 * @return 
	 * {
			"contacts":"liuhao",
			"countryCode":"86",
			"createdTime":"2015-09-07 15:08:44",
			"email":"liuhao1@letv.com",
			"id":400054,
			"isOld":1,
			"lastUpdateTime":"2015-09-07 15:08:44",
			"mobile":"18510086398",
			"mobileStatus":1,
			"siteDomain":"",
			"siteName":"",
			"siteType":18,
			"userKey":"41edba4711b7619632ba2fc6f6298af6",
			"userStatus":1,
			"userType":2,
			"userUnique":"l7w7u8yt77"
		}
	 * 
	 * 
	 * 
	 * 
	 * @throws Exception
	 */
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

	private boolean allowUrl(HttpServletRequest request){
		String requestUrl = request.getRequestURI().replace(request.getContextPath(), "");  
		//特殊url过滤
		if(null != allowUrls && allowUrls.length>=1) {
			for(String url : allowUrls) {  
				if(requestUrl.contains(url)) {  
					return true;  
				}  
			}
		}
		if("/".equals(requestUrl)) {
			return true; 
		}
		return false;
	}
	
	private boolean toLogin(HttpServletRequest request,HttpServletResponse response) throws Exception{
		boolean isAjaxRequest = (request.getHeader("x-requested-with") != null)? true:false;
		if (isAjaxRequest) {
			responseJson(request,response,"长时间未操作，请重新登录");
		} else {
			RequestDispatcher rd = request.getRequestDispatcher("/toLogin?back="+request.getRequestURI());
			rd.forward(request, response);
		}
		return false;
	}
	private boolean pass(Session session,HttpServletRequest request){
		request.getSession().setAttribute(Session.USER_SESSION_REQUEST_ATTRIBUTE, session);
		
		sessionService.runWithSession(session, "Usersession changed", new Executable<Session>(){
			@Override
			public Session execute() throws Throwable {
				return null;
			}
		});
		return true;
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
	
	
	@Override
	public void afterCompletion(HttpServletRequest arg0,
			HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1,
			Object arg2, ModelAndView arg3) throws Exception {
	}
	
	private void responseJson(HttpServletRequest req, HttpServletResponse res, String message) {
    	PrintWriter out = null;
		try {
			res.setContentType("text/html;charset=UTF-8");
			out = res.getWriter();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		ResultObject resultObject = new ResultObject(0);
		resultObject.addMsg(message);
		out.append(JSON.toJSONString(resultObject, SerializerFeature.WriteMapNullValue));
		out.flush();
	}

}