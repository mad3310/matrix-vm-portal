package com.letv.portal.service.slb.impl;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.letv.common.dao.IBaseDao;
import com.letv.common.exception.ValidateException;
import com.letv.portal.dao.slb.ISlbServerDao;
import com.letv.portal.enumeration.SlbStatus;
import com.letv.portal.model.slb.SlbServer;
import com.letv.portal.service.impl.BaseServiceImpl;
import com.letv.portal.service.slb.ISlbServerService;

@Service("slbServerService")
public class SlbServerServiceImpl extends BaseServiceImpl<SlbServer> implements ISlbServerService{
	
	private final static Logger logger = LoggerFactory.getLogger(SlbServerServiceImpl.class);
	
	@Resource
	private ISlbServerDao slbServerDao;

	public SlbServerServiceImpl() {
		super(SlbServer.class);
	}

	@Override
	public IBaseDao<SlbServer> getDao() {
		return this.slbServerDao;
	}
	
	@Override
	public void insert(SlbServer t) {
		if(t == null)
			throw new ValidateException("参数不合法");
		t.setStatus(SlbStatus.NORMAL.getValue());
		super.insert(t);
	}

}
