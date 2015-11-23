package com.letv.portal.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.letv.common.util.SpringContextUtil;
import com.letv.portal.model.ScheduleJobModel;
import com.letv.portal.proxy.IBackupProxy;
import com.letv.portal.proxy.IContainerProxy;
import com.letv.portal.proxy.IMclusterProxy;
import com.letv.portal.proxy.IMonitorProxy;
import com.letv.portal.service.IMonitorService;

@Component
public class JobFactory implements Job {

	private final static Logger logger = LoggerFactory.getLogger(JobFactory.class);
	
	private IJobExecute jobExecute = (IJobExecute) SpringContextUtil.getBean("jobExecute");
	private IMclusterProxy mclusterProxy = (IMclusterProxy) SpringContextUtil.getBean("mclusterProxy");
	private IContainerProxy containerProxy = (IContainerProxy) SpringContextUtil.getBean("containerProxy");
	private IMonitorProxy monitorProxy = (IMonitorProxy) SpringContextUtil.getBean("monitorProxy");
	private IMonitorService monitorService = (IMonitorService) SpringContextUtil.getBean("monitorService");
	private IBackupProxy backupProxy = (IBackupProxy) SpringContextUtil.getBean("backupProxy");
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		
		System.out.println("QuartzJobFactory start...");
        ScheduleJobModel scheduleJob = (ScheduleJobModel)context.getMergedJobDataMap().get("scheduleJob");
        String method = scheduleJob.getJobMethod();
        System.out.println("jobName= [" + scheduleJob.getJobName() + "]");
        
        if("test".equals(method)) {
        	this.jobExecute.test();
        }
        if("checkMclusterStatus".equals(method)) {
        	logger.info("check mcluster status");
        	this.mclusterProxy.checkStatus();
        }
        if("checkContainerStatus".equals(method)) {
        	logger.info("check container status");
        	this.containerProxy.checkStatus();
        }
        if("checkMclusterCount".equals(method)) {
        	logger.info("check Mcluster Count");
        	this.mclusterProxy.checkCount();
        }
        if("collectMclusterServiceData".equals(method)) {
        	logger.info("collectMclusterServiceData");
        	this.monitorProxy.collectMclusterServiceData();
        }
        if("collectClusterServiceData".equals(method)) {
        	logger.info("collectClusterServiceData");
        	this.monitorProxy.collectClusterServiceData();
        }
        if("deleteMonitorMonthAgo".equals(method)) {
        	logger.info("deleteMonitorMonthAgo");
        	this.monitorProxy.deleteOutData();
        }
        
        //定时任务备份
       /* if("wholeBackup4Db".equals(method)) {
        	logger.info("wholeBackup4Db");
        	this.backupProxy.backupTask();
        }*/
        if("wholeBackup4Db1".equals(method)) {
        	logger.info("wholeBackup4Db1");
        	this.backupProxy.backupTask(1);
        }
        if("wholeBackup4Db2".equals(method)) {
        	logger.info("wholeBackup4Db2");
        	this.backupProxy.backupTask(2);
        }
        if("wholeBackup4Db3".equals(method)) {
        	logger.info("wholeBackup4Db3");
        	this.backupProxy.backupTask(3);
        }
        if("wholeBackup4Db4".equals(method)) {
        	logger.info("wholeBackup4Db4");
        	this.backupProxy.backupTask(4);
        }
        
        if("deleteBackupHalfMonthAgo".equals(method)) {
        	logger.info("deleteBackupHalfMonthAgo");
        	this.backupProxy.deleteOutData();
        }
	}

}
