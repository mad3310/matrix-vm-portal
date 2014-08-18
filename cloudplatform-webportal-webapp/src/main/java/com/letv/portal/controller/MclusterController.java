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
@RequestMapping("/mcluster")
public class MclusterController {
	
	@RequestMapping("/list")   //http://localhost:8080/mcluster/list
	public void list(HttpServletRequest request,HttpServletResponse response) {
		PrintWriter out;
		try {
			response.setContentType("text/html;charset=UTF-8");
			out = response.getWriter();
			out.write(HttpUtil.getResultFromDBAPI(request,"/mcluster/list"));
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	} 

}
