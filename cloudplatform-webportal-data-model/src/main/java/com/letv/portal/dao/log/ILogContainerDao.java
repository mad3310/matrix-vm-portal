package com.letv.portal.dao.log;

import java.util.List;

import com.letv.common.dao.IBaseDao;
import com.letv.portal.model.ContainerModel;
import com.letv.portal.model.log.LogContainer;

public interface ILogContainerDao extends IBaseDao<LogContainer> {

	public  List<LogContainer> selectContainerByLogClusterId(Long clusterId);

	public LogContainer selectByName(String containerName);
}
