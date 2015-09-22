package com.letv.portal.service.order.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.letv.common.dao.IBaseDao;
import com.letv.portal.dao.order.IOrderSubDetailDao;
import com.letv.portal.model.order.OrderSubDetail;
import com.letv.portal.service.impl.BaseServiceImpl;
import com.letv.portal.service.order.IOrderSubDetailService;

@Service("orderSubDetailService")
public class OrderSubDetailServiceImpl extends BaseServiceImpl<OrderSubDetail> implements IOrderSubDetailService {
	
	@Autowired
	private IOrderSubDetailDao orderSubDetailDao;
	
	public OrderSubDetailServiceImpl() {
		super(OrderSubDetail.class);
	}

	@Override
	public IBaseDao<OrderSubDetail> getDao() {
		return this.orderSubDetailDao;
	}

}
