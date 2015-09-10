package com.letv.portal.controller.order;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.letv.common.result.ResultObject;
import com.letv.portal.model.order.Order;
import com.letv.portal.proxy.IDbProxy;
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
	@Autowired
	private IDbProxy dbProxy;

	@RequestMapping(value="/{id}",method=RequestMethod.GET)   
	public @ResponseBody ResultObject queryProductDetail(@PathVariable Long id, ResultObject obj) {
		obj.setData(this.orderService.selectOrderById(id));
		return obj;
	}
	
	@RequestMapping(value="/pay/success/{orderId}",method=RequestMethod.GET)   
	public void paySuccess(@PathVariable Long orderId, HttpServletResponse response) {
		//①查询是否付款成功
		//②成功后，更新订单状态为：2-已付款
		this.orderService.updateOrderStatus(orderId);
		//③根据订阅中产品类型调用相应的创建服务地址
		Order order = this.orderService.selectOrderById(orderId);
		if(order.getSubscription().getProductId()==1) {//rds
			this.dbProxy.build(order.getSubscription().getProductInfoRecordId());
			try {
				response.sendRedirect("/list/db");
			} catch (IOException e) {
				logger.error("redirect /list/db had error:", e);
			}
		}
		
	}
	
}
