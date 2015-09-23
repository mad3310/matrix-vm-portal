package com.letv.portal.controller.user;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.letv.common.result.ResultObject;
import com.letv.common.session.SessionServiceImpl;
import com.letv.portal.service.cloudvm.ICloudvmServerService;


/**Program Name: ServiceController <br>
 * Description:  <br>
 * @author name: liuhao1 <br>
 * Written Date: 2015年9月22日 <br>
 * Modified By: <br>
 * Modified Date: <br>
 */
@Controller
@RequestMapping("/service")
public class ServiceController {
	
	@Autowired
	private SessionServiceImpl sessionService;
	@Autowired
	private ICloudvmServerService cloudvmServerService;
	
	private final static Logger logger = LoggerFactory.getLogger(ServiceController.class);
	
	/**Methods Name: recentOperate <br>
	 * Description: <br>
	 * @author name: liuhao1
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method=RequestMethod.GET)   
	public @ResponseBody ResultObject recentOperate(ResultObject obj) throws Exception{
		Long userId = sessionService.getSession().getUserId();
		Map<String,Object> services = new HashMap<String,Object>();
		services.put("ecs", this.cloudvmServerService.selectByUserIdCount(userId));
		services.put("rds", 0);
		services.put("disk", 0);
		services.put("gce", 0);
		obj.setData(services);
		return obj;
	}
	
}
