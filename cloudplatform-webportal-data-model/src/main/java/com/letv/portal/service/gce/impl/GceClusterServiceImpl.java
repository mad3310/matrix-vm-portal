package com.letv.portal.service.gce.impl;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.letv.common.dao.IBaseDao;
import com.letv.portal.dao.gce.IGceClusterDao;
import com.letv.portal.model.MclusterModel;
import com.letv.portal.model.gce.GceCluster;
import com.letv.portal.service.gce.IGceClusterService;
import com.letv.portal.service.impl.BaseServiceImpl;

@Service("gceClusterService")
public class GceClusterServiceImpl extends BaseServiceImpl<GceCluster> implements IGceClusterService{
	
	private final static Logger logger = LoggerFactory.getLogger(GceClusterServiceImpl.class);
	
	@Resource
	private IGceClusterDao gceClusterDao;

	public GceClusterServiceImpl() {
		super(GceCluster.class);
	}

	@Override
	public IBaseDao<GceCluster> getDao() {
		return this.gceClusterDao;
	}

	@Override
	public Boolean isExistByName(String clusterName) {
		List<MclusterModel> mclusters = this.gceClusterDao.selectByName(clusterName);
		return mclusters.size() == 0?true:false;
	}

}
