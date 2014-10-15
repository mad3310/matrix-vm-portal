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


/**Program Name: SessionInterceptor <br>
 * Description:  session拦截类，暂不处理<br>
 * @author name: liuhao1 <br>
 * Written Date: 2014年10月15日 <br>
 * Modified By: <br>
 * Modified Date: <br>
 */
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
	public boolean preHandle(HttpServletRequest request,HttpServletResponse response, Object handler) throws Exception {
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
	}
} 
