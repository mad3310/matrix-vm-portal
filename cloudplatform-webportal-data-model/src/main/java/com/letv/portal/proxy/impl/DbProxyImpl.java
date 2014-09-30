package com.letv.portal.proxy.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.letv.common.dao.QueryParam;
import com.letv.common.email.SimpleTextEmailSender;
import com.letv.common.paging.impl.Page;
import com.letv.common.session.SessionServiceImpl;
import com.letv.portal.dao.IBaseDao;
import com.letv.portal.dao.IContainerDao;
import com.letv.portal.dao.IDbApplyStandardDao;
import com.letv.portal.dao.IDbDao;
import com.letv.portal.dao.IIpResourceDao;
import com.letv.portal.model.DbApplyStandardModel;
import com.letv.portal.model.DbModel;
import com.letv.portal.proxy.IDbProxy;
import com.letv.portal.service.IMclusterService;

@Component
public class DbProxyImpl extends BaseProxyImpl<DbModel> implements
		IDbProxy{
	
	private final static Logger logger = LoggerFactory.getLogger(DbProxyImpl.class);
	
	@Resource
	private IDbDao dbDao;
	@Resource
	private IDbApplyStandardDao dbApplyStandardDao;
	
	@Resource
	private IContainerDao containerDao;
	
	@Resource
	private IMclusterService mclusterService;
	
	@Resource
	private IIpResourceDao ipResourceDao;
	@Resource
	private SimpleTextEmailSender simpleTextEmailSender;

	@Autowired(required=false)
	private SessionServiceImpl sessionService;
	public DbProxyImpl() {
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
		this.dbDao.audit(new DbModel(status,mclusterId,auditInfo));
		this.dbApplyStandardDao.audit(new DbApplyStandardModel(status,auditInfo));
	}

	@Override
	public void buildNotice(String dbId,String buildFlag) {
		/*buildFlag = "success".equals(buildFlag)?Constant.DB_AUDIT_STATUS_BUILD_SUCCESS:Constant.DB_AUDIT_STATUS_BUILD_FAIL;
		DbModel dbModel = this.selectById(dbId);
		if(Constant.DB_AUDIT_STATUS_TRUE_BUILD_NEW_MCLUSTER.equals(dbModel.getStatus())) {
			this.mclusterService.buildNotice(dbModel.getClusterId(),Constant.DB_AUDIT_STATUS_BUILD_SUCCESS);
		}
		this.dbDao.audit(new DbModel(dbId,Constant.));*/
	}

	@Override
	public Map<String, String> selectCreateParams(String id) {
		return this.dbDao.selectCreateParams(id);
	}

	@Override
	public List<DbModel> selectByDbName(String dbName) {
		return this.dbDao.selectByDbName(dbName);
	}

	/**
	 * Methods Name: updateByMap <br>
	 * Description: 修改记录通过map
	 * @author name: wujun
	 * @param hashMap
	 * @return
	 */
	public void updateByMap(HashMap hashMap){
		 this.dbDao.updateByMap(hashMap);
	}
}
