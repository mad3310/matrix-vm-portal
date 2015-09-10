package com.letv.portal.service.order;

import com.letv.portal.model.order.Order;
import com.letv.portal.service.IBaseService;

/**
 * 订单service
 * @author lisuxiao
 *
 */
public interface IOrderService extends IBaseService<Order> {
	/**
	  * @Title: createOrder
	  * @Description: 根据订阅ID生产订单
	  * @param subscriptionId
	  * @return Long 订单ID   
	  * @throws 
	  * @author lisuxiao
	  * @date 2015年9月7日 下午3:08:06
	 */
	Long createOrder(Long subscriptionId);
	Order selectOrderById(Long orderId);
	void updateOrderStatus(Long orderId);
}
