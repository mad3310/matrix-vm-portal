package com.letv.portal.controller.billing.api;

import com.letv.common.result.ResultObject;
import com.letv.portal.service.common.IUserService;
import com.letv.portal.service.order.IOrderSubService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * 订单接口
 * @author lisuxiao
 *
 */
@Controller
@RequestMapping("/api/order")
public class OrderApiController {

	private final static Logger logger = LoggerFactory.getLogger(OrderApiController.class);

	@Autowired
	IOrderSubService orderSubService;
	@Autowired
	IUserService userService;
	/**
	 * @Title: queryOrderDetailById
	 * @Description: 根据订单id获取订单详情
	 * @param orderId
	 * @param obj
	 * @return ResultObject
	 * @throws
	 * @author lisuxiao
	 * @date 2015年10月29日 下午3:37:24
	 */
	@RequestMapping(value="/detail/{orderId}/{ucId}",method=RequestMethod.GET)
	public @ResponseBody ResultObject queryOrderDetailById(@PathVariable Long orderId, @PathVariable Long ucId, ResultObject obj) {
		Long userId = this.userService.getUserIdByUcId(ucId);
		if(null == userId) {
			obj.setResult(0);
			obj.addMsg("no user exist by ucId,please login lcp first.");
			return obj;
		}
		List<Map<String, Object>> ret = this.orderSubService.queryOrderDetailById(orderId, userId);
		if(ret==null) {
			obj.setResult(0);
			obj.addMsg("未获取到订单信息，请联系管理员！订单ID："+orderId);
		} else {
			obj.setData(ret);
		}
		return obj;
	}
}
