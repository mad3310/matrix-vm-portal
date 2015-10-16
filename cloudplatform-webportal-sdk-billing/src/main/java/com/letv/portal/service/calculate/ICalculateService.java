package com.letv.portal.service.calculate;

import java.math.BigDecimal;
import java.util.Map;


/**
 * 价格计算
 * @author lisuxiao
 *
 */
public interface ICalculateService {
	BigDecimal calculatePrice(Long productId, Map<String, Object> map);
	/**
	  * @Title: calculateStandardPrice
	  * @Description: 计算产品中具体规格价格
	  * @param productId 产品ID
	  * @param baseRegionId 地域ID
	  * @param standardName 规格名称
	  * @param standardValue 规格值
	  * @param orderNum 购买数量
	  * @param orderTime 购买时长
	  * @param standardType 规格类型
	  * @return Double 价格
	  * @throws 
	  * @author lisuxiao
	  * @date 2015年9月7日 下午4:51:12
	  */
	BigDecimal calculateStandardPrice(Long productId, Long baseRegionId, String standardName, String standardValue, 
			Integer orderNum, Integer orderTime, String standardType);
}
