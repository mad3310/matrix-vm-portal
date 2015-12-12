package com.letv.portal.dao.product;

import com.letv.common.dao.IBaseDao;
import com.letv.portal.model.product.ProductInfoRecord;

public interface IProductInfoRecordDao extends IBaseDao<ProductInfoRecord> {
	/**
	  * @Title: selectIdByInstanceId
	  * @Description: 根据实例id获取商品信息记录表id
	  * @param instanceId
	  * @return Long   
	  * @throws 
	  * @author lisuxiao
	  * @date 2015年12月11日 下午3:43:14
	  */
	Long selectIdByInstanceId(String instanceId);

}
