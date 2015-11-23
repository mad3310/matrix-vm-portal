package com.letv.portal.service.clouddb;

import java.util.Map;

import junit.framework.Assert;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.letv.portal.junitBase.AbstractTest;
import com.letv.portal.service.IDbUserService;
 
public class DbUserTest extends AbstractTest{

	@Autowired
	private IDbUserService dbUserService;
	
	private final static Logger logger = LoggerFactory.getLogger(
			DbUserTest.class);

  
    @Test
    public void testSelectCreateParams() {
    	Map<String, Object> selectCreateParams = this.dbUserService.selectCreateParams(21L, true);
    	Assert.assertNotNull(selectCreateParams);
    }
}
