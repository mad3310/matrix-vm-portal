package com.letv.portal.service.clouddb;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.letv.portal.junitBase.AbstractTest;
import com.letv.portal.proxy.IMonitorProxy;
import com.letv.portal.service.IMonitorService;
 
public class MonitorTest extends AbstractTest{

	@Autowired
	private IMonitorProxy monitorProxy;
	@Autowired
	private IMonitorService monitorServiceByJdbc;
	
	private final static Logger logger = LoggerFactory.getLogger(
			MonitorTest.class);

    @Test
    public void testDeleteOutData() {
    	this.monitorProxy.deleteOutData();
    }
    @Test
    public void testDeleteOutDataByIndex() {
    	Map<String, Object> params = new HashMap<String,Object>();
    	for (int i = 0; i < 10000000; i+=100) {
    		params.put("dbName", "WEBPORTAL_BACKUP_RESULT");
    		params.put("min", i);
    		params.put("max", i+100);
    		this.monitorServiceByJdbc.deleteOutDataByIndex(params);
		}
    }
    
}
