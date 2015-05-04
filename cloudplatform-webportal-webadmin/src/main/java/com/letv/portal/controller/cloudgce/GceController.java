package com.letv.portal.controller.cloudgce;

import java.util.HashMap;
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
import com.letv.portal.model.gce.GceImage;
import com.letv.portal.service.IUserService;
import com.letv.portal.service.gce.IGceImageService;
@Controller
@RequestMapping("/gce")
public class GceController {
	
	@Autowired
	private IGceImageService gceImageService;
	@Autowired
	private IUserService userService;
	
	private final static Logger logger = LoggerFactory.getLogger(GceController.class);

	@RequestMapping(value="/image",method=RequestMethod.GET)   
	public @ResponseBody ResultObject imageList(Page page,HttpServletRequest request,ResultObject obj) {
		Map<String,Object> params = HttpUtil.requestParam2Map(request);
		obj.setData(this.gceImageService.selectPageByParams(page, params));
		return obj;
	}
	
	@RequestMapping(value="/image",method=RequestMethod.POST)   
	public @ResponseBody ResultObject addImage(GceImage gceImage,HttpServletRequest request) {
		ResultObject obj = new ResultObject();
		this.gceImageService.insert(gceImage);
		return obj;
	}
	
	@RequestMapping(value="/image/{id}",method=RequestMethod.DELETE)   
	public @ResponseBody ResultObject delImage(@PathVariable Long id,HttpServletRequest request) {
		ResultObject obj = new ResultObject();
		if(id == null)
			throw new ValidateException("参数不合法");
		GceImage gceImage = this.gceImageService.selectById(id);
		if(gceImage == null)
			throw new ValidateException("参数不合法");
		this.gceImageService.delete(gceImage);
		return obj;
	}
	
	@RequestMapping(value="/image/{id}",method=RequestMethod.POST)   
	public @ResponseBody ResultObject updateImage(@PathVariable Long id,GceImage gceImage,HttpServletRequest request) {
		ResultObject obj = new ResultObject();
		if(id == null)
			throw new ValidateException("参数不合法");
		GceImage oldGceImage = this.gceImageService.selectById(id);
		if(oldGceImage == null)
			throw new ValidateException("参数不合法");
		this.gceImageService.updateBySelective(gceImage);
		return obj;
	}
	
	@RequestMapping(value="/user",method=RequestMethod.GET)   
	public @ResponseBody ResultObject gceAllUser(HttpServletRequest request) {
		ResultObject obj = new ResultObject();
		Map<String, Object> map = new HashMap<String, Object>();
		
		obj.setData(this.userService.selectByMap(map));
		return obj;
	}
}
