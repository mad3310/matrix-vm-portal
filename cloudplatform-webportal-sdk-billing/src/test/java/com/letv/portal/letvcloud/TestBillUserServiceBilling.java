package com.letv.portal.letvcloud;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.letv.portal.junitBase.AbstractTest;
import com.letv.portal.service.letvcloud.BillUserServiceBilling;


public class TestBillUserServiceBilling extends AbstractTest {

	@Autowired
	private BillUserServiceBilling billUserServiceBilling;
	
	private final static Logger logger = LoggerFactory.getLogger(TestBillUserServiceBilling.class);

	
  
}
