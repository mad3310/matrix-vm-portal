package com.letv.portal.dao.monitor.mysql;

import com.letv.common.dao.IBaseDao;
import com.letv.portal.model.monitor.mysql.MysqlHealthMonitor;

public interface IMysqlHealthMonitorDao extends IBaseDao<MysqlHealthMonitor> {
	int selectByHostIp(String hostIp);
}
