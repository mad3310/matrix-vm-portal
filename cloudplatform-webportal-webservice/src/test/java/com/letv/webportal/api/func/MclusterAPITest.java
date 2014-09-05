package com.letv.webportal.api.func;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.letv.portal.model.MclusterModel;
import com.letv.webportal.api.base.AbstractTestCase;


public class MclusterAPITest extends AbstractTestCase {
	public static Logger logger = LoggerFactory.getLogger(MclusterAPITest.class);

	@Test
	public void testList(){
		String url = API_URL + "/mcluster/list";
		logger.debug(url);
		
		Map<String, String> params = new HashMap<String, String>();  
        params.put("mclusterName", "yaokuo"); 
        
		String result = restTemplate.postForObject(url, params,String.class);
		logger.debug("result===>" + result);
	}
	
	@Test
	public void testSave(){
		String url = API_URL + "/mcluster/save";
		logger.debug(url);
		MclusterModel model = new MclusterModel(null,"testJunit","0","liuhao1@letv.com");
		String result = restTemplate.postForObject(url, model, String.class);
		logger.debug("result===>" + result);
	}
	@Test
	public void testGetInfoById(){
		String url = API_URL + "/mcluster/getById/{clusterId}";
		logger.debug(url);
		String result = restTemplate.getForObject(url, String.class,"0945e7a3-778a-40fc-a721-a7986906b7f4");
		logger.debug("result===>" + result);
	}
	@Test
	public void testGetInfoByIdInMap(){
		String url = API_URL + "/mcluster/getById/{clusterId}";
		Map<String, String> params = new HashMap<String, String>();  
        params.put("clusterId", "0945e7a3-778a-40fc-a721-a7986906b7f4"); 
        
		logger.debug(url);
		String result = restTemplate.getForObject(url, String.class,params);
		logger.debug("result===>" + result);
	}
	
	
	
}
