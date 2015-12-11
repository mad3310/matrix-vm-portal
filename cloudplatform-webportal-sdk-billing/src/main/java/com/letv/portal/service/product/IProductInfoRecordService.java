package com.letv.portal.service.product;

import com.letv.portal.model.product.ProductInfoRecord;
import com.letv.portal.service.IBaseService;

/**
 * 页面商品信息服务
 * @author lisuxiao
 *
 */
public interface IProductInfoRecordService extends IBaseService<ProductInfoRecord> {
	/**
	  * @Title: selectIdByInstanceId
	  * @Description: 根据实例id获取商品信息id
	  * @param instanceId
	  * @return Long   
	  * @throws 
	  * @author lisuxiao
	  * @date 2015年12月11日 下午3:42:08
	  */
	Long selectIdByInstanceId(String instanceId);
}
