package com.letv.portal.proxy.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.letv.portal.model.BuildModel;
import com.letv.portal.proxy.IBuildProxy;
import com.letv.portal.service.IBaseService;
import com.letv.portal.service.IBuildService;

@Component
public class BuildProxyImpl extends  BaseProxyImpl<BuildModel> implements IBuildProxy{
	
	private final static Logger logger = LoggerFactory.getLogger(BuildProxyImpl.class);

	@Autowired
	private IBuildService buildService;
	@Override
	public List<BuildModel> selectByMclusterId(Long mclusterId) {
		return this.buildService.selectByMclusterId(mclusterId);
	}

	@Override
	public void initStatus(Long mclusterId) {
		this.buildService.initStatus(mclusterId);
	}

	@Override
	public void updateStatusFail(BuildModel buildModel) {
		this.buildService.updateStatusFail(buildModel);
	}

	@Override
	public IBaseService<BuildModel> getService() {
		return buildService;
	}


}
