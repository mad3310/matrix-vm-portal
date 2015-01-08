package com.letv.portal.clouddb.controller;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.letv.common.exception.ValidateException;
import com.letv.common.result.ResultObject;
import com.letv.portal.proxy.IDbUserProxy;
import com.letv.portal.service.IDbUserService;
import com.mysql.jdbc.StringUtils;

@Controller
@RequestMapping("/dbIp")
public class DbIpController {
	
	@Resource
	private IDbUserService dbUserService;
	@Resource
	private IDbUserProxy dbUserProxy;
	
	private final static Logger logger = LoggerFactory.getLogger(DbIpController.class);
		
	@RequestMapping(value="/{dbId}", method=RequestMethod.GET)   
	public @ResponseBody ResultObject list(@PathVariable Long dbId,ResultObject obj) {
		if(null == dbId) {
			throw new ValidateException("参数不能为空");
		}
		obj.setData(this.dbUserService.selectIpsFromUser(dbId));
		return obj;
	}
	@RequestMapping(method=RequestMethod.POST)   
	public @ResponseBody ResultObject save(Long dbId,String ips,ResultObject obj) {
		if(null == dbId || StringUtils.isNullOrEmpty(ips)) {
			throw new ValidateException("参数不能为空");
		} else {
			this.dbUserProxy.saveOrUpdateIps(dbId,ips);
		}
		return obj;
	}
	
	@RequestMapping(value="/{dbId}/{username}", method=RequestMethod.GET)   
	public @ResponseBody ResultObject ips4dbUser(@PathVariable Long dbId,@PathVariable String username,ResultObject obj) {
		if(null == dbId) {
			throw new ValidateException("参数不能为空");
		} else {
			obj.setData(this.dbUserService.selectMarkIps4dbUser(dbId,username));
			return obj;
		}
	}
}
