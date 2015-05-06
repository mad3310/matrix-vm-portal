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
import com.letv.portal.enumeration.GceImageStatus;
import com.letv.portal.enumeration.GceStatus;
import com.letv.portal.model.gce.GceImage;
import com.letv.portal.model.gce.GceServer;
import com.letv.portal.model.slb.SlbServer;
import com.letv.portal.proxy.IGceProxy;
import com.letv.portal.service.gce.IGceImageService;
import com.letv.portal.service.gce.IGceServerService;

@Controller
@RequestMapping("/gce")
public class GceServerController {
	
	@Autowired(required=false)
	private SessionServiceImpl sessionService;
	@Autowired
	private IGceServerService gceServerService;
	@Autowired
	private IGceImageService gceImageService;
	@Autowired
	private IGceProxy gceProxy;
	
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
		if(gceServer == null || StringUtils.isEmpty(gceServer.getGceName())){
			throw new ValidateException("参数不合法");
		}else{
			
		}
		gceServer.setCreateUser(this.sessionService.getSession().getUserId());
		this.gceProxy.saveAndBuild(gceServer);
		return obj;
	}
	
	@RequestMapping(value="/restart",method=RequestMethod.POST)   
	public @ResponseBody ResultObject restart(Long id,ResultObject obj) {
		isAuthorityGce(id);
		this.gceProxy.restart(id);
		return obj;
	}
	@RequestMapping(value="/start",method=RequestMethod.POST)   
	public @ResponseBody ResultObject start(Long id,ResultObject obj) {
		isAuthorityGce(id);
		this.gceProxy.start(id);
		return obj;
	}
	@RequestMapping(value="/stop",method=RequestMethod.POST)   
	public @ResponseBody ResultObject stop(Long id,ResultObject obj) {
		isAuthorityGce(id);
		this.gceProxy.stop(id);
		return obj;
	}
	
	@RequestMapping(value="/{id}",method=RequestMethod.GET)
	public @ResponseBody ResultObject detail(@PathVariable Long id){
		isAuthorityGce(id);
		ResultObject obj = new ResultObject();
		GceServer gce = this.gceServerService.selectById(id);
		obj.setData(gce);
		return obj;
	}
	
	@RequestMapping(value="/image/list/{type}",method=RequestMethod.GET)
	public @ResponseBody ResultObject getImage(@PathVariable String type){
		ResultObject obj = new ResultObject();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("owner", sessionService.getSession().getUserId());
		map.put("type", type);
		map.put("status", GceImageStatus.AVAILABLE);
		
		List<GceImage> gceImages= this.gceImageService.selectByMap(map);
		obj.setData(gceImages);
		return obj;
	}	
	@RequestMapping(value="/log/url",method=RequestMethod.POST)
	public @ResponseBody ResultObject getImageByUrl(String imageUrl){
		ResultObject obj = new ResultObject();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("owner", sessionService.getSession().getUserId());
		map.put("url",imageUrl);
		map.put("status", GceImageStatus.AVAILABLE);
		
		List<GceImage> gceImages= this.gceImageService.selectByMap(map);
		if(gceImages ==null || gceImages.isEmpty())
			return obj;
		obj.setData(gceImages.get(0).getLogUrl());
		return obj;
	}	
	
	@RequestMapping( value="/{id}",method=RequestMethod.DELETE)   
	public @ResponseBody ResultObject delete(@PathVariable Long id,ResultObject obj) {
		if(id == null)
			throw new ValidateException("参数不合法");
		GceServer gce = this.gceServerService.selectById(id);
		if(gce == null || gce.getId()== null)
			throw new ValidateException("参数不合法");
		isAuthorityGce(gce.getId());
		this.gceProxy.delete(gce);
		return obj;
	}
	
	private void isAuthorityGce(Long id) {
		if(id == null)
			throw new ValidateException("参数不合法");
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("id", id);
		map.put("createUser", sessionService.getSession().getUserId());
		List<GceServer> gces = this.gceServerService.selectByMap(map);
		if(gces == null || gces.isEmpty())
			throw new ValidateException("参数不合法");
	}
}
