package com.letv.portal.service.order;

import java.util.Map;

import com.letv.portal.model.order.OrderSubDetail;
import com.letv.portal.service.common.IBaseService;

/**
 * 订单详情service
 * @author lisuxiao
 *
 */
public interface IOrderSubDetailService extends IBaseService<OrderSubDetail> {
	void updateBuyTime(Map<String, Object> params);
}
