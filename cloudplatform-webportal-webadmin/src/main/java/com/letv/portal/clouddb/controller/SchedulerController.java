package com.letv.portal.clouddb.controller;

import org.quartz.impl.StdScheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value="scheduler")
public class SchedulerController {

	@Autowired(required=false)
	private StdScheduler scheduler;
	
	
	@RequestMapping(value="/test",method=RequestMethod.GET)
	public void test() {
		System.out.println(this.scheduler);
		try {
			String[] names = this.scheduler.getTriggerNames(this.scheduler.DEFAULT_GROUP);
			for (String string : names) {
				System.out.println(string);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
