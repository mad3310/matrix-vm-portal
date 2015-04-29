package com.letv.portal.service.cbase.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.letv.common.dao.IBaseDao;
import com.letv.common.exception.ValidateException;
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
		cbaseBucket.setAuthType("sasl");

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
	public List<CbaseBucketModel> selectByBucketNameForValidate(
			String bucketName, Long createUser) {
		if (StringUtils.isEmpty(bucketName) || null == createUser)
			throw new ValidateException("参数不合法");
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("bucketName", bucketName);
		params.put("createUser", createUser);
		return this.cbaseBucketDao.selectByBucketNameForValidate(params);
	}
}
