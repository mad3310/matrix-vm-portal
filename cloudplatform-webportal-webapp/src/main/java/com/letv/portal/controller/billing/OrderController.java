package com.letv.portal.controller.billing;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.letv.common.result.ResultObject;
import com.letv.portal.service.order.IOrderService;

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
	IOrderService orderService;

	@RequestMapping(value="/{id}",method=RequestMethod.GET)   
	public @ResponseBody ResultObject queryProductDetail(@PathVariable Long id, ResultObject obj) {
		obj.setData(this.orderService.selectOrderById(id));
		return obj;
	}
	
	
}
