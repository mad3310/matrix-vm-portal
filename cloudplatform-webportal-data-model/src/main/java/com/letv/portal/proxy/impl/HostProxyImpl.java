package com.letv.portal.proxy.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.letv.portal.model.HostModel;
import com.letv.portal.proxy.IHostProxy;
import com.letv.portal.service.IBaseService;
import com.letv.portal.service.IHostService;

@Component
public class HostProxyImpl extends BaseProxyImpl<HostModel> implements
		IHostProxy{
	
	private final static Logger logger = LoggerFactory.getLogger(HostProxyImpl.class);

	@Autowired
	private IHostService hostService;
	@Override
	public IBaseService<HostModel> getService() {
		return hostService;
	}
	

}
