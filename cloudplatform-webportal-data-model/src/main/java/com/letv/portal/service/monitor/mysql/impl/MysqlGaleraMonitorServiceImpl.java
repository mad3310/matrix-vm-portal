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
import com.letv.portal.dao.monitor.mysql.IMysqlGaleraMonitorDao;
import com.letv.portal.model.ContainerModel;
import com.letv.portal.model.monitor.mysql.MysqlGaleraMonitor;
import com.letv.portal.service.IMonitorService;
import com.letv.portal.service.impl.BaseServiceImpl;
import com.letv.portal.service.monitor.mysql.IMysqlGaleraMonitorService;

@Service("mysqlGaleraMonitorService")
public class MysqlGaleraMonitorServiceImpl extends BaseServiceImpl<MysqlGaleraMonitor> implements IMysqlGaleraMonitorService{
	
	private final static Logger logger = LoggerFactory.getLogger(MysqlGaleraMonitorServiceImpl.class);
	
	private final static String[] titles = {"mysql.wsrep.local","mysql.wsrep.rep_rec","mysql.wsrep_flow_control"};
	
	@Resource
	private IMysqlGaleraMonitorDao mysqlGaleraMonitorDao;
	@Autowired
	private IMonitorService monitorService;
	
	public MysqlGaleraMonitorServiceImpl() {
		super(MysqlGaleraMonitor.class);
	}

	@Override
	public IBaseDao<MysqlGaleraMonitor> getDao() {
		return this.mysqlGaleraMonitorDao;
	}

	@Override
	public void collectMysqlGaleraMonitorData(ContainerModel container,
			Map<String, Object> map, Date d) {
		Map<String, Object> dbResult = this.monitorService.getLatestDataFromMonitorTables(container.getIpAddr(), titles, d);
		MysqlGaleraMonitor galera = new MysqlGaleraMonitor();
		galera.setHostIp(container.getIpAddr());
		galera.setHostTag(container.getHcluster().getHclusterNameAlias()+"-"+container.getHostIp()+"-"+container.getContainerName());
		galera.setWsrepLocalFail(dbResult.get("stat_wsrep_local_fail_command")==null?-1f:(Float)dbResult.get("stat_wsrep_local_fail_command"));
		galera.setWsrepLocalAbort(dbResult.get("stat_wsrep_local_bf_aborts_command")==null?-1f:(Float)dbResult.get("stat_wsrep_local_bf_aborts_command"));
		galera.setWsrepLocalReplays(dbResult.get("stat_wsrep_local_replays_command")==null?-1f:(Float)dbResult.get("stat_wsrep_local_replays_command"));
		galera.setWsrepReplicated(dbResult.get("stat_wsrep_replicated_command")==null?-1f:(Float)dbResult.get("stat_wsrep_replicated_command"));
		galera.setWsrepRepBytes(dbResult.get("stat_wsrep_replicated_bytes_command")==null?-1f:(Float)dbResult.get("stat_wsrep_replicated_bytes_command"));
		galera.setWsrepReceived(dbResult.get("stat_wsrep_received_command")==null?-1f:(Float)dbResult.get("stat_wsrep_received_command"));
		galera.setWsrepRecBytes(dbResult.get("stat_wsrep_received_bytes_command")==null?-1f:(Float)dbResult.get("stat_wsrep_received_bytes_command"));
		galera.setWsrepFlowControlPaused(dbResult.get("stat_wsrep_flow_control_paused_command")==null?-1f:(Float)dbResult.get("stat_wsrep_flow_control_paused_command"));
		galera.setWsrepFlowControlSent(dbResult.get("stat_wsrep_flow_control_sent_command")==null?-1f:(Float)dbResult.get("stat_wsrep_flow_control_sent_command"));
		galera.setWsrepFlowControlRecv(dbResult.get("stat_wsrep_flow_control_recv_command")==null?-1f:(Float)dbResult.get("stat_wsrep_flow_control_recv_command"));
		
		int i = this.mysqlGaleraMonitorDao.selectByHostIp(container.getIpAddr());
		if(i==1) {
			galera.setUpdateTime(new Timestamp(d.getTime()));
			this.mysqlGaleraMonitorDao.update(galera);
		} else if(i==0) {
			galera.setCreateTime(new Timestamp(d.getTime()));
			galera.setUpdateTime(new Timestamp(d.getTime()));
			this.mysqlGaleraMonitorDao.insert(galera);
		} else if(i>1) {
			logger.error("collectMysqlGaleraMonitorData.selectByHostIp method get many result, this is a bug. HOST_IP :"+container.getIpAddr());
		}
		
	}
	
}
