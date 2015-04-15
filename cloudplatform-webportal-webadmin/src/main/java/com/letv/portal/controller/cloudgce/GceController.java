package com.letv.portal.controller.cloudgce;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.letv.common.paging.impl.Page;
import com.letv.common.result.ResultObject;
import com.letv.common.util.HttpUtil;
import com.letv.portal.model.gce.GceImage;
import com.letv.portal.service.gce.IGceImageService;
@Controller
@RequestMapping("/gce")
public class GceController {
	
	@Autowired
	private IGceImageService gceImageService;
	
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
}
