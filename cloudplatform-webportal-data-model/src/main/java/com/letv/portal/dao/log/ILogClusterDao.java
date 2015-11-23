package com.letv.portal.dao.log;

import java.util.List;

import com.letv.common.dao.IBaseDao;
import com.letv.portal.model.MclusterModel;
import com.letv.portal.model.log.LogCluster;

public interface ILogClusterDao extends IBaseDao<LogCluster> {

	List<LogCluster> selectByName(String clusterName);

}
