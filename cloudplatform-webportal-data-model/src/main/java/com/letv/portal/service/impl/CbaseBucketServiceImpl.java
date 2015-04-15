package com.letv.portal.service.impl;

import com.letv.common.dao.IBaseDao;
import com.letv.portal.model.cbase.CbaseBucketModel;
import com.letv.portal.service.ICbaseBucketService;

public class CbaseBucketServiceImpl extends BaseServiceImpl<CbaseBucketModel>
implements ICbaseBucketService{

	public CbaseBucketServiceImpl(Class<CbaseBucketModel> entityClass) {
		super(entityClass);
		// TODO Auto-generated constructor stub
	}

	@Override
	public IBaseDao<CbaseBucketModel> getDao() {
		// TODO Auto-generated method stub
		return null;
	}

}
