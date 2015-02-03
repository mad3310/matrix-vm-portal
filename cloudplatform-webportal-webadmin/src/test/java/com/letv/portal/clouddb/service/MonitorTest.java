package com.letv.portal.clouddb.service;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.letv.portal.junitBase.AbstractTest;
import com.letv.portal.model.MclusterModel;
import com.letv.portal.service.IMonitorService;
 
public class MonitorTest extends AbstractTest{

	@Autowired
	private IMonitorService monitorService;
	
	private final static Logger logger = LoggerFactory.getLogger(
			MonitorTest.class);

    @Test
    public void testDeleteOutData() {
    	this.monitorService.deleteOutData();
    }
    
}
