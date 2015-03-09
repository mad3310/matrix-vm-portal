package com.letv.portal.service.slb.impl;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.letv.common.dao.IBaseDao;
import com.letv.portal.dao.slb.ISlbClusterDao;
import com.letv.portal.model.slb.SlbCluster;
import com.letv.portal.service.impl.BaseServiceImpl;
import com.letv.portal.service.slb.ISlbClusterService;

@Service("slbClusterService")
public class SlbClusterServiceImpl extends BaseServiceImpl<SlbCluster> implements ISlbClusterService{
	
	private final static Logger logger = LoggerFactory.getLogger(SlbClusterServiceImpl.class);
	
	@Resource
	private ISlbClusterDao slbClusterDao;

	public SlbClusterServiceImpl() {
		super(SlbCluster.class);
	}

	@Override
	public IBaseDao<SlbCluster> getDao() {
		return this.slbClusterDao;
	}

}
