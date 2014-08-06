package com.letv.mms.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.letv.mms.dao.IBaseDao;
import com.letv.mms.dao.StarDao;
import com.letv.mms.model.StarModel;
import com.letv.mms.service.IStarService;

/**
 * 
 * <br>
 * <b>功能：</b>StarService<br>
 * <b>日期：</b>2012-21-03 <br>
 * <b>版权所有：<b>版权所有(C) 2012，<br>
 */
@Service("starService")
public class StarServiceImpl extends BaseServiceImpl<StarModel> implements IStarService {

	@Autowired
	private StarDao dao;

	public StarServiceImpl() {
		super(StarModel.class);
	}
	
	@Override
	public IBaseDao<StarModel> getDao() {
		return dao;
	}
}
