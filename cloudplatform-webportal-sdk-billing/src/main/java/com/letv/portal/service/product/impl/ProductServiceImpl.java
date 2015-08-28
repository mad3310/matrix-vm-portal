package com.letv.portal.service.product.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.letv.common.dao.IBaseDao;
import com.letv.portal.dao.product.IProductDao;
import com.letv.portal.model.product.Product;
import com.letv.portal.service.impl.BaseServiceImpl;
import com.letv.portal.service.product.IProductService;

@Service("productService")
public class ProductServiceImpl extends BaseServiceImpl<Product> implements IProductService {
	
	public ProductServiceImpl() {
		super(Product.class);
	}

	@Autowired
	private IProductDao productDao;

	@Override
	public IBaseDao<Product> getDao() {
		return this.productDao;
	}


}
