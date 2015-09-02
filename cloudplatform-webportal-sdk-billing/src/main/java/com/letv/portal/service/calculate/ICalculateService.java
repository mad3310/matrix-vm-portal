package com.letv.portal.service.calculate;

import java.util.Map;


/**
 * 价格计算
 * @author lisuxiao
 *
 */
public interface ICalculateService {
	Double calculatePrice(Long productId, Map<String, Object> map);
}
