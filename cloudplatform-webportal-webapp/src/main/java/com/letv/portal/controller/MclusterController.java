package com.letv.portal.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

@Controller
@RequestMapping("/mcluster")
public class MclusterController {
	public static final String API_URL = "http://10.58.164.66:8080/api";
	
	@RequestMapping("/list")   //http://localhost:8080/mcluster/list
	public void list(HttpServletRequest request,HttpServletResponse response) {
		RestTemplate restTemplate = new RestTemplate();
		
		StringBuffer buffer = new StringBuffer();
		
		buffer.append(API_URL)
			  .append("/mcluster/list?")
			  .append("currentPage=")
			  .append(request.getParameter("currentPage"))
			  .append("&recordsPerPage=")
			  .append(request.getParameter("recordsPerPage"))
			  /*.append("&mclusterName=")
			  .append("".equals(request.getParameter("mclusterName"))?"":request.getParameter("mclusterName"))*/;
		System.out.println(buffer);
		String message = restTemplate.postForObject(buffer.toString(), null,String.class );
		System.out.println(message);
		PrintWriter out;
		try {
			response.setContentType("text/html;charset=UTF-8");
			out = response.getWriter();
			out.write(message);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
