package com.letv.mms.api.base;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:webservice-applicationContext.xml" })
public abstract class AbstractTestCase{
	
	@Before 
	public void setup() throws  Exception {
	}
    
}
