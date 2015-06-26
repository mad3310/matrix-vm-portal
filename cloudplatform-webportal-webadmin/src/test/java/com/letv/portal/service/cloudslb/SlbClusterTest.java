package com.letv.portal.service.cloudslb;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;

import com.letv.common.paging.impl.Page;
import com.letv.common.result.ResultObject;
import com.letv.portal.controller.cloudslb.SlbClusterController;
import com.letv.portal.junitBase.AbstractTest;
 
public class SlbClusterTest extends AbstractTest{

	@Autowired
	private SlbClusterController slbClusterController;
    
    @Test
    public void testGceClusterList() {
    	MockHttpServletRequest request =  new MockHttpServletRequest();
    	request.setAttribute("currentPage", 1);
    	request.setAttribute("recordsPerPage", 15);
    	ResultObject ro = slbClusterController.list(new Page(), request, new ResultObject());
    	Assert.assertEquals(18, ((Page)ro.getData()).getTotalRecords());
    }
    
}
