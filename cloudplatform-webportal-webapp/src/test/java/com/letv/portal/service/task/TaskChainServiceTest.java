package com.letv.portal.service.task;

import java.util.Date;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.letv.portal.junitBase.AbstractTest;
import com.letv.portal.model.task.TaskChain;
import com.letv.portal.model.task.TaskExecuteStatus;
import com.letv.portal.model.task.service.ITaskChainService;
 
public class TaskChainServiceTest extends AbstractTest{

	@Autowired
	private ITaskChainService taskChainService;
	
	private final static Logger logger = LoggerFactory.getLogger(TaskChainServiceTest.class);
	
    @Test
    public void testInsert() {
    	TaskChain tc = new TaskChain();
    	tc.setTaskId(111L);
    	tc.setStatus(TaskExecuteStatus.DOING);
    	tc.setStartTime(new Date());
    	this.taskChainService.insert(tc);
    }
    @Test
    public void TestSelectNextChainByIndexAndOrder() {
    	TaskChain tc = this.taskChainService.selectNextChainByIndexAndOrder(1L, 1);
    	Assert.assertNotNull(tc);
    	logger.debug("tc's id:" + tc.getId());
    }
    
}
