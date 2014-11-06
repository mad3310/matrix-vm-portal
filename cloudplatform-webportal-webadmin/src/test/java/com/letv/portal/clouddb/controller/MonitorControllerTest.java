package com.letv.portal.clouddb.controller;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Test;

import com.letv.portal.junitBase.AbstractTest;
import com.letv.portal.model.ContainerModel;
import com.letv.portal.model.ContainerMonitorModel;
import com.letv.portal.python.service.IBuildTaskService;
import com.letv.portal.python.service.IPythonService;
import com.letv.portal.service.IContainerService;

public class MonitorControllerTest extends AbstractTest{
	
	@Resource
	private IContainerService containerService;
	
	@Resource
	private IBuildTaskService buildTaskService;
	@Test
	public void list(){
		try {
			Map map = new HashMap<String, String>();
			map.put("ipAddr", "10.200.85.48");
			List<ContainerModel> cModels = this.containerService.selectAllByMap(map);
			List<ContainerMonitorModel> list = new ArrayList<ContainerMonitorModel>();
			list =	buildTaskService.getMonitorData(cModels);
			System.out.println("xx");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
