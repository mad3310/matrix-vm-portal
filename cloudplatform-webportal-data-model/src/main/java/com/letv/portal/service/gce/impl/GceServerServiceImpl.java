package com.letv.portal.service.gce.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.letv.common.dao.IBaseDao;
import com.letv.common.exception.ValidateException;
import com.letv.common.paging.impl.Page;
import com.letv.portal.dao.gce.IGceServerDao;
import com.letv.portal.dao.slb.ISlbServerDao;
import com.letv.portal.enumeration.GceStatus;
import com.letv.portal.enumeration.SlbStatus;
import com.letv.portal.model.DbUserModel;
import com.letv.portal.model.MclusterModel;
import com.letv.portal.model.gce.GceServer;
import com.letv.portal.model.slb.SlbConfig;
import com.letv.portal.model.slb.SlbServer;
import com.letv.portal.service.gce.IGceServerService;
import com.letv.portal.service.impl.BaseServiceImpl;
import com.letv.portal.service.slb.ISlbConfigService;
import com.letv.portal.service.slb.ISlbServerService;

@Service("gceServerService")
public class GceServerServiceImpl extends BaseServiceImpl<GceServer> implements IGceServerService{
	
	private final static Logger logger = LoggerFactory.getLogger(GceServerServiceImpl.class);
	
	@Resource
	private IGceServerDao gceServerDao;

	public GceServerServiceImpl() {
		super(GceServer.class);
	}

	@Override
	public IBaseDao<GceServer> getDao() {
		return this.gceServerDao;
	}
	
	@Override
	public void insert(GceServer t) {
		if(t == null)
			throw new ValidateException("参数不合法");
		t.setStatus(GceStatus.NORMAL.getValue());
		super.insert(t);
	}
	
	@Override
	public <K, V> Page selectPageByParams(Page page, Map<K, V> params) {
		page = super.selectPageByParams(page, params);
		List<GceServer> data = (List<GceServer>) page.getData();
		
		page.setData(data);
		return page;
	}

}
