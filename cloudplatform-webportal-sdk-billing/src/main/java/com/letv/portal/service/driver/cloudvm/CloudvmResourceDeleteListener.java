package com.letv.portal.service.driver.cloudvm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.letv.portal.service.openstack.billing.event.ResourceDeleteEvent;
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
	
	@Async
	@Override
	//云主机等相关资源删除后，调用event事件修改订阅状态为无效
	public void onApplicationEvent(ResourceDeleteEvent event) {
		this.subscriptionService.updateSubscriptionStateByInstanceId(event.getLocator().region()+"_"+event.getLocator().getId());
	}
	
}
