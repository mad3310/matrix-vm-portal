package com.letv.portal.letvcloud;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.letv.portal.junitBase.AbstractTest;
import com.letv.portal.service.letvcloud.BillUserInvoiceService;


public class TestBillUserInvoiceService extends AbstractTest {

	@Autowired
	private BillUserInvoiceService billUserInvoiceService;
	
	private final static Logger logger = LoggerFactory.getLogger(TestBillUserInvoiceService.class);

	
  
}
