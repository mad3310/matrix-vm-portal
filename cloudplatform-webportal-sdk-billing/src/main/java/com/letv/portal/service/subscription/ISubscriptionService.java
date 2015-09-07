package com.letv.portal.service.subscription;

import java.util.Map;

import com.letv.portal.model.subscription.Subscription;
import com.letv.portal.service.IBaseService;

/**
 * 订阅service
 * @author lisuxiao
 *
 */
public interface ISubscriptionService extends IBaseService<Subscription> {
	/**
	  * @Title: createSubscription
	  * @Description: 生产订阅 
	  * @param id
	  * @param map
	  * @return Boolean   
	  * @throws 
	  * @author lisuxiao
	  * @date 2015年9月6日 下午3:58:11
	  */
	Boolean createSubscription(Long id, Map<String, Object> map);
}
