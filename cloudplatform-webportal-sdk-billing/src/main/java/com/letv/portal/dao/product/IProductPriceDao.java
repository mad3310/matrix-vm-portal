package com.letv.portal.dao.product;

import java.util.Map;

import com.letv.common.dao.IBaseDao;
import com.letv.portal.model.product.ProductPrice;

public interface IProductPriceDao extends IBaseDao<ProductPrice> {
	ProductPrice selectProductPriceByMap(Map<String, Object> params);
}
