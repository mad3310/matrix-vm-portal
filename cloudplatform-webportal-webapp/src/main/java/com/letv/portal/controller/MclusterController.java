package com.letv.portal.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.letv.common.util.HttpUtil;

@Controller
@RequestMapping("/mcluster")
public class MclusterController {
	
	@RequestMapping("/list/data")   //http://localhost:8080/mcluster/list/data
	public void listData(HttpServletRequest request,HttpServletResponse response) {
		//取出session中用户id，追加到http rest请求中
		Map<String,String> map = new HashMap<String,String>();
		map.put("createUser", (String) request.getSession().getAttribute("userId"));
		System.out.println(request.getParameter("mclusterName"));
		PrintWriter out;
		try {
			response.setContentType("text/html;charset=UTF-8");
			out = response.getWriter();
			out.write(HttpUtil.getResultFromDBAPI(request,"/mcluster/list",map));
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	@RequestMapping("/list/mgrData")   //http://localhost:8080/mcluster/list/mgrData
	public void mgrData(HttpServletRequest request,HttpServletResponse response) {
		PrintWriter out;
		try {
			response.setContentType("text/html;charset=UTF-8");
			out = response.getWriter();
			out.write(HttpUtil.getResultFromDBAPI(request,"/mcluster/list",null));
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	/*@RequestMapping("/list")   //http://localhost:8080/mcluster/list
	public String list(HttpServletRequest request,HttpServletResponse response) {
		return "/clouddb/mcluster_list";
	}*/
	@RequestMapping("/mgrList")   //http://localhost:8080/mcluster/list
	public String mgr_list(HttpServletRequest request,HttpServletResponse response) {
		return "/clouddb/mgr_mcluster_list";
	}
	
	@RequestMapping("/toCreate")   //http://localhost:8080/mcluster/toCreate
	public String toCreate(HttpServletRequest request,HttpServletResponse response) {
		return "/clouddb/mcluster_create";
	}
	@RequestMapping("/save")   //http://localhost:8080/mcluster/save
	public String save(HttpServletRequest request,HttpServletResponse response) {
		
		//取出session中用户id，追加到http rest请求中
		Map<String,String> map = new HashMap<String,String>();
		map.put("createUser", (String) request.getSession().getAttribute("userId"));
		map.put("status", "1");
		
		String result = HttpUtil.getResultFromDBAPI(request,"/mcluster/save",map);
		if(result.contains("\"result\":1")) {
			return "redirect:/mcluster/list";
		}
		return "/common/error";
	}
	
	@RequestMapping(value="/mclusterInfo")
	public String dbApplyInfo(HttpServletRequest request,HttpServletResponse response){
		return "/clouddb/mcluster_info";
	}

}
