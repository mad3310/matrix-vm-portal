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

   /**
    * Methods Name: createMultiContainerPushZabbixInfo <br>
    * Description: 创建多个container，containerName不能重复<br>
    * @author name: wujun
    */
	@Test
	public void createMultiContainerPushZabbixInfo() {
		ContainerModel containerModel1 = new ContainerModel();
		containerModel1.setContainerName("webportal_111");
		containerModel1.setIpAddr("197.168.1.34");
		ContainerModel containerModel2 = new ContainerModel();
		containerModel2.setContainerName("webportal_222");
		containerModel2.setIpAddr("197.168.1.34");
		ContainerModel containerModel3 = new ContainerModel();
		containerModel3.setContainerName("webportal_333");
		containerModel3.setIpAddr("197.168.1.34");
		ContainerModel containerModel4 = new ContainerModel();
		containerModel4.setContainerName("webportal_444");
		containerModel4.setIpAddr("197.168.1.34");
		
		ContainerModel[] containerModels =new ContainerModel[]{containerModel1,containerModel2,containerModel3,containerModel4};
	
		zabbixPushService.createMultiContainerPushZabbixInfo(containerModels);
	}

	@Test
	public void createSingleContainer(){
		try {
		ZabbixPushModel zabbixPushModel = new ZabbixPushModel();
			
			
			ZabbixParam params = new ZabbixParam();
			params.setHost("container_webportal7");
			
			InterfacesModel interfacesModel = new InterfacesModel();
			interfacesModel.setIp("192.168.1.7");
			
			List<InterfacesModel> list = new ArrayList<InterfacesModel>();
			list.add(interfacesModel);
			params.setInterfaces(list);
			
			zabbixPushModel.setParams(params);  
			Boolean flag =	zabbixPushService.pushZabbixInfo(zabbixPushModel);
			System.out.println(flag);
		} catch (Exception e) {
			logger.debug("zabbix创建成功");
		}
		
	}
   
	@Test
    public void login(){
	  try {
		  zabbixPushService.loginZabbix();
	} catch (Exception e) {
		logger.debug("zabbix");
	}
	}
}
