package com.letv.portal.proxy.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.letv.portal.enumeration.ContainerMonitorStatus;
import com.letv.portal.enumeration.DbStatus;
import com.letv.portal.model.ContainerModel;
import com.letv.portal.model.ContainerMonitorModel;
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
	public Map<String,Integer> selectMclusterMonitor() {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("type", "mclustervip");
		List<ContainerModel> containers = this.containerService.selectAllByMap(map);
		
		int nothing = 0;
		int general = 0;
		int serious = 0;
		int crash = 0;
		
		for (ContainerModel containerModel : containers) {
			ContainerMonitorModel monitor = this.buildTaskService.getMonitorData(containerModel); 
			if(ContainerMonitorStatus.NORMAL.getValue() == Integer.parseInt(monitor.getStatus())) {
				nothing++;
			}
			if(ContainerMonitorStatus.GENERAL.getValue() == Integer.parseInt(monitor.getStatus())) {
				general++;
			}
			if(ContainerMonitorStatus.SERIOUS.getValue() == Integer.parseInt(monitor.getStatus())) {
				serious++;
			}
			if(ContainerMonitorStatus.CRASH.getValue() == Integer.parseInt(monitor.getStatus())) {
				crash++;
			}
		}
		Map<String,Integer> data = new HashMap<String,Integer>();
		/*
		 *  nothing 
			tel:sms:email
			sms:email
			刘浩 json中不能有特殊字符，我在在这改了，你回来看看。
		 */
		data.put("nothing", nothing);
		data.put("serious", general);
		data.put("general", serious);
		data.put("crash", crash);
		return data;
	}
}
