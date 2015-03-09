package com.letv.portal.controller.cloudslb;

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
import com.letv.common.util.StringUtil;
import com.letv.portal.model.slb.SlbServer;
import com.letv.portal.service.slb.ISlbServerService;

@Controller
@RequestMapping("/slb")
public class SlbServerController {
	
	@Autowired(required=false)
	private SessionServiceImpl sessionService;
	
	@Autowired
	private ISlbServerService slbServerService;
	
	private final static Logger logger = LoggerFactory.getLogger(SlbServerController.class);
	
	@RequestMapping(method=RequestMethod.GET)   
	public @ResponseBody ResultObject list(Page page,HttpServletRequest request,ResultObject obj) {
		Map<String,Object> params = HttpUtil.requestParam2Map(request);
		params.put("createUser", sessionService.getSession().getUserId());
		String slbName = (String) params.get("slbName");
		if(!StringUtils.isEmpty(slbName))
			params.put("slbName", StringUtil.transSqlCharacter(slbName));
		obj.setData(this.slbServerService.selectPageByParams(page, params));
		return obj;
	}
	
	@RequestMapping(method=RequestMethod.POST)   
	public @ResponseBody ResultObject save(SlbServer slbServer,ResultObject obj) {
		if(slbServer == null || StringUtils.isEmpty(slbServer.getSlbName()))
			throw new ValidateException("参数不合法");
		slbServer.setCreateUser(this.sessionService.getSession().getUserId());
		slbServer.setIp("10.58.88.163"); //get ip from python api
		slbServer.setSlbClusterId(1L); //create cluster when create slbServer
		this.slbServerService.insert(slbServer);
		return obj;
	}
	
	@RequestMapping(value="/{id}",method=RequestMethod.GET)
	public @ResponseBody ResultObject detail(@PathVariable Long id){
		isAuthoritySlb(id);
		ResultObject obj = new ResultObject();
		SlbServer slb = this.slbServerService.selectById(id);
		obj.setData(slb);
		return obj;
	}	
	
	
	
	private void isAuthoritySlb(Long id) {
		if(id == null)
			throw new ValidateException("参数不合法");
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("id", id);
		map.put("createUser", sessionService.getSession().getUserId());
		List<SlbServer> slbs = this.slbServerService.selectByMap(map);
		if(slbs == null || slbs.isEmpty())
			throw new ValidateException("参数不合法");
	}
}
