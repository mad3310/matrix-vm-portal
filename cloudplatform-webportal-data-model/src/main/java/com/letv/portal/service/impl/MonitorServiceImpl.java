package com.letv.portal.service.impl;


import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.letv.common.dao.IBaseDao;
import com.letv.portal.dao.IMonitorDao;
import com.letv.portal.model.ContainerModel;
import com.letv.portal.model.MonitorDetailModel;
import com.letv.portal.model.MonitorIndexModel;
import com.letv.portal.model.monitor.MonitorViewYModel;
import com.letv.portal.service.IContainerService;
import com.letv.portal.service.IMonitorIndexService;
import com.letv.portal.service.IMonitorService;

@Service("monitorService")
public class MonitorServiceImpl extends BaseServiceImpl<MonitorDetailModel> implements IMonitorService {

	private final static Logger logger = LoggerFactory.getLogger(MonitorServiceImpl.class);
	
	@Autowired
	private IMonitorDao monitorDao;
	
	@Autowired
	private IMonitorIndexService monitorIndexService;
	
	@Autowired
	private IContainerService containerService;
	
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
	public List<MonitorViewYModel> getDbConnMonitor(String ip,Long chartId,Integer strategy) {
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
			
			for (int i = 0; i < beforData.size()-1; i++) {
				List<Object> point = new ArrayList<Object>();
				point.add(beforData.get(i+1).getMonitorDate());
				float diff = beforData.get(i+1).getDetailValue()-beforData.get(i).getDetailValue();
				float time = (beforData.get(i+1).getMonitorDate().getTime()-beforData.get(i).getMonitorDate().getTime())/1000;
				Long value = (long) (diff>0?diff/time:0);
				point.add(value);
				datas.add(point);
			}
			
			ydata.setName(s);
			ydata.setData(datas);
			ydatas.add(ydata);
		}
		return ydatas;
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
			List<Map<String,Object>> ids = this.monitorDao.selectExtremeIdByMonitorDate(map);
			if(ids.isEmpty() || ids.get(0) == null || ids.get(0).isEmpty()) {
				continue;
			}
			Map<String, Object> extremeIds = ids.get(0);
			Long max = ((BigInteger)extremeIds.get("maxId")).longValue();
			Long min = ((BigInteger)extremeIds.get("minId")).longValue();
			if(max == null || max == 0 || max == min)
				return;
			for (Long i = min; i <= max; i+=5000) {
				map.put("id", i);
				this.monitorDao.deleteOutDataByIndex(map);
			}
		}
	}
}
