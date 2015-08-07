package com.letv.portal.dao.monitor.mysql;

import java.util.List;

import com.letv.common.dao.IBaseDao;
import com.letv.portal.model.monitor.mysql.MysqlDbSpaceMonitor;

public interface IMysqlDbSpaceMonitorDao extends IBaseDao<MysqlDbSpaceMonitor> {
	List<MysqlDbSpaceMonitor> selectByHostTag(String hostTag);
}
