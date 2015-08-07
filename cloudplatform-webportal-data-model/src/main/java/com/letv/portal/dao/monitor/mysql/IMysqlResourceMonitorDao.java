package com.letv.portal.dao.monitor.mysql;

import com.letv.common.dao.IBaseDao;
import com.letv.portal.model.monitor.mysql.MysqlResourceMonitor;

public interface IMysqlResourceMonitorDao extends IBaseDao<MysqlResourceMonitor> {
	int selectByHostIp(String hostIp);
}
