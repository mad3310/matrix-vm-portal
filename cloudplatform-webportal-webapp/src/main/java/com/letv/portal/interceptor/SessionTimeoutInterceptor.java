package com.letv.portal.interceptor;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jasig.cas.client.authentication.AttributePrincipal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.letv.common.result.ResultObject;
import com.letv.common.session.Executable;
import com.letv.common.session.Session;
import com.letv.common.session.SessionServiceImpl;
import com.letv.common.util.ConfigUtil;
import com.letv.portal.clouddb.controller.LoginController;
import com.letv.portal.model.UserLogin;
import com.letv.portal.proxy.ILoginProxy;

/**
 * 处理session超时的拦截器
 */
@Component
public class SessionTimeoutInterceptor  implements HandlerInterceptor{
	private final static Logger logger = LoggerFactory.getLogger(SessionTimeoutInterceptor.class);
	
	private final static String CAS_AUTH_HTTP = ConfigUtil.getString("cas.auth.http");
	private final static String CAS_LOCAL_HTTP = ConfigUtil.getString("cas.local.http");
	
	@Autowired(required=false)
	private SessionServiceImpl sessionService;
	
	@Autowired
	private ILoginProxy loginProxy;
	
	public String[] allowUrls;//还没发现可以直接配置不拦截的资源，所以在代码里面来排除
	
	public void setAllowUrls(String[] allowUrls) {
		this.allowUrls = allowUrls;
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
			Object arg2) throws Exception {
		String requestUrl = request.getRequestURI().replace(request.getContextPath(), "");  
		if(null != allowUrls && allowUrls.length>=1) {
			for(String url : allowUrls) {  
				if(requestUrl.contains(url)) {  
					return true;  
				}  
			}
		}
		AttributePrincipal principal = (AttributePrincipal) request.getUserPrincipal();
		if(principal != null) {
			Session session = (Session) request.getSession().getAttribute(Session.USER_SESSION_REQUEST_ATTRIBUTE);
			if(session == null) {
				UserLogin userLogin = new UserLogin();
				userLogin.setUserName(principal.getName());
				userLogin.setLoginIp(LoginController.getIp(request));
				session = this.loginProxy.saveOrUpdateUserAndLogin(userLogin);
				request.getSession().setAttribute(Session.USER_SESSION_REQUEST_ATTRIBUTE, session);
				
			} 
			sessionService.runWithSession(session, "Usersession changed", new Executable<Session>(){
				@Override
				public Session execute() throws Throwable {
					return null;
				}
			});
		} 
		return true;
		
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