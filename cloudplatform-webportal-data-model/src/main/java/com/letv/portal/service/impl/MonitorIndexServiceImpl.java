package com.letv.portal.service.impl;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.letv.common.dao.IBaseDao;
import com.letv.portal.dao.IMonitorIndexDao;
import com.letv.portal.model.MonitorIndexModel;
import com.letv.portal.service.IMonitorIndexService;

@Service("monitorIndexService")
public class MonitorIndexServiceImpl extends BaseServiceImpl<MonitorIndexModel> implements IMonitorIndexService {
	
	@Autowired
	private IMonitorIndexDao monitorIndexDao;
	
	public MonitorIndexServiceImpl() {
		super(MonitorIndexModel.class);
	}

	
	@Override
	public IBaseDao<MonitorIndexModel> getDao() {
		return this.monitorIndexDao;
	}

	public List<MonitorIndexModel> selectMonitorCount(){
		return this.monitorIndexDao.selectMonitorCount();
	}



}
