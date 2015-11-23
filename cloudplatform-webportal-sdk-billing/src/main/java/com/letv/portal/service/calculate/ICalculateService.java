package com.letv.portal.service.calculate;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.letv.portal.model.base.BaseStandard;


/**
 * 价格计算
 * @author lisuxiao
 *
 */
public interface ICalculateService {
	BigDecimal calculatePrice(Long productId, Map<String, Object> map);
	/**
	  * @Title: calculatePrice
	  * @Description: 根据参数计算产品价格
	  * @param productId 产品id
	  * @param map 产品参数
	  * @param baseStandards 产品所有元素的所有规格
	  * @return BigDecimal   
	  * @throws 
	  * @author lisuxiao
	  * @date 2015年11月20日 下午3:00:21
	  */
	BigDecimal calculatePrice(Long productId, Map<String, Object> map, List<BaseStandard> baseStandards);
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
	  * @param baseStandards 传入一个元素的所有规格
	  * @return BigDecimal  价格
	  * @throws 
	  * @author lisuxiao
	  * @date 2015年11月20日 下午7:02:27
	  */
	BigDecimal calculateStandardPrice(Long productId, Long baseRegionId, String standardName, String standardValue, 
			Integer orderNum, Integer orderTime, String standardType, List<BaseStandard> baseStandards);
}
