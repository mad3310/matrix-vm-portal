package com.letv.portal.task.service.gce;

import java.util.HashMap;
import java.util.Map;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.letv.portal.junitBase.AbstractTest;
import com.letv.portal.service.gce.IGceContainerExtService;
import com.letv.portal.task.gce.service.impl.TaskGceContainerStartGbalancerServiceImpl;
 
public class TestTaskGceContainerStartGbalancerService extends AbstractTest{

	@Autowired
	private TaskGceContainerStartGbalancerServiceImpl startGbalancer;
	@Autowired
	private IGceContainerExtService ext;
	
	
    @Test
    //@Ignore
    public void testExecute() {
    	Map<String,Object> params = new HashMap<String,Object>();
    	//params.put("rdsId", "3216");
    	params.put("rdsId", "3229");
    	//params.put("gceClusterId", "106");
    	params.put("gceClusterId", "116");
    	try {
			startGbalancer.execute(params);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    @Test
    @Ignore
    public void testSelect() {
    	Map<String,Object> params = new HashMap<String,Object>();
    	params.put("type", "glb");
    	params.put("containerId", 125);
    	this.ext.selectByMap(params);
    }
    
    
}
