package com.letv.portal.clouddb.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Test;

import com.letv.common.paging.impl.Page;
import com.letv.common.result.ResultObject;
import com.letv.portal.junitBase.AbstractTest;
import com.letv.portal.model.HclusterModel;
import com.letv.portal.model.HostModel;
import com.letv.portal.service.IHclusterService;


public class HclusterControllerTest extends AbstractTest{
	  
	@Resource
	private IHclusterService  hclusterService;
	

	@Test
	public void list(){
		try {
			Page page = new Page();
			page.setCurrentPage(0);
			page.setRecordsPerPage(10);
		
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("hclusterName", null);
			
			ResultObject obj = new ResultObject();
			obj.setData(this.hclusterService.findPagebyParams(params, page));
          System.out.print(obj);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void saveHost(){ 
		try {    
			HclusterModel hclusterModel = new HclusterModel();
			hclusterModel.setHclusterName("wujun5");
			hclusterService.insert(hclusterModel);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	@Test
	public void deleteHost(){
		try {
            Long id = 2L;
        	HclusterModel hclusterModel = new HclusterModel();
        	hclusterModel.setId(id);;
         this.hclusterService.delete(hclusterModel);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void updateHost(){
		Map<String,String> map  = new HashMap<String, String>();
		map.put("id", "1");
		try {
			List<HclusterModel> list =  this.hclusterService.selectByMap(map);
			  System.out.println(list);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Test
	public void hclusterValidate(){
		try {
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("hclusterName", "wujun5");
			List<HclusterModel> list = this.hclusterService.selectByMap(map);
			System.out.println("xxx");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
