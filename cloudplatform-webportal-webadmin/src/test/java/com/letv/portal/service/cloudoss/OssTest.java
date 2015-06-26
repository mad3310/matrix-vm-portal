package com.letv.portal.service.cloudoss;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;

import com.letv.common.paging.impl.Page;
import com.letv.common.result.ResultObject;
import com.letv.portal.controller.cloudoss.OssController;
import com.letv.portal.junitBase.AbstractTest;
 
public class OssTest extends AbstractTest{

	@Autowired
	private OssController ossController;
    
    @Test
    public void testGceClusterList() {
    	MockHttpServletRequest request =  new MockHttpServletRequest();
    	request.setAttribute("currentPage", 1);
    	request.setAttribute("recordsPerPage", 15);
    	ResultObject ro = ossController.list(new Page(), request, new ResultObject());
    	Assert.assertEquals(10, ((Page)ro.getData()).getTotalRecords());
    }
    
}
