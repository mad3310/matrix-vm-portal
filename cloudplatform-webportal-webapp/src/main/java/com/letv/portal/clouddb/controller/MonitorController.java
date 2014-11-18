package com.letv.portal.clouddb.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.letv.common.result.ResultObject;
import com.letv.portal.model.MonitorTimeModel;
import com.letv.portal.proxy.IContainerProxy;
import com.letv.portal.service.IContainerService;
import com.letv.portal.service.IDbService;
import com.letv.portal.service.IMonitorIndexService;
import com.letv.portal.service.IMonitorService;
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
	private IMonitorService monitorService;
	@Resource
	private IDbService dbService;
	
	/**
	 * Methods Name: mclusterMonitorCharts <br>
	 * Description: 监控视图<br>
	 * @author name: liuhao1
	 * @param mclusterId
	 * @param result
	 * @return
	 */
	@RequestMapping(value="/{dbId}/{chartId}/{strategy}",method=RequestMethod.GET)
	public @ResponseBody ResultObject mclusterMonitorCharts(@PathVariable Long dbId,@PathVariable Long chartId,@PathVariable Integer strategy,ResultObject result) {
		Long mclusterId = this.dbService.selectById(dbId).getMclusterId();
		result.setData(this.monitorService.getMonitorViewData(mclusterId,chartId,strategy));
		return result;
	}

}
