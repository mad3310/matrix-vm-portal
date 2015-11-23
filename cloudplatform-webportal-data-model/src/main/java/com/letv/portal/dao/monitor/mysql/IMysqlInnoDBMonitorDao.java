package com.letv.portal.dao.monitor.mysql;

import com.letv.common.dao.IBaseDao;
import com.letv.portal.model.monitor.mysql.MysqlInnoDBMonitor;

public interface IMysqlInnoDBMonitorDao extends IBaseDao<MysqlInnoDBMonitor> {
	int selectByHostIp(String hostIp);
}
