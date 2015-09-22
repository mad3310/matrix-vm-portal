package com.letv.portal.dao.order;

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
}
