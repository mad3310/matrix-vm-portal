package com.letv.portal.service.subscription.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.letv.common.dao.IBaseDao;
import com.letv.portal.dao.subscription.ISubscriptionDetailDao;
import com.letv.portal.model.subscription.SubscriptionDetail;
import com.letv.portal.service.impl.BaseServiceImpl;
import com.letv.portal.service.subscription.ISubscriptionDetailService;

@Service("subscriptionDetailService")
public class SubscriptionDetailServiceImpl extends BaseServiceImpl<SubscriptionDetail> implements ISubscriptionDetailService {
	
	private final static Logger logger = LoggerFactory.getLogger(SubscriptionDetailServiceImpl.class);
	
	public SubscriptionDetailServiceImpl() {
		super(SubscriptionDetail.class);
	}

	@Autowired
	private ISubscriptionDetailDao subscriptionDetailDao;

	@Override
	public IBaseDao<SubscriptionDetail> getDao() {
		return this.subscriptionDetailDao;
	}

	@Override
	public List<SubscriptionDetail> selectByMapAndTime(Long subscriptionId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("subscriptionId", subscriptionId);
		params.put("valid", 1);
		params.put("date", new Date());
		return this.subscriptionDetailDao.selectByMapAndTime(params);
	}


}
