package com.letv.portal.service.log;

import java.util.List;
import java.util.Map;

import com.letv.portal.model.HclusterModel;
import com.letv.portal.model.gce.GceCluster;
import com.letv.portal.model.log.LogCluster;
import com.letv.portal.service.IBaseService;

public interface ILogClusterService extends IBaseService<LogCluster> {

	Boolean isExistByName(String string);

	List<LogCluster> selectByName(String clusterName);
	
	public void asyncClusterCount(Map<String,Object> mm,HclusterModel hcluster);
}
