package com.letv.portal.clouddb.service;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.letv.portal.junitBase.AbstractTest;
import com.letv.portal.model.MclusterModel;
import com.letv.portal.service.IMclusterService;
 
public class MclusterTest extends AbstractTest{

	@Autowired
	private IMclusterService mclusterService;
	
	private final static Logger logger = LoggerFactory.getLogger(
			MclusterTest.class);

  
    @Test
    public void testDelete() {
    	MclusterModel mcluster = this.mclusterService.selectById(28L);
    	this.mclusterService.delete(mcluster);
    }
    
}
