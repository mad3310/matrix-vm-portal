package com.letv.portal.controller.billing;

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

import com.letv.common.paging.impl.Page;
import com.letv.common.result.ResultObject;
import com.letv.common.util.HttpUtil;
import com.letv.portal.service.subscription.ISubscriptionDetailService;
import com.letv.portal.service.subscription.ISubscriptionService;

/**
 * 订阅接口
 * @author lisuxiao
 *
 */
@Controller
@RequestMapping("/subscription")
public class SubscriptionController {
	
	private final static Logger logger = LoggerFactory.getLogger(SubscriptionController.class);
	
	@Autowired
	ISubscriptionService subscriptionService;
	@Autowired
	ISubscriptionDetailService subscriptionDetailService;
	
	@RequestMapping(value="/list",method=RequestMethod.GET)   
	public @ResponseBody ResultObject list(Page page,HttpServletRequest request,ResultObject obj) {
		Map<String,Object> params = HttpUtil.requestParam2Map(request);
		obj.setData(this.subscriptionService.queryPaginationByMap(page, params));
		return obj;
	}
	
	@RequestMapping(value="/detail/{id}", method=RequestMethod.GET)   
	public @ResponseBody ResultObject detailById(@PathVariable Long id, Page page,HttpServletRequest request,ResultObject obj) {
		Map<String,Object> params = HttpUtil.requestParam2Map(request);
		params.put("subscriptionId", id);
		obj.setData(this.subscriptionDetailService.queryPaginationByMap(page, params));
		return obj;
	}
	
}
