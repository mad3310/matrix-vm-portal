package com.letv.portal.proxy.impl;

import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.letv.portal.enumeration.MclusterStatus;
import com.letv.portal.model.ContainerModel;
import com.letv.portal.proxy.IContainerProxy;
import com.letv.portal.python.service.IBuildTaskService;
import com.letv.portal.service.IBaseService;
import com.letv.portal.service.IContainerService;

@Component("containerProxy")
public class ContainerProxyImpl extends BaseProxyImpl<ContainerModel> implements
		IContainerProxy{

	private final static Logger logger = LoggerFactory.getLogger(ContainerProxyImpl.class);
	
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
		container.setStatus(MclusterStatus.STOPPING.getValue());
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

	@Override
	public List<ContainerModel> selectByMclusterId(Long mclusterId) {
		return this.containerService.selectByMclusterId(mclusterId);
	}
	
}
