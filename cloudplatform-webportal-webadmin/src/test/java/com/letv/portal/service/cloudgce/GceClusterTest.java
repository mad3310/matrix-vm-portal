package com.letv.portal.service.cloudgce;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;

import com.letv.common.paging.impl.Page;
import com.letv.common.result.ResultObject;
import com.letv.portal.controller.cloudgce.GceClusterController;
import com.letv.portal.junitBase.AbstractTest;
 
public class GceClusterTest extends AbstractTest{

	@Autowired
	private GceClusterController gceClusterController;
    
    @Test
    public void testGceClusterList() {
    	MockHttpServletRequest request =  new MockHttpServletRequest();
    	request.setParameter("currentPage", "1");
    	request.setParameter("recordsPerPage", "15");
    	request.setParameter("status", "");
    	ResultObject ro = gceClusterController.list(new Page(), request, new ResultObject());
    	Assert.assertEquals(92, ((Page)ro.getData()).getTotalRecords());
    }
    
}
