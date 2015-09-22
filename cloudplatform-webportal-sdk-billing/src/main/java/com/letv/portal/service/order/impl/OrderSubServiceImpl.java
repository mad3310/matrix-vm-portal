package com.letv.portal.service.order.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.letv.common.dao.IBaseDao;
import com.letv.common.session.SessionServiceImpl;
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
	    return this.orderSubDao.selectByMap(params);
	}


}
