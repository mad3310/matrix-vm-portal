package com.letv.portal.service.task;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.letv.portal.junitBase.AbstractTest;
import com.letv.portal.model.DbUserModel;
import com.letv.portal.model.task.TemplateTaskChain;
import com.letv.portal.model.task.service.ITemplateTaskChainService;
 
public class TemplateTaskChainServiceTest extends AbstractTest{

	@Autowired
	private ITemplateTaskChainService templateTaskChainService;
	
	private final static Logger logger = LoggerFactory.getLogger(TemplateTaskChainServiceTest.class);
	
    @Test
    public void testSelectByTemplateTaskId() {
    	Long templateId = 1L;
    	List<TemplateTaskChain> ttcs = this.templateTaskChainService.selectByTemplateTaskId(templateId);
    	Assert.assertNotNull(ttcs);
    	for (TemplateTaskChain templateTaskChain : ttcs) {
    		logger.debug("taskId is:" + templateTaskChain.getTaskId());
    		logger.debug("taskDetailId is:" + templateTaskChain.getTaskDetailId());
		}
    }
    
}
