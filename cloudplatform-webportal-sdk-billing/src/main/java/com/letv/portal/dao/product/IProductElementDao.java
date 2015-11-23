package com.letv.portal.dao.product;

import java.util.List;

import com.letv.common.dao.IBaseDao;
import com.letv.portal.model.product.ProductElement;

public interface IProductElementDao extends IBaseDao<ProductElement> {

	List<ProductElement> selectByProductIdWithBaseElement(Long productId);
}
