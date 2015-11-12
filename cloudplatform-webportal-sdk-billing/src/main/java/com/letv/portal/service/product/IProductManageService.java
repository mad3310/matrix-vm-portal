package com.letv.portal.service.product;

import com.letv.common.result.ResultObject;
import com.letv.portal.service.openstack.billing.CheckResult;


/**
 * 产品管理类，分配具体服务产品类
 * @author lisuxiao
 *
 */
public interface IProductManageService {
	
	boolean buy(Long id, String paramsData, String displayData, ResultObject obj);
	
	CheckResult validateParamsDataByServiceProvider(Long id, String params);
	
}
