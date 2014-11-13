package com.letv.portal.dao;

import java.util.List;

import com.letv.common.dao.IBaseDao;
import com.letv.portal.model.MonitorIndexModel;

public interface IMonitorIndexDao extends IBaseDao<MonitorIndexModel>{
	public List<MonitorIndexModel> selectMonitorCount();
}
