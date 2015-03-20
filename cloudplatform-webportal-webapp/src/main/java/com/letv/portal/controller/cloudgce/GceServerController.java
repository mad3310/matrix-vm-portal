package com.letv.portal.controller.cloudgce;

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
import com.letv.portal.model.gce.GceServer;
import com.letv.portal.service.gce.IGceServerService;

@Controller
@RequestMapping("/gce")
public class GceServerController {
	
	@Autowired(required=false)
	private SessionServiceImpl sessionService;
	
	@Autowired
	private IGceServerService gceServerService;
	
	private final static Logger logger = LoggerFactory.getLogger(GceServerController.class);
	
	@RequestMapping(method=RequestMethod.GET)   
	public @ResponseBody ResultObject list(Page page,HttpServletRequest request,ResultObject obj) {
		Map<String,Object> params = HttpUtil.requestParam2Map(request);
		params.put("createUser", sessionService.getSession().getUserId());
		String gceName = (String) params.get("gceName");
		if(!StringUtils.isEmpty(gceName))
			params.put("gceName", StringUtil.transSqlCharacter(gceName));
		obj.setData(this.gceServerService.selectPageByParams(page, params));
		return obj;
	}
	
	@RequestMapping(method=RequestMethod.POST)   
	public @ResponseBody ResultObject save(GceServer gceServer,ResultObject obj) {
		if(gceServer == null || StringUtils.isEmpty(gceServer.getGceName()) || StringUtils.isEmpty(gceServer.getForwardPort())){
			throw new ValidateException("参数不合法");
		}else{
			
		}
		
		gceServer.setCreateUser(this.sessionService.getSession().getUserId());
		gceServer.setIp("10.58.88.163"); //get ip from python api
		gceServer.setGceClusterId(1L); //create cluster when create slbServer
		this.gceServerService.insert(gceServer);
		return obj;
	}
	
	@RequestMapping(value="/{id}",method=RequestMethod.GET)
	public @ResponseBody ResultObject detail(@PathVariable Long id){
		isAuthoritySlb(id);
		ResultObject obj = new ResultObject();
		GceServer slb = this.gceServerService.selectById(id);
		obj.setData(slb);
		return obj;
	}	
	
	private void isAuthoritySlb(Long id) {
		if(id == null)
			throw new ValidateException("参数不合法");
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("id", id);
		map.put("createUser", sessionService.getSession().getUserId());
		List<GceServer> slbs = this.gceServerService.selectByMap(map);
		if(slbs == null || slbs.isEmpty())
			throw new ValidateException("参数不合法");
	}
}
