package com.letv.portal.controller.clouddb;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.letv.common.paging.impl.Page;
import com.letv.common.result.ResultObject;
import com.letv.common.util.HttpUtil;
import com.letv.common.util.StringUtil;
import com.letv.portal.model.monitor.ClusterModel;
import com.letv.portal.model.monitor.NodeModel;
import com.letv.portal.proxy.IContainerProxy;
import com.letv.portal.proxy.IMonitorProxy;
import com.letv.portal.python.service.IBuildTaskService;
import com.letv.portal.service.IContainerService;
import com.letv.portal.service.IMclusterService;
import com.letv.portal.service.IMonitorIndexService;
import com.letv.portal.service.adminoplog.ClassAoLog;
/**
 * Program Name: MonitorController <br>
 * Description:  监控<br>
 * @author name: wujun <br>
 * Written Date: 2014年11月6日 <br>
 * Modified By: <br>
 * Modified Date: <br>
 */
@ClassAoLog(module="RDS管理/监控")
@Controller
@RequestMapping("/monitor")
public class MonitorController {
	
	@Resource
	private IMonitorProxy monitorProxy;
	@Resource
	private IMonitorIndexService monitorIndexService;
	@Resource
	private IContainerProxy containerProxy;
	@Resource
	private IContainerService containerService;
	@Resource
	private IMclusterService mclusterService;
	
	@Autowired
	private IBuildTaskService buildTaskService;
	
	/**
	 * Methods Name: mclusterList <br>
	 * Description: 集群列表展示<br>
	 * @author name: wujun
	 * @param ip
	 * @param result
	 * @return
	 */
	@RequestMapping(value="/mcluster/list",method=RequestMethod.GET)
	public @ResponseBody ResultObject mclusterList(HttpServletRequest request,ResultObject result) {
		Map<String,Object> params = HttpUtil.requestParam2Map(request);
		if(params !=null && params.containsKey("ipAddr")) {
			params.put("ipAddr", StringUtil.transSqlCharacter((String)params.get("ipAddr")));
		}
		result.setData(this.containerService.selectVaildVipContainers(params));
		return result; 
	} 
	/**
	 * Methods Name: mclusterMonitorList <br>
	 * Description: 集群状态是否正常<br>
	 * @author name: wujun
	 * @param result
	 * @return
	 */
	@RequestMapping(value="/{ip}/mcluster/status",method=RequestMethod.GET)
	public @ResponseBody ResultObject monitorClusterList(@PathVariable String ip,ResultObject result) {
		ClusterModel clusterModel = (ClusterModel) this.buildTaskService.getMonitorData(ip, 1L);
		result.setData(clusterModel);
		return result;
	}
	@RequestMapping(value="/{ip}/node/status",method=RequestMethod.GET)
	public @ResponseBody ResultObject monitorNodeList(@PathVariable String ip,ResultObject result) {
		NodeModel monitorNode = (NodeModel) this.buildTaskService.getMonitorData(ip, 2L);
		result.setData(monitorNode);
		return result;  
	}
	@RequestMapping(value="/{ip}/db/status",method=RequestMethod.GET)
	public @ResponseBody ResultObject monitorDbList(@PathVariable String ip,ResultObject result) {
		NodeModel monitorNode = (NodeModel) this.buildTaskService.getMonitorData(ip, 3L);
		result.setData(monitorNode);
		return result;  
	}
    /**
     * Methods Name: mclusterMonitorNodeAndDbDetail <br>
     * Description: 集群节点详情展示<br>
     * @author name: wujun
     * @param ip
     * @param result
     * @return
     */
	@RequestMapping(value="/{ip}/mcluster/nodeAndDb",method=RequestMethod.GET)
	public @ResponseBody ResultObject mclusterMonitorNodeAndDbDetail(@PathVariable String ip,ResultObject result) {
		result.setData(this.containerProxy.selectMonitorDetailNodeAndDbData(ip));
		return result;
	}
	/**
	 * Methods Name: mclusterMonitorClusterDetail <br>
	 * Description: 集群cluster信息展示<br>
	 * @author name: wujun
	 * @param ip
	 * @param result
	 * @return
	 */
	@RequestMapping(value="/{ip}/mcluster/cluster",method=RequestMethod.GET)
	public @ResponseBody ResultObject mclusterMonitorClusterDetail(@PathVariable String ip,ResultObject result) {
		result.setData(this.containerProxy.selectMonitorDetailClusterData(ip));
		return result;
	}
	/**
	 * Methods Name: mclusterMonitorCharts <br>
	 * Description: 监控视图<br>
	 * @author name: wujun
	 * @param mclusterId
	 * @param result
	 * @return
	 */
	@RequestMapping(value="/{mclusterId}/{chartId}/{strategy}",method=RequestMethod.GET)
	public @ResponseBody ResultObject mclusterMonitorCharts(@PathVariable Long mclusterId,@PathVariable Long chartId,@PathVariable Integer strategy,ResultObject result) {
		result.setData(this.monitorProxy.getMonitorViewData(mclusterId,chartId,strategy));
		return result;
	}
	
	/**
	 * Methods Name: mclusterMonitorCharts <br>
	 * Description: 监控视图数目<br>
	 * @author name: wujun 
	 * @param mclusterId
	 * @param result
	 * @return
	 */
	@RequestMapping(value="/index",method=RequestMethod.GET)
	public @ResponseBody ResultObject mclusterMonitorChartsCount(ResultObject result) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("status", 1);
		result.setData(this.monitorIndexService.selectByMap(map));
		return result;
	}

}
