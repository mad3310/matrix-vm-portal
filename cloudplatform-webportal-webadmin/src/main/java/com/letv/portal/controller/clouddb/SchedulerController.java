package com.letv.portal.controller.clouddb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.letv.portal.service.adminoplog.ClassAoLog;

@ClassAoLog(module="调度")
@Controller
@RequestMapping(value="scheduler")
public class SchedulerController {

	@Autowired(required=false)
	private SchedulerFactoryBean schedulerFactoryBean;
	
	
	@RequestMapping(value="/test",method=RequestMethod.GET)
	public void test() {
	
	}
}