package com.letv.portal.service.monitor.mysql;

import java.util.Date;
import java.util.Map;

import com.letv.portal.model.ContainerModel;
import com.letv.portal.model.monitor.mysql.MysqlHealthMonitor;
import com.letv.portal.service.IBaseService;

public interface IMysqlHealthMonitorService extends IBaseService<MysqlHealthMonitor> {
	
	void collectMysqlHealthMonitorData(ContainerModel container, Map<String, Object> map, Date d, Date start, boolean query);
}
