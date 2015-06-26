package com.letv.portal.controller.cloudslb;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.letv.common.paging.impl.Page;
import com.letv.common.result.ResultObject;
import com.letv.common.util.HttpUtil;
import com.letv.portal.service.slb.ISlbContainerService;

@Controller
@RequestMapping("/slb/container")
public class SlbContainerController {
	
	@Resource
	private ISlbContainerService slbContainerService;
	
	private final static Logger logger = LoggerFactory.getLogger(SlbContainerController.class);
	
	
	/**
	  * @Title: list
	  * @Description: 根据slbClusterId获取相关slbContainer列表
	  * @param gceClusterId
	  * @param result
	  * @return ResultObject   
	  * @throws 
	  * @author lisuxiao
	  * @date 2015年6月26日 下午2:07:32
	  */
	@RequestMapping(value="/{slbClusterId}",method=RequestMethod.GET)
	public @ResponseBody ResultObject list(@PathVariable Long slbClusterId,ResultObject result) {
		result.setData(this.slbContainerService.selectBySlbClusterId(slbClusterId));
		return result;
	}
	
	/**
	  * @Title: list
	  * @Description: 获取指定页数指定条数的slbContainer列表
	  * @param page
	  * @param request
	  * @param obj
	  * @return ResultObject   
	  * @throws 
	  * @author lisuxiao
	  * @date 2015年6月26日 下午1:34:42
	  */
	@RequestMapping(method=RequestMethod.GET)   
	public @ResponseBody ResultObject list(Page page,HttpServletRequest request,ResultObject obj) {
		Map<String,Object> params = HttpUtil.requestParam2Map(request);
		obj.setData(this.slbContainerService.selectPageByParams(page, params));
		return obj;
	}
	
}
