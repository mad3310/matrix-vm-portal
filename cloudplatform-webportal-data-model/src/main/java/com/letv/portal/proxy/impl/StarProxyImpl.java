package com.letv.portal.proxy.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.letv.portal.dao.IBaseDao;
import com.letv.portal.dao.StarDao;
import com.letv.portal.model.StarModel;
import com.letv.portal.proxy.IStarProxy;

/**
 * 
 * <br>
 * <b>功能：</b>StarService<br>
 * <b>日期：</b>2012-21-03 <br>
 * <b>版权所有：<b>版权所有(C) 2012，<br>
 */
@Component
public class StarProxyImpl extends BaseProxyImpl<StarModel> implements IStarProxy {

	@Autowired
	private StarDao dao;

	public StarProxyImpl() {
		super(StarModel.class);
	}
	
	@Override
	public IBaseDao<StarModel> getDao() {
		return dao;
	}
}
