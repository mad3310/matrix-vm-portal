package com.letv.portal.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.letv.common.dao.IBaseDao;
import com.letv.portal.dao.IBasePriceDao;
import com.letv.portal.model.BasePrice;
import com.letv.portal.service.IBasePriceService;

@Service("BasePriceService")
public class BasePriceServiceImpl extends BaseServiceImpl<BasePrice> implements IBasePriceService {
	
	public BasePriceServiceImpl() {
		super(BasePrice.class);
	}

	@Autowired
	private IBasePriceDao basePriceDao;

	@Override
	public IBaseDao<BasePrice> getDao() {
		return this.basePriceDao;
	}

}
