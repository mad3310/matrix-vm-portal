package com.letv.portal.service.cbase.impl;

import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.letv.common.dao.IBaseDao;
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
	public void insert(CbaseBucketModel cbaseBucket) {

	}

	@Override
	public Map<String, Object> save(CbaseBucketModel cbaseBucket) {
		return null;

	}

	public <K, V> Page selectPageByParams(Page page, Map<K, V> params) {
		return page;
	}

	public CbaseBucketModel selectById(Long id) {
		return null;
	}

	@Override
	public void dbList(Long cbaseId) {
		// TODO Auto-generated method stub
		
	}
}
