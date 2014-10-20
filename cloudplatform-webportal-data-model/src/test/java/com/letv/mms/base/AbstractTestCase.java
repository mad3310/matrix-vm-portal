package com.letv.mms.base;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:data-applicationContext.xml"})
public abstract class AbstractTestCase{
	
	@Before 
	public void setup() throws  Exception {
	}
    
}
