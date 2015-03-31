package com.letv.portal.dao.gce;

import java.util.List;

import com.letv.common.dao.IBaseDao;
import com.letv.portal.model.ContainerModel;
import com.letv.portal.model.gce.GceContainer;

public interface IGceContainerDao extends IBaseDao<GceContainer> {

	public  List<GceContainer> selectContainerByGceClusterId(Long clusterId);
}
