package com.letv.portal.service.cbase.impl;

import java.util.HashMap;
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
import com.letv.portal.enumeration.CbaseBucketStatus;
import com.letv.portal.model.cbase.CbaseBucketModel;
import com.letv.portal.model.cbase.CbaseClusterModel;
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
		cbaseBucket.setStatus(CbaseBucketStatus.BUILDDING.getValue());

		StringBuffer cbaseClusterName = new StringBuffer();
		cbaseClusterName.append(cbaseBucket.getCreateUser()).append("_")
				.append(cbaseBucket.getBucketName());

		/* function 验证cbaseClusterName是否存在 */

		CbaseClusterModel cbaseCluster = new CbaseClusterModel();
		cbaseCluster.setHclusterId(cbaseBucket.getHclusterId());
		cbaseCluster.setCbaseClusterName(cbaseClusterName.toString());
		cbaseCluster.setStatus(CbaseBucketStatus.BUILDDING.getValue());
		cbaseCluster.setCreateUser(cbaseBucket.getCreateUser());
		cbaseCluster.setAdminUser("root");
		cbaseCluster.setAdminPassword(cbaseClusterName.toString());

		this.cbaseClusterService.insert(cbaseCluster);

		cbaseBucket.setCbaseClusterId(cbaseCluster.getId());

		this.cbaseBucketDao.insert(cbaseBucket);

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("cbaseClusterId", cbaseCluster.getId());
		params.put("cacheId", cbaseBucket.getId());
		params.put("serviceName", cbaseBucket.getBucketName());
		params.put("clusterName", cbaseCluster.getCbaseClusterName());
		return params;
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
