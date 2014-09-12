package com.letv.portal.clouddb.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;



import com.mysql.jdbc.StringUtils;


@Controller
@RequestMapping("/account")
public class AccountController {
	private final static Logger logger = LoggerFactory.getLogger(AccountController.class);
	
	@RequestMapping("/login")   //http://localhost:8080/account/login
	public String login(HttpServletRequest request,HttpServletResponse response) {
		
		String loginName=request.getParameter("loginName");
		String password=request.getParameter("password");
		if(StringUtils.isNullOrEmpty(loginName)) {
			return "/account/login";
		}
		
		if(!"yaokuo@letv.com".equals(loginName) ) {
			request.setAttribute("error", "用户名或密码错误！");
			return "/account/login";
		}
		
		if(!"000000".equals(password)) {
			request.setAttribute("error", "用户名或密码错误！");
			return "/account/login";
		}
		
		request.getSession().setAttribute("username", loginName);
		request.getSession().setAttribute("userId", loginName);
		request.getSession().setAttribute("role", "user");
		return "redirect:/db/list";

	}
	@RequestMapping("/logout")   //http://localhost:8080/account/logout
	public String logout(HttpServletRequest request,HttpServletResponse response) {
		logger.debug("logout===>");
		request.getSession().removeAttribute("username");
		request.getSession().removeAttribute("userId");
		request.getSession().removeAttribute("role");
		
		return "redirect:http://cas.oss.letv.cn:7777/cas/logout?service=http://10.58.166.19:8081";
	}
	
	

}
