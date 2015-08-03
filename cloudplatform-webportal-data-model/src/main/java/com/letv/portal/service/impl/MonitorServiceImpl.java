package com.letv.portal.service.impl;


import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.letv.common.dao.IBaseDao;
import com.letv.portal.dao.IMonitorDao;
import com.letv.portal.model.ContainerModel;
import com.letv.portal.model.MonitorDetailModel;
import com.letv.portal.model.MonitorIndexModel;
import com.letv.portal.model.monitor.MonitorViewYModel;
import com.letv.portal.model.monitor.mysql.MysqlDbSpaceMonitor;
import com.letv.portal.service.IContainerService;
import com.letv.portal.service.IMonitorIndexService;
import com.letv.portal.service.IMonitorService;
import com.letv.portal.service.monitor.mysql.IMysqlDbSpaceMonitorService;
import com.letv.portal.service.monitor.mysql.IMysqlHealthMonitorService;
import com.letv.portal.service.monitor.mysql.IMysqlInnoDBMonitorService;
import com.letv.portal.service.monitor.mysql.IMysqlKeyBufferMonitorService;
import com.letv.portal.service.monitor.mysql.IMysqlResourceMonitorService;
import com.letv.portal.service.monitor.mysql.IMysqlTableSpaceMonitorService;

@Service("monitorService")
public class MonitorServiceImpl extends BaseServiceImpl<MonitorDetailModel> implements IMonitorService {

	private final static Logger logger = LoggerFactory.getLogger(MonitorServiceImpl.class);
	
	@Autowired
	private IMonitorDao monitorDao;
	
	@Autowired
	private IMonitorIndexService monitorIndexService;
	
	@Autowired
	private IContainerService containerService;
	
	@Autowired
	private IMysqlHealthMonitorService mysqlHealthMonitorService;
	@Autowired
	private IMysqlResourceMonitorService mysqlResourceMonitorService;
	@Autowired
	private IMysqlKeyBufferMonitorService mysqlKeyBufferMonitorService;
	@Autowired
	private IMysqlInnoDBMonitorService mysqlInnoDBMonitorService;
	@Autowired
	private IMysqlDbSpaceMonitorService mysqlDbSpaceMonitorService;
	@Autowired
	private IMysqlTableSpaceMonitorService mysqlTableSpaceMonitorService;
	
	public MonitorServiceImpl() {
		super(MonitorDetailModel.class);
	}

	
	@Override
	public IBaseDao<MonitorDetailModel> getDao() {
		return this.monitorDao;
	}
	public List<String> selectDistinct(Map map){
		return this.monitorDao.selectDistinct(map);
	}
	

	@Override
	public List<MonitorViewYModel> getMonitorViewData(Long mclusterId,Long chartId,Integer strategy) {
		logger.info("get Data-------start");
		Date start = new Date();
		List<MonitorViewYModel> ydatas = new ArrayList<MonitorViewYModel>();
	    Map<String, Object> map = new HashMap<String, Object>();
	    map.put("mclusterId", mclusterId);
	    map.put("type", "mclusternode");
	    List<ContainerModel> containers = this.containerService.selectByMap(map);	  
	    
	    MonitorIndexModel monitorIndexModel  = this.monitorIndexService.selectById(chartId);	   
	    Date end = new Date();
	    String[] detailNames =  monitorIndexModel.getMonitorPoint().split(",");
	    
		Map<String, Object> params = new HashMap<String, Object>();
		
		params.put("dbName", monitorIndexModel.getDetailTable());
		params.put("start", getStartDate(end,strategy));
		params.put("end", end);
		Date prepare = new Date();
		logger.info("get Data-------prepare" + (prepare.getTime()-start.getTime())/1000);
		
		for (ContainerModel c : containers) {
			for (String s : detailNames) {
				MonitorViewYModel ydata = new MonitorViewYModel();
				params.put("ip", c.getIpAddr());
				params.put("detailName", s);

				List<MonitorDetailModel> list = this.monitorDao.selectDateTime(params);
				List<List<Object>> datas = new ArrayList<List<Object>>();
				for (MonitorDetailModel monitorDetail : list) {
					List<Object> point = new ArrayList<Object>();
					point.add(monitorDetail.getMonitorDate());
					point.add(monitorDetail.getDetailValue());
					datas.add(point);
				}
				ydata.setName(c.getIpAddr() +":"+s);
				ydata.setData(datas);
				ydatas.add(ydata);
			}
		}
		logger.info("get Data-------end" + (new Date().getTime()-prepare.getTime())/1000);
		return ydatas;
	}
	
	@Override
	public List<MonitorViewYModel> getMonitorData(String ip,Long chartId,Integer strategy,boolean isTimeAveraging,int format) {
		List<MonitorViewYModel> ydatas = new ArrayList<MonitorViewYModel>();
		
		MonitorIndexModel monitorIndexModel  = this.monitorIndexService.selectById(chartId);	   
		Date end = new Date();
		String[] detailNames =  monitorIndexModel.getMonitorPoint().split(",");
		
		Map<String, Object> params = new HashMap<String, Object>();
		
		params.put("dbName", monitorIndexModel.getDetailTable());
		params.put("start", getStartDate(end,strategy));
		params.put("end", end);
		
		/*
		 * 1、按照detailNames进行查询，将contianer数据获取到。
		 * 2、存储到两个list，进行减法计算，除以频次。
		 */
		List<MonitorDetailModel> beforData = new ArrayList<MonitorDetailModel>();
		for (String s : detailNames) {
			MonitorViewYModel ydata = new MonitorViewYModel();
			params.put("ip", ip);
			params.put("detailName", s);
			
			beforData = this.monitorDao.selectDateTime(params); 
			
			List<List<Object>> datas = new ArrayList<List<Object>>();
			if(isTimeAveraging) {
				for (int i = 0; i < beforData.size()-1; i++) {
					List<Object> point = new ArrayList<Object>();
					point.add(beforData.get(i+1).getMonitorDate());
					float diff = beforData.get(i+1).getDetailValue()-beforData.get(i).getDetailValue();
					float time = (beforData.get(i+1).getMonitorDate().getTime()-beforData.get(i).getMonitorDate().getTime())/1000;
					float value = diff>0?diff/time:0;
					value = monitorDataAdapter(value,format);
					point.add(value);
					datas.add(point);
				}
			} else {
				for (int i = 0; i <= beforData.size()-1; i++) {
					List<Object> point = new ArrayList<Object>();
					point.add(beforData.get(i).getMonitorDate());
					float value = beforData.get(i).getDetailValue()>=0?beforData.get(i).getDetailValue():0;
					value = monitorDataAdapter(value,format);
					point.add(value);
					datas.add(point);
				}
			}
			
			ydata.setName(s);
			ydata.setData(datas);
			ydatas.add(ydata);
		}
		return ydatas;
	}
	
	private float monitorDataAdapter(float value, int format) {
		DecimalFormat df=new DecimalFormat("##########.00");
		switch(format) {
			case 1: //B 转   M
				value=value/1024/1024;
				break;
			case 2: //B 转  G
				value = value/1024/1024/1024;
				break;
			default:
				break;
		}
		return Float.valueOf(df.format(value));
		
	}


	private Date getStartDate(Date end, Integer strategy) {
		Calendar now = Calendar.getInstance();
		now.setTime(end);
		switch (strategy) {
		case 1:
			now.add(Calendar.HOUR, -1); //one hour ago
			break;
		case 2:
			now.add(Calendar.HOUR, -3); //three hour ago
			break;
		case 3:
			now.add(Calendar.HOUR, -24);  // one day ago
			break;
		case 4:
			now.add(Calendar.HOUR, -168); // one week ago
			break;
		case 5:
			now.add(Calendar.MONTH, -1); // one month ago
			break;
		default:
			now.add(Calendar.HOUR, -1);
			break;
		}
		return now.getTime();
	}
	
	public List<MonitorDetailModel> selectDateTime(Map map){
		return  this.monitorDao.selectDateTime(map);
	}
	
    public List<MonitorIndexModel> selectMonitorCount(){
    	return this.monitorIndexService.selectMonitorCount();
    }


	@Override
	public Float selectDbStorage(Long mclusterId) {
		List<MonitorViewYModel> ydatas = new ArrayList<MonitorViewYModel>();
	    Map<String, Object> map = new HashMap<String, Object>();
	    map.put("mclusterId", mclusterId);
	    map.put("type", "mclusternode");
	    List<ContainerModel> containers = this.containerService.selectByMap(map);
	    if(containers.size()<0) {
	    	return 0F;
	    }
		return this.monitorDao.selectDbStorage(containers.get(0).getIpAddr());
	}


	@Override
	public List<Map<String,Object>> selectDbConnect(Long mclusterId) {
		List<MonitorViewYModel> ydatas = new ArrayList<MonitorViewYModel>();
	    Map<String, Object> map = new HashMap<String, Object>();
	    map.put("mclusterId", mclusterId);
	    map.put("type", "mclusternode");
	    List<ContainerModel> containers = this.containerService.selectByMap(map);
	    if(containers.size()<0) {
	    	return null;
	    }
		return this.monitorDao.selectDbConnect(containers.get(0).getIpAddr());
	}

	@Override
	public void deleteOutDataByIndex(Map<String, Object> map) {
		this.monitorDao.deleteOutDataByIndex(map);
	}

	@Override
	public List<Map<String, Object>> selectExtremeIdByMonitorDate(
			Map<String, Object> map) {
		return this.monitorDao.selectExtremeIdByMonitorDate(map);
	}


	@Override
	public void addMonitorPartition(Map<String, Object> map) {
		this.monitorDao.addMonitorPartition(map);
	}


	@Override
	public void deleteMonitorPartitionThirtyDaysAgo(Map<String, Object> map) {
		this.monitorDao.deleteMonitorPartitionThirtyDaysAgo(map);
	}
	
	
	@Override
	public void insertMysqlMonitorData(ContainerModel container, Map<String, Object> map, Date d) {
		this.mysqlHealthMonitorService.collectMysqlHealthMonitorData(container, map, d);
		this.mysqlResourceMonitorService.collectMysqlResourceMonitorData(container, map, d);
		this.mysqlKeyBufferMonitorService.collectMysqlKeyBufferMonitorData(container, map, d);
		this.mysqlInnoDBMonitorService.collectMysqlInnoDBMonitorData(container, map, d);
	}


	@Override
	public Map<String, Object> getLatestDataFromMonitorTables(String containerIp, String[] titles, Date d) {
		Map<String, String> param = new HashMap<String, String>();
		List<MonitorIndexModel> indexs = null;
		Map<String, Object> results = new HashMap<String, Object>();
		for (String title : titles) {
			param.put("titleText", title);
			indexs = this.monitorIndexService.selectByMap(param);
			if(indexs!=null && indexs.size()==1) {
				Map<String, Object> result = getLatestDataFromMonitorTable(containerIp, indexs.get(0).getDetailTable(), indexs.get(0).getMonitorPoint(), d);
				results.putAll(result);
			} else if(indexs.size()>1){
				logger.info("have many MonitorIndexModels with titleText is : "+title);
			} else {
				logger.info("have no MonitorIndexModel with titleText is : "+title);
			}
		}
		return results;
	}
	
	/**
	  * @Title: getLatestDataFromMonitorTable
	  * @Description: 获取单个监控表的最新数据
	  * @param containerIp
	  * @param tableName
	  * @param colNames
	  * @param d
	  * @return Map<String,Object>   
	  * @throws 
	  * @author lisuxiao
	  * @date 2015年7月30日 下午2:07:27
	  */
	private Map<String, Object> getLatestDataFromMonitorTable(String containerIp, String tableName, String colNames, Date d) {
		Map<String, Object> result = new HashMap<String, Object>();
		String[] cols = colNames.split(",");
		Map<String, Object> params = new HashMap<String, Object>();
		Calendar cal = Calendar.getInstance();
		cal.setTime(d);
		cal.add(Calendar.HOUR, -1);
		//cal.add(Calendar.DATE, -1);
		Date start = new Date(cal.getTimeInMillis());
		params.put("dbName", tableName);
		params.put("ip", containerIp);
		params.put("start", start);
		params.put("end", d);
		params.put("count", cols.length*3);//防止监控时，该批数据未全部保存
		List<MonitorDetailModel> models = this.monitorDao.selectLastestData(params);
		if("WEBPORTAL_MONITOR_MYSQL_BASE_QUERY".equals(tableName)) {
			Map<String, Object> computer = new HashMap<String, Object>();
			for (MonitorDetailModel monitorDetailModel : models) {
				//拿到2次值和对应时间，相减后除以时间
				if("stat_QPS_command".equals(monitorDetailModel.getDetailName()) || "stat_Com_rollback".equals(monitorDetailModel.getDetailName()) ||
						"stat_Com_commit_command".equals(monitorDetailModel.getDetailName())) {
					if(computer.get(monitorDetailModel.getDetailName()+"_value")==null) {
						computer.put(monitorDetailModel.getDetailName()+"_value", monitorDetailModel.getDetailValue());
						computer.put(monitorDetailModel.getDetailName()+"_time", monitorDetailModel.getMonitorDate());
					} else {
						long time = (((Date)computer.get(monitorDetailModel.getDetailName()+"_time")).getTime()-monitorDetailModel.getMonitorDate().getTime())/1000;
						float ret = ((Float)computer.get(monitorDetailModel.getDetailName()+"_value")-monitorDetailModel.getDetailValue())/time;
						result.put(monitorDetailModel.getDetailName(), ret);
					}
				} else {
					result.put(monitorDetailModel.getDetailName(),monitorDetailModel.getDetailValue());
				}
				if(result.size()==cols.length) {//采用最新2次数据，满足采集条数后，退出
					break;
				}
			}
		} else {
			for (MonitorDetailModel monitorDetailModel : models) {
				result.put(monitorDetailModel.getDetailName(),monitorDetailModel.getDetailValue());
				if(result.size()==cols.length) {//采用最新2次数据，满足采集条数后，退出
					break;
				}
			}
		}
		
		return result;
	}


	@Override
	public void insertMysqlMonitorSpaceData(String dbName, ContainerModel container,
			Map<String, Object> map, Date d) {
		int count = 0;
		MysqlDbSpaceMonitor dbSpace = null;
		//由于保存tableSpace表时需要有dbSpace的id，所以第一个for循环先保存dbSpace表，第二个for循环保存tableSpace表
		for(Iterator it =  map.keySet().iterator();it.hasNext();){
			String key = (String) it.next();
			if(map.get(key)!=null && map.get(key) instanceof Map) {
				continue;
			} else {
				dbSpace = this.mysqlDbSpaceMonitorService.collectMysqlDbSpaceMonitorData(dbName, container, (String)map.get(key), d);
				count++;
				break;
			}
		}
		//确认保存了dbSpace表
		if(count==1) {
			for(Iterator it =  map.keySet().iterator();it.hasNext();){
				String key = (String) it.next();
				if(map.get(key)!=null && map.get(key) instanceof Map) {
					Map<String,Object> sizeAndComment = (Map<String, Object>) map.get(key);
					this.mysqlTableSpaceMonitorService.collectMysqlTableSpaceMonitorData(dbSpace.getId(), key, container, sizeAndComment, d);
				}
			}
		}
		
	}
	
	

}
