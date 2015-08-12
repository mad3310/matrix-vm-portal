package com.letv.portal.service.clouddb;

import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;

import com.letv.common.result.ResultObject;
import com.letv.portal.controller.clouddb.CronJobsController;
import com.letv.portal.junitBase.AbstractTest;
 
public class CronJobsControllerTest extends AbstractTest{

	@Autowired
	private CronJobsController cron;
	
	private final static Logger logger = LoggerFactory.getLogger(
			CronJobsControllerTest.class);

    @Test
    @Ignore
    public void testCollectMysqlMonitorBaseData() {
    	MockHttpServletRequest request =  new MockHttpServletRequest();
    	this.cron.collectMysqlMonitorBaseData(request, new ResultObject());
    }
    
    @Test
    @Ignore
    public void testCollectMysqlMonitorData() {
    	MockHttpServletRequest request =  new MockHttpServletRequest();
    	this.cron.collectMysqlMonitorData(request, new ResultObject());
    }
    
    @Test
    @Ignore
    public void testCollectMysqlMonitorBaseSizeData() {
    	MockHttpServletRequest request =  new MockHttpServletRequest();
    	this.cron.collectMysqlMonitorBaseSpaceData(request, new ResultObject());
    }
    
    @Test
    @Ignore
    public void testDeleteMonitorPartitionThirtyDaysAgo() {
    	MockHttpServletRequest request =  new MockHttpServletRequest();
    	this.cron.deleteMonitorPartitionThirtyDaysAgo(request, new ResultObject());
    }
    
    @Test
    @Ignore
    public void testAddMonitorPartition() {
    	MockHttpServletRequest request =  new MockHttpServletRequest();
    	this.cron.addMonitorPartition(request, new ResultObject());
    }
    @Test
    //@Ignore
    public void testDeleteMonitorPartition() {
    	MockHttpServletRequest request =  new MockHttpServletRequest();
    	this.cron.deleteMonitorPartitionThirtyDaysAgo(request, new ResultObject());
    }
    @Test
    @Ignore
    public void testMonitorErrorReport() {
    	MockHttpServletRequest request =  new MockHttpServletRequest();
    	this.cron.monitorErrorReport(request, new ResultObject());
    }
    @Test
    @Ignore
    public void testCollectMclusterMonitorData() {
    	MockHttpServletRequest request =  new MockHttpServletRequest();
    	this.cron.collectMclusterMonitorData(request, new ResultObject());
    }
    @Test
    @Ignore
    public void testDeleteMonitorErrorData() {
    	MockHttpServletRequest request =  new MockHttpServletRequest();
    	this.cron.deleteMonitorErrorData(request, new ResultObject());
    }
    
}
