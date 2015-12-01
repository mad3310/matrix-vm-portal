package com.letv.portal.controller.billing;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.letv.common.result.ResultObject;
import com.letv.portal.service.order.IOrderSubService;

/**
 * 订单接口
 * @author lisuxiao
 *
 */
@Controller
@RequestMapping("/order")
public class OrderController {

	private final static Logger logger = LoggerFactory.getLogger(OrderController.class);

	@Autowired
	IOrderSubService orderSubService;

	/**
	 * @Title: queryOrderInfo
	 * @Description: 根据订单编号获取订单详情
	 * @param orderNumber
	 * @param obj
	 * @return ResultObject
	 * @throws
	 * @author lisuxiao
	 * @date 2015年9月22日 下午3:42:15
	 */
	@RequestMapping(value="/{orderNumber}",method=RequestMethod.GET)
	public @ResponseBody ResultObject queryOrderInfo(@PathVariable String orderNumber, ResultObject obj) {
		List<Map<String, Object>> ret = this.orderSubService.queryOrderInfo(orderNumber);
		if(ret==null) {
			obj.setResult(0);
			obj.addMsg("未获取到订单信息，请联系管理员！订单编号："+orderNumber);
		} else {
			obj.setData(ret);
		}
		return obj;
	}

	/**
	 * @Title: queryOrderPayInfo
	 * @Description: 获取订单支付信息
	 * @param orderNumber
	 * @param obj
	 * @return ResultObject
	 * @throws
	 * @author lisuxiao
	 * @date 2015年9月23日 上午10:53:00
	 */
	@RequestMapping(value="/pay/{orderNumber}",method=RequestMethod.GET)
	public @ResponseBody ResultObject queryOrderPayInfo(@PathVariable String orderNumber, ResultObject obj) {
		Map<String, Object> ret = this.orderSubService.queryOrderPayInfo(orderNumber);
		if(ret==null) {
			obj.setResult(0);
			obj.addMsg("未获取到订单支付信息，请联系管理员！订单编号："+orderNumber);
		} else {
			obj.setData(ret);
		}
		return obj;
	}
}
