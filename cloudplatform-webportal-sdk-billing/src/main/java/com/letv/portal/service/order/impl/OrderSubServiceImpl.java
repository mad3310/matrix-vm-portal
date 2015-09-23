package com.letv.portal.service.order.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.letv.common.dao.IBaseDao;
import com.letv.common.session.SessionServiceImpl;
import com.letv.portal.constant.Arithmetic4Double;
import com.letv.portal.dao.order.IOrderSubDao;
import com.letv.portal.dao.order.IOrderSubDetailDao;
import com.letv.portal.model.order.OrderSub;
import com.letv.portal.model.order.OrderSubDetail;
import com.letv.portal.model.subscription.Subscription;
import com.letv.portal.model.subscription.SubscriptionDetail;
import com.letv.portal.service.impl.BaseServiceImpl;
import com.letv.portal.service.order.IOrderSubService;

@Service("orderSubService")
public class OrderSubServiceImpl extends BaseServiceImpl<OrderSub> implements IOrderSubService {
	
	@Autowired
	private IOrderSubDao orderSubDao;
	@Autowired
	private IOrderSubDetailDao orderDetailDao;
	@Autowired(required=false)
	private SessionServiceImpl sessionService;
	
	private final static Logger logger = LoggerFactory.getLogger(OrderSubServiceImpl.class);
	
	public OrderSubServiceImpl() {
		super(OrderSub.class);
	}

	@Override
	public IBaseDao<OrderSub> getDao() {
		return this.orderSubDao;
	}

	@Override
	public Long createOrder(Subscription sub, Long orderId, List<SubscriptionDetail> subDetails, double totalPrice) {
		OrderSub orderSub = new OrderSub();
		orderSub.setSubscriptionId(sub.getId());
		orderSub.setOrderId(orderId);
		orderSub.setStartTime(sub.getStartTime());
		orderSub.setEndTime(sub.getEndTime());
		orderSub.setCreateUser(sessionService.getSession().getUserId());
		orderSub.setPrice(totalPrice);
		this.orderSubDao.insert(orderSub);
		for (SubscriptionDetail subscriptionDetail : subDetails) {
			OrderSubDetail od = new OrderSubDetail();
			od.setOrderSubId(orderSub.getId());
			od.setSubscriptionDetailId(subscriptionDetail.getId());
			od.setStartTime(subscriptionDetail.getStartTime());
			od.setEndTime(subscriptionDetail.getEndTime());
			od.setPrice(subscriptionDetail.getPrice());
			od.setCreateUser(sessionService.getSession().getUserId());
			this.orderDetailDao.insert(od);
		}
		return orderSub.getId();
	}



	@Override
	public List<OrderSub> selectOrderSubByOrderId(Long orderId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("orderId", orderId);
		params.put("userId", sessionService.getSession().getUserId());
		return this.orderSubDao.selectByMap(params);
	}



	@Override
	public void modifyPriceById(Map<String, Object> params) {
		params.put("updateUser", sessionService.getSession().getUserId());
		this.orderSubDao.modifyPriceById(params);
	}



	@Override
	public List<OrderSub> selectOrderSubByOrderNumber(String orderNumber) {
		Map<String, Object> params = new HashMap<String, Object>();
	    params.put("orderNumber", orderNumber);
	    params.put("userId", sessionService.getSession().getUserId());
	    return this.orderSubDao.selectByMap(params);
	}
	
	@Override
	public List<OrderSub> selectOrderSubByOrderNumberWithOutSession(String orderNumber) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("orderNumber", orderNumber);
		return this.orderSubDao.selectByMap(params);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> queryOrderInfo(String orderNumber) {
		List<OrderSub> orderSubs = selectOrderSubByOrderNumber(orderNumber);
		if(orderSubs==null || orderSubs.size()==0) {
			return null;
		}
		
		List<Map<String, Object>> retList = new ArrayList<Map<String, Object>>();
		Set<String> judgeParam = new HashSet<String>();
		
		for (OrderSub orderSub : orderSubs) {
			if(judgeParam.contains(orderSub.getProductInfoRecord().getParams())) {
				continue;
			}
			Map<String, Object> params = JSONObject.parseObject(orderSub.getProductInfoRecord().getParams(), Map.class);	
			Map<String, Object> ret = new HashMap<String, Object>();
			ret.put("price", getValidProductOrderPrice(orderSub, (Integer)params.get("count")));
			ret.put("totalPrice", getValidTotalOrderPrice(orderSubs));
			ret.put("orderStatus", orderSub.getOrder().getStatus());
			ret.put("payNumber", orderSub.getOrder().getPayNumber());
			ret.put("orderNumber", orderSub.getOrder().getOrderNumber());
			ret.put("orderTime", orderSub.getSubscription().getOrderTime());
			ret.put("orderNum", (Integer)params.get("count"));
			ret.put("params", orderSub.getOrder().getDescn());
			retList.add(ret);
			judgeParam.add(orderSub.getProductInfoRecord().getParams());
		}
		
		return retList;
	}
	
	/**
	  * @Title: getValidProductOrderPrice
	  * @Description: 获取该条目价格
	  * @param orderSub
	  * @param count
	  * @return double   
	  * @throws 
	  * @author lisuxiao
	  * @date 2015年9月22日 下午5:49:23
	  */
	private double getValidProductOrderPrice(OrderSub orderSub, double count) {
		double price = 0;
		if ((orderSub.getDiscountPrice() == null)
				|| (orderSub.getDiscountPrice() < 0.0D)) {//使用原价
			price = Arithmetic4Double.multi(count, orderSub.getPrice());
		} else {
			price = Arithmetic4Double.multi(count, orderSub.getDiscountPrice());//使用折扣价
		}
		return price;
	}
	
	/**
	  * @Title: getValidOrderPrice
	  * @Description: 获取总订单的价格(优先使用折扣价)
	  * @param order
	  * @return double   
	  * @throws 
	  * @author lisuxiao
	  * @date 2015年9月17日 下午4:23:46
	  */
	private double getValidTotalOrderPrice(List<OrderSub> orderSubs) {
		double price = 0;
		for (OrderSub orderSub : orderSubs) {
			if ((orderSub.getDiscountPrice() == null)
					|| (orderSub.getDiscountPrice() < 0.0D)) {//使用原价
				price = Arithmetic4Double.add(price, orderSub.getPrice());
			} else {
				price = Arithmetic4Double.add(price, orderSub.getDiscountPrice());//使用折扣价
			}
		}
		return price;
	}

	@Override
	public Map<String, Object> queryOrderPayInfo(String orderNumber) {
		List<OrderSub> orderSubs = selectOrderSubByOrderNumber(orderNumber);
		if(orderSubs==null || orderSubs.size()==0) {
			return null;
		}
		Map<String, Object> ret = new HashMap<String, Object>();
		if(orderSubs.get(0).getOrder().getPayNumber()==null) {
			ret.put("totalPrice", getValidTotalOrderPrice(orderSubs));
			ret.put("payNumber", orderSubs.get(0).getOrder().getPayNumber());
		}
		ret.put("status", orderSubs.get(0).getOrder().getStatus());
		return null;
	}


}
