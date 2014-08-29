package com.letv.portal.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@RequestMapping("/login")   //http://localhost:8080/user/login
	public String login(HttpServletRequest request,HttpServletResponse response) {
		String username=request.getParameter("email");
		String password=request.getParameter("password");
		
		request.getSession().setAttribute("username", username);
		request.getSession().setAttribute("userId", username);
		
		return "redirect:/db/list";
		
	}
	

}
