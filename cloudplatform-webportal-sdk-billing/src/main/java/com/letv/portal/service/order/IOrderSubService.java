package com.letv.portal.service.order;

import java.util.List;
import java.util.Map;

import com.letv.portal.model.order.OrderSub;
import com.letv.portal.model.subscription.Subscription;
import com.letv.portal.model.subscription.SubscriptionDetail;
import com.letv.portal.service.IBaseService;

/**
 * 订单service
 * @author lisuxiao
 *
 */
public interface IOrderSubService extends IBaseService<OrderSub> {
	/**
	  * @Title: createOrder
	  * @Description: 根据订阅,订单ID生产子订单
	  * @param subscription
	  * @param orderId
	  * @return Long 订单ID   
	  * @throws 
	  * @author lisuxiao
	  * @date 2015年9月7日 下午3:08:06
	 */
	Long createOrder(Subscription subscription, Long orderId, List<SubscriptionDetail> subDetails, double totalPrice);
	List<OrderSub> selectOrderSubByOrderId(Long orderId);
	/**
	  * @Title: selectOrderByOrderNumber
	  * @Description: 根据orderNumber查询订单
	  * @param orderNumber
	  * @return Order   
	  * @throws 
	  * @author lisuxiao
	  * @date 2015年9月17日 下午4:44:38
	  */
	List<OrderSub> selectOrderSubByOrderNumber(String orderNumber);
	
	/**
	  * @Title: modifyPriceById
	  * @Description: 修改订单价格
	  * @param params void   
	  * @throws 
	  * @author lisuxiao
	  * @date 2015年9月13日 上午11:44:44
	  */
	void modifyPriceById(Map<String, Object> params);
	
	List<Map<String, Object>> queryOrderInfo(String orderNumber);
}
