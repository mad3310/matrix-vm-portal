package com.letv.portal.clouddb.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.jasig.cas.client.authentication.AttributePrincipal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.letv.common.exception.NoSessionException;
import com.letv.common.exception.ValidateException;
import com.letv.common.session.Executable;
import com.letv.common.session.Session;
import com.letv.common.session.SessionServiceImpl;
import com.letv.portal.model.UserLogin;
import com.letv.portal.proxy.ILoginProxy;
import com.letv.portal.service.ILoginService;

@Controller
@RequestMapping(value="/account")
public class LoginController{
	
	private static String WEB_URL = "http://www.letv.com";
	
	private final static Logger logger = LoggerFactory.getLogger(LoginController.class);
	
	@Autowired
	private ILoginService loginManager;
	
	@Autowired
	private SessionServiceImpl sessionService;
	
	@Autowired
	private ILoginProxy loginProxy;

	
	/**Methods Name: login <br>
	 * Description: cas登录完成后，跳转到本页面做相关用户记录，然后跳转到业务界面<br>
	 * @author name: liuhao1
	 * @param request
	 * @param mav
	 * @return
	 */
	@RequestMapping("/login")
	public ModelAndView login(HttpServletRequest request,ModelAndView mav) {
		
		AttributePrincipal principal = (AttributePrincipal) request.getUserPrincipal();
		if(principal != null) {
			UserLogin userLogin = new UserLogin();
			userLogin.setLoginName(principal.getName());
			userLogin.setLoginIp(LoginController.getIp(request));
			Session session = this.loginProxy.saveOrUpdateUserAndLogin(userLogin);
			request.getSession().setAttribute(Session.USER_SESSION_REQUEST_ATTRIBUTE, session);
			sessionService.runWithSession(session, "Usersession changed", new Executable<Session>(){
	            @Override
	            public Session execute() throws Throwable {
	               return null;
	            }
	         });
			logger.info("User:"+principal.getName()+"login success!");
		} else {
			//重新登录
			throw new NoSessionException("请重新登录!");
		}
        
		mav.setViewName("redirect:/dashboard");
		return mav;
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
