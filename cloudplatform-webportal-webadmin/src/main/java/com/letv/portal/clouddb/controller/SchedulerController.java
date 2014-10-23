package com.letv.portal.clouddb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value="scheduler")
public class SchedulerController {

	@Autowired
	private SchedulerFactoryBean schedulerFactoryBean;
	
	
	@RequestMapping(value="/test",method=RequestMethod.GET)
	public void test() {
	
	}
}
