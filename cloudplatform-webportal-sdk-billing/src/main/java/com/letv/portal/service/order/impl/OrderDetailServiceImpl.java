package com.letv.portal.service.order.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.letv.common.dao.IBaseDao;
import com.letv.portal.dao.order.IOrderDetailDao;
import com.letv.portal.model.order.OrderDetail;
import com.letv.portal.service.impl.BaseServiceImpl;
import com.letv.portal.service.order.IOrderDetailService;

@Service("orderDetailService")
public class OrderDetailServiceImpl extends BaseServiceImpl<OrderDetail> implements IOrderDetailService {
	
	@Autowired
	private IOrderDetailDao orderDetailDao;
	
	public OrderDetailServiceImpl() {
		super(OrderDetail.class);
	}

	@Override
	public IBaseDao<OrderDetail> getDao() {
		return this.orderDetailDao;
	}

}
