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
import com.letv.portal.dao.monitor.mysql.IMysqlInnoDBMonitorDao;
import com.letv.portal.model.ContainerModel;
import com.letv.portal.model.monitor.mysql.MysqlInnoDBMonitor;
import com.letv.portal.service.IMonitorService;
import com.letv.portal.service.impl.BaseServiceImpl;
import com.letv.portal.service.monitor.mysql.IMysqlInnoDBMonitorService;

@Service("mysqlInnoDBMonitorService")
public class MysqlInnoDBMonitorServiceImpl extends BaseServiceImpl<MysqlInnoDBMonitor> implements IMysqlInnoDBMonitorService{
	
	private final static Logger logger = LoggerFactory.getLogger(MysqlInnoDBMonitorServiceImpl.class);
	
	private final static String[] titles = {"db.row_opers.ps","mysql.innodb.buffer"};
	
	@Resource
	private IMysqlInnoDBMonitorDao mysqlInnoDBMonitorDao;
	@Autowired
	private IMonitorService monitorService;
	
	public MysqlInnoDBMonitorServiceImpl() {
		super(MysqlInnoDBMonitor.class);
	}

	@Override
	public IBaseDao<MysqlInnoDBMonitor> getDao() {
		return this.mysqlInnoDBMonitorDao;
	}

	@Override
	public void collectMysqlInnoDBMonitorData(ContainerModel container,
			Map<String, Object> map, Date d) {
		Map<String, Object> dbResult = this.monitorService.getLatestDataFromMonitorTables(container.getIpAddr(), titles, d);
		MysqlInnoDBMonitor innodb = new MysqlInnoDBMonitor();
		innodb.setHostIp(container.getIpAddr());
		innodb.setHostTag(container.getHcluster().getHclusterNameAlias()+"-"+container.getHostIp()+"-"+container.getContainerName());
		innodb.setInnodbBufferPoolSize(Float.parseFloat((String)map.get("stat_innodb_bufferpool_size_command")));
		if(dbResult.get("stat_innodb_bufferpool_reads_command")!=null && dbResult.get("stat_innodb_bufferpool_read_request_command")!=null) {
			if((Float)dbResult.get("stat_innodb_bufferpool_read_request_command")!=0f) {
				float readHits = (Float)dbResult.get("stat_innodb_bufferpool_reads_command")/(Float)dbResult.get("stat_innodb_bufferpool_read_request_command");
				innodb.setInnodbBufferReadHits(1-readHits);
			} else {
				innodb.setInnodbBufferReadHits(0f);
			}
		}
		innodb.setInnodbRowsRead((Float)dbResult.get("num_reads_sec"));
		innodb.setInnodbRowsInsert((Float)dbResult.get("num_inserts_sec"));
		innodb.setInnodbRowsUpdate((Float)dbResult.get("num_updates_sec"));
		innodb.setInnodbRowsDelete((Float)dbResult.get("num_deletes_sec"));
		
		int i = this.mysqlInnoDBMonitorDao.selectByHostIp(container.getIpAddr());
		if(i==1) {
			innodb.setUpdateTime(new Timestamp(d.getTime()));
			this.mysqlInnoDBMonitorDao.update(innodb);
		} else if(i==0) {
			innodb.setCreateTime(new Timestamp(d.getTime()));
			innodb.setUpdateTime(new Timestamp(d.getTime()));
			this.mysqlInnoDBMonitorDao.insert(innodb);
		} else if(i>1) {
			logger.error("collectMysqlInnoDBMonitorData.selectByHostIp method get many result, this is a bug. HOST_IP :"+container.getIpAddr());
		}
		
	}
	
}
