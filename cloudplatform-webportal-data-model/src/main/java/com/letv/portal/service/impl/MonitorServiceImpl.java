package com.letv.portal.service.impl;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.letv.common.dao.IBaseDao;
import com.letv.portal.dao.IMonitorDao;
import com.letv.portal.dao.IMonitorViewDao;
import com.letv.portal.model.ContainerModel;
import com.letv.portal.model.MonitorDetailModel;
import com.letv.portal.model.MonitorIndexModel;
import com.letv.portal.model.MonitorViewYModel;
import com.letv.portal.service.IContainerService;
import com.letv.portal.service.IMonitorIndexService;
import com.letv.portal.service.IMonitorService;

@Service("monitorService")
public class MonitorServiceImpl extends BaseServiceImpl<MonitorDetailModel> implements IMonitorService {

	@Autowired
	private IMonitorDao monitorDao;
	
	@Autowired
	private IMonitorViewDao monitorViewDao;
	
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
		List<MonitorViewYModel> ydatas = new ArrayList<MonitorViewYModel>();
	    Map<String, Object> map = new HashMap<String, Object>();
	    map.put("mclusterId", mclusterId);
	    map.put("type", "mclusternode");
	    List<ContainerModel> containers = this.containerService.selectByMap(map);	  
	    
	    MonitorIndexModel monitorIndexModel  = this.monitorIndexService.selectById(chartId);	   
	    String dataTable = monitorIndexModel.getDetailTable();
	    
	    Map<String, Object> indexParams = new HashMap<String, Object>();
	    indexParams.put("dbName",monitorIndexModel.getDetailTable());
	    
	    Date end = new Date();
	   
	    List<String> detailNames =  this.monitorDao.selectDistinct(indexParams);
	    
		Map<String, Object> params = new HashMap<String, Object>();
		
		params.put("dbName", monitorIndexModel.getDetailTable());
		params.put("start", getStartDate(end,strategy));
		params.put("end", end);
		
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
				ydata.setType("spline");
				ydata.setData(datas);
				ydatas.add(ydata);
			}
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

}
