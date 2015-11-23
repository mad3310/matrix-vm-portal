package com.letv.portal.service.monitor.mysql;

import java.util.Date;
import java.util.Map;

import com.letv.portal.model.ContainerModel;
import com.letv.portal.model.monitor.mysql.MysqlGaleraMonitor;
import com.letv.portal.service.IBaseService;

public interface IMysqlGaleraMonitorService extends IBaseService<MysqlGaleraMonitor> {
	void collectMysqlGaleraMonitorData(ContainerModel container, Map<String, Object> map, Date d, Date start, boolean query);
}
