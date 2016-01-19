package com.letv.portal.service.driver.cloudvm;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.letv.lcp.openstack.model.event.ResourceDeleteEvent;
import com.letv.portal.service.order.IOrderService;
import com.letv.portal.service.order.IOrderSubService;
import com.letv.portal.service.product.IProductInfoRecordService;
import com.letv.portal.service.subscription.ISubscriptionService;

/**
 * 云主机等相关资源删除后调用
 * @author lisuxiao
 *
 */
@Component
public class CloudvmResourceDeleteListener implements ApplicationListener<ResourceDeleteEvent> {
	
	@Autowired
	ISubscriptionService subscriptionService;
	@Autowired
	IOrderSubService orderSubService;
	@Autowired
	IOrderService orderService;
	@Autowired
	IProductInfoRecordService productInfoRecordService;
	
	@Override
	@Async
	//云主机等相关资源删除后，调用event事件修改订阅状态为无效,将所有需要支付的订单改为无效
	public void onApplicationEvent(ResourceDeleteEvent event) {
		String instanceId = event.getLocator().region()+"_"+event.getLocator().getId();
		this.subscriptionService.updateSubscriptionStateByInstanceId(instanceId);
		Long productInfoRecordId = this.productInfoRecordService.selectIdByInstanceId(instanceId);
		List<Long> orderIds = this.orderSubService.selectOrderIdByProductInfoRecordId(productInfoRecordId);
		for (Long orderId : orderIds) {
			this.orderService.updateOrderStatus(orderId, 1);
		}
	}
	
}
