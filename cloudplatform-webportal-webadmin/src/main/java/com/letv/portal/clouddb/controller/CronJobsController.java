package com.letv.portal.clouddb.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.letv.common.result.ResultObject;
import com.letv.portal.proxy.IBackupProxy;
import com.letv.portal.proxy.IContainerProxy;
import com.letv.portal.proxy.IMclusterProxy;
import com.letv.portal.proxy.IMonitorProxy;
import com.letv.portal.service.IMonitorService;

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
	 * Description: 检查集群数量一致性<br>
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
	/**Methods Name: deleteMonitorMonthAgo <br>
	 * Description: 删除一个月之前监控数据<br>
	 * @author name: liuhao1
	 * @param request
	 * @param obj
	 * @return
	 */
	@RequestMapping(value="/mcluster/monitor",method=RequestMethod.DELETE)   
	public @ResponseBody ResultObject deleteMonitorMonthAgo(HttpServletRequest request,ResultObject obj) {
		logger.info("deleteMonitorMonthAgo");
    	this.monitorService.deleteOutData();
    	return obj;
	}
	
	
	/**Methods Name: dbBackup <br>
	 * Description: db数据库备份<br>
	 * @author name: liuhao1
	 * @param stage  1:0点备份  2:2点备份 3:4点备份 4:6点备份
	 * @param request
	 * @param obj
	 * @return
	 */
	@RequestMapping(value="/db/backup",method=RequestMethod.POST)   
	public @ResponseBody ResultObject dbBackup(int stage, HttpServletRequest request,ResultObject obj) {
		logger.info("db backup");
		if(stage<1 || stage>4) {
			obj.setResult(0);
			obj.getMsgs().add("参数不合法");
			return obj;
		}
		this.backupProxy.backupTask(stage);
		return obj;
	}
	/**Methods Name: dbBackupCheck <br>
	 * Description: db数据库备份检查<br>
	 * @author name: liuhao1
	 * @param request
	 * @param obj
	 * @return
	 */
	@RequestMapping(value="/db/backup/check",method=RequestMethod.GET)   
	public @ResponseBody ResultObject dbBackupCheck(HttpServletRequest request,ResultObject obj) {
		logger.info("db backup check");
		this.backupProxy.checkBackupStatusTask();
		return obj;
	}
}
