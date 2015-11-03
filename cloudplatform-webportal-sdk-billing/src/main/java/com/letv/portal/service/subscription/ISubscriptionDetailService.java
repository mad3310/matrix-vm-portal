package com.letv.portal.service.subscription;

import java.util.List;
import java.util.Map;

import com.letv.portal.model.subscription.SubscriptionDetail;
import com.letv.portal.service.IBaseService;

/**
 * 订阅详情service
 * @author lisuxiao
 *
 */
public interface ISubscriptionDetailService extends IBaseService<SubscriptionDetail> {
	List<SubscriptionDetail> selectBySubscriptionId(Long subscriptionId);
	/**
	  * @Title: updateBuyTime
	  * @Description: 创建完服务后，根据当前时间变更用户购买起止时间
	  * @param params void   
	  * @throws 
	  * @author lisuxiao
	  * @date 2015年9月30日 下午2:24:59
	  */
	void updateBuyTime(Map<String, Object> params);
}
