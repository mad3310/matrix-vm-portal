package com.letv.portal.proxy.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.letv.portal.enumeration.DbStatus;
import com.letv.portal.model.ContainerModel;
import com.letv.portal.model.ContainerMonitorModel;
import com.letv.portal.model.MclusterModel;
import com.letv.portal.proxy.IContainerProxy;
import com.letv.portal.proxy.IDashBoardProxy;
import com.letv.portal.python.service.IBuildTaskService;
import com.letv.portal.service.IContainerService;
import com.letv.portal.service.IDbService;
import com.letv.portal.service.IDbUserService;
import com.letv.portal.service.IHclusterService;
import com.letv.portal.service.IHostService;
import com.letv.portal.service.IMclusterService;

@Component
public class DashBoardProxyImpl implements IDashBoardProxy{
	
	private final static Logger logger = LoggerFactory.getLogger(DashBoardProxyImpl.class);

	
	@Autowired
	private IMclusterService mclusterService;
	@Autowired
	private IContainerService containerService;
	@Autowired
	private IDbService dbService;
	@Autowired
	private IDbUserService dbUserService;
	@Autowired
	private IHclusterService hclusterService;
	@Autowired
	private IHostService hostService;
	@Autowired
	private IBuildTaskService buildTaskService;
	
	@Autowired
	private IContainerProxy containerProxy;
	
	@Override
	public Map<String, Integer> selectManagerResource() {
		Map<String,Integer> statistics = new HashMap<String,Integer>();
		statistics.put("db", this.dbService.selectByMapCount(null));
		statistics.put("dbUser", this.dbUserService.selectByMapCount(null));
		statistics.put("mcluster", this.mclusterService.selectByMapCount(null));
		statistics.put("hcluster", this.hclusterService.selectByMapCount(null));
		statistics.put("host", this.hostService.selectByMapCount(null));
		
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("status", DbStatus.DEFAULT.getValue());
		statistics.put("dbAudit", this.dbService.selectByMapCount(map));
		statistics.put("dbUserAudit", this.dbUserService.selectByMapCount(map));
		map.put("status", DbStatus.BUILDFAIL.getValue());
		statistics.put("dbFaild", this.dbService.selectByMapCount(map));
		statistics.put("dbUserFaild", this.dbUserService.selectByMapCount(map));
		map.put("status", DbStatus.BUILDDING.getValue());
		statistics.put("dbBuilding", this.dbService.selectByMapCount(map));
		statistics.put("dbUserBuilding", this.dbUserService.selectByMapCount(map));
		return statistics;
	}

	@Override
	public List<ContainerMonitorModel> selectMclusterMonitor() {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("type", "mclustervip");
		List<ContainerModel> containers = this.containerService.selectAllByMap(map);
		return this.buildTaskService.getMonitorData(containers);
	}
}
