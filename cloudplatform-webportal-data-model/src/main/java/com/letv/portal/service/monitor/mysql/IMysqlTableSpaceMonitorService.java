package com.letv.portal.service.monitor.mysql;

import java.util.Date;
import java.util.Map;

import com.letv.portal.model.ContainerModel;
import com.letv.portal.model.monitor.mysql.MysqlTableSpaceMonitor;
import com.letv.portal.service.IBaseService;

public interface IMysqlTableSpaceMonitorService extends IBaseService<MysqlTableSpaceMonitor> {
	void collectMysqlTableSpaceMonitorData(long dbSpaceId, String tableName, ContainerModel container, Map<String, Object> sizeAndComment, Date d);
}
