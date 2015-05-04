package com.letv.portal.service.log.impl;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.letv.common.dao.IBaseDao;
import com.letv.portal.dao.log.ILogClusterDao;
import com.letv.portal.model.MclusterModel;
import com.letv.portal.model.log.LogCluster;
import com.letv.portal.service.log.ILogClusterService;
import com.letv.portal.service.impl.BaseServiceImpl;

@Service("logClusterService")
public class LogClusterServiceImpl extends BaseServiceImpl<LogCluster> implements ILogClusterService{
	
	private final static Logger logger = LoggerFactory.getLogger(LogClusterServiceImpl.class);
	
	@Resource
	private ILogClusterDao logClusterDao;

	public LogClusterServiceImpl() {
		super(LogCluster.class);
	}

	@Override
	public IBaseDao<LogCluster> getDao() {
		return this.logClusterDao;
	}

	@Override
	public Boolean isExistByName(String clusterName) {
		List<MclusterModel> mclusters = this.logClusterDao.selectByName(clusterName);
		return mclusters.size() == 0?true:false;
	}

}
