package com.letv.portal.dao.subscription;

import java.util.List;
import java.util.Map;

import com.letv.common.dao.IBaseDao;
import com.letv.portal.model.subscription.Subscription;

public interface ISubscriptionDao extends IBaseDao<Subscription> {
	
	List<Subscription> selectValidSubscription(Map<String, Object> params);

}
