package com.letv.portal.proxy.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.letv.portal.model.ContainerModel;
import com.letv.portal.proxy.IContainerProxy;
import com.letv.portal.service.IBaseService;
import com.letv.portal.service.IContainerService;

@Component
public class ContainerProxyImpl extends BaseProxyImpl<ContainerModel> implements
		IContainerProxy{

	@Autowired
	private IContainerService containerService;
	@Override
	public IBaseService<ContainerModel> getService() {
		return containerService;
	}
	
}
