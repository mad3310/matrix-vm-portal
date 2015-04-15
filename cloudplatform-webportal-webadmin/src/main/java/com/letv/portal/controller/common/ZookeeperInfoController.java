package com.letv.portal.controller.common;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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
import com.letv.common.util.HttpUtil;
import com.letv.portal.model.common.ZookeeperInfo;
import com.letv.portal.service.common.IZookeeperInfoService;
@Controller
@RequestMapping("/zk")
public class ZookeeperInfoController {
	
	@Autowired
	private IZookeeperInfoService zookeeperInfoService;
	
	private final static Logger logger = LoggerFactory.getLogger(ZookeeperInfoController.class);

	@RequestMapping(method=RequestMethod.GET)   
	public @ResponseBody ResultObject list(Page page,HttpServletRequest request,ResultObject obj) {
		Map<String,Object> params = HttpUtil.requestParam2Map(request);
		obj.setData(this.zookeeperInfoService.selectPageByParams(page, params));
		return obj;
	}
	
	@RequestMapping(value="/{id}",method=RequestMethod.GET)
	public @ResponseBody ResultObject detail(@PathVariable Long id,ResultObject obj) {
		obj.setData(this.zookeeperInfoService.selectById(id));
		return obj;
	}
	
	@RequestMapping(method=RequestMethod.POST)   
	public @ResponseBody ResultObject insert(ZookeeperInfo zk,ResultObject obj) {
		this.zookeeperInfoService.insert(zk);
		return obj;
	}
	@RequestMapping(value="/{id}",method=RequestMethod.POST)   
	public @ResponseBody ResultObject update(@PathVariable Long id,ZookeeperInfo zk,ResultObject obj) {
		if(id == null)
			throw new ValidateException("参数不合法");
		ZookeeperInfo oldzk = this.zookeeperInfoService.selectById(id);
		if(oldzk == null)
			throw new ValidateException("参数不合法");
		this.zookeeperInfoService.updateBySelective(zk);
		return obj;
	}
	@RequestMapping(value = "/{id}", method=RequestMethod.DELETE) 
	public @ResponseBody ResultObject delete(@PathVariable Long id,ResultObject obj) {
		if(id == null)
			throw new ValidateException("参数不合法");
		ZookeeperInfo zk = this.zookeeperInfoService.selectById(id);
		if(zk == null)
			throw new ValidateException("参数不合法");
		this.zookeeperInfoService.delete(zk);
		return obj;
	}
}
