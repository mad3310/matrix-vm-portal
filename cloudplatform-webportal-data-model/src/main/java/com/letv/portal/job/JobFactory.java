package com.letv.portal.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.letv.portal.model.ScheduleJobModel;

@Component
public class JobFactory implements Job {

	@Autowired
	private IJobExecute jobExecute;
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		
		System.out.println("QuartzJobFactory start...");
        ScheduleJobModel scheduleJob = (ScheduleJobModel)context.getMergedJobDataMap().get("scheduleJob");
        String method = scheduleJob.getJobMethod();
        System.out.println("jobName= [" + scheduleJob.getJobName() + "]");
        
        if("test".equals(method)) {
        	//注入拿不到，待解决。
//        	this.jobExecute.test();
        }
	}

}
