package com.letv.portal.service.base.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.letv.common.dao.IBaseDao;
import com.letv.portal.dao.base.IBasePriceDao;
import com.letv.portal.model.base.BasePrice;
import com.letv.portal.service.base.IBasePriceService;
import com.letv.portal.service.common.impl.BaseServiceImpl;

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
