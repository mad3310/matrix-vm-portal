package com.letv.portal.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.letv.common.dao.IBaseDao;
import com.letv.common.dao.QueryParam;
import com.letv.common.paging.impl.Page;
import com.letv.portal.dao.IContainerDao;
import com.letv.portal.dao.IHclusterDao;
import com.letv.portal.dao.IHostDao;
import com.letv.portal.model.HclusterModel;
import com.letv.portal.model.HostModel;
import com.letv.portal.service.IHclusterService;
import com.letv.portal.service.IHostService;
import com.letv.portal.service.IMclusterService;

@Service("hclusterService")
public class HClusterServiceImpl extends BaseServiceImpl<HclusterModel> implements
		IHclusterService{
	
	private final static Logger logger = LoggerFactory.getLogger(HClusterServiceImpl.class);
	
	@Resource
	private IHclusterDao hclusterDao;

	public HClusterServiceImpl() {
		super(HclusterModel.class);
	}

	@Override
	public IBaseDao<HclusterModel> getDao() {
		return this.hclusterDao;
	}


	@Override
	public Page findPagebyParams(Map<String, Object> params, Page page) {
		QueryParam param = new QueryParam(params,page);
		page.setData(this.hclusterDao.selectPageByMap(param));
		page.setTotalRecords(this.hclusterDao.selectByMapCount(params));
		return page;
	}
	public List<HclusterModel> selectByHclusterId(Long hclusterId){
		return hclusterDao.selectByHclusterId(hclusterId);
	}

	@Override
	public List<HclusterModel> selectByName(Map<String, String> map) {
		// TODO Auto-generated method stub
		return hclusterDao.selectByName(map);
	}


}
