package com.letv.portal.controller.billing.api;

import com.letv.common.result.ResultObject;
import com.letv.common.util.DataFormat;
import com.letv.common.util.HttpUtil;
import com.letv.portal.constant.Constants;
import com.letv.portal.model.order.Order;
import com.letv.portal.model.order.OrderSub;
import com.letv.portal.model.subscription.Subscription;
import com.letv.portal.model.subscription.SubscriptionDetail;
import com.letv.portal.service.common.IUserService;
import com.letv.portal.service.calculate.ICalculateService;
import com.letv.portal.service.calculate.IHostCalculateService;
import com.letv.portal.service.order.IOrderService;
import com.letv.portal.service.order.IOrderSubService;
import com.letv.portal.service.product.IHostProductService;
import com.letv.portal.service.product.IProductService;
import com.letv.portal.service.subscription.ISubscriptionDetailService;
import com.letv.portal.service.subscription.ISubscriptionService;
import com.letv.portal.util.SerialNumberUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 续费
 * @author lisuxiao
 *
 */
@Controller
@RequestMapping("/api/billing/renew")
public class RenewApiController {
	private final static Logger logger = LoggerFactory.getLogger(RenewApiController.class);

	@Autowired
	IProductService productService;
	@Autowired
	IHostProductService hostProductService;
	@Autowired
	ICalculateService calculateService;
	@Autowired
	IHostCalculateService hostCalculateService;
	@Autowired
	ISubscriptionService subscriptionService;
	@Autowired
	ISubscriptionDetailService subscriptionDetailService;
	@Autowired
	IOrderService orderService;
	@Autowired
	IUserService userService;
	@Autowired
	IOrderSubService orderSubService;

	private void transferParams(Map<String, Object> params, List<SubscriptionDetail> subDetails) {
		for (SubscriptionDetail subscriptionDetail : subDetails) {
			params.put(subscriptionDetail.getElementName(), subscriptionDetail.getStandardValue());
			if(subscriptionDetail.getStandardType()!=null) {
				params.put(subscriptionDetail.getElementName()+"_type", subscriptionDetail.getStandardType());
			}
		}
	}

	@RequestMapping(value="/price/{productId}",method=RequestMethod.POST)
	public @ResponseBody ResultObject queryProductPrice( @PathVariable Long productId, HttpServletRequest request, ResultObject obj) {
		Map<String,Object> map = HttpUtil.requestParam2Map(request);
		BigDecimal ret = null;
		if(map.get("subscriptionId")==null || map.get("userId")==null) {
			obj.setResult(0);
			obj.addMsg("输入参数不合法");
			return obj;
		}
		Map<String, Object> subDetailParams = new HashMap<String, Object>();
		subDetailParams.put("subscriptionId", map.get("subscriptionId"));
		Long userId = this.userService.getUserIdByUcId(Long.parseLong((String)map.get("userId")));
        if(null == userId) {
            obj.setResult(0);
            obj.addMsg("no user exist by ucId,please login lcp first.");
            return obj;
        }
		subDetailParams.put("userId", userId);

		List<SubscriptionDetail> subDetails = this.subscriptionDetailService.selectByMap(subDetailParams);
		if(subDetails==null||subDetails.size()==0) {
			obj.setResult(0);
			obj.addMsg("查询原有订阅详细异常,订阅ID:" + map.get("subscriptionId"));
			return obj;
		}
		map.put("region", subDetails.get(0).getSubscription().getBaseRegionId()+"");
		map.put("order_num", "1");
		map.put("orderTime", map.get("order_time"));

		transferParams(map, subDetails);

		if(productId==Constants.PRODUCT_VM || productId==Constants.PRODUCT_VOLUME ||
				productId==Constants.PRODUCT_ROUTER || productId==Constants.PRODUCT_FLOATINGIP || productId==Constants.PRODUCT_PRIVATE_VM) {//云主机走自己的验证和计算
			if(hostProductService.validateData(productId, map)) {
				ret =  hostCalculateService.calculatePrice(productId, map);
			}
		} else {
			if(productService.validateData(productId, map)) {//规划的通用逻辑
				ret = calculateService.calculatePrice(productId, map);
			}
		}
		if(ret==null) {
			obj.setResult(0);
			obj.addMsg("输入参数不合法");
		} else {
			obj.setData(DataFormat.formatBigDecimalToString(ret));
		}
		return obj;
	}


	@RequestMapping(value="/buy/{productId}",method=RequestMethod.POST)
	public @ResponseBody ResultObject buy(@PathVariable Long productId, HttpServletRequest request, ResultObject obj) {
		Map<String,Object> map = HttpUtil.requestParam2Map(request);
		if(map.get("subscriptionId")==null || map.get("userId")==null) {
			obj.setResult(0);
			obj.addMsg("输入参数不合法");
			return obj;
		}
		Long subscriptionId = Long.parseLong((String)map.get("subscriptionId"));
		//List<OrderSub> orderSubs = this.orderSubService.selectOrderSubBySubscriptionId(subscriptionId);
		Subscription s = this.subscriptionService.selectById(subscriptionId);
		List<OrderSub> orderSubs = this.orderSubService.selectOrderSubByProductInfoRecordId(s.getProductInfoRecordId());
		if(s==null || s.getValid()==0 || orderSubs==null || orderSubs.size()==0) {
			obj.setResult(0);
			obj.addMsg("查询原有订阅失败或状态异常,订阅ID:" + subscriptionId);
			return obj;
		}
		for (OrderSub orderSub : orderSubs) {
			if(s.getEndTime().getTime()<orderSub.getSubscription().getEndTime().getTime()) {
				obj.setResult(0);
				obj.addMsg("该订阅已续费,订阅ID:" + subscriptionId);
				return obj;
			}
		}
		
		Long userId = this.userService.getUserIdByUcId(Long.parseLong((String) map.get("userId")));
		if(null == userId) {
			obj.setResult(0);
			obj.addMsg("no user exist by ucId,please login lcp first.");
			return obj;
		}
		//map中参数会保存到计费详情中,业务字段删除
		map.remove("subscriptionId");
		map.remove("userId");

		Map<String, Object> subDetailParams = new HashMap<String, Object>();
		subDetailParams.put("subscriptionId", subscriptionId);
		subDetailParams.put("userId", userId);

		List<SubscriptionDetail> subDetails = this.subscriptionDetailService.selectByMap(subDetailParams);
		if(subDetails==null||subDetails.size()==0) {
			obj.setResult(0);
			obj.addMsg("查询原有订阅详细异常,订阅ID:" + subscriptionId);
			return obj;
		}
		map.put("region", subDetails.get(0).getSubscription().getBaseRegionId()+"");
		map.put("order_num", "1");

		transferParams(map, subDetails);

		String orderTime = (String)map.get("order_time");
		map.put("orderTime", orderTime);

		if(validateData(productId, map)) {//验证参数合法性
			//生产订阅
			Subscription sub = this.subscriptionService.createSubscription(productId, map,
					subDetails.get(0).getSubscription().getProductInfoRecordId(), subDetails.get(0).getSubscription().getEndTime(), orderTime, userId, 1);
			//生产总订单
			Order o = new Order();
			o.setOrderNumber(SerialNumberUtil.getNumber(2));
			o.setStatus(0);
			o.setCreateUser(userId);
			o.setCreateTime(new Timestamp(new Date().getTime()));
			this.orderService.insert(o);
			obj.setData(o.getOrderNumber());

			List<SubscriptionDetail> sDs = this.subscriptionDetailService.selectBySubscriptionId(sub.getId());
			BigDecimal totalPrice = getOrderTotalPrice(productId, sub, sDs, (String)map.get("order_time"), map);

			//生成子订单
			this.orderSubService.createOrder(sub, o.getId(), sDs, totalPrice, userId);
		} else {
			obj.setResult(0);
			obj.addMsg("参数合法性验证失败");
		}

		return obj;
	}

	private boolean validateData(Long productId, Map<String, Object> map) {
		if(productId==Constants.PRODUCT_VM || productId==Constants.PRODUCT_VOLUME ||
				productId==Constants.PRODUCT_ROUTER || productId==Constants.PRODUCT_FLOATINGIP) {//云主机相关
			return hostProductService.validateData(productId, map);
		} else {//规划的通用逻辑
			return productService.validateData(productId, map);
		}
	}

	private BigDecimal getOrderTotalPrice(Long productId, Subscription sub, List<SubscriptionDetail> subDetails, String orderTime, Map<String, Object> billingParams) {
		for (SubscriptionDetail subscriptionDetail : subDetails) {
			if(productId==Constants.PRODUCT_VM || productId==Constants.PRODUCT_VOLUME ||
					productId==Constants.PRODUCT_ROUTER || productId==Constants.PRODUCT_FLOATINGIP || productId==Constants.PRODUCT_PRIVATE_VM) {
				subscriptionDetail.setPrice(this.hostCalculateService.calculateStandardPrice(sub.getProductId(), sub.getBaseRegionId(), subscriptionDetail.getElementName(), subscriptionDetail.getStandardValue(),
						1, Integer.parseInt(orderTime), (String)billingParams.get(subscriptionDetail.getElementName()+"_type")));
			} else {
				subscriptionDetail.setPrice(this.calculateService.calculateStandardPrice(sub.getProductId(), sub.getBaseRegionId(), subscriptionDetail.getElementName(), subscriptionDetail.getStandardValue(),
						1, Integer.parseInt(orderTime), (String)billingParams.get(subscriptionDetail.getElementName()+"_type")));
			}
		}
		BigDecimal totalPrice = new BigDecimal(0);
		for (SubscriptionDetail subscriptionDetail : subDetails) {
			totalPrice = subscriptionDetail.getPrice().add(totalPrice);
		}
		return totalPrice;
	}

}
