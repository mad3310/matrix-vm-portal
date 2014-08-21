package com.letv.portal.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.letv.common.util.HttpUtil;

@Controller
@RequestMapping("/db")
public class DbController {
	@RequestMapping(value="/list")
	public String list(HttpServletRequest request,HttpServletResponse response){
		return "/clouddb/db_list";
	}	
	
	@RequestMapping(value="/toForm")
	public String toForm(HttpServletRequest request,HttpServletResponse response){
		request.setAttribute("clusterId", request.getParameter("clusterId"));
		return "/clouddb/db_applyform";
	}	
	
	@RequestMapping("/list/data")   //http://localhost:8080/db/list/data
	public void listData(HttpServletRequest request,HttpServletResponse response) {
		
		//取出session中用户id，追加到http rest请求中
		Map<String,String> map = new HashMap<String,String>();
		map.put("createUser", (String) request.getSession().getAttribute("userId"));
				
		PrintWriter out;
		try {
			response.setContentType("text/html;charset=UTF-8");
			out = response.getWriter();
			out.write(HttpUtil.getResultFromDBAPI(request,"/db/list",map));
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	@RequestMapping("/save")   //http://localhost:8080/mcluster/save
	public String save(HttpServletRequest request,HttpServletResponse response) {
		
		//取出session中用户id，追加到http rest请求中
		Map<String,String> map = new HashMap<String,String>();
		map.put("createUser", (String) request.getSession().getAttribute("userId"));
		map.put("status", "1");
		
		String result = HttpUtil.getResultFromDBAPI(request,"/dbApplyStandard/save",map);
		if(result.contains("\"result\":1")) {
			return "redirect:/db/list";
		}
		return "/common/error";
	}
	
	@RequestMapping(value="/dbApplyInfo")
	public String dbApplyInfo(HttpServletRequest request,HttpServletResponse response){
		return "/clouddb/db_apply_info";
	}
	
	@RequestMapping("/list/dbApplyInfo")   //http://localhost:8080/db/list/data
	public void listDbApplyInfo(HttpServletRequest request,HttpServletResponse response) {
		
		//取出session中用户id，追加到http rest请求中
		Map<String,String> map = new HashMap<String,String>();
		map.put("createUser", (String) request.getSession().getAttribute("userId"));
				
		PrintWriter out;
		try {
			response.setContentType("text/html;charset=UTF-8");
			out = response.getWriter();
			out.write(HttpUtil.getResultFromDBAPI(request,"/db/dbApplyInfo",map));
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
