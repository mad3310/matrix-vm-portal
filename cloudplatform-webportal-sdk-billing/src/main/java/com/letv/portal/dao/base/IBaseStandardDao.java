package com.letv.portal.dao.base;

import java.util.List;
import java.util.Map;

import com.letv.common.dao.IBaseDao;
import com.letv.portal.model.base.BaseStandard;

public interface IBaseStandardDao extends IBaseDao<BaseStandard> {
	List<Map<String, Object>> selectStadardInfoWithFatherId(Map<String, Object> map);
	List<BaseStandard> selectBaseStandardWithPrice(Long baseElementId);
	/**
	  * @Title: selectBaseStandardWithPriceByElementName
	  * @Description: 根据元素名称查询规格及基础价格
	  * @param standard
	  * @return List<BaseStandard>   
	  * @throws 
	  * @author lisuxiao
	  * @date 2015年9月7日 下午5:18:11
	  */
	List<BaseStandard> selectBaseStandardWithPriceByElementName(String elementName);
	/**
	  * @Title: selectBaseStandardByProductId
	  * @Description: 根据产品id查询该产品元素下所有规格
	  * @param productId
	  * @return List<BaseStandard>   
	  * @throws 
	  * @author lisuxiao
	  * @date 2015年11月20日 上午11:30:29
	  */
	List<BaseStandard> selectBaseStandardByProductId(Long productId);
}
