package com.letv.portal.service.subscription.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.letv.common.dao.IBaseDao;
import com.letv.common.session.SessionServiceImpl;
import com.letv.portal.dao.subscription.ISubscriptionDao;
import com.letv.portal.dao.subscription.ISubscriptionDetailDao;
import com.letv.portal.model.subscription.Subscription;
import com.letv.portal.model.subscription.SubscriptionDetail;
import com.letv.portal.service.impl.BaseServiceImpl;
import com.letv.portal.service.product.IProductService;
import com.letv.portal.service.subscription.ISubscriptionService;

@Service("subscriptionService")
public class SubscriptionServiceImpl extends BaseServiceImpl<Subscription> implements ISubscriptionService {
	
	@Autowired
	private IProductService productService;
	
	private final static Logger logger = LoggerFactory.getLogger(SubscriptionServiceImpl.class);
	
	public SubscriptionServiceImpl() {
		super(Subscription.class);
	}

	@Autowired
	private ISubscriptionDao subscriptionDao;
	@Autowired
	private ISubscriptionDetailDao subscriptionDetailDao;
	@Autowired(required=false)
	private SessionServiceImpl sessionService;

	@Override
	public IBaseDao<Subscription> getDao() {
		return this.subscriptionDao;
	}


	@Override
	public Boolean createSubscription(Long id, Map<String, Object> map) {
		if(this.productService.validateData(id, map)) {
			Subscription sub = new Subscription();
			sub.setProductId(id);
			sub.setBaseRegionId(Long.parseLong((String)map.get("region")));
			sub.setHclusterId(Long.parseLong((String)map.get("area")));
			sub.setChargeType(map.get("chargeType")==null?null:Integer.parseInt((String)map.get("chargeType")));
			sub.setOrderNum(Integer.parseInt((String)map.get("order_num")));
			Integer t = Integer.parseInt((String)map.get("order_time"));
			sub.setOrderTime(t);
			Date d = new Date();
			sub.setStartTime(d);
			Calendar cal = Calendar.getInstance();
			cal.setTimeInMillis(d.getTime());
			cal.add(Calendar.MONTH, t);
			sub.setEndTime(cal.getTime());
			sub.setValid(true);
			sub.setUserId(sessionService.getSession().getUserId());
			sub.setCreateUser(sessionService.getSession().getUserId());
			sub.setDeleted(false);
			this.subscriptionDao.insert(sub);
			for (String key : map.keySet()) {
				if("region".equals(key) || "area".equals(key) || "chargeType".equals(key) 
						|| "order_num".equals(key) || "order_time".equals(key)) {
					continue;
				}
				SubscriptionDetail detail = new SubscriptionDetail();
				detail.setSubscriptionId(sub.getId());
				detail.setStandardName(key);
				detail.setStandardValue((String)map.get(key));
				detail.setOrderTime(t);
				detail.setStartTime(d);
				detail.setEndTime(cal.getTime());
				detail.setUserId(sessionService.getSession().getUserId());
				detail.setCreateUser(sessionService.getSession().getUserId());
				detail.setDeleted(false);
				detail.setValid(true);
				this.subscriptionDetailDao.insert(detail);
			}
		}
		
		return true;
	}


}
