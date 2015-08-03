package com.letv.portal.service.monitor.mysql.impl;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.letv.common.dao.IBaseDao;
import com.letv.portal.dao.monitor.mysql.IMysqlDbSpaceMonitorDao;
import com.letv.portal.model.ContainerModel;
import com.letv.portal.model.monitor.mysql.MysqlDbSpaceMonitor;
import com.letv.portal.service.impl.BaseServiceImpl;
import com.letv.portal.service.monitor.mysql.IMysqlDbSpaceMonitorService;

@Service("mysqlDbSpaceMonitorService")
public class MysqlDbSpaceMonitorServiceImpl extends BaseServiceImpl<MysqlDbSpaceMonitor> implements IMysqlDbSpaceMonitorService{
	
	private final static Logger logger = LoggerFactory.getLogger(MysqlDbSpaceMonitorServiceImpl.class);
	
	@Resource
	private IMysqlDbSpaceMonitorDao mysqlDbSpaceMonitorDao;
	
	public MysqlDbSpaceMonitorServiceImpl() {
		super(MysqlDbSpaceMonitor.class);
	}

	@Override
	public IBaseDao<MysqlDbSpaceMonitor> getDao() {
		return this.mysqlDbSpaceMonitorDao;
	}

	@Override
	public MysqlDbSpaceMonitor collectMysqlDbSpaceMonitorData(String dbName, ContainerModel container,
		String size, Date d) {
		MysqlDbSpaceMonitor dbSpace = new MysqlDbSpaceMonitor();
		dbSpace.setHostIp(container.getIpAddr());
		dbSpace.setHostTag(container.getHcluster().getHclusterNameAlias()+"-"+container.getHostIp()+"-"+container.getContainerName());
		dbSpace.setName(dbName);
		dbSpace.setSize(Float.parseFloat(size));
		
		List<MysqlDbSpaceMonitor> dbSpaces = this.mysqlDbSpaceMonitorDao.selectByHostTag(dbSpace.getHostTag());
		if(dbSpaces!=null && dbSpaces.size()==1) {
			dbSpace.setId(dbSpaces.get(0).getId());
			dbSpace.setUpdateTime(new Timestamp(d.getTime()));
			this.mysqlDbSpaceMonitorDao.update(dbSpace);
		} else if(dbSpaces==null || dbSpaces.size()==0) {
			dbSpace.setCreateTime(new Timestamp(d.getTime()));
			dbSpace.setUpdateTime(new Timestamp(d.getTime()));
			this.mysqlDbSpaceMonitorDao.insert(dbSpace);
		} else if(dbSpaces!=null && dbSpaces.size()>1) {
			logger.error("collectMysqlDbSpaceMonitorData.selectByHostTag method get many result, this is a bug. HOST_Tag :"+dbSpace.getHostTag());
		}
		return dbSpace;
	}

}
