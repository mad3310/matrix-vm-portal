package com.letv.portal.service.gce.impl;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.letv.common.dao.IBaseDao;
import com.letv.portal.dao.gce.IGceImageDao;
import com.letv.portal.model.gce.GceImage;
import com.letv.portal.service.gce.IGceImageService;
import com.letv.portal.service.impl.BaseServiceImpl;

@Service("gceServerService")
public class GceImageServiceImpl extends BaseServiceImpl<GceImage> implements IGceImageService{
	
	private final static Logger logger = LoggerFactory.getLogger(GceImageServiceImpl.class);
	
	@Resource
	private IGceImageDao gceImageDao;

	public GceImageServiceImpl() {
		super(GceImage.class);
	}

	@Override
	public IBaseDao<GceImage> getDao() {
		return this.gceImageDao;
	}
}
