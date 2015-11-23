package com.letv.portal.dao.monitor.mysql;

import com.letv.common.dao.IBaseDao;
import com.letv.portal.model.monitor.mysql.MysqlGaleraMonitor;

public interface IMysqlGaleraMonitorDao extends IBaseDao<MysqlGaleraMonitor> {
	int selectByHostIp(String hostIp);
}
