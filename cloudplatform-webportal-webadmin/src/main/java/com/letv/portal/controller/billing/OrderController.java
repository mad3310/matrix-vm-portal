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
import com.letv.portal.service.order.IOrderDetailService;
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
	IOrderDetailService orderDetailService;
	
	/**
	  * @Title: list
	  * @Description: 订单列表
	  * @param page
	  * @param request
	  * @param obj
	  * @return ResultObject   
	  * @throws 
	  * @author lisuxiao
	  * @date 2015年9月13日 下午2:06:56
	  */
	@RequestMapping(value="/list",method=RequestMethod.GET)   
	public @ResponseBody ResultObject list(Page page,HttpServletRequest request,ResultObject obj) {
		Map<String,Object> params = HttpUtil.requestParam2Map(request);
		obj.setData(this.orderService.queryPaginationByMap(page, params));
		return obj;
	}
	
	/**
	  * @Title: detailById
	  * @Description: 根据订单id，获取订单明细
	  * @param id
	  * @param page
	  * @param request
	  * @param obj
	  * @return ResultObject   
	  * @throws 
	  * @author lisuxiao
	  * @date 2015年9月13日 下午2:06:31
	  */
	@RequestMapping(value="/detail/{id}", method=RequestMethod.GET)   
	public @ResponseBody ResultObject detailById(@PathVariable Long id, Page page,HttpServletRequest request,ResultObject obj) {
		Map<String,Object> params = HttpUtil.requestParam2Map(request);
		params.put("orderId", id);
		obj.setData(this.orderDetailService.queryPaginationByMap(page, params));
		return obj;
	}
	
	/**
	  * @Title: modifyPriceById
	  * @Description: 修改订单价格（折扣价）
	  * @param id
	  * @param page
	  * @param request
	  * @param obj
	  * @return ResultObject   
	  * @throws 
	  * @author lisuxiao
	  * @date 2015年9月13日 下午2:05:58
	  */
	@RequestMapping(value="/modify/price/{id}", method=RequestMethod.POST)   
	public @ResponseBody ResultObject modifyPriceById(@PathVariable Long id, Page page,HttpServletRequest request,ResultObject obj) {
		Map<String,Object> params = HttpUtil.requestParam2Map(request);
		params.put("orderId", id);
		this.orderService.modifyPriceById(params);
		return obj;
	}
	
}
