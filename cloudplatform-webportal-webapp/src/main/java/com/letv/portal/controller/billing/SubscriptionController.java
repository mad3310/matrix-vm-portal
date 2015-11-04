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
import com.letv.portal.model.order.OrderSub;
import com.letv.portal.service.order.IOrderSubService;
import com.letv.portal.service.subscription.ISubscriptionService;

/**
 * 订阅接口(续费使用)
 * @author lisuxiao
 *
 */
@Controller
@RequestMapping("/subscription/renew")
public class SubscriptionController {
	
	private final static Logger logger = LoggerFactory.getLogger(SubscriptionController.class);
	
	@Autowired
	ISubscriptionService subscriptionService;
	@Autowired
	IOrderSubService orderSubService;
	
	@RequestMapping(value="/list/{userId}",method=RequestMethod.GET)   
	public @ResponseBody ResultObject list(@PathVariable Long userId, Page page,HttpServletRequest request,ResultObject obj) {
		Map<String,Object> params = HttpUtil.requestParam2Map(request);
		params.put("userId", userId);
		params.put("valid", 1);
		params.put("chargeType", 0);
		obj.setData(this.subscriptionService.queryPaginationByMap(page, params));
		return obj;
	}
	
	@RequestMapping(value="/detail/{id}/{userId}", method=RequestMethod.GET)   
	public @ResponseBody ResultObject detailById(@PathVariable Long id, @PathVariable Long userId,HttpServletRequest request,ResultObject obj) {
		Map<String,Object> params = HttpUtil.requestParam2Map(request);
		OrderSub orderSub = this.orderSubService.selectDetailBySubscriptionId(id, userId);
		if(orderSub==null) {
			obj.setResult(0);
			obj.addMsg("未获取到订阅信息，请联系管理员！订阅ID："+id);
		}  else {
			orderSub.getSubscription().setName((String)params.get("name"));
			obj.setData(orderSub);
		}
		
		return obj;
	}
	
}
