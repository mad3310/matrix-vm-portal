package com.letv.portal.service.slb;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.letv.common.paging.impl.Page;
import com.letv.portal.junitBase.AbstractTest;
import com.letv.portal.model.slb.SlbServer;
 
public class SlbServerServiceTest extends AbstractTest{

	@Autowired
	private ISlbServerService slbServerService;
	
	private final static Logger logger = LoggerFactory.getLogger(
			SlbServerServiceTest.class);

    @Test
    public void testSelectPageByParams() {
    	Page page = new Page(1,20);
    	Map<String,Object> params = new HashMap<String,Object>();
    	params.put("slbName", "test");
		page = this.slbServerService.selectPageByParams(page, params);
		
		logger.info("totalPages are:" + page.getTotalPages());
		
		List<SlbServer> data = (List<SlbServer>) page.getData();
		
		for (SlbServer slbServer : data) {
			logger.info(slbServer.getSlbName());
		}
    }
    
    @Test
    public void testInsert() {
    	SlbServer slb = new SlbServer();
    	slb.setSlbName("test1");
    	slb.setHclusterId(1L);
    	slb.setSlbClusterId(1L);
    	this.slbServerService.insert(slb);
    	logger.info("slb id:" + slb.getId());
    	
    }
    
    @Test
    public void testSelectById() {
    	SlbServer slb = this.slbServerService.selectById(8L);
    	Assert.assertNotNull(slb);
    	logger.info("slb name:" + slb.getSlbName());
    }
    
}
