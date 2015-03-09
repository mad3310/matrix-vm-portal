package com.letv.portal.controller.clouddb;

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
	
	/**Methods Name: list4Db <br>
	 * Description: 查看db创建进度<br>
	 * @author name: liuhao1
	 * @param dbId
	 * @param result
	 * @return
	 */
	@RequestMapping(value="/db/{dbId}", method=RequestMethod.GET)   
	public @ResponseBody ResultObject list4Db(@PathVariable Long dbId,ResultObject result) {
		result.setData(this.buildService.getStepByDbId(dbId));
		return result;
	}	
}
