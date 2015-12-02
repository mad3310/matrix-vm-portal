package com.letv.portal.controller.billing.api;

import com.letv.common.paging.impl.Page;
import com.letv.common.result.ResultObject;
import com.letv.common.util.HttpUtil;
import com.letv.portal.model.order.OrderSub;
import com.letv.portal.service.IUserService;
import com.letv.portal.service.order.IOrderSubService;
import com.letv.portal.service.subscription.ISubscriptionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 订阅接口
 * @author lisuxiao
 *
 */
@Controller
@RequestMapping("/api/subscription/renew")
public class SubscriptionApiController {

	private final static Logger logger = LoggerFactory.getLogger(SubscriptionApiController.class);

	@Autowired
	ISubscriptionService subscriptionService;
	@Autowired
	IOrderSubService orderSubService;
	@Autowired
	IUserService userService;

	@RequestMapping(value="/list/{ucId}",method=RequestMethod.GET)
	public @ResponseBody ResultObject list(@PathVariable Long ucId, Page page,HttpServletRequest request,ResultObject obj) {
		Long userId = this.userService.getUserIdByUcId(ucId);
		if(null == userId) {
			obj.setResult(0);
			obj.addMsg("no user exist by ucId,please login lcp first.");
			return obj;
		}
		Map<String,Object> params = HttpUtil.requestParam2Map(request);
		params.put("userId", userId);
		params.put("valid", 1);
		params.put("chargeType", 0);
		obj.setData(this.subscriptionService.queryPaginationByMap(page, params));
		return obj;
	}

	@RequestMapping(value="/detail/{id}/{ucId}", method=RequestMethod.GET)
	public @ResponseBody ResultObject detailById(@PathVariable Long id, @PathVariable Long ucId,HttpServletRequest request,ResultObject obj) {
		Long userId = this.userService.getUserIdByUcId(ucId);
		if(null == userId) {
			obj.setResult(0);
			obj.addMsg("no user exist by ucId,please login lcp first.");
			return obj;
		}
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
