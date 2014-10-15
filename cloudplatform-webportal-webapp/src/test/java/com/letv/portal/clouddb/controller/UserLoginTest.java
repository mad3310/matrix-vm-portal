package com.letv.portal.clouddb.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import org.junit.Test;
import com.letv.portal.fixedPush.IFixedPushService;
import com.letv.portal.junitBase.AbstractTest;


public class UserLoginTest extends AbstractTest{
	
	
	@Resource
	private IFixedPushService FixedPushService;
	
	@Test
	public void createContainerPushFixedInfoTest()
	{   
	   // String url = "http://localhost:8080/webTest2/";	
		Map<String,String> map = new HashMap<String,String>();
		List<String> xxList = new ArrayList<String>();
		xxList.add("10.200.85.91");
		xxList.add("10.200.85.92");
		map.put("hostIp", "10.200.85.93");
		map.put("code", "100");
		map.put("rescode", "200");
		map.put("containerIp", xxList.toString());	
		map.put("resmsg", "webportalAPI");	
		
		Boolean result = FixedPushService.createContainerPushFixedInfo(map);
		System.out.println(result);
	}
	
	
}
