package com.letv.portal.clouddb.service;

import java.util.Iterator;
import java.util.Map;

import junit.framework.Assert;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.letv.portal.junitBase.AbstractTest;
import com.letv.portal.service.IDbService;
 
public class DbTest extends AbstractTest{

	@Autowired
	private IDbService dbService;
	
	private final static Logger logger = LoggerFactory.getLogger(
			DbTest.class);

  
    @Test
    public void testSelectCreateParams() {
    	Map<String, Object> selectCreateParams = this.dbService.selectCreateParams(13L, false);
    	Assert.assertNotNull(selectCreateParams);
    }
}
