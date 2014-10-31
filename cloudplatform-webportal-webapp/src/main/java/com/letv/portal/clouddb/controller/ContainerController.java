package com.letv.portal.clouddb.controller;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.letv.common.result.ResultObject;
import com.letv.portal.service.IContainerService;

@Controller
@RequestMapping("/container")
public class ContainerController {
	
	@Resource
	private IContainerService containerService;
	
	private final static Logger logger = LoggerFactory.getLogger(ContainerController.class);
	
	/**Methods Name: list <br>
	 * Description: 根据mclusterId获取相关container列表<br>
	 * @author name: liuhao1
	 * @param mclusterId
	 * @param result
	 * @return
	 */
	@RequestMapping(value="/{mclusterId}",method=RequestMethod.GET)
	public @ResponseBody ResultObject list(@PathVariable Long mclusterId,ResultObject result) {
		result.setData(this.containerService.selectByMclusterId(mclusterId));
		return result;
	}
}
