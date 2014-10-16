package com.letv.portal.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;

import com.letv.portal.model.ScheduleJobModel;

@Component
public class JobFactory implements Job {

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		
		System.out.println("QuartzJobFactory start...");
        ScheduleJobModel scheduleJob = (ScheduleJobModel)context.getMergedJobDataMap().get("scheduleJob");
        System.out.println("jobName= [" + scheduleJob.getJobName() + "]");
	}

}
