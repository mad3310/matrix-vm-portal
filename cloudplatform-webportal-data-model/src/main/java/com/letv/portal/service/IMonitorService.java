package com.letv.portal.service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.letv.portal.model.MonitorDetailModel;
import com.letv.portal.model.MonitorViewModel;

public interface IMonitorService extends IBaseService<MonitorDetailModel>{

	public String getMonitorViewData(Long MclusterId);
	
	public List<String> selectDistinct(Map map);
	
	public List<MonitorDetailModel> selectDateTime(Map map);
	
}
