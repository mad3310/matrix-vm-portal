package com.letv.portal.clouddb.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.letv.portal.junitBase.AbstractTest;
import com.letv.portal.model.ContainerModel;
import com.letv.portal.model.InterfacesModel;
import com.letv.portal.model.ZabbixParam;
import com.letv.portal.model.ZabbixPushModel;
import com.letv.portal.zabbixPush.IZabbixPushService;

public class ZabbixTest extends AbstractTest {
	private final static Logger logger = LoggerFactory
			.getLogger(UserLoginTest.class);
	@Resource
	public IZabbixPushService zabbixPushService;

	@Test
    public void login(){
	  try {
		  zabbixPushService.loginZabbix();
	} catch (Exception e) {
		logger.debug("zabbix");
	}
	}

	@Test
	public void createContainerPushZabbixInfo() {
		ContainerModel containerModel1 = new ContainerModel();
		ContainerModel containerModel2 = new ContainerModel();
		ContainerModel containerModel3 = new ContainerModel();
		ContainerModel containerModel4 = new ContainerModel();
		
		ContainerModel[] containerModels =new ContainerModel[]{containerModel1,containerModel2,containerModel3,containerModel4};
		int count  = 0;
		
	try {
		for(ContainerModel c:containerModels){
			ZabbixPushModel zabbixPushModel = new ZabbixPushModel();
						
			ZabbixParam params = new ZabbixParam();
			params.setHost(c.getContainerName());
			
			InterfacesModel interfacesModel = new InterfacesModel();
			interfacesModel.setIp(c.getIpAddr());
			
			List<InterfacesModel> list = new ArrayList<InterfacesModel>();
			list.add(interfacesModel);
			params.setInterfaces(list);
			
			zabbixPushModel.setParams(params);  
			Boolean flag =	zabbixPushService.createContainerPushZabbixInfo(zabbixPushModel);
			System.out.println(flag);
			if(flag==true)
			count++;
			logger.debug("增加了"+count+"个container");
		}		

		
		
		} catch (Exception e) {
			logger.debug("zabbix");
		}
	}
	@Test
	public void createContainer() {
		ZabbixParam params = new ZabbixParam();
		System.out.println("xx");
	}
	
	public void createSingleContainer(){
		try {
		ZabbixPushModel zabbixPushModel = new ZabbixPushModel();
//			zabbixPushModel.setJsonrpc("2.0");
//			zabbixPushModel.setMethod("host.create");
//			zabbixPushModel.setId(1);
			
			
			ZabbixParam params = new ZabbixParam();
			params.setHost("container_webportal");
			
			InterfacesModel interfacesModel = new InterfacesModel();
			interfacesModel.setIp("1921.168.1.2");
			
			List<InterfacesModel> list = new ArrayList<InterfacesModel>();
			list.add(interfacesModel);
			params.setInterfaces(list);
			
			zabbixPushModel.setParams(params);  
			Boolean flag =	zabbixPushService.createContainerPushZabbixInfo(zabbixPushModel);
			System.out.println(flag);
		} catch (Exception e) {
			logger.debug("zabbix创建成功");
		}
		
	}
	
}
