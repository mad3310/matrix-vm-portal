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
import com.letv.portal.dao.monitor.mysql.IMysqlKeyBufferMonitorDao;
import com.letv.portal.model.ContainerModel;
import com.letv.portal.model.monitor.mysql.MysqlKeyBufferMonitor;
import com.letv.portal.service.IMonitorService;
import com.letv.portal.service.impl.BaseServiceImpl;
import com.letv.portal.service.monitor.mysql.IMysqlKeyBufferMonitorService;

@Service("mysqlKeyBufferMonitorService")
public class MysqlKeyBufferMonitorServiceImpl extends BaseServiceImpl<MysqlKeyBufferMonitor> implements IMysqlKeyBufferMonitorService{
	
	private final static Logger logger = LoggerFactory.getLogger(MysqlKeyBufferMonitorServiceImpl.class);
	
	private final static String[] titles = {"mysql.key.blocks","mysql.key.rate"};
	
	@Resource
	private IMysqlKeyBufferMonitorDao mysqlKeyBufferMonitorDao;
	@Autowired
	private IMonitorService monitorService;
	
	public MysqlKeyBufferMonitorServiceImpl() {
		super(MysqlKeyBufferMonitor.class);
	}

	@Override
	public IBaseDao<MysqlKeyBufferMonitor> getDao() {
		return this.mysqlKeyBufferMonitorDao;
	}

	@Override
	public void collectMysqlKeyBufferMonitorData(ContainerModel container,
			Map<String, Object> map, Date d, Date start, boolean query) {
		Map<String, Object> dbResult = this.monitorService.getLatestDataFromMonitorTables(container.getIpAddr(), titles, d, start);
		MysqlKeyBufferMonitor keyBuffer = new MysqlKeyBufferMonitor();
		keyBuffer.setHostIp(container.getIpAddr());
		keyBuffer.setHostTag(container.getHcluster().getHclusterNameAlias()+"-"+container.getHostIp()+"-"+container.getContainerName());
		if(query == true) {
			keyBuffer.setKeyBufferSize(map.get("stat_key_buffer_size_command")==null?-1f:Float.parseFloat((String)map.get("stat_key_buffer_size_command")));
			keyBuffer.setSortBufferSize(map.get("stat_sort_buffer_size_command")==null?-1f:Float.parseFloat((String)map.get("stat_sort_buffer_size_command")));
			keyBuffer.setJoinBufferSize(map.get("stat_join_buffer_size_command")==null?-1f:Float.parseFloat((String)map.get("stat_join_buffer_size_command")));
		}
		keyBuffer.setKeyBlocksUnused(dbResult.get("stat_key_blocks_unused_command")==null?-1:((Float)dbResult.get("stat_key_blocks_unused_command")).intValue());
		keyBuffer.setKeyBlocksUsed(dbResult.get("stat_key_blocks_used_command")==null?-1:((Float)dbResult.get("stat_key_blocks_used_command")).intValue());
		keyBuffer.setKeyBlocksNotFlushed(dbResult.get("stat_key_blocks_not_flushed_command")==null?-1:((Float)dbResult.get("stat_key_blocks_not_flushed_command")).intValue());
		if(dbResult.get("stat_key_blocks_used_command")!=null || dbResult.get("stat_key_blocks_unused_command")!=null) {
			keyBuffer.setKeyBlocksUsedRate((Float)dbResult.get("stat_key_blocks_used_command")/((Float)dbResult.get("stat_key_blocks_used_command")+(Float)dbResult.get("stat_key_blocks_unused_command")));
		} else {
			keyBuffer.setKeyBlocksUsedRate(-1f);
		}
		if(dbResult.get("stat_key_buffer_reads_command")!=null && dbResult.get("stat_key_buffer_reads_request_command")!=null) {
			if((Float)dbResult.get("stat_key_buffer_reads_request_command")!=0f) {
				float f = (Float)dbResult.get("stat_key_buffer_reads_command")/(Float)dbResult.get("stat_key_buffer_reads_request_command");
				keyBuffer.setKeyBufferReadRate(1-f);
			} else {
				keyBuffer.setKeyBufferReadRate(0f);
			}
		} else {
			keyBuffer.setKeyBufferReadRate(-1f);
		}
		if(dbResult.get("stat_key_buffer_writes_command")!=null && dbResult.get("stat_key_buffer_writes_request_command")!=null) {
			if((Float)dbResult.get("stat_key_buffer_writes_request_command")!=0f) {
				float f = (Float)dbResult.get("stat_key_buffer_writes_command")/(Float)dbResult.get("stat_key_buffer_writes_request_command");
				keyBuffer.setKeyBufferWriteRate(1-f);
			} else {
				keyBuffer.setKeyBufferWriteRate(0f);//除数为0时，设置值为0
			}
		} else {
			keyBuffer.setKeyBufferWriteRate(-1f);//-1定义为数据异常
		}
		
		int i = this.mysqlKeyBufferMonitorDao.selectByHostIp(container.getIpAddr());
		if(i==1) {
			keyBuffer.setUpdateTime(new Timestamp(d.getTime()));
			this.mysqlKeyBufferMonitorDao.update(keyBuffer);
		} else if(i==0) {
			keyBuffer.setCreateTime(new Timestamp(d.getTime()));
			keyBuffer.setUpdateTime(new Timestamp(d.getTime()));
			this.mysqlKeyBufferMonitorDao.insert(keyBuffer);
		} else if(i>1) {
			logger.error("collectMysqlKeyBufferMonitorData.selectByHostIp method get many result, this is a bug. HOST_IP :"+container.getIpAddr());
		}
	}
	
}
