package com.letv.portal.clouddb.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.letv.common.paging.impl.Page;
import com.letv.common.result.ResultObject;
import com.letv.common.session.SessionServiceImpl;
import com.letv.portal.constant.Constant;
import com.letv.portal.model.DbModel;
import com.letv.portal.service.IContainerService;
import com.letv.portal.service.IDbService;
import com.letv.portal.service.IDbUserService;
import com.letv.portal.service.IMclusterService;
import com.letv.portal.service.IUserService;

@Controller
@RequestMapping("/user")
public class UserController {
	@Resource
	private IUserService  userService;

	
	private final static Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@RequestMapping(value="/{userId}", method=RequestMethod.GET)   
	public @ResponseBody ResultObject getUserById(@PathVariable Long userId) {
		ResultObject obj = new ResultObject();
		obj.setData(this.userService.getUserById(userId));
		return obj;
	}
	

	
}
