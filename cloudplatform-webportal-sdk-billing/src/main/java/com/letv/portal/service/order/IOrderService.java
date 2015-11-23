package com.letv.portal.service.order;

import com.letv.portal.model.order.Order;
import com.letv.portal.service.IBaseService;

/**
 * 订单service
 * @author lisuxiao
 *
 */
public interface IOrderService extends IBaseService<Order> {
	Order selectOrderById(Long orderId);

	void updateOrderStatus(Long orderId);
	
}
