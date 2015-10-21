package com.letv.portal.dao.order;

import java.util.List;
import java.util.Map;

import com.letv.common.dao.IBaseDao;
import com.letv.portal.model.order.OrderSub;

public interface IOrderSubDao extends IBaseDao<OrderSub> {
	/**
	  * @Title: modifyPriceById
	  * @Description: 修改订单价格（折扣价）
	  * @param params void   
	  * @throws 
	  * @author lisuxiao
	  * @date 2015年9月13日 上午11:46:07
	  */
	void modifyPriceById(Map<String, Object> params);
	/**
	  * @Title: selectOrderSubBySubscriptionId
	  * @Description: 根据订阅id获取子订单信息
	  * @param subscriptionId
	  * @return List<OrderSub>   
	  * @throws 
	  * @author lisuxiao
	  * @date 2015年10月21日 下午3:17:35
	  */
	List<OrderSub> selectOrderSubBySubscriptionId(long subscriptionId);
}
