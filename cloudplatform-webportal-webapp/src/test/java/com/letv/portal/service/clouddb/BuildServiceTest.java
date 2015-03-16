package com.letv.portal.service.clouddb;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.letv.portal.junitBase.AbstractTest;
import com.letv.portal.model.MclusterModel;
import com.letv.portal.service.IBuildService;
 
public class BuildServiceTest extends AbstractTest{

	@Autowired
	private IBuildService buildService;
	
	private final static Logger logger = LoggerFactory.getLogger(
			BuildServiceTest.class);

    @Test
    public void getStepByDbId() {
    	int i = this.buildService.getStepByDbId(12L);
    	System.out.println(i);
    }
    
}
