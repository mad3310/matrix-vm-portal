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
	
	@Autowired
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
		ScheduleJobModel testJob = new ScheduleJobModel();
		
		testJob.setJobName("test");
		testJob.setJobMethod("test");
		testJob.setJobGroup("webportal");
		testJob.setJobStatus("1");
		testJob.setCronExpression("0/30 * * * * ?");
		testJob.setDescn("测试job初始化");
		jobs.add(testJob);
		
		/*
		 * 业务检查job
		 */
		ScheduleJobModel mclusterJob = new ScheduleJobModel();
		mclusterJob.setJobName("checkMclusterStatus");
		mclusterJob.setJobMethod("checkMclusterStatus");
		mclusterJob.setJobGroup("webportal");
		mclusterJob.setJobStatus("1");
		mclusterJob.setCronExpression("0/30 * * * * ?");
		mclusterJob.setDescn("检查container集群状态");
		jobs.add(mclusterJob);
		
		ScheduleJobModel containerJob = new ScheduleJobModel();
		containerJob.setJobName("checkContainerStatus");
		containerJob.setJobMethod("checkContainerStatus");
		containerJob.setJobGroup("webportal");
		containerJob.setJobStatus("1");
		containerJob.setCronExpression("0/30 * * * * ?");
		containerJob.setDescn("检查container单节点状态");
		jobs.add(containerJob);
		
		return jobs;
	}
      
}
