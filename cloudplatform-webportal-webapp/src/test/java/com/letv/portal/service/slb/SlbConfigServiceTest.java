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

import ch.qos.logback.core.util.AggregationType;

import com.letv.common.paging.impl.Page;
import com.letv.portal.enumeration.SlbAgentType;
import com.letv.portal.junitBase.AbstractTest;
import com.letv.portal.model.slb.SlbConfig;
 
public class SlbConfigServiceTest extends AbstractTest{

	@Autowired
	private ISlbConfigService slbConfigService;
	
	private final static Logger logger = LoggerFactory.getLogger(
			SlbConfigServiceTest.class);

    @Test
    public void testSelectPageByParams() {
    	Page page = new Page(1,20);
    	Map<String,Object> params = new HashMap<String,Object>();
    	params.put("slbId", 1L);
		page = this.slbConfigService.selectPageByParams(page, params);
		
		logger.info("totalPages are:" + page.getTotalPages());
		
		List<SlbConfig> data = (List<SlbConfig>) page.getData();
		
		for (SlbConfig SlbConfig : data) {
			logger.info("agentType:" + SlbConfig.getAgentType());
		}
    }
    
    @Test
    public void testInsert() {
    	SlbConfig slbConfig = new SlbConfig();
    	slbConfig.setAgentType(SlbAgentType.HTTP);
    	slbConfig.setFrontPort("80");
    	slbConfig.setSlbId(1L);
    	this.slbConfigService.insert(slbConfig);
    	logger.info("slb id:" + slbConfig.getId());
    	
    }
    
}
