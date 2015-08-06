package com.letv.portal.service.monitor.mysql.impl;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.letv.common.dao.IBaseDao;
import com.letv.portal.dao.monitor.mysql.IMysqlResourceMonitorDao;
import com.letv.portal.model.ContainerModel;
import com.letv.portal.model.monitor.mysql.MysqlResourceMonitor;
import com.letv.portal.service.IMonitorService;
import com.letv.portal.service.impl.BaseServiceImpl;
import com.letv.portal.service.monitor.mysql.IMysqlResourceMonitorService;

@Service("mysqlResourceMonitorService")
public class MysqlResourceMonitorServiceImpl extends BaseServiceImpl<MysqlResourceMonitor> implements IMysqlResourceMonitorService{
	
	private final static Logger logger = LoggerFactory.getLogger(MysqlResourceMonitorServiceImpl.class);
	
	private final static String[] titles = {"mysql.resource"};
	
	@Resource
	private IMysqlResourceMonitorDao mysqlResourceMonitorDao;
	@Autowired
	private IMonitorService monitorService;
	
	public MysqlResourceMonitorServiceImpl() {
		super(MysqlResourceMonitor.class);
	}

	@Override
	public IBaseDao<MysqlResourceMonitor> getDao() {
		return this.mysqlResourceMonitorDao;
	}

	@Override
	public void collectMysqlResourceMonitorData(ContainerModel container,
			Map<String, Object> map, Date d) {
		Map<String, Object> dbResult = this.monitorService.getLatestDataFromMonitorTables(container.getIpAddr(), titles, d);
		MysqlResourceMonitor resource = new MysqlResourceMonitor();
		resource.setHostIp(container.getIpAddr());
		resource.setHostTag(container.getHcluster().getHclusterNameAlias()+"-"+container.getHostIp()+"-"+container.getContainerName());
		resource.setMaxConnect(map.get("stat_max_conn_command")==null?-1:Integer.parseInt((String)map.get("stat_max_conn_command")));
		resource.setMaxConnectError(map.get("stat_max_err_conn_command")==null?-1:Integer.parseInt((String)map.get("stat_max_err_conn_command")));
		resource.setMaxOpenFile(map.get("stat_max_open_file_command")==null?-1:Integer.parseInt((String)map.get("stat_max_open_file_command")));
		resource.setHadOpenFile(dbResult.get("stat_opened_file_command")==null?-1:((Float)dbResult.get("stat_opened_file_command")).intValue());
		resource.setCacheTableCount(map.get("stat_table_cach_command")==null?-1:Integer.parseInt((String)map.get("stat_table_cach_command")));
		resource.setCacheTableNohitCount(dbResult.get("stat_table_cach_noha_command")==null?-1:((Float)dbResult.get("stat_table_cach_noha_command")).intValue());
		resource.setHadOpenTable(dbResult.get("stat_opened_table_cach_command")==null?-1:((Float)dbResult.get("stat_opened_table_cach_command")).intValue());
		
		int i = this.mysqlResourceMonitorDao.selectByHostIp(container.getIpAddr());
		if(i==1) {
			resource.setUpdateTime(new Timestamp(d.getTime()));
			this.mysqlResourceMonitorDao.update(resource);
		} else if(i==0) {
			resource.setCreateTime(new Timestamp(d.getTime()));
			resource.setUpdateTime(new Timestamp(d.getTime()));
			this.mysqlResourceMonitorDao.insert(resource);
		} else if(i>1) {
			logger.error("mysqlResourceMonitorDao.selectByHostIp method get many result, this is a bug. HOST_IP :"+container.getIpAddr());
		}
	}
	
}
