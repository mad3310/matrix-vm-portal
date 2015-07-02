package com.letv.portal.dao.cloudvm;

import java.util.HashMap;
import java.util.List;

import com.letv.common.dao.IBaseDao;
import com.letv.portal.model.cloudvm.CloudvmRegion;

public interface ICloudvmRegionDao extends IBaseDao<CloudvmRegion> {
	public List<CloudvmRegion> selectByCode(HashMap<String, Object> params);
}
