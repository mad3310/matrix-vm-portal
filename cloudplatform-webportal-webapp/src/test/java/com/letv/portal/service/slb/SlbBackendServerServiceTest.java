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
import com.letv.portal.enumeration.SlbAgentType;
import com.letv.portal.junitBase.AbstractTest;
import com.letv.portal.model.slb.SlbBackendServer;
 
public class SlbBackendServerServiceTest extends AbstractTest{

	@Autowired
	private ISlbBackendServerService slbBackendServerService;
	
	private final static Logger logger = LoggerFactory.getLogger(
			SlbBackendServerServiceTest.class);

    @Test
    public void testSelectPageByParams() {
    	Page page = new Page(1,20);
    	Map<String,Object> params = new HashMap<String,Object>();
    	params.put("slbId", 1L);
		page = this.slbBackendServerService.selectPageByParams(page, params);
		
		logger.info("totalPages are:" + page.getTotalPages());
		
		List<SlbBackendServer> data = (List<SlbBackendServer>) page.getData();
		
		for (SlbBackendServer slbBackendServer : data) {
			logger.info(slbBackendServer.getServerName());
		}
    }
    
    @Test
    public void testInsert() {
    	SlbBackendServer slbBackendServer = new SlbBackendServer();
    	slbBackendServer.setServerIp("192.168.1.1");
    	slbBackendServer.setServerName("test");
    	slbBackendServer.setSlbId(1L);
    	slbBackendServer.setPort("80");
    	this.slbBackendServerService.insert(slbBackendServer);
    	logger.info("slbBackendServer id:" + slbBackendServer.getId());
    	
    }
    
}
