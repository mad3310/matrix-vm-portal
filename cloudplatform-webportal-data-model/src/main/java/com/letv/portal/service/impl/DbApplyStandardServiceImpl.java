package com.letv.portal.service.impl;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.letv.common.dao.QueryParam;
import com.letv.common.paging.impl.Page;
import com.letv.portal.dao.IBaseDao;
import com.letv.portal.dao.IDbApplyStandardDao;
import com.letv.portal.model.DbApplyStandardModel;
import com.letv.portal.service.IDbApplyStandardService;

@Service("dbApplyStandardService")
public class DbApplyStandardServiceImpl extends BaseServiceImpl<DbApplyStandardModel> implements
		IDbApplyStandardService{
	
	@Resource
	private IDbApplyStandardDao dbApplyStandardDao;

	public DbApplyStandardServiceImpl() {
		super(DbApplyStandardModel.class);
	}

	@Override
	public IBaseDao<DbApplyStandardModel> getDao() {
		return this.dbApplyStandardDao;
	}

	@Override
	public Page findPagebyParams(Map<String, Object> params, Page page) {
		QueryParam param = new QueryParam(params,page);
		page.setData(this.dbApplyStandardDao.selectPageByMap(param));
		page.setTotalRecords(this.dbApplyStandardDao.selectByMapCount(params));
		return page;
		
	}
	
}
