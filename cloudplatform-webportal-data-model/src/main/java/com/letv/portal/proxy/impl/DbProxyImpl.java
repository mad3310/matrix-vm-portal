package com.letv.portal.proxy.impl;

import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.letv.portal.constant.Constant;
import com.letv.portal.model.DbModel;
import com.letv.portal.model.MclusterModel;
import com.letv.portal.proxy.IDbProxy;
import com.letv.portal.proxy.IMclusterProxy;
import com.letv.portal.python.service.IBuildTaskService;
import com.letv.portal.service.IBaseService;
import com.letv.portal.service.IDbService;

@Component
public class DbProxyImpl extends BaseProxyImpl<DbModel> implements
		IDbProxy{
	
	private final static Logger logger = LoggerFactory.getLogger(DbProxyImpl.class);
	
	@Autowired
	private IDbService dbService;
	@Autowired
	private IMclusterProxy mclusterProxy;
	@Autowired
	private IBuildTaskService buildTaskService;
	
	@Override
	public IBaseService<DbModel> getService() {
		return dbService;
	}

	@Override
	public void auditAndBuild(Map<String, Object> params) {
		//判断审批类型 如果为不同过，保存审批结果，不通过。
		//判断mclusterId是否为空，如果为空，新建mcluster
		//保存审批结果
		//执行创建mcluster操作
		//执行创建db操作。
		
		if(Constant.STATUS_AUDIT_FAIL == (Integer)params.get("status")) {
			
		} else {
			Boolean isNewMcluster = params.get("mclusterId") == null;
			MclusterModel mcluster = new MclusterModel();
			DbModel dbModel = new DbModel();
			try {
				BeanUtils.populate(mcluster, params);
				BeanUtils.populate(dbModel, params);
			} catch (Exception e) {
			}
			if(isNewMcluster) {
				this.mclusterProxy.insert(mcluster);
			}
			
			dbModel.setMclusterId(mcluster.getId());
			dbModel.setId((Long) params.get("dbId"));
			this.updateBySelective(dbModel);
			
			if(isNewMcluster) {
				this.buildTaskService.buildMcluster(mcluster,dbModel.getId());
			} else {
				this.buildTaskService.buildDb(dbModel.getId());
			}
			
		}
		
	}
	
}
