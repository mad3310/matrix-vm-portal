package com.letv.portal.controller.cloudcache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.letv.common.result.ResultObject;
import com.letv.portal.model.task.service.ITaskChainService;

@Controller("cachebuild")
@RequestMapping("/build")
public class BuildController {

	@Autowired
	private ITaskChainService taskChainService;

	private final static Logger logger = LoggerFactory
			.getLogger(BuildController.class);

	@RequestMapping(value = "/cache/{cacheId}", method = RequestMethod.GET)
	public @ResponseBody ResultObject list4Cache(@PathVariable Long cacheId,
			ResultObject result) {
		result.setData(this.taskChainService.getStepByCacheId(cacheId));
		return result;
	}
}
