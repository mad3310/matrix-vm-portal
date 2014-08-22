
package com.letv.portal.api;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.letv.common.result.ResultObject;
import com.letv.portal.model.ContainerModel;
import com.letv.portal.service.IContainerService;

/**Program Name: ContainerAPI <br>
 * Description:  <br>
 * @author name: liuhao1 <br>
 * Written Date: 2014年8月20日 <br>
 * Modified By: <br>
 * Modified Date: <br>
 */
@Controller
@RequestMapping("/container")
public class ContainerAPI {
	@Resource
	private IContainerService containerService;
	
	private final static Logger logger = LoggerFactory.getLogger(ContainerAPI.class);
	
	
	@RequestMapping("/save")   //http://localhost:8080/api/container/save
	public ResultObject save(ContainerModel container, HttpServletRequest request) {
		ResultObject obj = new ResultObject();
		List<ContainerModel> containers = new ArrayList<ContainerModel>();
		
		
		return obj;
	}
	
}
