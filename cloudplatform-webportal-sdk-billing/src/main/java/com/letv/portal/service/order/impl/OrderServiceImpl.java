package com.letv.portal.service.order.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.letv.common.dao.IBaseDao;
import com.letv.common.session.SessionServiceImpl;
import com.letv.portal.constant.Arithmetic4Double;
import com.letv.portal.dao.order.IOrderDao;
import com.letv.portal.dao.order.IOrderDetailDao;
import com.letv.portal.dao.subscription.ISubscriptionDao;
import com.letv.portal.dao.subscription.ISubscriptionDetailDao;
import com.letv.portal.model.order.Order;
import com.letv.portal.model.order.OrderDetail;
import com.letv.portal.model.subscription.Subscription;
import com.letv.portal.model.subscription.SubscriptionDetail;
import com.letv.portal.service.calculate.ICalculateService;
import com.letv.portal.service.impl.BaseServiceImpl;
import com.letv.portal.service.order.IOrderService;
import com.letv.portal.service.product.IProductService;

@Service("orderService")
public class OrderServiceImpl extends BaseServiceImpl<Order> implements IOrderService {
	
	@Autowired
	private IProductService productService;
	@Autowired
	private ICalculateService calculateService;
	@Autowired
	private ISubscriptionDao subscriptionDao;
	@Autowired
	private ISubscriptionDetailDao subscriptionDetailDao;
	@Autowired
	private IOrderDao orderDao;
	@Autowired
	private IOrderDetailDao orderDetailDao;
	@Autowired(required=false)
	private SessionServiceImpl sessionService;
	
	private final static Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);
	
	public OrderServiceImpl() {
		super(Order.class);
	}

	

	@Override
	public IBaseDao<Order> getDao() {
		return this.orderDao;
	}


	@Override
	public Long createOrder(Long subscriptionId) {
		Subscription sub = this.subscriptionDao.selectById(subscriptionId);
		if(sub.getChargeType()==0) {
			Order order = new Order();
			order.setSubscriptionId(subscriptionId);
			order.setStartTime(sub.getStartTime());
			order.setEndTime(sub.getEndTime());
			order.setCreateUser(sessionService.getSession().getUserId());
			order.setStatus(0);//未付款
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("subscriptionId", subscriptionId);
			params.put("valid", 1);
			params.put("date", new Date());
			List<SubscriptionDetail> subDetails = this.subscriptionDetailDao.selectByMapAndTime(params);
			for (SubscriptionDetail subscriptionDetail : subDetails) {
				subscriptionDetail.setPrice(this.calculateService.calculateStandardPrice(sub.getProductId(), sub.getBaseRegionId(), subscriptionDetail.getStandardName(), subscriptionDetail.getStandardValue(),
						sub.getOrderNum(), sub.getOrderTime()));
			}
			Double totalPrice = 0d;
			for (SubscriptionDetail subscriptionDetail : subDetails) {
				totalPrice = Arithmetic4Double.add(totalPrice, subscriptionDetail.getPrice());
			}
			order.setPrice(totalPrice);
			this.orderDao.insert(order);
			for (SubscriptionDetail subscriptionDetail : subDetails) {
				OrderDetail od = new OrderDetail();
				od.setOrderId(order.getId());
				od.setSubscriptionDetailId(subscriptionDetail.getId());
				od.setStartTime(subscriptionDetail.getStartTime());
				od.setEndTime(subscriptionDetail.getEndTime());
				od.setPrice(subscriptionDetail.getPrice());
				od.setCreateUser(sessionService.getSession().getUserId());
				this.orderDetailDao.insert(od);
			}
			return order.getId();
		}
		return null;
	}



	@Override
	public Order selectOrderById(Long orderId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", orderId);
		params.put("userId", sessionService.getSession().getUserId());
		List<Order> rets = this.orderDao.selectByMap(params);
		return rets==null?null:rets.get(0);
	}



	@Override
	public void updateOrderStatus(Long orderId) {
		Order o = new Order();
		o.setId(orderId);
		o.setStatus(2);//已付款
		this.orderDao.updateBySelective(o);
	}


}
