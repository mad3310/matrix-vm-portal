package com.letv.portal.service.product;

import com.letv.common.result.ResultObject;
import com.letv.lcp.openstack.model.billing.CheckResult;
import com.letv.portal.model.order.Order;


/**
 * 产品管理类，分配具体服务产品类
 * @author lisuxiao
 *
 */
public interface IProductManageService {
	
	boolean buy(Long id, String paramsData, String displayData, String groupId, ResultObject obj);
	
	/**
	  * @Title: createOrder
	  * @Description: 生成总订单
	  * @return Long   
	  * @throws 
	  * @author lisuxiao
	  * @date 2016年3月10日 上午9:36:28
	  */
	Order createOrder(String paramsData);
	
	CheckResult validateParamsDataByServiceProvider(Long id, String params, boolean auditUser);
	
}
