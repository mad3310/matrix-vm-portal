package com.letv.portal.proxy;

import com.letv.portal.model.cbase.CbaseBucketModel;

public interface ICbaseProxy extends IBaseProxy<CbaseBucketModel> {

	public void saveAndBuild(CbaseBucketModel cbaseBucketModel);
}
