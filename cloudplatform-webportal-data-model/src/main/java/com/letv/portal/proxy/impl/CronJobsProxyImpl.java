package com.letv.portal.proxy.impl;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.letv.portal.constant.Constant;
import com.letv.portal.model.HclusterModel;
import com.letv.portal.model.HostModel;
import com.letv.portal.proxy.ICronJobsProxy;
import com.letv.portal.python.service.IPythonService;
import com.letv.portal.service.IHclusterService;
import com.letv.portal.service.IHostService;
import com.letv.portal.service.IMclusterService;
import com.letv.portal.service.cbase.ICbaseClusterService;
import com.letv.portal.service.cbase.ICbaseContainerService;
import com.letv.portal.service.gce.IGceClusterService;
import com.letv.portal.service.log.ILogClusterService;
import com.letv.portal.service.slb.ISlbClusterService;

@Component
public class CronJobsProxyImpl implements ICronJobsProxy{
	
	private final static Logger logger = LoggerFactory.getLogger(CronJobsProxyImpl.class);

	@Autowired
	private IHclusterService hclusterService;
	@Autowired
	private IHostService hostService;
	@Autowired
	private IPythonService pythonService;
	@Autowired
	private IGceClusterService gceClusterService;
	@Autowired
	private ISlbClusterService slbClusterService;
	@Autowired
	private IMclusterService mclusterService;
	@Autowired
	private ILogClusterService logClusterService;
	@Resource
	private ICbaseClusterService cbaseClusterService;
	@Resource
	private ICbaseContainerService cbaseContainerService;
	
	@Override
	public void checkCount() {
		List<HclusterModel> rdsHcluster = this.hclusterService.selectByMap(null);
		for (HclusterModel hcluster : rdsHcluster) {
			List<HostModel> hosts = this.hostService.selectByHclusterId(hcluster.getId());
			if(null == hosts || hosts.isEmpty())
				continue;
			HostModel host = hosts.get(0);
			Map map = this.transResult( this.pythonService.checkMclusterCount(host.getHostIp(),host.getName(),host.getPassword()));
			if(null == map || map.isEmpty())
				continue;
			if(!Constant.PYTHON_API_RESPONSE_SUCCESS.equals(String.valueOf(((Map<String,Object>)map.get("meta")).get("code")))) 
				continue;
			List<Map<String,Object>> data = (List<Map<String,Object>>) ((Map) map.get("response")).get("data");
			if(data == null || data.isEmpty())
				continue;
			asyncClusterCount(data,hcluster);
		}
	}
	
	private void asyncClusterCount(List<Map<String,Object>> data,HclusterModel hcluster) {
		for (Map<String,Object> mm : data) {
			String type = (String) mm.get("type");
			if("jetty".equals(type) || "nginx".equals(type)) {
				this.gceClusterService.asyncClusterCount(mm,hcluster);
				continue;
			}
			if("gbalancerCluster".equals(type)) {
				this.slbClusterService.asyncClusterCount(mm,hcluster);
				continue;
			}
			if("logstash".equals(type)) {
				this.logClusterService.asyncClusterCount(mm,hcluster);
				continue;
			}
			if("cbase".equals(type)) {
				this.cbaseClusterService.asyncClusterCount(mm,hcluster);
				continue;
			}
			if("mcluster".equals(type) || "gbalancer".equals(type)) {
//				this.mclusterService.asyncClusterCount(mm,hcluster);
				continue;
			}
		}
	}
	
	private Map<String,Object> transResult(String result){
		ObjectMapper resultMapper = new ObjectMapper();
		Map<String,Object> jsonResult = new HashMap<String,Object>();
		if(StringUtils.isEmpty(result))
			return jsonResult;
		try {
			jsonResult = resultMapper.readValue(result, Map.class);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return jsonResult;
	}
	
}
