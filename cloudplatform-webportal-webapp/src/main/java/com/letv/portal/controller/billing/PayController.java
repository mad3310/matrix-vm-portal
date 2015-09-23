package com.letv.portal.controller.billing;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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

import com.letv.common.exception.ValidateException;
import com.letv.common.result.ResultObject;
import com.letv.common.session.SessionServiceImpl;
import com.letv.common.util.HttpUtil;
import com.letv.portal.model.order.OrderSub;
import com.letv.portal.model.product.Product;
import com.letv.portal.model.product.ProductInfoRecord;
import com.letv.portal.proxy.IDbProxy;
import com.letv.portal.service.openstack.billing.ResourceCreateService;
import com.letv.portal.service.order.IOrderSubService;
import com.letv.portal.service.pay.IPayService;
import com.letv.portal.service.product.IProductInfoRecordService;

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
	private IOrderSubService orderSubService;
	@Autowired
	private IProductInfoRecordService productInfoRecordService;
	@Autowired(required=false)
	private SessionServiceImpl sessionService;

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
	@RequestMapping(value="/{orderNumber}",method=RequestMethod.GET)   
	public @ResponseBody ResultObject pay(@PathVariable String orderNumber, HttpServletRequest request,HttpServletResponse response, ResultObject obj) {
		Map<String,Object> params = HttpUtil.requestParam2Map(request);
		obj.setData(this.payService.pay(orderNumber, params, response));
		return obj;
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
	@RequestMapping(value="/queryStat/{orderNumber}",method=RequestMethod.GET)   
	public @ResponseBody ResultObject queryStat(@PathVariable String orderNumber, ResultObject obj) {
		obj.setData(this.payService.queryState(orderNumber));
		return obj;
	}
	
}
