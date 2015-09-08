package com.letv.portal.dao.subscription;

import java.util.List;
import java.util.Map;

import com.letv.common.dao.IBaseDao;
import com.letv.portal.model.subscription.SubscriptionDetail;

public interface ISubscriptionDetailDao extends IBaseDao<SubscriptionDetail> {
	List<SubscriptionDetail> selectByMapAndTime(Map<String, Object> params);
}
