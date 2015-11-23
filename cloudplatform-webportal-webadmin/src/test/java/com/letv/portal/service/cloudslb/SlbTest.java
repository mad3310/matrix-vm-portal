package com.letv.portal.service.cloudslb;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;

import com.letv.common.paging.impl.Page;
import com.letv.common.result.ResultObject;
import com.letv.portal.controller.cloudslb.SlbController;
import com.letv.portal.junitBase.AbstractTest;
 
public class SlbTest extends AbstractTest{

	@Autowired
	private SlbController slbController;
    
    @Test
    public void testGceClusterList() {
    	MockHttpServletRequest request =  new MockHttpServletRequest();
    	request.setAttribute("currentPage", 1);
    	request.setAttribute("recordsPerPage", 15);
    	ResultObject ro = slbController.list(new Page(), request, new ResultObject());
    	Assert.assertEquals(16, ((Page)ro.getData()).getTotalRecords());
    }
    
}
