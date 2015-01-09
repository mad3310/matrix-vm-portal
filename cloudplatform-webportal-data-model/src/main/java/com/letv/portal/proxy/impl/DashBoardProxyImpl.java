package com.letv.portal.proxy.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.letv.common.session.SessionServiceImpl;
import com.letv.portal.enumeration.DbStatus;
import com.letv.portal.enumeration.MonitorStatus;
import com.letv.portal.model.ContainerModel;
import com.letv.portal.model.DbModel;
import com.letv.portal.model.MclusterModel;
import com.letv.portal.model.monitor.BaseMonitor;
import com.letv.portal.proxy.IContainerProxy;
import com.letv.portal.proxy.IDashBoardProxy;
import com.letv.portal.python.service.IBuildTaskService;
import com.letv.portal.service.IContainerService;
import com.letv.portal.service.IDbService;
import com.letv.portal.service.IDbUserService;
import com.letv.portal.service.IHclusterService;
import com.letv.portal.service.IHostService;
import com.letv.portal.service.IMclusterService;
import com.letv.portal.service.IMonitorService;

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
	@Autowired
	private IMonitorService monitorService;
	
	@Autowired(required=false)
	private SessionServiceImpl sessionService;
	
	@Value("${db.auto.build.count}")
	private int DB_AUTO_BUILD_COUNT;
	
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
	public Map<String, Integer> selectMonitorAlert(Long monitorType) {
		List<MclusterModel> mclusters = this.mclusterService.selectValidMclusters();
		
		
		int nothing = 0;
		int general = 0;
		int serious = 0;
		int crash = 0;
		int timeout = 0;
		for (MclusterModel mcluster : mclusters) {
			ContainerModel container = this.selectValidVipContianer(mcluster.getId(), "mclustervip");
			if(container == null) {
				timeout++;
				continue;
			}
			BaseMonitor monitor = this.buildTaskService.getMonitorData(container.getIpAddr(),monitorType); 
			if(MonitorStatus.NORMAL.getValue() == monitor.getResult()) {
				nothing++;
			}
			if(MonitorStatus.GENERAL.getValue() == monitor.getResult()) {
				general++;
			}
			if(MonitorStatus.SERIOUS.getValue() == monitor.getResult()) {
				serious++;
			}
			if(MonitorStatus.CRASH.getValue() == monitor.getResult()) {
				crash++;
			}
			if(MonitorStatus.TIMEOUT.getValue() == monitor.getResult()) {
				timeout++;
			}
		}
		Map<String,Integer> data = new HashMap<String,Integer>();
		/*
		 *  nothing 
			tel:sms:email
			sms:email
		 */
		data.put("nothing", nothing);
		data.put("general", general);
		data.put("serious", serious);
		data.put("crash", crash);
		data.put("timeout", timeout);
		return data;
	}
	
	private ContainerModel selectValidVipContianer(Long mclusterId,String type){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("mclusterId", mclusterId);
		map.put("type", type);
		List<ContainerModel> containers = this.containerService.selectAllByMap(map);
		if(containers.isEmpty()) {
			return null;
		}
		return containers.get(0);
	}

	@Override
	public Map<String, Integer> selectAppResource() {
		Map<String,Integer> statistics = new HashMap<String,Integer>();
		
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("createUser", sessionService.getSession().getUserId());
		
		Integer db = this.dbService.selectByMapCount(map);
		Integer dbFree = DB_AUTO_BUILD_COUNT - db;
		statistics.put("db",db);
		statistics.put("dbFree", dbFree>0?dbFree:0);
		statistics.put("dbUser", this.dbUserService.selectByMapCount(map));
		return statistics;
	}

	@Override
	public Map<String, Float> selectDbStorage() {
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("createUser", sessionService.getSession().getUserId());
		map.put("status", DbStatus.NORMAL.getValue());
		List<DbModel> dbs = this.dbService.selectByMap(map);
		Map<String,Float> storage = new HashMap<String,Float>();
		for (DbModel db : dbs) {
			//依次查询使用量。
			storage.put(db.getDbName(), this.monitorService.selectDbStorage(db.getMclusterId()));
		}
		return storage;
	}

	@Override
	public List<Map<String,Object>> selectDbConnect() {
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("createUser", sessionService.getSession().getUserId());
		map.put("status", DbStatus.NORMAL.getValue());
		List<DbModel> dbs = this.dbService.selectByMap(map);
		List<Map<String,Object>> connect = new ArrayList<Map<String,Object>>();
		for (DbModel db : dbs) {
			HashMap<String, Object> data = new HashMap<String,Object>();
			data.put("dbName", db.getDbName());
			List<Map<String, Object>> selectDbConnect = this.monitorService.selectDbConnect(db.getMclusterId());
			float total = 0F;
			for (Map<String, Object> value : selectDbConnect) {
				total +=  (Float) value.get("detailValue");
			}
			data.put("value", selectDbConnect);
			data.put("total", total);
			connect.add(data);
		}
		return connect;
	}

}
