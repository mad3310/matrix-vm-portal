package com.letv.portal.controller.clouddb;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.letv.portal.service.openstack.cronjobs.VmSyncService;
import com.letv.portal.service.openstack.cronjobs.VolumeSyncService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.letv.common.result.ResultObject;
import com.letv.portal.constant.Constant;
import com.letv.portal.model.MonitorIndexModel;
import com.letv.portal.model.adminoplog.AoLogType;
import com.letv.portal.proxy.IBackupProxy;
import com.letv.portal.proxy.IContainerProxy;
import com.letv.portal.proxy.ICronJobsProxy;
import com.letv.portal.proxy.IGceProxy;
import com.letv.portal.proxy.IMclusterProxy;
import com.letv.portal.proxy.IMonitorProxy;
import com.letv.portal.service.IMonitorIndexService;
import com.letv.portal.service.IMonitorService;
import com.letv.portal.service.adminoplog.AoLog;
import com.letv.portal.service.adminoplog.ClassAoLog;

@ClassAoLog(module="后台任务",ignore=true)
@Controller
@RequestMapping("/cronJobs")
public class CronJobsController {
	
	@Autowired
	private IMonitorProxy monitorProxy;
	@Autowired
	private IMclusterProxy mclusterProxy;
	@Autowired
	private IContainerProxy containerProxy;
	@Autowired
	private IBackupProxy backupProxy;
	@Autowired
	private IMonitorService monitorService;
	@Autowired
	private IMonitorIndexService monitorIndexService;
	@Autowired
	private IGceProxy gceProxy;
	
	@Autowired
	private ICronJobsProxy cronJobsProxy;

	@Autowired
	private VmSyncService vmSyncService;

	@Autowired
	private VolumeSyncService volumeSyncService;
	
	private final static Logger logger = LoggerFactory.getLogger(CronJobsController.class);
		
	/**Methods Name: collectMclusterMonitorData <br>
	 * Description: 收集图表监控数据<br>
	 * @author name: liuhao1
	 * @param request
	 * @param obj
	 * @return
	 */
	@RequestMapping(value="/mcluster/monitor",method=RequestMethod.GET)   
	public @ResponseBody ResultObject collectMclusterMonitorData(HttpServletRequest request,ResultObject obj) {
		this.monitorProxy.collectMclusterServiceData();
		return obj;
	}
	/**Methods Name: collectMclusterMonitorData <br>
	 * Description: 收集图表监控数据2<br>
	 * @author name: liuhao1
	 * @param request
	 * @param obj
	 * @return
	 */
	@RequestMapping(value="/cluster/monitor",method=RequestMethod.GET)   
	public @ResponseBody ResultObject collectClusterMonitorData(HttpServletRequest request,ResultObject obj) {
		this.monitorProxy.collectClusterServiceData();
		return obj;
	}
	@RequestMapping(value="/oss/monitor",method=RequestMethod.GET)   
	public @ResponseBody ResultObject collectOSSMonitorData(HttpServletRequest request,ResultObject obj) {
		this.monitorProxy.collectOSSServiceData();
		return obj;
	}
	/**Methods Name: checkMclusterStatus <br>
	 * Description: 检查mcluster健康状况<br>
	 * @author name: liuhao1
	 * @param request
	 * @param obj
	 * @return
	 */
	@RequestMapping(value="/mcluster/status/check",method=RequestMethod.GET)
	public @ResponseBody ResultObject checkMclusterStatus(HttpServletRequest request,ResultObject obj) {
		logger.info("check mcluster status");
    	this.mclusterProxy.checkStatus();
    	return obj;
	}
	/**Methods Name: checkContainerStatus <br>
	 * Description: 检查container健康状况<br>
	 * @author name: liuhao1
	 * @param request
	 * @param obj
	 * @return
	 */
	@RequestMapping(value="/container/status/check",method=RequestMethod.GET)   
	public @ResponseBody ResultObject checkContainerStatus(HttpServletRequest request,ResultObject obj) {
		logger.info("check container status");
    	this.containerProxy.checkStatus();
    	return obj;
	}
	/**Methods Name: checkMclusterCount <br>
	 * Description: 检查RDS集群数量一致性<br>
	 * @author name: liuhao1
	 * @param request
	 * @param obj
	 * @return
	 */
	@RequestMapping(value="/mcluster/count/check",method=RequestMethod.GET)   
	public @ResponseBody ResultObject checkMclusterCount(HttpServletRequest request,ResultObject obj) {
		logger.info("check Mcluster Count");
    	this.mclusterProxy.checkCount();
    	return obj;
	}
	
	/**Methods Name: checkGceClusterCount <br>
	 * Description: 检查服务集群一致性<br>
	 * @author name: howie
	 * @param request
	 * @param obj
	 * @return
	 */
	@RequestMapping(value="/cluster/count/check",method=RequestMethod.GET)   
	public @ResponseBody ResultObject checkGceClusterCount(HttpServletRequest request,ResultObject obj) {
		logger.info("check gceCluster Count");
		this.cronJobsProxy.checkCount();
		return obj;
	}
	/**Methods Name: deleteMonitorMonthAgo <br>
	 * Description: 删除一个月之前监控数据<br>
	 * @author name: liuhao1
	 * @param request
	 * @param obj
	 * @return
	 */
	@AoLog(desc="删除一个月之前监控数据",type=AoLogType.DELETE,ignore=true)
	@RequestMapping(value="/mcluster/monitor",method=RequestMethod.DELETE)   
	public @ResponseBody ResultObject deleteMonitorMonthAgo(HttpServletRequest request,ResultObject obj) {
		logger.info("deleteMonitorMonthAgo");
		Map<String,Object> indexParams = new  HashMap<String,Object>();
		indexParams.put("status", 1);
		List<MonitorIndexModel> indexs = this.monitorIndexService.selectByMap(indexParams);
		
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, -1);    //得到前一个月
		long date = cal.getTimeInMillis();
		Date monthAgo = new Date(date);
		for (MonitorIndexModel monitorIndexModel : indexs) {
			this.monitorProxy.deleteOutData(monitorIndexModel.getDetailTable(), "MONITOR_DATE", monthAgo);
		}
    	return obj;
	}
	
	/**Methods Name: dbBackup <br>
	 * Description: db数据库备份<br>
	 * @author name: liuhao1
	 * @param count  
	 * @param request
	 * @param obj
	 * @return
	 */
	@AoLog(desc="db数据库备份",type=AoLogType.INSERT,ignore=true)
	@RequestMapping(value="/db/backup",method=RequestMethod.POST)   
	public @ResponseBody ResultObject dbBackup(int count, HttpServletRequest request,ResultObject obj) {
		logger.info("db backup");
		if(count ==0 || count<0)
			count = 5;
		this.backupProxy.backupTask(count);
		return obj;
	}

	/**Methods Name: dbBackup <br>
	 * Description: db数据库备份报告<br>
	 * @author name: liuhao1
	 * @param count  
	 * @param request
	 * @param obj
	 * @return
	 */
	@RequestMapping(value="/db/backup/report",method=RequestMethod.GET)   
	public @ResponseBody ResultObject dbBackupReport(HttpServletRequest request,ResultObject obj) {
		this.backupProxy.backupTaskReport();
		return obj;
	}
	
	/**Methods Name: deleteBackupHalfMonthAgo <br>
	 * Description: <br>
	 * @author name: liuhao1
	 * @param request
	 * @param obj
	 * @return
	 */
	@AoLog(desc="删除半个月以前的备份",type=AoLogType.DELETE,ignore=true)
	@RequestMapping(value="/db/backup",method=RequestMethod.DELETE)   
	public @ResponseBody ResultObject deleteBackupHalfMonthAgo(HttpServletRequest request,ResultObject obj) {
		logger.info("deleteBackupHalfMonthAgo");
    	this.backupProxy.deleteOutData();
    	return obj;
	}
	
	/**Methods Name: deleteMonitorPartitionMonthAgo <br>
	 * Description: 删除30天之前分区<br>
	 * @author name: 
	 * @param request
	 * @param obj
	 * @return
	 */
	@AoLog(desc="删除30天之前分区",type=AoLogType.DELETE,ignore=true)
	@RequestMapping(value="/monitor/deletePartition",method=RequestMethod.DELETE)   
	public @ResponseBody ResultObject deleteMonitorPartitionThirtyDaysAgo(HttpServletRequest request,ResultObject obj) {
		logger.info("deleteMonitorPartitionThirtyDaysAgo");
		this.monitorProxy.deleteMonitorPartitionThirtyDaysAgo();
    	return obj;
	}
	
	/**Methods Name: addMonitorPartition <br>
	 * Description: 添加监控表分区<br>
	 * @author name:
	 * @param request
	 * @param obj
	 * @return
	 */
	@AoLog(desc="添加监控表分区",type=AoLogType.INSERT,ignore=true)
	@RequestMapping(value="/monitor/addPartition",method=RequestMethod.GET)   
	public @ResponseBody ResultObject addMonitorPartition(HttpServletRequest request,ResultObject obj) {
		logger.info("addMonitorPartition");
		this.monitorProxy.addMonitorPartition();
		return obj;
	}
	
	
	/**
	  * @Title: collectMysqlMonitorData
	  * @Description: 收集mysql监控数据保存到mysql页面展示表（监控监控、资源监控、键缓存监控、InnoDB监控）
	  * @param request
	  * @param obj
	  * @return ResultObject   
	  * @throws 
	  * @author lisuxiao
	  * @date 2015年8月3日 上午9:46:51
	  */
	@RequestMapping(value="/monitor/collectMysqlMonitorData",method=RequestMethod.GET)   
	public @ResponseBody ResultObject collectMysqlMonitorData(HttpServletRequest request,ResultObject obj) {
		this.monitorProxy.collectMysqlMonitorData();
		return obj;
	}
	
	/**
	  * @Title: modifyMysqlMonitorConstantStatus
	  * @Description: 修改mysql监控不变数据为true
	  * @param request
	  * @param obj
	  * @return ResultObject   
	  * @throws 
	  * @author lisuxiao
	  * @date 2015年8月21日 下午4:11:13
	  */
	@RequestMapping(value="/monitor/modifyMysqlMonitorConstantStatus",method=RequestMethod.GET)   
	public @ResponseBody ResultObject modifyMysqlMonitorConstantStatus(HttpServletRequest request,ResultObject obj) {
		Constant.QUERY_MYSQL_CONSTANT_DATA = true;
		return obj;
	}
	
	/**
	  * @Title: collectMysqlMonitorBaseData
	  * @Description: 调用Python接口维护mysql监控历史数据
	  * @param request
	  * @param obj
	  * @return ResultObject   
	  * @throws 
	  * @author lisuxiao
	  * @date 2015年8月3日 上午9:47:57
	  */
	@RequestMapping(value="/monitor/collectMysqlMonitorBaseData",method=RequestMethod.GET)   
	public @ResponseBody ResultObject collectMysqlMonitorBaseData(HttpServletRequest request,ResultObject obj) {
		this.monitorProxy.collectMysqlMonitorBaseData();
		return obj;
	}
	
	/**
	  * @Title: collectMysqlMonitorBaseSpaceData
	  * @Description: 保存数据库大小和表大小历史及维护页面展示表（表空间监控）
	  * @param request
	  * @param obj
	  * @return ResultObject   
	  * @throws 
	  * @author lisuxiao
	  * @date 2015年8月3日 上午9:49:46
	  */
	@RequestMapping(value="/monitor/collectMysqlMonitorBaseSpaceData",method=RequestMethod.GET)   
	public @ResponseBody ResultObject collectMysqlMonitorBaseSpaceData(HttpServletRequest request,ResultObject obj) {
		this.monitorProxy.collectMysqlMonitorBaseSpaceData();
		return obj;
	}
	
	/**
	  * @Title: monitorErrorReport
	  * @Description: 发送监控错误报告
	  * @param request
	  * @param obj
	  * @return ResultObject   
	  * @throws 
	  * @author lisuxiao
	  * @date 2015年8月10日 下午2:13:52
	  */
	@RequestMapping(value="/monitor/error/report",method=RequestMethod.GET)   
	public @ResponseBody ResultObject monitorErrorReport(HttpServletRequest request,ResultObject obj) {
		this.monitorProxy.monitorErrorReport();
		return obj;
	}
	/**
	  * @Title: deleteMonitorErrorData
	  * @Description: 删除监控错误历史数据
	  * @param request
	  * @param obj
	  * @return ResultObject   
	  * @throws 
	  * @author lisuxiao
	  * @date 2015年8月10日 下午3:57:28
	  */
	@RequestMapping(value="/monitor/error/clear",method=RequestMethod.GET)   
	public @ResponseBody ResultObject deleteMonitorErrorData(HttpServletRequest request,ResultObject obj) {
		this.monitorProxy.deleteMonitorErrorData();
		return obj;
	}

	@RequestMapping(value="/openstack/sync/vm",method=RequestMethod.GET)
	public @ResponseBody ResultObject syncVm(@RequestParam int recordsPerPage,ResultObject obj){
		this.vmSyncService.sync(recordsPerPage);
		return obj;
	}

	@RequestMapping(value = "/openstack/sync/volume", method = RequestMethod.GET)
	public
	@ResponseBody
	ResultObject syncVolume(@RequestParam int recordsPerPage, ResultObject obj) {
		this.volumeSyncService.sync(recordsPerPage);
		return obj;
	}
}
