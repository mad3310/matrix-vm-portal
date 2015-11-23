package com.letv.portal.service.monitor.mysql;

import java.util.Date;

import com.letv.portal.model.ContainerModel;
import com.letv.portal.model.monitor.mysql.MysqlDbSpaceMonitor;
import com.letv.portal.service.IBaseService;

public interface IMysqlDbSpaceMonitorService extends IBaseService<MysqlDbSpaceMonitor> {

	MysqlDbSpaceMonitor collectMysqlDbSpaceMonitorData(String dbName, ContainerModel container, String size, Date d);
}
