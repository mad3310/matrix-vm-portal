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
import com.letv.portal.dao.order.IOrderDao;
import com.letv.portal.model.order.Order;
import com.letv.portal.service.impl.BaseServiceImpl;
import com.letv.portal.service.order.IOrderService;

@Service("orderService")
public class OrderServiceImpl extends BaseServiceImpl<Order> implements IOrderService {
	
	@Autowired
	private IOrderDao orderDao;
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
	public Order selectOrderById(Long orderId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", orderId);
		params.put("userId", sessionService.getSession().getUserId());
		List<Order> rets = this.orderDao.selectByMap(params);
		return rets!=null && rets.size()!=0 ? rets.get(0) : null;
	}

	@Override
	public void updateOrderStatus(Long orderId, Integer status) {
		Order o = new Order();
		o.setId(orderId);
		o.setStatus(status);
		this.orderDao.updateBySelective(o);
	}


}
