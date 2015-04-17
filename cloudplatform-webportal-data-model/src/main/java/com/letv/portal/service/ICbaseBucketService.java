package com.letv.portal.service;

import com.letv.portal.model.cbase.CbaseBucketModel;


public interface ICbaseBucketService extends IBaseService<CbaseBucketModel> {

	public void dbList(Long cbaseId);
}
