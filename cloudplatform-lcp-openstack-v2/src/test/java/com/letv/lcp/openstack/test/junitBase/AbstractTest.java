package com.letv.lcp.openstack.test.junitBase;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:data-applicationContext.xml"})
public abstract class AbstractTest extends TestCase {
	
	@Before   
	public void setup() throws  Exception {
	}
    
}