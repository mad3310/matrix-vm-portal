package com.letv.portal.service.subscription;

import java.util.List;

import com.letv.portal.model.subscription.SubscriptionDetail;
import com.letv.portal.service.IBaseService;

/**
 * 订阅详情service
 * @author lisuxiao
 *
 */
public interface ISubscriptionDetailService extends IBaseService<SubscriptionDetail> {
	List<SubscriptionDetail> selectByMapAndTime(Long subscriptionId);
}
