package com.letv.portal.job;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Component;

import com.letv.portal.model.ScheduleJobModel;

/**Program Name: QuartzJob <br>
 * Description:  初始化所有的定时任务<br>
 * @author name: liuhao1 <br>
 * Written Date: 2014年10月16日 <br>
 * Modified By: <br>
 * Modified Date: <br>
 */
@Component("initializingJob")
public class InitializingJob {

	private final static Logger logger = LoggerFactory.getLogger(InitializingJob.class);
	
	@Value("${start.timing.jobs}")
	private String START_TIMING_JOBS;
	
	@Autowired(required=false)
	private SchedulerFactoryBean schedulerFactoryBean;
	
	@PostConstruct
	public void initScheduler() throws  SchedulerException,ParseException,ClassNotFoundException {
		
		
			logger.info("initScheduler start....");
			Scheduler scheduler = schedulerFactoryBean.getScheduler();
			
			//这里获取任务信息数据
			List<ScheduleJobModel> jobList = getJobs();
			
			//测试数据end
			
			for (ScheduleJobModel job : jobList) {
				
				TriggerKey triggerKey = TriggerKey.triggerKey(job.getJobName(), job.getJobGroup());
				
				CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
				
				//不存在，创建一个
				if (null == trigger) {
					JobDetail jobDetail = JobBuilder.newJob(JobFactory.class).withIdentity(job.getJobName(), job.getJobGroup()).build();
					jobDetail.getJobDataMap().put("scheduleJob", job);
					
					//表达式调度构建器
					CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCronExpression());
					
					//按新的cronExpression表达式构建一个新的trigger
					trigger = TriggerBuilder.newTrigger().withIdentity(job.getJobName(), job.getJobGroup()).withSchedule(scheduleBuilder).build();
					
					scheduler.scheduleJob(jobDetail, trigger);
				} else {
					// Trigger已存在，那么更新相应的定时设置
					//表达式调度构建器
					CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCronExpression());
					
					//按新的cronExpression表达式重新构建trigger
					trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();
					
					//按新的trigger重新设置job执行
					scheduler.rescheduleJob(triggerKey, trigger);
				}
			}
			logger.info("initScheduler success");
		}
	
	
	public List<ScheduleJobModel> getJobs(){
		List<ScheduleJobModel> jobs = new ArrayList<ScheduleJobModel>();
		//测试数据 start
		/*ScheduleJobModel testJob = new ScheduleJobModel();
		
		testJob.setJobName("test");
		testJob.setJobMethod("test");
		testJob.setJobGroup("webportal");
		testJob.setJobStatus("1");
		testJob.setCronExpression("0/30 * * * * ?");
		testJob.setDescn("测试job初始化");
		jobs.add(testJob);*/
		
		/*
		 * 业务检查job
		 */
		if("Y".equals(START_TIMING_JOBS)) {
			ScheduleJobModel mclusterJob = new ScheduleJobModel();
			mclusterJob.setJobName("checkMclusterStatus");
			mclusterJob.setJobMethod("checkMclusterStatus");
			mclusterJob.setJobGroup("webportal");
			mclusterJob.setJobStatus("1");
			mclusterJob.setCronExpression("0 0/5 * * * ?");
			mclusterJob.setDescn("检查container集群状态");
			jobs.add(mclusterJob);
			
			ScheduleJobModel containerJob = new ScheduleJobModel();
			containerJob.setJobName("checkContainerStatus");
			containerJob.setJobMethod("checkContainerStatus");
			containerJob.setJobGroup("webportal");
			containerJob.setJobStatus("1");
			containerJob.setCronExpression("0 0/5 * * * ?");
			containerJob.setDescn("检查container单节点状态");
			jobs.add(containerJob);
			
			ScheduleJobModel checkMclusterCount = new ScheduleJobModel();
			checkMclusterCount.setJobName("checkMclusterCount");
			checkMclusterCount.setJobMethod("checkMclusterCount");
			checkMclusterCount.setJobGroup("webportal");
			checkMclusterCount.setJobStatus("1");
			checkMclusterCount.setCronExpression("0 0/10 * * * ?");
			checkMclusterCount.setDescn("检查container单节点状态");
			jobs.add(checkMclusterCount);
			
			ScheduleJobModel collectMclusterServiceData = new ScheduleJobModel();
			collectMclusterServiceData.setJobName("collectMclusterServiceData");
			collectMclusterServiceData.setJobMethod("collectMclusterServiceData");
			collectMclusterServiceData.setJobGroup("webportal");
			collectMclusterServiceData.setJobStatus("1");
			collectMclusterServiceData.setCronExpression("0 0/1 * * * ?");
			collectMclusterServiceData.setDescn("获取监控数据");
			jobs.add(collectMclusterServiceData);
			
			//数据库全量备份，每天凌晨4点
			ScheduleJobModel backupDbData = new ScheduleJobModel();
			backupDbData.setJobName("wholeBackup4Db");
			backupDbData.setJobMethod("wholeBackup4Db");
			backupDbData.setJobGroup("webportal");
			backupDbData.setJobStatus("1");
			backupDbData.setCronExpression("0 0 4 * * ?"); //暂时设置每两小时执行一次备份。测试完成后，修改为凌晨4点
			backupDbData.setDescn("db数据库全量备份");
			jobs.add(backupDbData);
			//数据库全量备份检查 ，每隔一小时
			ScheduleJobModel checkBackupStatus = new ScheduleJobModel();
			checkBackupStatus.setJobName("checkBackupStatus");
			checkBackupStatus.setJobMethod("checkBackupStatus");
			checkBackupStatus.setJobGroup("webportal");
			checkBackupStatus.setJobStatus("1");
			checkBackupStatus.setCronExpression("0 0/30 * * * ?");
			checkBackupStatus.setDescn("db数据库全量备份检查");
			jobs.add(checkBackupStatus);
		}
		
		return jobs;
	}
      
}
