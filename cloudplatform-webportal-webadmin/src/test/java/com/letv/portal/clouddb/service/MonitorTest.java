package com.letv.portal.clouddb.service;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.letv.portal.junitBase.AbstractTest;
import com.letv.portal.proxy.IMonitorProxy;
 
public class MonitorTest extends AbstractTest{

	@Autowired
	private IMonitorProxy monitorProxy;
	
	private final static Logger logger = LoggerFactory.getLogger(
			MonitorTest.class);

    @Test
    public void testDeleteOutData() {
    	this.monitorProxy.deleteOutData();
    }
    
}
