package com.letv.portal.clouddb.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.letv.common.result.ResultObject;
import com.letv.portal.model.ContainerModel;
import com.letv.portal.python.service.IBuildTaskService;
import com.letv.portal.service.IContainerService;

@Controller
@RequestMapping("/monitor")
public class MonitorController {
	
	@Resource
	private IContainerService containerService;
	
	@Resource
	private IBuildTaskService buildTaskService;
	
	@RequestMapping(value="/container",method=RequestMethod.GET)
	public @ResponseBody ResultObject containerMonitorList(ResultObject result) {
		Map map = new HashMap<String, String>();
		map.put("type", "mclustervip");
		List<ContainerModel> cModels = this.containerService.selectAllByMap(map);
		result.setData(this.buildTaskService.getMonitorData(cModels));
		return result;
	}
}
