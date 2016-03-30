package com.letv.portal.service.cloudvm.impl;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.letv.common.dao.IBaseDao;
import com.letv.common.session.SessionServiceImpl;
import com.letv.portal.dao.cloudvm.ICloudvmClusterDao;
import com.letv.portal.model.cloudvm.CloudvmCluster;
import com.letv.portal.service.cloudvm.ICloudvmClusterService;
import com.letv.portal.service.common.impl.BaseServiceImpl;

@Service("cloudvmClusterService")
public class CloudvmClusterServiceImpl extends BaseServiceImpl<CloudvmCluster>
		implements ICloudvmClusterService {

	@SuppressWarnings("unused")
	private final static Logger logger = LoggerFactory.getLogger(CloudvmClusterServiceImpl.class);

	@Resource
	private ICloudvmClusterDao cloudvmClusterDao;

	@Autowired
	private SessionServiceImpl sessionService;

	public CloudvmClusterServiceImpl() {
		super(CloudvmCluster.class);
	}

	@Override
	public IBaseDao<CloudvmCluster> getDao() {
		return cloudvmClusterDao;
	}

	@Override
	public List<CloudvmCluster> selectByRegionId(Long regionId) {
		return this.cloudvmClusterDao.selectByRegionId(regionId);
	}

}
