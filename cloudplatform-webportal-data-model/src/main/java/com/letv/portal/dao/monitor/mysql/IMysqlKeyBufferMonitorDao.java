package com.letv.portal.dao.monitor.mysql;

import com.letv.common.dao.IBaseDao;
import com.letv.portal.model.monitor.mysql.MysqlKeyBufferMonitor;

public interface IMysqlKeyBufferMonitorDao extends IBaseDao<MysqlKeyBufferMonitor> {
	int selectByHostIp(String hostIp);
}
