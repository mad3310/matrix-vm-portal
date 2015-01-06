package com.letv.portal.service.impl;

import java.util.HashMap;
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
import com.letv.portal.dao.IContainerDao;
import com.letv.portal.dao.IDbDao;
import com.letv.portal.dao.IIpResourceDao;
import com.letv.portal.model.DbModel;
import com.letv.portal.service.IDbService;
import com.letv.portal.service.IDbUserService;

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
	@Autowired
	private IDbUserService dbUserService;
	
	@Resource
	private IContainerDao containerDao;
	
	
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
	public Map<String, Object> selectCreateParams(Long id) {
		return this.dbDao.selectCreateParams(id);
	}

	@Override
	public List<DbModel> selectByDbName(String dbName) {
		return this.dbDao.selectByDbName(dbName);
	}

	@Override
	public void deleteByMclusterId(Long mclusterId) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("mclusterId", mclusterId);
		List<DbModel> dbs = this.dbDao.selectByMap(map);
		for (DbModel dbModel : dbs) {
			this.delete(dbModel);
			this.dbUserService.deleteByDbId(dbModel.getId());
		}
	}
	@Override
	public DbModel dbList(Long dbId){
		DbModel db = this.selectById(dbId);
		db.setContainers(this.containerDao.selectByMclusterId(db.getMclusterId()));
		return db;
	}

	@Override
	public List<DbModel> selectDbByMclusterId(Long mclusterId) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("mclusterId", mclusterId);
		List<DbModel> dbs = this.dbDao.selectByMap(map);
		return dbs;
	}
}
