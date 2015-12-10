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
	/**
	  * @Title: selectOrderSubByProductInfoRecordId
	  * @Description: 根据产品信息记录表id查询有效订单
	  * @param productInfoRecordId
	  * @return List<OrderSub>   
	  * @throws 
	  * @author lisuxiao
	  * @date 2015年12月10日 上午11:19:33
	  */
	List<OrderSub> selectOrderSubByProductInfoRecordId(long productInfoRecordId);
	/**
	  * @Title: selectDetailBySubscriptionId
	  * @Description: 获取订单订阅相关信息
	  * @param params
	  * @return List<OrderSub>   
	  * @throws 
	  * @author lisuxiao
	  * @date 2015年11月2日 下午3:55:10
	  */
	OrderSub selectDetailBySubscriptionId(Map<String, Object> params);
}
