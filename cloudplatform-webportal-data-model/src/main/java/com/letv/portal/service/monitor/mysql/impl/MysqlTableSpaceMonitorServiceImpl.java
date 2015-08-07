package com.letv.portal.service.monitor.mysql.impl;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.letv.common.dao.IBaseDao;
import com.letv.portal.dao.monitor.mysql.IMysqlTableSpaceMonitorDao;
import com.letv.portal.model.ContainerModel;
import com.letv.portal.model.monitor.mysql.MysqlTableSpaceMonitor;
import com.letv.portal.service.impl.BaseServiceImpl;
import com.letv.portal.service.monitor.mysql.IMysqlTableSpaceMonitorService;

@Service("mysqlTableSpaceMonitorService")
public class MysqlTableSpaceMonitorServiceImpl extends BaseServiceImpl<MysqlTableSpaceMonitor> implements IMysqlTableSpaceMonitorService{
	
	private final static Logger logger = LoggerFactory.getLogger(MysqlTableSpaceMonitorServiceImpl.class);
	
	@Resource
	private IMysqlTableSpaceMonitorDao mysqlTableSpaceMonitorDao;
	
	public MysqlTableSpaceMonitorServiceImpl() {
		super(MysqlTableSpaceMonitor.class);
	}

	@Override
	public IBaseDao<MysqlTableSpaceMonitor> getDao() {
		return this.mysqlTableSpaceMonitorDao;
	}

	@Override
	public void collectMysqlTableSpaceMonitorData(long dbSpaceId, String tableName,
			ContainerModel container, Map<String, Object> sizeAndComment, Date d) {
		MysqlTableSpaceMonitor tableSpace = new MysqlTableSpaceMonitor();
		tableSpace.setName(tableName);
		tableSpace.setSize(sizeAndComment.get("total_kb")==null?-1f:Float.parseFloat((String)sizeAndComment.get("total_kb")));
		tableSpace.setDescn((String)sizeAndComment.get("table_comment"));
		tableSpace.setDbSpaceId(dbSpaceId);
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("dbSpaceId", dbSpaceId);
		params.put("name", tableName);
		List<MysqlTableSpaceMonitor> tableSpaces = this.mysqlTableSpaceMonitorDao.selectByMap(params);
		if(tableSpaces!=null && tableSpaces.size()==1) {
			tableSpace.setUpdateTime(new Timestamp(d.getTime()));
			this.mysqlTableSpaceMonitorDao.update(tableSpace);
		} else if(tableSpaces==null || tableSpaces.size()==0) {
			tableSpace.setCreateTime(new Timestamp(d.getTime()));
			tableSpace.setUpdateTime(new Timestamp(d.getTime()));
			this.mysqlTableSpaceMonitorDao.insert(tableSpace);
		} else if(tableSpaces!=null && tableSpaces.size()>1) {
			logger.error("collectMysqlTableSpaceMonitorData.selectByMap method get many result, this is a bug. params :"
					+params.toString());
		}
		
	}
	
}
