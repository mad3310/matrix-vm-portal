package com.letv.portal.task.service.gce;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.letv.portal.junitBase.AbstractTest;
import com.letv.portal.task.gce.service.impl.TaskGceContainerStartMoxiServiceImpl;
 
public class TestTaskGceContainerStartMoxiService extends AbstractTest{

	@Autowired
	private TaskGceContainerStartMoxiServiceImpl startMoxi;
	
	
    @Test
    public void testExecute() {
    	Map<String,Object> params = new HashMap<String,Object>();
    	params.put("ocsId", "75");
    	params.put("gceClusterId", "116");
    	try {
    		startMoxi.execute(params);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    
    
}
