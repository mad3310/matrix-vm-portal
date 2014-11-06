package com.letv.portal.clouddb.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.letv.common.result.ResultObject;
import com.letv.portal.model.ContainerModel;
import com.letv.portal.proxy.IContainerProxy;
import com.letv.portal.python.service.IBuildTaskService;
import com.letv.portal.service.IContainerService;
/**
 * Program Name: MonitorController <br>
 * Description:  监控<br>
 * @author name: wujun <br>
 * Written Date: 2014年11月6日 <br>
 * Modified By: <br>
 * Modified Date: <br>
 */
@Controller
@RequestMapping("/monitor")
public class MonitorController {
	
	@Resource
	private IContainerService containerService;
	
	@Resource
	private IContainerProxy containerProxy;
	/**
	 * Methods Name: containerMonitorList <br>
	 * Description: 展示mcluster集群监控列表<br>
	 * @author name: wujun
	 * @param result
	 * @return
	 */
	@RequestMapping(value="/mcluster",method=RequestMethod.GET)
	public @ResponseBody ResultObject containerMonitorList(ResultObject result) {
		Map map = new HashMap<String, String>();
		map.put("type", "mclustervip");
		result.setData(this.containerProxy.selectMonitorMclusterDetailOrList(map));
		return result;  
	}
	/**
	 * Methods Name: containerMonitorDetail <br>
	 * Description: 展示mcluster集群监控详情<br>
	 * @author name: wujun
	 * @param ip
	 * @param result
	 * @return
	 */
	@RequestMapping(value="/{ip}/mcluster",method=RequestMethod.GET)
	public @ResponseBody ResultObject containerMonitorDetail(@PathVariable String ip,ResultObject result) {
		Map map = new HashMap<String, String>();
		map.put("ipAddr", ip);
		result.setData(this.containerProxy.selectMonitorMclusterDetailOrList(map));
		return result;
	}
}
