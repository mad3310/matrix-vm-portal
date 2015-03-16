package com.letv.portal.service.slb.impl;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.letv.common.dao.IBaseDao;
import com.letv.common.exception.ValidateException;
import com.letv.portal.dao.slb.ISlbConfigDao;
import com.letv.portal.enumeration.SlbStatus;
import com.letv.portal.model.slb.SlbConfig;
import com.letv.portal.model.slb.SlbServer;
import com.letv.portal.service.impl.BaseServiceImpl;
import com.letv.portal.service.slb.ISlbConfigService;

@Service("slbConfigService")
public class SlbConfigServiceImpl extends BaseServiceImpl<SlbConfig> implements ISlbConfigService{
	
	private final static Logger logger = LoggerFactory.getLogger(SlbConfigServiceImpl.class);
	
	@Resource
	private ISlbConfigDao slbConfigDao;

	public SlbConfigServiceImpl() {
		super(SlbConfig.class);
	}

	@Override
	public IBaseDao<SlbConfig> getDao() {
		return this.slbConfigDao;
	}

	@Override
	public List<SlbConfig> selectBySlbServerId(Long id) {
		return this.slbConfigDao.selectBySlbServerId(id);
	}

}
