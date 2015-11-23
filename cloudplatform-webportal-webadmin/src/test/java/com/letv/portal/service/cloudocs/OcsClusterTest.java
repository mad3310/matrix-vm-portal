package com.letv.portal.service.cloudocs;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;

import com.letv.common.paging.impl.Page;
import com.letv.common.result.ResultObject;
import com.letv.portal.controller.cloudocs.OcsClusterController;
import com.letv.portal.junitBase.AbstractTest;
 
public class OcsClusterTest extends AbstractTest{

	@Autowired
	private OcsClusterController ocsClusterController;
    
    @Test
    public void testGceClusterList() {
    	MockHttpServletRequest request =  new MockHttpServletRequest();
    	request.setAttribute("currentPage", 1);
    	request.setAttribute("recordsPerPage", 15);
    	ResultObject ro = ocsClusterController.list(new Page(), request, new ResultObject());
    	Assert.assertEquals(29, ((Page)ro.getData()).getTotalRecords());
    }
    
}
