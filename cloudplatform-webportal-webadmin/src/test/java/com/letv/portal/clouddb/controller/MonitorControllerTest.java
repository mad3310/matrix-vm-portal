package com.letv.portal.clouddb.controller;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Test;

import com.letv.portal.junitBase.AbstractTest;
import com.letv.portal.model.ContainerModel;
import com.letv.portal.model.MonitorDetailModel;
import com.letv.portal.model.monitor.ContainerMonitorModel;
import com.letv.portal.proxy.IContainerProxy;
import com.letv.portal.proxy.IMonitorProxy;
import com.letv.portal.service.IContainerService;
import com.letv.portal.service.IMonitorService;

public class MonitorControllerTest extends AbstractTest{
	
	@Resource
	private IContainerService containerService;
	@Resource
	private IContainerProxy containerProxy;
	@Resource
	private IMonitorProxy monitorProxy;
	@Resource
	private IMonitorService monitorService;
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
	@Test
	public void insertMonitorDetail(){
		MonitorDetailModel monitorDetailModel = new MonitorDetailModel();
		monitorDetailModel.setDetailName("monitor");
		monitorDetailModel.setDetailValue(111);
		monitorDetailModel.setDbName("WEBPORTAL_MONITOR_DETAIL_XX");
//		this.monitorService.insert(monitorDetailModel);
	}

	@Test
	public void monitorView(){
//		String ip ="10.200.85.48";
		
    	   try {			
			this.monitorProxy.collectMclusterServiceData();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    		
       System.out.println("xx");
	}

	@Test
	public void monitorView1(){
//		String ip ="10.200.85.48";
		Map map = new HashMap<String, Object>();
		map.put("dbName", "WEBPORTAL_MONITOR_DB_INNODB_BUFFER_MEMALLOC");
		this.monitorService.selectDistinct(map);
		System.out.println("xx");
	}
	@Test
	public void monitorView2(){
		MonitorTimeModel monitorTimeModel = new MonitorTimeModel();
		monitorTimeModel.setStrategy(1);
		this.monitorService.getMonitorViewData(2144L,1L,monitorTimeModel);
		System.out.println();
	}
	
	@Test
	public void monitorView3(){
    	Map map = new HashMap<String, Object>();
    	map.put("dbName", "WEBPORTAL_MONITOR_DB_INNODB_BUFFER_MEMALLOC");
    	map.put("end", "2014-11-12");
    	map.put("start", "2014-11-11"); 
    	System.out.println(this.monitorService.selectDateTime(map));
	}
	@Test
	public void monitorView4(){
		for(int i=0;i<10;i++){
			try {
				System.out.println(i);
				if(i==5){
					System.out.println("Exception");
					throw new Exception();
				}				
			} catch (Exception e) {
				System.out.println(i);
				if(i<10){
					continue;
				}
			}
			System.out.println(i);
	}
	}
		
}
