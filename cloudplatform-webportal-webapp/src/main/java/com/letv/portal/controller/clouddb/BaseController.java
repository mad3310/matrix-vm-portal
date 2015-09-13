package com.letv.portal.controller.clouddb;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class BaseController {
	
	@Value("${oauth.auth.http}")
	private String OAUTH_AUTH_HTTP;
	@Value("${webportal.local.http}")
	private String WEBPORTAL_LOCAL_HTTP;
	
	@RequestMapping(value="/404",method=RequestMethod.GET)   
	public String notFound(HttpServletRequest request,HttpServletResponse response) {
		return "/error/404";
	}
	@RequestMapping(value="/500",method=RequestMethod.GET)   
	public String webportalException(HttpServletRequest request,HttpServletResponse response) {
		return "/error/500";
	}
	@RequestMapping(value="/browserError",method=RequestMethod.GET)   
	public String browserErrorException(HttpServletRequest request,HttpServletResponse response) {
		return "/error/browserError";
	}
	@RequestMapping(value="/home",method=RequestMethod.GET)   
	public String home(HttpServletRequest request,HttpServletResponse response) {
		return "/portal/home";
	}
	
	@RequestMapping(value ="/toLogin")
	public ModelAndView toLogin(@RequestParam String back,ModelAndView mav){
		StringBuffer buffer = new StringBuffer();
//		buffer.append(OAUTH_AUTH_HTTP).append("/index?redirect_uri=").append(WEBPORTAL_LOCAL_HTTP).append("/oauth/callback");
		buffer.append("http://uc.letvcloud.com").append("/login.do?backUrl=").append("http://matrix.letvcloud.com:8081/").append(back);
		mav.addObject("loginURI", buffer.toString());
		mav.setViewName("/toLogin");
		return mav;
	}
}
