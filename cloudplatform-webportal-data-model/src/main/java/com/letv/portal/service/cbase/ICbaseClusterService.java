package com.letv.portal.service.cbase;

import java.util.List;

import com.letv.portal.model.MclusterModel;
import com.letv.portal.model.cbase.CbaseClusterModel;
import com.letv.portal.service.IBaseService;

public interface ICbaseClusterService extends IBaseService<CbaseClusterModel> {

	public List<CbaseClusterModel> selectByName(String cbaseclusterName);
	public boolean isExistByName(String cbaseclusterName);
}
