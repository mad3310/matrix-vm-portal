package com.letv.portal.service.product;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.letv.portal.model.product.Product;
import com.letv.portal.model.product.ProductElement;
import com.letv.portal.service.IBaseService;

/**Program Name: IBaseElementService <br>
 * Description:  产品<br>
 * @author name: liuhao1 <br>
 * Written Date: 2015年7月30日 <br>
 * Modified By: <br>
 * Modified Date: <br>
 */
public interface IProductService extends IBaseService<Product> {
	Map<String, Object> queryProductDetailById(Long id);
	/**
	  * @Title: getRegionIdByCode
	  * @Description: 地域名称获取地域ID
	  * @param regionCode
	  * @return Long   
	  * @throws 
	  * @author lisuxiao
	  * @date 2015年9月19日 下午4:30:07
	  */
	Long getRegionIdByCode(String regionCode);
	/**
	  * @Title: validateData
	  * @Description: 验证产品信息是否合法
	  * @param id
	  * @param map
	  * @return boolean   
	  * @throws 
	  * @author lisuxiao
	  * @date 2015年9月6日 下午5:00:37
	  */
	boolean validateData(Long id, Map<String,Object> map);
	
	/**
	  * @Title: selectByProductIdWithBaseElement
	  * @Description: 根据产品id获取产品元素
	  * @param productId
	  * @return List<ProductElement>   
	  * @throws 
	  * @author lisuxiao
	  * @date 2015年11月11日 下午3:35:45
	  */
	List<ProductElement> selectByProductIdWithBaseElement(Long productId);
	/**
	  * @Title: dailyConsume
	  * @Description: 获取该用户下各产品每日消费情况
	  * @return Map<Long, BigDecimal>   
	  * @throws 
	  * @author lisuxiao
	  * @date 2015年10月21日 下午2:20:14
	  */
	Map<Long, BigDecimal> dailyConsume();
}
