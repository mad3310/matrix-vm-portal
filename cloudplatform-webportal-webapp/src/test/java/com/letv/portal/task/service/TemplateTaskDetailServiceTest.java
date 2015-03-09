package com.letv.portal.task.service;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.letv.portal.junitBase.AbstractTest;
import com.letv.portal.model.task.TemplateTaskDetail;
import com.letv.portal.model.task.service.ITemplateTaskDetailService;
 
public class TemplateTaskDetailServiceTest extends AbstractTest{

	@Autowired
	private ITemplateTaskDetailService templateTaskDetailService;
	
	private final static Logger logger = LoggerFactory.getLogger(TemplateTaskDetailServiceTest.class);
	
    @Test
    public void testSelectById() {
    	Long id = 1L;
    	TemplateTaskDetail ttd = this.templateTaskDetailService.selectById(id);
    	Assert.assertNotNull(ttd);
    	logger.debug("ttd's name is:" + ttd.getName());
    }
    
}
