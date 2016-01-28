package com.letv.portal.service.pay.impl;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.letv.portal.model.order.Order;
import com.letv.portal.service.order.IOrderService;

@Service("payServiceOfNewTransaction")
public class PayServiceOfNewTransaction {
	
	@Autowired
	private IOrderService orderService;

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public boolean validateOrderStatus(Order order, Map<String, Object> ret) {
		boolean b = false;
		if (0 == order.getStatus().intValue()) {
			updateOrderPayInfo(order.getId(), null, null, 3, null);// 支付中or审批中
			b = true;
		} else if (1 == order.getStatus().intValue()) {
			ret.put("alert", "订单状态已失效");
			ret.put("status", 1);
		} else if (2 == order.getStatus().intValue()) {
			ret.put("alert", "订单状态已支付成功，请勿重复提交");
		} else if (3 == order.getStatus().intValue()
				&& new Date().getTime() - order.getUpdateTime().getTime() > 3000) {
			updateOrderPayInfo(order.getId(), null, null, null, null);// 更新修改时间
			b = true;
		} else if(4 == order.getStatus().intValue()) {
			ret.put("alert", "审批通过");
		} else {
			ret.put("alert", "支付中");
		}
		return b;
	}

	private boolean updateOrderPayInfo(long orderId, String orderNumber,
			Date payTime, Integer status, BigDecimal accountMoney) {
		Order o = new Order();
		o.setId(orderId);
		o.setStatus(status);
		o.setUpdateTime(new Timestamp(new Date().getTime()));
		o.setPayNumber(orderNumber);
		o.setPayTime(payTime);
		if (accountMoney != null) {
			o.setAccountPrice(accountMoney);
		}
		this.orderService.updateBySelective(o);
		return true;
	}


}
