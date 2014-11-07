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
import com.letv.portal.proxy.IContainerProxy;
import com.letv.portal.python.service.IBuildTaskService;
import com.letv.portal.python.service.IPythonService;
import com.letv.portal.service.IContainerService;

public class MonitorControllerTest extends AbstractTest{
	
	@Resource
	private IContainerService containerService;
	@Resource
	private IContainerProxy containerProxy;
	@Test
	public void list(){
		try {
			Map map = new HashMap<String, String>();
			map.put("ipAddr", "10.154.238.25");
			List<ContainerMonitorModel> list = new ArrayList<ContainerMonitorModel>();
			list =	containerProxy.selectMonitorMclusterDetailOrList(map);
			System.out.println("xx");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void list1(){
		try {
			String ip = "10.200.85.48";
			ContainerMonitorModel containerMonitorModel = new ContainerMonitorModel();
			containerMonitorModel =	this.containerProxy.selectMonitorDetailNodeAndDbData(ip);
			System.out.println("xx");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Test
	public void list2(){
		try {
			String ip = "10.200.85.48";
			ContainerMonitorModel containerMonitorModel = new ContainerMonitorModel();
			containerMonitorModel =	this.containerProxy.selectMonitorDetailClusterData(ip);
			System.out.println("xx");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void list3(){
		try {
			Map map = new HashMap<String, String>();
			map.put("type", "mclustervip");
			List<ContainerModel> list = new ArrayList<ContainerModel>();
			list =	this.containerProxy.selectMonitorMclusterList(map);
			System.out.println("xx");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
