package com.letv.portal.service.impl;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.letv.portal.dao.IBaseDao;
import com.letv.portal.dao.IBuildDao;
import com.letv.portal.model.BuildModel;
import com.letv.portal.service.IBuildService;

@Service("buildService")
public class BuildServiceImpl extends BaseServiceImpl<BuildModel> implements
		IBuildService{
	
	private final static Logger logger = LoggerFactory.getLogger(BuildServiceImpl.class);
	
	@Resource
	private IBuildDao buildDao;

	public BuildServiceImpl() {
		super(BuildModel.class);
	}

	@Override
	public IBaseDao<BuildModel> getDao() {
		return this.buildDao;
	}


}
