package com.letv.portal.service.impl;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.letv.common.dao.IBaseDao;
import com.letv.portal.dao.IDbDao;
import com.letv.portal.dao.cbase.ICbaseBucketDao;
import com.letv.portal.model.DbModel;
import com.letv.portal.model.cbase.CbaseBucketModel;
import com.letv.portal.service.ICbaseBucketService;

@Service("cbaseBucketService")
public class CbaseBucketServiceImpl extends BaseServiceImpl<CbaseBucketModel>
		implements ICbaseBucketService {

	private final static Logger logger = LoggerFactory
			.getLogger(CbaseBucketServiceImpl.class);

	@Resource
	private ICbaseBucketDao cbaseBucketDao;
	
	
	public CbaseBucketServiceImpl() {
		super(CbaseBucketModel.class);
	}

	@Override
	public IBaseDao<CbaseBucketModel> getDao() {
		return this.cbaseBucketDao;
	}

	public void dbList(Long cbaseId) {
		System.out.println("succeed " + cbaseId);
		CbaseBucketModel cbaseBucketModel = new CbaseBucketModel();
		
		cbaseBucketDao.insert(cbaseBucketModel);

	}

}
