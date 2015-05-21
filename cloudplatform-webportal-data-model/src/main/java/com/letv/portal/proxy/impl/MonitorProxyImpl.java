package com.letv.portal.proxy.impl;

import java.math.BigInteger;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.letv.common.exception.ValidateException;
import com.letv.portal.model.ContainerModel;
import com.letv.portal.model.DbModel;
import com.letv.portal.model.MonitorIndexModel;
import com.letv.portal.model.gce.GceCluster;
import com.letv.portal.model.gce.GceContainer;
import com.letv.portal.model.gce.GceServer;
import com.letv.portal.model.monitor.MonitorViewYModel;
import com.letv.portal.model.slb.SlbCluster;
import com.letv.portal.model.slb.SlbContainer;
import com.letv.portal.model.slb.SlbServer;
import com.letv.portal.model.swift.SwiftServer;
import com.letv.portal.proxy.IMonitorProxy;
import com.letv.portal.python.service.IBuildTaskService;
import com.letv.portal.service.IContainerService;
import com.letv.portal.service.IDbService;
import com.letv.portal.service.IMonitorIndexService;
import com.letv.portal.service.IMonitorService;
import com.letv.portal.service.gce.IGceClusterService;
import com.letv.portal.service.gce.IGceContainerService;
import com.letv.portal.service.gce.IGceServerService;
import com.letv.portal.service.slb.ISlbClusterService;
import com.letv.portal.service.slb.ISlbContainerService;
import com.letv.portal.service.slb.ISlbServerService;
import com.letv.portal.service.swift.ISwiftServerService;

@Component("monitorProxy")
public class MonitorProxyImpl implements IMonitorProxy{
	private final static Logger logger = LoggerFactory.getLogger(MonitorProxyImpl.class);
	@Autowired
	private IMonitorService monitorService;
	@Autowired
	private IMonitorService monitorServiceByJdbc;
	
	@Autowired
	private IBuildTaskService buildTaskService;
	
	@Autowired
	private IContainerService containerService;
	@Autowired
	private ISlbClusterService slbClusterService;
	@Autowired
	private IGceClusterService gceClusterService;
	@Autowired
	private IDbService dbService;
	@Autowired
	private IGceServerService gceServerService;
	@Autowired
	private ISwiftServerService swiftServerService;
	@Autowired
	private IGceContainerService gceContainerService;
	@Autowired
	private ISlbServerService slbServerService;
	@Autowired
	private ISlbContainerService slbContainerService;
	
	@Autowired
	private IMonitorIndexService monitorIndexService;
	
	@Override
	public void collectMclusterServiceData() {
		Map<String,String> map = new  HashMap<String,String>();
		map.put("type", "mclusternode");
		List<ContainerModel> contianers = this.containerService.selectByMap(map);
		
		Map<String,Object> indexParams = new  HashMap<String,Object>();
		indexParams.put("status", 1);
		List<MonitorIndexModel> indexs = this.monitorIndexService.selectByMap(indexParams);
		Date date = new Date();
		logger.info("collectMclusterServiceData start" + date);
		for (MonitorIndexModel index : indexs) {
			for (ContainerModel container : contianers) {
				this.buildTaskService.getContainerServiceData(container, index,date);
			}
		}
		logger.info("collectMclusterServiceData end");
	}
	@Override
	public void collectClusterServiceData() {
		List<SlbCluster> slbClusters = this.slbClusterService.selectByMap(null);
		List<GceCluster> gceClusters = this.gceClusterService.selectByMap(null);
		
		Map<String,Object> indexParams = new  HashMap<String,Object>();
		indexParams.put("status", 2);
		List<MonitorIndexModel> indexs = this.monitorIndexService.selectByMap(indexParams);
		Date date = new Date();
		logger.info("collectClusterServiceData start" + date);
		for (MonitorIndexModel index : indexs) {
			for (GceCluster gceCluster : gceClusters) {
				this.buildTaskService.getClusterServiceData(gceCluster.getClusterName(),gceCluster.getHclusterId(), index,date);
			}
			for (SlbCluster slbCluster : slbClusters) {
				this.buildTaskService.getClusterServiceData(slbCluster.getClusterName(),slbCluster.getHclusterId(), index,date);
			}
		}
		logger.info("collectClusterServiceData end");
	}
	@Override
	public void collectOSSServiceData() {
		/*List<SwiftServer> swifts = this.swiftServerService.selectByMap(null);
		
		Map<String,Object> indexParams = new  HashMap<String,Object>();
		indexParams.put("status", 3);
		List<MonitorIndexModel> indexs = this.monitorIndexService.selectByMap(indexParams);
		Date date = new Date();
		logger.info("collectClusterServiceData start" + date);
		for (MonitorIndexModel index : indexs) {
			for (SwiftServer swift : swifts) {
				this.buildTaskService.getClusterServiceData(swift, index,date);
			}
		}*/
	}
	
	@Override
	public List<MonitorViewYModel> getMonitorViewData(Long mclusterId,Long chartId, Integer strategy) {
		return this.monitorService.getMonitorViewData(mclusterId, chartId, strategy);
	}
	@Override
	@Async
	public void deleteOutData() {
		Map<String,Object> indexParams = new  HashMap<String,Object>();
		indexParams.put("status", 1);
		List<MonitorIndexModel> indexs = this.monitorIndexService.selectByMap(indexParams);
		
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, -1);    //得到前一个月
		long date = cal.getTimeInMillis();
		Date monthAgo = new Date(date);
		
		Map<String, Object> map = new HashMap<String, Object>();
		for (MonitorIndexModel monitorIndexModel : indexs) {
			
			//get max id and min id from table where monitor_date<monthAgo
			//for in  min and max, delete every 5000 by id.
			
			map.put("dbName", monitorIndexModel.getDetailTable());
			map.put("monitorDate", monthAgo);
			List<Map<String,Object>> ids = this.monitorService.selectExtremeIdByMonitorDate(map);
			if(ids.isEmpty() || ids.get(0) == null || ids.get(0).isEmpty()) {
				continue;
			}
			Map<String, Object> extremeIds = ids.get(0);
			Long max = ((BigInteger)extremeIds.get("maxId")).longValue();
			Long min = ((BigInteger)extremeIds.get("minId")).longValue();
			if(max == null || max == 0 || max == min)
				return;
			Long j = min;
			for (Long i = min; i <= max; i+=100) {
				j = i-100;
				map.put("min", j);
				map.put("max", i);
//				this.monitorService.deleteOutDataByIndex(map);
				this.monitorServiceByJdbc.deleteOutDataByIndex(map);
			}
			map.put("min", max-100);
			map.put("max", max);
			this.monitorServiceByJdbc.deleteOutDataByIndex(map);
		}
	}
	@Override
	public List<MonitorViewYModel> getData(String type, Long serverId,Long chartId, Integer strategy, boolean isTimeAveraging,int format) {
		if("RDS".equals(type.toUpperCase()))
			return this.getDbData(serverId, chartId, strategy, isTimeAveraging,format);
		if("GCE".equals(type.toUpperCase()))
			return this.getGceData(serverId, chartId, strategy, isTimeAveraging,format);
		if("SLB".equals(type.toUpperCase()))
			return this.getSlbData(serverId, chartId, strategy, isTimeAveraging,format);
		return null;
	}
	@Override
	public List<MonitorViewYModel> getDbData(Long serverId,Long chartId, Integer strategy,boolean isTimeAveraging,int format) {
		DbModel db = this.dbService.selectById(serverId);
		if(db == null || db.getMclusterId() == null)
			throw new ValidateException("参数不合法");
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("mclusterId", db.getMclusterId());
		map.put("type", "mclusternode");
		List<ContainerModel> containers = this.containerService.selectByMap(map);	
		if(containers == null || containers.isEmpty())
			throw new ValidateException("参数不合法");
		List<MonitorViewYModel> data = this.monitorService.getMonitorData(containers.get(0).getIpAddr(), chartId, strategy,isTimeAveraging,format);
		return data;
	}
	public List<MonitorViewYModel> getGceData(Long serverId,Long chartId, Integer strategy,boolean isTimeAveraging,int format) {
		GceServer gce = this.gceServerService.selectById(serverId);
		if(gce == null || gce.getGceClusterId() == null)
			throw new ValidateException("参数不合法");
		
		List<GceContainer> containers = this.gceContainerService.selectByGceClusterId(gce.getGceClusterId());
		if(containers == null || containers.isEmpty())
			throw new ValidateException("参数不合法");
		List<MonitorViewYModel> data = this.monitorService.getMonitorData(containers.get(0).getContainerName(), chartId, strategy,isTimeAveraging,format);
		return data;
	}
	public List<MonitorViewYModel> getSlbData(Long serverId,Long chartId, Integer strategy,boolean isTimeAveraging,int format) {
		SlbServer slb = this.slbServerService.selectById(serverId);
		if(slb == null || slb.getSlbClusterId() == null)
			throw new ValidateException("参数不合法");
		List<SlbContainer> containers = this.slbContainerService.selectBySlbClusterId(slb.getSlbClusterId());
		if(containers == null || containers.isEmpty())
			throw new ValidateException("参数不合法");
		List<MonitorViewYModel> data = this.monitorService.getMonitorData(containers.get(0).getContainerName(), chartId, strategy,isTimeAveraging,format);
		return data;
	}

}
