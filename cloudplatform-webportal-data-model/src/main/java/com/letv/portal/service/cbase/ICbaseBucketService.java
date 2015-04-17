package com.letv.portal.service.cbase;

import java.util.Map;

import com.letv.portal.model.cbase.CbaseBucketModel;
import com.letv.portal.service.IBaseService;

public interface ICbaseBucketService extends IBaseService<CbaseBucketModel> {

	Map<String, Object> save(CbaseBucketModel cbaseBucketModel);
	public void dbList(Long cbaseId);
}
