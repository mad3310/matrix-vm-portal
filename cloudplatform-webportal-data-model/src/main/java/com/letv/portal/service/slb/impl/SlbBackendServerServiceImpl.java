package com.letv.portal.service.slb.impl;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.letv.common.dao.IBaseDao;
import com.letv.portal.dao.slb.ISlbBackendServerDao;
import com.letv.portal.model.slb.SlbBackendServer;
import com.letv.portal.service.impl.BaseServiceImpl;
import com.letv.portal.service.slb.ISlbBackendServerService;

@Service("slbBackendServerService")
public class SlbBackendServerServiceImpl extends BaseServiceImpl<SlbBackendServer> implements ISlbBackendServerService{
	
	private final static Logger logger = LoggerFactory.getLogger(SlbBackendServerServiceImpl.class);
	
	@Resource
	private ISlbBackendServerDao slbBackendServerDao;

	public SlbBackendServerServiceImpl() {
		super(SlbBackendServer.class);
	}

	@Override
	public IBaseDao<SlbBackendServer> getDao() {
		return this.slbBackendServerDao;
	}

	@Override
	public List<SlbBackendServer> selectBySlbServerId(Long slbServerId) {
		return this.slbBackendServerDao.selectBySlbServerId(slbServerId);
	}

}
