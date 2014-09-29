package com.letv.portal.clouddb.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.beanutils.BeanUtils;
import org.junit.Test;

import com.letv.common.paging.impl.Page;
import com.letv.common.result.ResultObject;
import com.letv.portal.junitBase.AbstractTest;
import com.letv.portal.model.HostModel;
import com.letv.portal.service.IHostService;
import com.letv.portal.view.HostView;

public class HostControllerTest extends AbstractTest{
	@Resource
	private IHostService hostService;
	
	/**
	 * Methods Name: list <br>
	 * Description: 查询host的列表数据
	 * @author name: wujun
	 */
	@Test
	public void list(){
		try {
			Page page = new Page();
			page.setCurrentPage(0);
			page.setRecordsPerPage(10);
		
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("hostName", null);
			
			ResultObject obj = new ResultObject();
			obj.setData(this.hostService.findPagebyParams(params, page));
            System.out.print(obj);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Methods Name: buildHost <br>
	 * Description: hostControllerInsert
	 * @author name: wujun
	 */
	@Test
	public void saveHost(){ 
		try {    
			HostModel hostModel = new HostModel();
			hostModel.setHostName("wujun4");
			hostModel.setHostIp("192.168.1.11");
			hostService.insert(hostModel);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * Methods Name: hostList <br>
	 * Description:  列表展示
	 * @author name: wujun
	 */
	@Test
	public void hostList(){
		try {
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("hostName", "wujun1");
			ResultObject obj = new ResultObject();
			obj.setData(this.hostService.selectByMap(map));
			System.out.println(obj.getData());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	/**
	 * Methods Name: deletehost <br>
	 * Description: 删除host
	 * @author name: wujun
	 */
	@Test
	public void deleteHost(){
		try {
            String id = "c5f72b50-3fc1-11e4-83a5-b82a72c38a8f";
			HostModel hostModel = new HostModel();
//			hostModel.setId(id);
           this.hostService.delete(hostModel);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * Methods Name: deletehost <br>
	 * Description: 删除host
	 * @author name: wujun
	 */
	@Test
	public void updateHost(){
		try {
			 HostModel hostModel = new HostModel();
			 hostModel.setHostName("wujunSpeacil");
			 hostModel.setId("3");
			 this.hostService.update(hostModel);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
}
