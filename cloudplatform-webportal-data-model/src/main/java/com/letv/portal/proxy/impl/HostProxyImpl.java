package com.letv.portal.proxy.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.letv.portal.model.HclusterModel;
import com.letv.portal.model.HostModel;
import com.letv.portal.proxy.IHostProxy;
import com.letv.portal.python.service.IBuildTaskService;
import com.letv.portal.service.IBaseService;
import com.letv.portal.service.IHclusterService;
import com.letv.portal.service.IHostService;

@Component
public class HostProxyImpl extends BaseProxyImpl<HostModel> implements
		IHostProxy{
	
	private final static Logger logger = LoggerFactory.getLogger(HostProxyImpl.class);

	@Autowired
	private IHostService hostService;
	@Autowired
	private IBuildTaskService buildTaskService;
	@Resource
	private IHclusterService hclusterService;
	@Override
	public IBaseService<HostModel> getService() {
		return hostService;
	}
	@Override
	public void insertAndPhyhonApi(HostModel hostModel) {		
		hostModel.setName("root");
		hostModel.setPassword("root");
		this.hostService.insert(hostModel);
		Map<String, String>  map = new HashMap<String, String>();
		map.put("id", hostModel.getHclusterId().toString());
  		List<HclusterModel> list = (List<HclusterModel>)hclusterService.selectByMap(map);
  		if(null!=list&&list.size()>0){
  			HclusterModel hclusterModel = list.get(0);
  			hostModel.setHcluster(hclusterModel);
  		}
  		try {
  			this.buildTaskService.createHost(hostModel);
		} catch (Exception e)  {
			this.hostService.delete(hostModel);
			throw new RuntimeException("host ip not exist!");
		}
	
	}
	

}
