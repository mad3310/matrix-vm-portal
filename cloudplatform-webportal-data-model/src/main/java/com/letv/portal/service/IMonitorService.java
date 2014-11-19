package com.letv.portal.service;

import java.util.List;
import java.util.Map;

import com.letv.portal.model.MonitorDetailModel;
import com.letv.portal.model.MonitorIndexModel;
import com.letv.portal.model.MonitorViewModel;
import com.letv.portal.model.MonitorViewYModel;

public interface IMonitorService extends IBaseService<MonitorDetailModel>{
	
	public List<MonitorViewYModel> getMonitorViewData(Long MclusterId,Long chartId,Integer strategy);
	
	public List<String> getMonitorXData(Integer strategy);
	
	public List<String> selectDistinct(Map map);
	
	public List<MonitorDetailModel> selectDateTime(Map map);
	
	public List<MonitorIndexModel> selectMonitorCount();
	
}
