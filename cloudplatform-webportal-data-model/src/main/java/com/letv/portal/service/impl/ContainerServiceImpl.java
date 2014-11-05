package com.letv.portal.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.letv.common.dao.IBaseDao;
import com.letv.common.dao.QueryParam;
import com.letv.common.paging.impl.Page;
import com.letv.portal.dao.IContainerDao;
import com.letv.portal.dao.IDbDao;
import com.letv.portal.model.ContainerModel;
import com.letv.portal.service.IContainerService;

@Service("containerService")
public class ContainerServiceImpl extends BaseServiceImpl<ContainerModel> implements
		IContainerService{
	
	@Resource
	private IContainerDao containerDao;
	@Resource
	private IDbDao dbDao;

	public ContainerServiceImpl() {
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
	public List<ContainerModel> selectByMclusterId(Long mclusterId) {
		return this.containerDao.selectByMclusterId(mclusterId);
	}

	@Override
	public List<ContainerModel> selectNormalByClusterId(Long mclusterId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteByMclusterId(Long mclusterId) {
		this.containerDao.deleteByMclusterId(mclusterId);
		
	}

	@Override
	public void updateHostIpByName(ContainerModel container) {
		this.containerDao.updateHostIpByName(container);
	}
	@Override
	public ContainerModel selectByName(String containerName) {
		return this.containerDao.selectByName(containerName);
	}
	public  List<ContainerModel> selectContainerByMclusterId(Long clusterId){
		return this.containerDao.selectContainerByMclusterId(clusterId);
	}
	public  List<ContainerModel> selectAllByMap(Map map){
		return this.containerDao.selectAllByMap(map);	
	}
}
