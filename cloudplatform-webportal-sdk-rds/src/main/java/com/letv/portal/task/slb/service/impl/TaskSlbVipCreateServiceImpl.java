package com.letv.portal.task.slb.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.letv.portal.model.HostModel;
import com.letv.portal.model.slb.SlbCluster;
import com.letv.portal.model.slb.SlbServer;
import com.letv.portal.model.task.TaskResult;
import com.letv.portal.model.task.service.IBaseTaskService;
import com.letv.portal.python.service.ISlbPythonService;
import com.letv.portal.service.slb.ISlbServerService;

@Service("taskSlbVipCreateService")
public class TaskSlbVipCreateServiceImpl extends BaseTask4SlbServiceImpl implements IBaseTaskService{

	@Autowired
	private ISlbPythonService slbPythonService;
	@Autowired
	private ISlbServerService slbServerService;
	
	private final static Logger logger = LoggerFactory.getLogger(TaskSlbVipCreateServiceImpl.class);
	
	@Override
	public TaskResult execute(Map<String, Object> params) throws Exception {
		TaskResult tr = super.execute(params);
		if(!tr.isSuccess())
			return tr;

		//执行业务
		SlbCluster cluster = super.getCluster(params);
		SlbServer server = super.getServer(params);
		HostModel host = super.getHost(cluster.getHclusterId());
		
		Map<String,String> map = new HashMap<String,String>();
		map.put("num", "1");
		
		String result = this.slbPythonService.createContainer2(map,host.getHostIp(),host.getName(),host.getPassword());
		tr = analyzeRestServiceResult(result);
		
		if(tr.isSuccess()) {
			Map data = (Map) ((Map)transToMap(result).get("response")).get("data");
			server.setIp((String) data.get("ip"));
			this.slbServerService.updateBySelective(server);
		}
		tr.setParams(params);
		return tr;
	}
	
	@Override
	public void callBack(TaskResult tr) {
		super.rollBack(tr);
	}
	
}
