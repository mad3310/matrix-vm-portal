package com.letv.portal.service.cloudgce;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;

import com.letv.common.paging.impl.Page;
import com.letv.common.result.ResultObject;
import com.letv.portal.controller.cloudgce.GceClusterController;
import com.letv.portal.junitBase.AbstractTest;
import com.letv.portal.service.gce.IGceClusterService;
 
public class GceClusterTest extends AbstractTest{

	@Autowired
	private IGceClusterService gceClusterService;
	
	@Autowired
	private GceClusterController gceClusterController;
    
    @Test
    public void testGceClusterList() {
    	MockHttpServletRequest request =  new MockHttpServletRequest();
    	request.setAttribute("currentPage", 1);
    	request.setAttribute("recordsPerPage", 15);
    	ResultObject ro = gceClusterController.list(new Page(), request, new ResultObject());
    	System.out.println(ro.getData());
    }
    
}
