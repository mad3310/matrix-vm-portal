package com.letv.portal.service.impl;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.letv.common.dao.QueryParam;
import com.letv.common.paging.impl.Page;
import com.letv.portal.dao.IBaseDao;
import com.letv.portal.dao.IDbDao;
import com.letv.portal.model.DbModel;
import com.letv.portal.service.IDbService;

@Service("dbService")
public class DbServiceImpl extends BaseServiceImpl<DbModel> implements
		IDbService{
	
	@Resource
	private IDbDao dbDao;

	public DbServiceImpl() {
		super(DbModel.class);
	}

	@Override
	public IBaseDao<DbModel> getDao() {
		return this.dbDao;
	}

	@Override
	public Page findPagebyParams(Map<String, Object> params, Page page) {
		QueryParam param = new QueryParam(params,page);
		page.setData(this.dbDao.selectPageByMap(param));
		page.setTotalRecords(this.dbDao.selectByMapCount(params));
		return page;
		
	}
	
}
