package com.letv.portal.service.cloudocs;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;

import com.letv.common.paging.impl.Page;
import com.letv.common.result.ResultObject;
import com.letv.portal.controller.cloudocs.OcsController;
import com.letv.portal.junitBase.AbstractTest;
 
public class OcsTest extends AbstractTest{

	@Autowired
	private OcsController ocsController;
    
    @Test
    public void testGceClusterList() {
    	MockHttpServletRequest request =  new MockHttpServletRequest();
    	request.setAttribute("currentPage", 1);
    	request.setAttribute("recordsPerPage", 15);
    	ResultObject ro = ocsController.list(new Page(), request, new ResultObject());
    	Assert.assertEquals(28, ((Page)ro.getData()).getTotalRecords());
    }
    
}
