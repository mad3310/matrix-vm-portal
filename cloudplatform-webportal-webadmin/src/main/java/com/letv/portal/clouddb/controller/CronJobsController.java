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
import com.letv.portal.proxy.IMonitorProxy;

@Controller
@RequestMapping("/cronJobs")
public class CronJobsController {
	
	@Autowired
	private IMonitorProxy monitorProxy;
	
	private final static Logger logger = LoggerFactory.getLogger(CronJobsController.class);
		
	@RequestMapping(value="/mcluster/monitor",method=RequestMethod.GET)   
	public @ResponseBody ResultObject collectMclusterMonitorData(HttpServletRequest request,ResultObject obj) {
		this.monitorProxy.collectMclusterServiceData();
		return obj;
	}
}
