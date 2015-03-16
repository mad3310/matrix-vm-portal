package com.letv.portal.service.task;

import java.util.Date;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.letv.portal.junitBase.AbstractTest;
import com.letv.portal.model.task.TaskChainIndex;
import com.letv.portal.model.task.TaskExecuteStatus;
import com.letv.portal.model.task.service.ITaskChainIndexService;
 
public class TaskChainIndexServiceTest extends AbstractTest{

	@Autowired
	private ITaskChainIndexService taskChainIndexService;
	
	private final static Logger logger = LoggerFactory.getLogger(TaskChainIndexServiceTest.class);
	
    @Test
    public void testInsert() {
    	TaskChainIndex tci = new TaskChainIndex();
    	tci.setTaskId(111L);
    	tci.setStatus(TaskExecuteStatus.DOING);
    	tci.setStartTime(new Date());
    	this.taskChainIndexService.insert(tci);
    }
    
    @Test
    public void testUpdateBySelective() {
    	TaskChainIndex tci = new TaskChainIndex();
    	tci.setId(1L);
    	tci.setTaskId(111L);
    	tci.setStatus(TaskExecuteStatus.DOING);
		tci.setStartTime(new Date());
		this.taskChainIndexService.updateBySelective(tci);
    }
    
}
