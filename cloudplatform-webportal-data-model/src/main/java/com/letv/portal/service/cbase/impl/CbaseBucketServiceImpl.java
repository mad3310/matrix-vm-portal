package com.letv.portal.service.cbase.impl;

import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.letv.common.dao.IBaseDao;
import com.letv.common.dao.QueryParam;
import com.letv.common.paging.impl.Page;
import com.letv.portal.dao.cbase.ICbaseBucketDao;
import com.letv.portal.model.cbase.CbaseBucketModel;
import com.letv.portal.service.cbase.ICbaseBucketService;
import com.letv.portal.service.cbase.ICbaseClusterService;
import com.letv.portal.service.cbase.ICbaseContainerService;
import com.letv.portal.service.impl.BaseServiceImpl;

@Service("cbaseBucketService")
public class CbaseBucketServiceImpl extends BaseServiceImpl<CbaseBucketModel>
		implements ICbaseBucketService {

	private final static Logger logger = LoggerFactory
			.getLogger(CbaseBucketServiceImpl.class);

	@Resource
	private ICbaseBucketDao cbaseBucketDao;
	@Autowired
	private ICbaseClusterService cbaseClusterService;
	@Autowired
	private ICbaseContainerService cbaseContainerService;

	public CbaseBucketServiceImpl() {
		super(CbaseBucketModel.class);
	}

	@Override
	public IBaseDao<CbaseBucketModel> getDao() {
		return this.cbaseBucketDao;
	}


	@Override
	public Map<String, Object> save(CbaseBucketModel cbaseBucket) {
		return null;

	}

	@Override
	public void dbList(Long cbaseId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Page findPagebyParams(Map<String, Object> params, Page page) {
		
		QueryParam param = new QueryParam(params, page);
		page.setData(this.cbaseBucketDao.selectPageByMap(param));
		page.setTotalRecords(this.cbaseBucketDao.selectByMapCount(params));
		
		
		return page;

	}
}
