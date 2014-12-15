package com.letv.portal.clouddb.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.annotations.Delete;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.letv.portal.junitBase.AbstractTest;
import com.letv.portal.model.ContainerModel;
import com.letv.portal.model.zabbix.InterfacesModel;
import com.letv.portal.model.zabbix.ZabbixParam;
import com.letv.portal.model.zabbix.ZabbixPushDeleteModel;
import com.letv.portal.model.zabbix.ZabbixPushModel;
import com.letv.portal.service.IContainerService;
import com.letv.portal.zabbixPush.IZabbixPushService;

public class ZabbixTest extends AbstractTest {
	private final static Logger logger = LoggerFactory
			.getLogger(UserLoginTest.class);
	@Resource
	public IZabbixPushService zabbixPushService;
	
	@Resource
	public IContainerService containerService;

   /**
    * Methods Name: createMultiContainerPushZabbixInfo <br>
    * Description: 创建多个container，containerName不能重复<br>
    * @author name: wujun
    */
	@Test
	public void createMultiContainerPushZabbixInfo() {
		List<ContainerModel> list  = new ArrayList<ContainerModel>();
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
		list.add(containerModel1);
		list.add(containerModel2);
		list.add(containerModel3);
		list.add(containerModel4);
	
		zabbixPushService.createMultiContainerPushZabbixInfo(list);
	}

	@Test
	public void createSingleContainer(){
		Boolean flag  =false;
		try {
			List<ContainerModel> list  = new ArrayList<ContainerModel>();
			ContainerModel containerModel1 = new ContainerModel();
			containerModel1.setContainerName("www3");
			containerModel1.setIpAddr("10.58.166.34");
			containerModel1.setId(3L);
			list.add(containerModel1);
		
			flag = zabbixPushService.createMultiContainerPushZabbixInfo(list);
			System.out.println(flag);
		} catch (Exception e) {
			logger.debug("zabbix创建成功");
		}
		
	}
	@Test
	public void createMutilContainer(){
		Boolean flag  =false;
		try {
			List<ContainerModel> list  = new ArrayList<ContainerModel>();
			ContainerModel containerModel1 = new ContainerModel();
			containerModel1.setContainerName("www3");
			containerModel1.setIpAddr("10.58.166.34");
			containerModel1.setId(3L);
			list.add(containerModel1);
		
			flag = zabbixPushService.createMultiContainerPushZabbixInfo(list);
			System.out.println(flag);
		} catch (Exception e) {
			logger.debug("zabbix创建成功");
		}
		
	}
   @Test
   public void deleContainer(){
		  ContainerModel containerModel1 = new ContainerModel();
		  containerModel1.setZabbixHosts("10226");
		  
		  ContainerModel containerModel2 = new ContainerModel();
		  containerModel2.setZabbixHosts("10228");
		  
		  ContainerModel containerModel3= new ContainerModel();
		  containerModel3.setZabbixHosts("10229");
		  
		  ContainerModel containerModel4 = new ContainerModel();
		  containerModel4.setZabbixHosts("10227");
		  List<ContainerModel> list  = new ArrayList<ContainerModel>();
		  list.add(containerModel1);
		  list.add(containerModel2);
		  list.add(containerModel3);
		  list.add(containerModel4);
		 
	   Boolean flag  =false;

		try {
			
	     flag =	zabbixPushService.deleteMutilContainerPushZabbixInfo(list);
	     System.out.println(flag);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
   } 
   
	@Test
    public void login(){
	  try {
		  List<ContainerModel> list = this.containerService.selectContainerByMclusterId(227L);
		  System.out.println("xx");
		//  zabbixPushService.loginZabbix();
	} catch (Exception e) {
		logger.debug("zabbix"+e.getMessage());
	}
	}
}
