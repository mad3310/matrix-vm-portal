package com.letv.portal.controller.clouddb;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.letv.common.exception.ValidateException;
import com.letv.common.result.ResultObject;
import com.letv.portal.model.DbModel;
import com.letv.portal.proxy.IMonitorProxy;
import com.letv.portal.service.IDbService;
import com.letv.portal.service.IMonitorIndexService;
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
	private IMonitorProxy monitorProxy;
	@Resource
	private IDbService dbService;
	@Resource
	private IMonitorIndexService monitorIndexService;
	
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
		DbModel dbModel = this.dbService.selectById(dbId);
		if(dbModel == null)
			throw new ValidateException("参数不合法，相关数据不存在");
		result.setData(this.monitorProxy.getDbConnMonitor(dbModel.getMclusterId(), chartId, strategy));
		return result;
	}
	
	@RequestMapping(value="/index/{indexId}",method=RequestMethod.GET)
	public @ResponseBody ResultObject mclusterMonitorChartsCount(@PathVariable Long indexId,ResultObject result) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("status", 1);
		map.put("id", indexId);
		result.setData(this.monitorIndexService.selectByMap(map));
		return result;
	}

}
