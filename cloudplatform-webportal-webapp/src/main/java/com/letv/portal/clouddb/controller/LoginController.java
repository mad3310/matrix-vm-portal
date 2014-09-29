package com.letv.portal.clouddb.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.letv.common.exception.ValidateException;
import com.letv.common.session.Session;
import com.letv.common.session.SessionServiceImpl;
import com.letv.common.util.WebUtil;
import com.letv.portal.model.UserLogin;
import com.letv.portal.proxy.ILoginProxy;
import com.letv.portal.service.ILoginService;

@Controller
public class LoginController{
	
	private String WEB_URL = "http://www.letv.com";
	
	@Autowired
	private ILoginService loginManager;
	
	@Autowired
	private SessionServiceImpl sessionService;
	
	@Autowired
	private ILoginProxy loginProxy;

	
	@RequestMapping("/login")
	public String login(HttpServletRequest request){
		
		String redirectUrl = WebUtil.getRequestUrl(request);
		String requestQueryStr = WebUtil.getRequestUri(request);
		
		if(!isReferrequest(request)
		&& request.getHeader("referer").indexOf("passport.letv.com")<0)
				throw new ValidateException("请确认请求地址正确");
		
		validateRetURL(redirectUrl);
		
		String userName = getUserNameFromPassportHeader(request, false);
		
		UserLogin userLogin = new UserLogin();
		userLogin.setUserName(userName);
		String ip = getIp(request);
		userLogin.setLoginIp(ip);
	    //loginProxy
		Session userSession = loginProxy.saveOrUpdateUserAndLogin(userLogin);
		//设置全局的session
//		sessionService.setSession(userSession, "login");
//		
		if (StringUtils.isEmpty(redirectUrl)) {
			return LogoutController.DASHBORAD_ADDRESS;
		}
		
		if(StringUtils.isNotEmpty(requestQueryStr))
		{
			requestQueryStr = WebUtil.urlDecode(requestQueryStr);
			redirectUrl += "?" + requestQueryStr;
		}
		
		return "redirectUrl";
   }
	
	
	private void validateRetURL(String retURL)
	{
		if(StringUtils.isNotEmpty(retURL))
		{	
			String tempRetURL = retURL.replaceAll("http:", "https:");
			String tempWEBURL = WEB_URL.replaceAll("http:", "https:");
			boolean result = tempRetURL.startsWith(tempWEBURL);
			if(!result)
				throw new ValidateException("请确认跳转地址正确");
		}
	}
	
	public boolean isReferrequest(HttpServletRequest request) {
		String referHeader = request.getHeader("referer");
		String tempWEBURL = WEB_URL.replaceAll("http:", "https:");
		if(StringUtils.isNotEmpty(referHeader) 
				&& 
		   referHeader.replaceAll("http:", "https:").startsWith(tempWEBURL))
			return true;
		return false;
	}
	
	public String getUserNameFromPassportHeader(HttpServletRequest request, boolean isOpenCheckByRequestUrl)
	{		
		String userNamePassport = "";
		
		if(!isOpenCheckByRequestUrl)  {
			userNamePassport = request.getHeader("X-LetvPassport-UserId");
			return userNamePassport;
		}
		
		return userNamePassport;
	}
	
	/**
     * 从HttpServletRequest实例中获取IP地址
     * 
     * @return 请求方IP地址
     */
	public static String getIp(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        if (ip.indexOf(",") > 0) {
            ip = ip.substring(0, ip.indexOf(","));
        }
        return ip;
    }
}
