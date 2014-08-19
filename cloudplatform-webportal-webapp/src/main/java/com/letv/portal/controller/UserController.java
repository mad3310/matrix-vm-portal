package com.letv.portal.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import com.letv.common.util.HttpUtil;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@RequestMapping("/login")   //http://localhost:8080/user/login
	public String login(HttpServletRequest request,HttpServletResponse response) {
		String username=request.getParameter("email");
		String password=request.getParameter("password");
		//验证过程
		String userId="1";
		
		request.getSession().setAttribute("username", username);
		request.getSession().setAttribute("userId", userId);
		
		return "/clouddb/mcluster_list";
		
	}
	

}
