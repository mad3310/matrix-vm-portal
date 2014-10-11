package com.letv.portal.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.letv.common.dao.IBaseDao;
import com.letv.common.dao.QueryParam;
import com.letv.common.email.SimpleTextEmailSender;
import com.letv.common.paging.impl.Page;
import com.letv.common.session.SessionServiceImpl;
import com.letv.portal.dao.IDbDao;
import com.letv.portal.dao.IIpResourceDao;
import com.letv.portal.model.DbModel;
import com.letv.portal.service.IDbService;

@Service("dbService")
public class DbServiceImpl extends BaseServiceImpl<DbModel> implements
		IDbService{
	
	private final static Logger logger = LoggerFactory.getLogger(DbServiceImpl.class);
	
	@Resource
	private IDbDao dbDao;
	@Resource
	private IIpResourceDao ipResourceDao;
	@Resource
	private SimpleTextEmailSender simpleTextEmailSender;

	@Autowired(required=false)
	private SessionServiceImpl sessionService;
	
	
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

	@Override
	public void audit(String dbId,String dbApplyStandardId,String status,String mclusterId,String auditInfo) {
	}

	@Override
	public void buildNotice(String dbId,String buildFlag) {

	}

	@Override
	public Map<String, String> selectCreateParams(Long id) {
		return this.dbDao.selectCreateParams(id);
	}

	@Override
	public List<DbModel> selectByDbName(String dbName) {
		return this.dbDao.selectByDbName(dbName);
	}
}
