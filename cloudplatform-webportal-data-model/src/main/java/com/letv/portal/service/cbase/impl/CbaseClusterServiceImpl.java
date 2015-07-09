package com.letv.portal.service.cbase.impl;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.letv.common.dao.IBaseDao;
import com.letv.portal.dao.cbase.ICbaseClusterDao;
import com.letv.portal.model.cbase.CbaseClusterModel;
import com.letv.portal.service.cbase.ICbaseClusterService;
import com.letv.portal.service.impl.BaseServiceImpl;

@Service("cbaseClusterService")
public class CbaseClusterServiceImpl extends BaseServiceImpl<CbaseClusterModel>
		implements ICbaseClusterService {

	private final static Logger logger = LoggerFactory
			.getLogger(CbaseClusterServiceImpl.class);

	@Resource
	private ICbaseClusterDao cbaseClusterDao;

	public CbaseClusterServiceImpl() {
		super(CbaseClusterModel.class);
	}

	@Override
	public IBaseDao<CbaseClusterModel> getDao() {
		return this.cbaseClusterDao;
	}

}
