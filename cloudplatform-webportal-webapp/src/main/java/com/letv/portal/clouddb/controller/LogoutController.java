package com.letv.portal.clouddb.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.letv.common.exception.CommonException;
import com.letv.common.session.Session;
import com.letv.common.session.SessionServiceImpl;
import com.letv.common.util.ConfigUtil;
import com.letv.portal.service.ILoginService;

@Controller
public class LogoutController{

	public static final String DASHBORAD_ADDRESS = "/dashboard";
	
	private final static String CAS_AUTH_HTTP = ConfigUtil.getString("cas.auth.http");
	private final static String CAS_LOCAL_HTTP = ConfigUtil.getString("cas.local.http");
	
	@Autowired
	private ILoginService loginService;
	
	@Autowired
	private SessionServiceImpl sessionService;
	
	/**Methods Name: logout <br>
	 * Description: 用户登出<br>
	 * @author name: liuhao1
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/logout",method=RequestMethod.GET)   //http://localhost:8080/account/logout
	public String logout(HttpServletRequest request, HttpServletResponse response){
		Session session = (Session)request.getSession().getAttribute(Session.USER_SESSION_REQUEST_ATTRIBUTE);
		
		loginService.logout();
		
		sessionService.setSession(null,"logout");
		
//		String logoutAddress = PASSPORT_LOGOUT_ADDRESS + WEB_URL + DASHBORAD_ADDRESS;
		String logoutAddress = CAS_AUTH_HTTP + "/cas/logout?service=" + CAS_LOCAL_HTTP;
		try {
			response.sendRedirect(logoutAddress);
		} catch (IOException e) {
			throw new CommonException("logout时出现异常！", e);
		}
		return null;
   }
}
