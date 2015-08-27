package com.letv.portal.dao.cbase;

import java.util.List;

import com.letv.common.dao.IBaseDao;
import com.letv.portal.model.cbase.CbaseContainerModel;

public interface ICbaseContainerDao extends IBaseDao<CbaseContainerModel> {

	public List<CbaseContainerModel> selectContainerByCbaseClusterId(
			Long clusterId);

	public CbaseContainerModel selectByName(String containerName);
}
