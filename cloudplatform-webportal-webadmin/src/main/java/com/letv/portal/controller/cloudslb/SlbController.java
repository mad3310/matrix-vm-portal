package com.letv.portal.controller.cloudslb;

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
import com.letv.portal.service.slb.ISlbServerService;


@Controller
@RequestMapping("/slb")
public class SlbController {
	
	@Autowired
	private ISlbServerService slbServerService;
	
	private final static Logger logger = LoggerFactory.getLogger(SlbController.class);

	/**
	  * @Title: list
	  * @Description: 获取指定页数指定条数的slbServer列表
	  * @param page
	  * @param request
	  * @param obj
	  * @return ResultObject   
	  * @throws 
	  * @author lisuxiao
	  * @date 2015年6月26日 下午1:47:31
	  */
	@RequestMapping(method=RequestMethod.GET)   
	public @ResponseBody ResultObject list(Page page,HttpServletRequest request,ResultObject obj) {
		Map<String,Object> params = HttpUtil.requestParam2Map(request);
		obj.setData(this.slbServerService.selectPageByParams(page, params));
		return obj;
	}
	
}
