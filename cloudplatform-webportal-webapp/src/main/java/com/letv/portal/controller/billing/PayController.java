package com.letv.portal.controller.billing;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
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
import com.letv.common.util.HttpUtil;
import com.letv.portal.model.order.Order;
import com.letv.portal.proxy.IDbProxy;
import com.letv.portal.service.order.IOrderService;
import com.letv.portal.service.pay.IPayService;

/**
 * 订单接口
 * @author lisuxiao
 *
 */
@Controller
@RequestMapping("/pay")
public class PayController {
	
	private final static Logger logger = LoggerFactory.getLogger(PayController.class);
	
	@Autowired
	IPayService payService;
	@Autowired
	private IDbProxy dbProxy;
	@Autowired
	private IOrderService orderService;

	/**
	  * @Title: pay
	  * @Description: 去支付
	  * @param orderId
	  * @param request
	  * @param response
	  * @param obj
	  * @return ResultObject   
	  * @throws 
	  * @author lisuxiao
	  * @date 2015年9月17日 下午5:04:06
	  */
	@RequestMapping(value="/{orderId}",method=RequestMethod.GET)   
	public @ResponseBody ResultObject pay(@PathVariable Long orderId, HttpServletRequest request,HttpServletResponse response, ResultObject obj) {
		Map<String,Object> params = HttpUtil.requestParam2Map(request);
		obj.setData(this.payService.pay(orderId, params, response));
		return obj;
	}
	
	
	@RequestMapping(value="/success/{orderId}",method=RequestMethod.GET)   
	public void paySuccess(@PathVariable Long orderId) {
		//根据订阅中产品类型调用相应的创建服务地址
		Order order = this.orderService.selectOrderById(orderId);
		if(order.getStatus()==2 && order.getPayNumber()!=null) {//支付成功
			if(order.getSubscription().getProductId()==1) {//rds
				this.dbProxy.build(order.getSubscription().getProductInfoRecordId());
			}
		}
	}
	
	/**
	  * @Title: callback
	  * @Description: 支付完成后回调
	  * @param orderId
	  * @param request
	  * @param response void   
	  * @throws 
	  * @author lisuxiao
	  * @date 2015年9月17日 下午5:11:28
	  */
	@RequestMapping(value="/callback",method=RequestMethod.GET)   
	public void callback(HttpServletRequest request, HttpServletResponse response) {
		Map<String,Object> params = HttpUtil.requestParam2Map(request);
		Boolean b = this.payService.callback(params);
		try {
			response.getWriter().write(b.toString());
		} catch (IOException e) {
			logger.error("callback response had error : ", e);
		}
	}
	
	/**
	  * @Title: queryStat
	  * @Description: 查询支付结果
	  * @param orderId
	  * @param obj
	  * @return ResultObject   
	  * @throws 
	  * @author lisuxiao
	  * @date 2015年9月17日 下午5:13:43
	  */
	@RequestMapping(value="/queryStat/{orderId}",method=RequestMethod.GET)   
	public @ResponseBody ResultObject queryStat(@PathVariable Long orderId, ResultObject obj) {
		obj.setData(this.payService.queryState(orderId));
		return obj;
	}
	
}
