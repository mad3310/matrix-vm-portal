package com.letv.portal.service.impl;

import org.springframework.stereotype.Service;

import com.letv.common.dao.IBaseDao;
import com.letv.portal.model.DbModel;
import com.letv.portal.model.cbase.CbaseBucketModel;
import com.letv.portal.service.ICbaseBucketService;

@Service("cbaseBucketService")
public class CbaseBucketServiceImpl extends BaseServiceImpl<CbaseBucketModel>
		implements ICbaseBucketService {

	public CbaseBucketServiceImpl() {
		super(CbaseBucketModel.class);
	}
	
	@Override
	public IBaseDao<CbaseBucketModel> getDao() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void dbList(Long cbaseId){
		System.out.println("succeed "+cbaseId);
		
	}

}
