package com.letv.portal.clouddb.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.letv.common.result.ResultObject;
import com.letv.portal.service.IBuildService;

@Controller
@RequestMapping("/build")
public class BuildController {
	
	@Autowired
	private IBuildService buildService;
	
	private final static Logger logger = LoggerFactory.getLogger(BuildController.class);
	
	/**Methods Name: list4Mcluster <br>
	 * Description: 获取集群创建过程<br>
	 * @author name: liuhao1
	 * @param mclusterId
	 * @param result
	 * @return
	 */
	@RequestMapping(value="/mcluster/{mclusterId}", method=RequestMethod.GET)   
	public @ResponseBody ResultObject list4Mcluster(@PathVariable Long mclusterId,ResultObject result) {
		result.setData(this.buildService.selectByMclusterId(mclusterId));
		return result;
	}	
	/**Methods Name: list4Db <br>
	 * Description: 获取db创建中集群创建过程<br>
	 * @author name: liuhao1
	 * @param mclusterId
	 * @param result
	 * @return
	 */
	@RequestMapping(value="/db/{dbId}", method=RequestMethod.GET)   
	public @ResponseBody ResultObject list4Db(@PathVariable Long dbId,ResultObject result) {
		result.setData(this.buildService.selectByDbId(dbId));
		return result;
	}	
}
