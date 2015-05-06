package com.letv.portal.controller.cloudswift;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.letv.common.exception.ValidateException;
import com.letv.common.paging.impl.Page;
import com.letv.common.result.ResultObject;
import com.letv.common.session.SessionServiceImpl;
import com.letv.common.util.HttpUtil;
import com.letv.portal.model.slb.SlbServer;
import com.letv.portal.model.swift.SwiftServer;
import com.letv.portal.proxy.ISwiftServerProxy;
import com.letv.portal.service.swift.ISwiftServerService;

@Controller
@RequestMapping("/oss")
public class SwiftServerController {
	
	@Autowired(required=false)
	private SessionServiceImpl sessionService;
	
	@Autowired
	private ISwiftServerService swiftServerService;
	@Autowired
	private ISwiftServerProxy swiftServerProxy;
	
	private final static Logger logger = LoggerFactory.getLogger(SwiftServerController.class);
	
	@RequestMapping(method=RequestMethod.GET)   
	public @ResponseBody ResultObject list(Page page,HttpServletRequest request,ResultObject obj) {
		Map<String,Object> params = HttpUtil.requestParam2Map(request);
		params.put("createUser", sessionService.getSession().getUserId());
		obj.setData(this.swiftServerService.selectPageByParams(page, params));
		return obj;
	}
	
	@RequestMapping(method=RequestMethod.POST)   
	public @ResponseBody ResultObject save(SwiftServer swift,ResultObject obj) {
		if(swift == null || StringUtils.isEmpty(swift.getName()))
			throw new ValidateException("参数不合法");
		swift.setCreateUser(this.sessionService.getSession().getUserId());
		this.swiftServerProxy.saveAndBuild(swift);
		return obj;
	}
	
	@RequestMapping(value="/{id}",method=RequestMethod.GET)
	public @ResponseBody ResultObject detail(@PathVariable Long id){
		isAuthoritySwift(id);
		ResultObject obj = new ResultObject();
		SwiftServer swift = this.swiftServerService.selectById(id);
		obj.setData(swift);
		return obj;
	}	
	
	private void isAuthoritySwift(Long id) {
		if(id == null)
			throw new ValidateException("参数不合法");
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("id", id);
		map.put("createUser", sessionService.getSession().getUserId());
		List<SwiftServer> swifts = this.swiftServerService.selectByMap(map);
		if(swifts == null || swifts.isEmpty())
			throw new ValidateException("参数不合法");
	}
}
