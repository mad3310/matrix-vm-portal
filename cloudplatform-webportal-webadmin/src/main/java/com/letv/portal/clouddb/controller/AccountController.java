package com.letv.portal.clouddb.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.letv.common.session.Executable;
import com.letv.common.session.Session;
import com.letv.common.session.SessionServiceImpl;
import com.letv.portal.model.UserModel;
import com.letv.portal.proxy.ILoginProxy;
import com.mysql.jdbc.StringUtils;


@Controller
@RequestMapping("/account")
public class AccountController {
	
	@Autowired(required=false)
	private SessionServiceImpl sessionService;
	
	
	@Autowired
	private ILoginProxy loginProxy;

	
	@RequestMapping("/login")
	public String login(HttpServletRequest request,HttpServletResponse response) {
		
		String loginName=request.getParameter("loginName");
		String password=request.getParameter("password");
		Session userSession  =  sessionService.getSession(); 
		
		if(StringUtils.isNullOrEmpty(loginName)) {
			return "/account/login";
		}
		
		if(!"sysadmin@letv.com".equals(loginName) ) {
			request.setAttribute("error", "用户名或密码错误！");
			return "/account/login";
		}
		
		if(!"000000".equals(password)) {
			request.setAttribute("error", "用户名或密码错误！");
			return "/account/login";
		}
		
		UserModel userModel = new UserModel(); 
		userModel.setUserName(loginName);
		userModel.setId(1L);	
		userSession =  loginProxy.createUserSession(userModel);
	    
		sessionService.runWithSession(userSession, "Usersession changed", new Executable<Session>(){
            @Override
            public Session execute() throws Throwable {
               return null;
            }
         });

		request.getSession().setAttribute("loginName", loginName);
		request.getSession().setAttribute("userId", loginName);
		request.getSession().setAttribute("role", "sysadmin");
		request.getSession().setMaxInactiveInterval(3600);//单位：秒
		return "redirect:/list/mcluster";
		
	}
	@RequestMapping("/logout")   //http://localhost:8080/account/logout
	public String logout(HttpServletRequest request,HttpServletResponse response) {
		request.getSession().invalidate();
		return "redirect:/account/login";
	}
	
}
