package com.letv.portal.service.impl;

import com.letv.common.dao.IBaseDao;
import com.letv.portal.model.cbase.CbaseBucketMode;
import com.letv.portal.service.ICbaseBucketService;

public class CbaseBucketServiceImpl extends BaseServiceImpl<CbaseBucketMode>
implements ICbaseBucketService{

	public CbaseBucketServiceImpl(Class<CbaseBucketMode> entityClass) {
		super(entityClass);
		// TODO Auto-generated constructor stub
	}

	@Override
	public IBaseDao<CbaseBucketMode> getDao() {
		// TODO Auto-generated method stub
		return null;
	}

}
