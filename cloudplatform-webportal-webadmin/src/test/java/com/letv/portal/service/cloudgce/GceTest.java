package com.letv.portal.service.cloudgce;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;

import com.letv.common.paging.impl.Page;
import com.letv.common.result.ResultObject;
import com.letv.portal.controller.cloudgce.GceController;
import com.letv.portal.junitBase.AbstractTest;
 
public class GceTest extends AbstractTest{

	@Autowired
	private GceController gceController;
    
    @Test
    public void testGceClusterList() {
    	MockHttpServletRequest request =  new MockHttpServletRequest();
    	request.setAttribute("currentPage", 1);
    	request.setAttribute("recordsPerPage", 15);
    	ResultObject ro = gceController.list(new Page(), request, new ResultObject());
    	Assert.assertEquals(34, ((Page)ro.getData()).getTotalRecords());
    }
    
}
