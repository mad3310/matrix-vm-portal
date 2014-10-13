package com.letv.portal.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.jasig.cas.client.authentication.AttributePrincipal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.letv.common.exception.NoSessionException;
import com.letv.common.session.Executable;
import com.letv.common.session.Session;
import com.letv.common.session.SessionServiceImpl;
import com.letv.portal.clouddb.controller.LoginController;
import com.letv.portal.model.UserLogin;
import com.letv.portal.proxy.ILoginProxy;
import com.letv.portal.service.ILoginService;


public class SessionInterceptor implements HandlerInterceptor {
	private final static Logger logger = LoggerFactory.getLogger(SessionInterceptor.class);
	
	@Autowired(required=false)
	private SessionServiceImpl sessionService;
	
	@Autowired(required=false)
	private ILoginService loginService;
	
	@Autowired
	private ILoginProxy loginProxy;
	
	private String[] allowUrls;//还没发现可以直接配置不拦截的资源，所以在代码里面来排除
	
	public void setAllowUrls(String[] allowUrls) {
		this.allowUrls = allowUrls;
	}

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
      //      HttpSession session = request.getSession();
    //       Session userSession = (Session)session.getAttribute(Session.USER_SESSION_REQUEST_ATTRIBUTE);
        Session userSession  =  sessionService.getSession();   
    	if (null == userSession) {
    		AttributePrincipal principal = (AttributePrincipal) request.getUserPrincipal();
            String userName = principal.getName();
            
            String userNamePassport = userName + "@letv.com";
            
            if(logger.isInfoEnabled())
				logger.info("checking session,passportUserName:"+userNamePassport);
            
            if(StringUtils.isEmpty(userNamePassport)) {
				if(null == userSession)
					throw new NoSessionException("请重新登录!");
			} else {
				if( null == userSession || (null!=userSession && !userSession.getUserName().equals(userNamePassport))) {
					UserLogin userLogin = new UserLogin();
					userLogin.setUserName(userNamePassport);
					userLogin.setLoginIp(LoginController.getIp(request));
					userSession = loginProxy.saveOrUpdateUserAndLogin(userLogin);				
	//				session.setAttribute(Session.USER_SESSION_REQUEST_ATTRIBUTE,userSession);
					if(logger.isInfoEnabled())
						logger.info("checking session,passportUserName："+userNamePassport+",but UserSession is null,so restore UserSession,this process finished!");
				}
			}
        }
		
		sessionService.runWithSession(userSession, "Usersession changed", new Executable<Session>(){
            @Override
            public Session execute() throws Throwable {
               return null;
            }
         });
		return true;
	}


	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		
	}


	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		
		sessionService.runWithSession(null, "Usersession changed", new Executable<Session>(){
            @Override
            public Session execute() throws Throwable {
               return null;
            }
         });
		
	}
} 
