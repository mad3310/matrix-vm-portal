package com.letv.portal.proxy.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.letv.portal.model.ContainerModel;
import com.letv.portal.model.MonitorDetailModel;
import com.letv.portal.model.MonitorIndexModel;
import com.letv.portal.proxy.IMonitorProxy;
import com.letv.portal.python.service.IBuildTaskService;
import com.letv.portal.service.IContainerService;
import com.letv.portal.service.IMonitorIndexService;
import com.letv.portal.service.IMonitorService;

@Component("monitorProxy")
public class MonitorProxyImpl implements IMonitorProxy{
	private final static Logger logger = LoggerFactory.getLogger(MonitorProxyImpl.class);
	@Autowired
	private IMonitorService monitorService;
	
	@Autowired
	private IBuildTaskService buildService;
	
	@Autowired
	private IContainerService containerService;
	
	@Autowired
	private IMonitorIndexService moniIndexService;
	
	@Override
	public void collectMclusterServiceData() {
		Map<String,String> map = new  HashMap<String,String>();
		map.put("type", "mclusternode");
		List<ContainerModel> contianers = this.containerService.selectAllByMap(map);
		for (ContainerModel container : contianers) {
			try {
				intoMclusterServiceDataDb(this.buildService.getContainerServiceData(container.getIpAddr()));
			} catch (Exception e) {
				//报警邮件
				logger.error("获取数据失败");
			}
		}
		
	
	}

	private void intoMclusterServiceDataDb(Map map) throws Exception{
		for(Iterator it =  map.keySet().iterator();it.hasNext();){
			 Object keString = it.next();	
			 List<MonitorDetailModel> list  = (List<MonitorDetailModel>) map.get(keString);
			 for(MonitorDetailModel m:list){
               this.monitorService.insert(m);
			 }
		}	
	}

}
