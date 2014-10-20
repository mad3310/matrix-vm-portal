package com.letv.portal.proxy.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.letv.portal.enumeration.MclusterStatus;
import com.letv.portal.model.ContainerModel;
import com.letv.portal.model.MclusterModel;
import com.letv.portal.proxy.IContainerProxy;
import com.letv.portal.python.service.IBuildTaskService;
import com.letv.portal.service.IBaseService;
import com.letv.portal.service.IContainerService;

@Component
public class ContainerProxyImpl extends BaseProxyImpl<ContainerModel> implements
		IContainerProxy{

	@Autowired
	private IContainerService containerService;
	@Autowired
	private IBuildTaskService buildTaskService;
	
	@Override
	public IBaseService<ContainerModel> getService() {
		return containerService;
	}
	
	@Override
	public void start(Long containerId) {
		ContainerModel container = this.containerService.selectById(containerId);
		container.setStatus(MclusterStatus.STARTING.getValue());
		this.containerService.updateBySelective(container);
		this.buildTaskService.startContainer(container);
		
	}
	@Override
	public void stop(Long containerId) {
		ContainerModel container = this.containerService.selectById(containerId);
		container.setStatus(MclusterStatus.STOPING.getValue());
		this.containerService.updateBySelective(container);
		this.buildTaskService.stopContainer(container);
		
	}
	
	@Override
	public void checkStatus() {
		List<ContainerModel> list = this.containerService.selectByMap(null);
		for (ContainerModel container : list) {
			this.checkStatus(container);
		}
	}
	
	private void checkStatus(ContainerModel container) {
		this.buildTaskService.checkContainerStatus(container);
	}
	
}
