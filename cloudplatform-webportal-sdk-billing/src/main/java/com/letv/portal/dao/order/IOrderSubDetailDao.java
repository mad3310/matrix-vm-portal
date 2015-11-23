package com.letv.portal.dao.order;

import java.util.Map;

import com.letv.common.dao.IBaseDao;
import com.letv.portal.model.order.OrderSubDetail;

public interface IOrderSubDetailDao extends IBaseDao<OrderSubDetail> {
	void updateBuyTime(Map<String, Object> params);
}
