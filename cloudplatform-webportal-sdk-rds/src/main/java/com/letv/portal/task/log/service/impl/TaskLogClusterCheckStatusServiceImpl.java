package com.letv.portal.task.log.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.letv.portal.constant.Constant;
import com.letv.portal.enumeration.MclusterStatus;
import com.letv.portal.model.HostModel;
import com.letv.portal.model.log.LogCluster;
import com.letv.portal.model.log.LogContainer;
import com.letv.portal.model.log.LogServer;
import com.letv.portal.model.task.TaskResult;
import com.letv.portal.model.task.service.IBaseTaskService;
import com.letv.portal.python.service.ILogPythonService;
import com.letv.portal.service.IHostService;
import com.letv.portal.service.log.ILogContainerService;

@Service("taskLogClusterCheckStatusService")
public class TaskLogClusterCheckStatusServiceImpl extends BaseTask4LogServiceImpl implements IBaseTaskService{

	@Autowired
	private ILogPythonService logPythonService;
	@Autowired
	private IHostService hostService;
	@Autowired
	private ILogContainerService logContainerService;
	
	private final static long PYTHON_CREATE_CHECK_TIME = 180000;
	private final static long PYTHON_CHECK_INTERVAL_TIME = 3000;
	
	private final static Logger logger = LoggerFactory.getLogger(TaskLogClusterCheckStatusServiceImpl.class);
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public TaskResult execute(Map<String, Object> params) throws Exception{
		TaskResult tr = super.execute(params);
		if(!tr.isSuccess())
			return tr;
		
		LogCluster logCluster = super.getLogCluster(params);
		HostModel host = super.getHost(logCluster.getHclusterId());
		LogServer logServer = super.getLogServer(params);
		
		String result = logPythonService.checkContainerCreateStatus(logCluster.getClusterName(),host.getHostIp(),host.getName(),host.getPassword());
		tr = analyzeRestServiceResult(result);
		
		Long start = new Date().getTime();
		while(!tr.isSuccess()) {
			Thread.sleep(PYTHON_CHECK_INTERVAL_TIME);
			if(new Date().getTime()-start >PYTHON_CREATE_CHECK_TIME) {
				tr.setResult("check time over");
				break;
			}
			result = logPythonService.checkContainerCreateStatus(logCluster.getClusterName(),host.getHostIp(),host.getName(),host.getPassword());
			tr = analyzeRestServiceResult(result);
		}
		if(tr.isSuccess()) {
			List<Map> containers = (List<Map>)((Map)transToMap(result).get("response")).get("containers");
			for (Map map : containers) {
				LogContainer container = new LogContainer();
				BeanUtils.populate(container, map);
				container.setLogClusterId(logCluster.getId());
				container.setIpMask((String) map.get("netMask"));
				container.setStatus(MclusterStatus.RUNNING.getValue());
				//物理机集群维护完成后，修改此处，需要关联物理机id
				HostModel hostModel = this.hostService.selectByIp((String) map.get("hostIp"));
				if(null != hostModel) {
					container.setHostId(hostModel.getId());
				}
				List<Map> portBindings = (List<Map>) map.get("port_bindings");
				StringBuffer hostPort = new StringBuffer();
				StringBuffer containerPort = new StringBuffer();
				StringBuffer protocol = new StringBuffer();
				for (Map portBinding : portBindings) {
					if("manager".equals(portBinding.get("type"))) {
						container.setMgrBindHostPort((String)portBinding.get("hostPort"));
						continue;
					}
					hostPort.append((String)portBinding.get("hostPort")).append(",");
					containerPort.append((String)portBinding.get("containerPort")).append(",");
					protocol.append((String)portBinding.get("protocol")).append(",");
				}
				container.setBingHostPort(hostPort.length()>0?hostPort.substring(0, hostPort.length()-1):hostPort.toString());
				container.setBindContainerPort(containerPort.length()>0?containerPort.substring(0, containerPort.length()-1):containerPort.toString());
				container.setBingProtocol(protocol.length()>0?protocol.substring(0, protocol.length()-1):protocol.toString());
				
				this.logContainerService.insert(container);
			}
		}
		tr.setParams(params);
		return tr;
	}
	
	@Override
	public TaskResult analyzeRestServiceResult(String result) {
		TaskResult tr = new TaskResult();
		Map<String, Object> map = transToMap(result);
		if(map == null) {
			tr.setSuccess(false);
			tr.setResult("api connect failed");
			return tr;
		}
		Map<String,Object> meta = (Map<String, Object>) map.get("meta");
		Map<String,Object> response = null;
		
		boolean isSucess = Constant.PYTHON_API_RESPONSE_SUCCESS.equals(String.valueOf(meta.get("code")));
		if(isSucess) {
			response = (Map<String, Object>) map.get("response");
			isSucess = Constant.PYTHON_API_RESULT_SUCCESS.equals(String.valueOf(response.get("code")));
		}
		if(isSucess) {
			tr.setResult((String) response.get("message"));
		} else {
			tr.setResult((String) meta.get("errorType") +":"+ (String) meta.get("errorDetail"));
		}
		tr.setSuccess(isSucess);
		return tr;
	}
	
}
