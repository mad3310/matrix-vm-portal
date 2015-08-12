package com.letv.portal.controller.user;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.letv.common.paging.impl.Page;
import com.letv.common.result.ResultObject;
import com.letv.common.util.HttpUtil;
import com.letv.portal.service.IUserService;
import com.letv.portal.service.adminoplog.ClassAoLog;

@ClassAoLog(module="用户管理")
@Controller
@RequestMapping("/user")
public class UserController {
	@Resource
	private IUserService  userService;

	
	private final static Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@RequestMapping(method=RequestMethod.GET)   
	public @ResponseBody ResultObject listAll(Page page,HttpServletRequest request,ResultObject obj) {
		Map<String,Object> params = HttpUtil.requestParam2Map(request);
		obj.setData(this.userService.selectByMap(params));
		return obj;
	}
	

	
}
