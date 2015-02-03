package com.letv.portal.clouddb.service;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.letv.portal.junitBase.AbstractTest;
import com.letv.portal.model.DbUserModel;
import com.letv.portal.service.IDbUserService;
 
public class DbUserTest extends AbstractTest{

	@Autowired
	private IDbUserService dbUserService;
	
	private final static Logger logger = LoggerFactory.getLogger(
			DbUserTest.class);

    @Test
    public void getStepByDbId() {
    	DbUserModel dbUser = new DbUserModel();
    	dbUser.setUsername("test_jll");
    	dbUser.setDbId(14L);
    	dbUser.setDescn("hello world");
    	this.dbUserService.updateDescnByUsername(dbUser);
    }
    
}
