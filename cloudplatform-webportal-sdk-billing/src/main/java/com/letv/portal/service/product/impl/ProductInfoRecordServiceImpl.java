package com.letv.portal.service.product.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.letv.common.dao.IBaseDao;
import com.letv.portal.dao.product.IProductInfoRecordDao;
import com.letv.portal.model.product.ProductInfoRecord;
import com.letv.portal.service.impl.BaseServiceImpl;
import com.letv.portal.service.product.IProductInfoRecordService;

@Service("productInfoRecordService")
public class ProductInfoRecordServiceImpl extends BaseServiceImpl<ProductInfoRecord> implements IProductInfoRecordService {
	
	private final static Logger logger = LoggerFactory.getLogger(ProductInfoRecordServiceImpl.class);
	
	public ProductInfoRecordServiceImpl() {
		super(ProductInfoRecord.class);
	}

	@Autowired
	private IProductInfoRecordDao productInfoRecordDao;

	@Override
	public IBaseDao<ProductInfoRecord> getDao() {
		return this.productInfoRecordDao;
	}

	@Override
	public Long selectIdByInstanceId(String instanceId) {
		return this.productInfoRecordDao.selectIdByInstanceId(instanceId);
	}


}
