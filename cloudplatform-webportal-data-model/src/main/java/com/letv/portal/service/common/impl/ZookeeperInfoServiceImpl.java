package com.letv.portal.service.common.impl;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.letv.common.dao.IBaseDao;
import com.letv.portal.dao.common.IZookeeperInfoDao;
import com.letv.portal.model.common.ZookeeperInfo;
import com.letv.portal.service.common.IZookeeperInfoService;
import com.letv.portal.service.impl.BaseServiceImpl;

@Service("zookeeperInfoService")
public class ZookeeperInfoServiceImpl extends BaseServiceImpl<ZookeeperInfo> implements IZookeeperInfoService{
	
	private final static Logger logger = LoggerFactory.getLogger(ZookeeperInfoServiceImpl.class);
	
	@Resource
	private IZookeeperInfoDao zookeeperInfoDao;

	public ZookeeperInfoServiceImpl() {
		super(ZookeeperInfo.class);
	}

	@Override
	public IBaseDao<ZookeeperInfo> getDao() {
		return this.zookeeperInfoDao;
	}
}
