package com.letv.portal.service.cloudgce;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;

import com.letv.common.paging.impl.Page;
import com.letv.common.result.ResultObject;
import com.letv.portal.controller.cloudgce.GceContainerController;
import com.letv.portal.junitBase.AbstractTest;
 
public class GceContainerTest extends AbstractTest{

	@Autowired
	private GceContainerController gceContainerController;
    
    @Test
    public void testGceContainerList() {
    	MockHttpServletRequest request =  new MockHttpServletRequest();
    	request.setAttribute("currentPage", 1);
    	request.setAttribute("recordsPerPage", 15);
    	ResultObject ro = gceContainerController.list(new Page(), request, new ResultObject());
    	Assert.assertEquals(110, ((Page)ro.getData()).getTotalRecords());
    }
    
    @Test
    public void testGceContainerByClusterId() {
    	ResultObject ro = gceContainerController.list(19l, new ResultObject());
    	Assert.assertEquals(2, ((List)ro.getData()).size());
    }
    
}
