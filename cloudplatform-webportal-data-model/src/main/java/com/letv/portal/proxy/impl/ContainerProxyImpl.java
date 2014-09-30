package com.letv.portal.proxy.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.letv.common.dao.QueryParam;
import com.letv.common.paging.impl.Page;
import com.letv.portal.dao.IBaseDao;
import com.letv.portal.dao.IContainerDao;
import com.letv.portal.dao.IDbDao;
import com.letv.portal.model.ContainerModel;
import com.letv.portal.proxy.IContainerProxy;

@Component
public class ContainerProxyImpl extends BaseProxyImpl<ContainerModel> implements
		IContainerProxy{
	
	@Resource
	private IContainerDao containerDao;
	@Resource
	private IDbDao dbDao;

	public ContainerProxyImpl() {
		super(ContainerModel.class);
	}

	@Override
	public IBaseDao<ContainerModel> getDao() {
		return this.containerDao;
	}

	@Override
	public Page findPagebyParams(Map<String, Object> params, Page page) {
		QueryParam param = new QueryParam(params,page);
		page.setData(this.containerDao.selectPageByMap(param));
		page.setTotalRecords(this.containerDao.selectByMapCount(params));
		return page;
		
	}
	
	@Override
	public void insert(ContainerModel t) {
		
//		if(StringUtils.isNullOrEmpty(toTest))
		
		super.insert(t);
	}

	@Override
	public List<ContainerModel> selectByClusterId(String clusterId) {
		return this.containerDao.selectByClusterId(clusterId);
	}

	@Override
	public List<ContainerModel> selectNormalByClusterId(String clusterId) {
		// TODO Auto-generated method stub
		return null;
	}
}
