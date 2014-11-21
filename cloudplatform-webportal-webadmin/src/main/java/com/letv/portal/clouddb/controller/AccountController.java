package com.letv.portal.clouddb.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.letv.common.session.Executable;
import com.letv.common.session.Session;
import com.letv.common.session.SessionServiceImpl;
import com.letv.common.util.ConfigUtil;
import com.letv.common.util.PasswordEncoder;
import com.letv.portal.model.UserLogin;
import com.letv.portal.model.UserModel;
import com.letv.portal.proxy.ILoginProxy;
import com.letv.portal.service.IUserService;
import com.mysql.jdbc.StringUtils;


@Controller
@RequestMapping("/account")
public class AccountController {
	
	@Autowired(required=false)
	private SessionServiceImpl sessionService;
	@Autowired
	private IUserService userService;
	
	@Autowired
	private ILoginProxy loginProxy;

	
	@RequestMapping("/login")
	public String login(HttpServletRequest request,HttpServletResponse response) {
		
		String loginName=request.getParameter("loginName");
		String password=request.getParameter("password");
		
		if(StringUtils.isNullOrEmpty(loginName)) {
			return "/account/login";
		} else {
			
			/*UserModel user = this.userService.getUserByName(loginName);
			
			if(null == user) {
				request.setAttribute("error", "用户名或密码错误！");
				return "/account/login";
			}
			if(1 != user.getType()) {
				request.setAttribute("error", "用户无权限！");
				return "/account/login";
			}
			Boolean validate = PasswordEncoder.isPasswordValid4MD5(user.getPassword(), password, "webportal");
			
			if(!validate) {
				request.setAttribute("error", "用户名或密码错误！");
				return "/account/login";
			}*/
			
			if(!"sysadmin".equals(loginName) || !ConfigUtil.getString("admin.pwd").equals(password) ) {
				request.setAttribute("error", "用户名或密码错误！");
				return "/account/login";
			}
			UserLogin userLogin = new UserLogin();
			userLogin.setUserName(loginName);
			userLogin.setLoginIp(getIp(request));
			Session session = this.loginProxy.saveOrUpdateUserAndLogin(userLogin);
			request.getSession().setAttribute(Session.USER_SESSION_REQUEST_ATTRIBUTE, session);
			
			sessionService.runWithSession(session, "Usersession changed", new Executable<Session>(){
	            @Override
	            public Session execute() throws Throwable {
	               return null;
	            }
	         });
			return "redirect:/dashboard";
		}
		
	}
	
	@RequestMapping("/logout")   //http://localhost:8080/account/logout
	public String logout(HttpServletRequest request,HttpServletResponse response) {
		sessionService.runWithSession(null, "用户退出", new Executable<Session>(){
            @Override
            public Session execute() throws Throwable {
               return null;
            }
         });
		request.getSession().invalidate();
		sessionService.setSession(null,"logout");
		
		return "redirect:/account/login";
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
