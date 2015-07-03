package com.letv.portal.service.cloudvm.impl;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.letv.common.dao.IBaseDao;
import com.letv.common.exception.ValidateException;
import com.letv.common.session.SessionServiceImpl;
import com.letv.portal.dao.cloudvm.ICloudvmRegionDao;
import com.letv.portal.model.cloudvm.CloudvmRegion;
import com.letv.portal.service.cloudvm.ICloudvmRegionService;
import com.letv.portal.service.impl.BaseServiceImpl;

@Service("cloudvmRegionService")
public class CloudvmRegionServiceImpl extends BaseServiceImpl<CloudvmRegion>
		implements ICloudvmRegionService {

	@SuppressWarnings("unused")
	private final static Logger logger = LoggerFactory
			.getLogger(CloudvmRegionServiceImpl.class);

	@Resource
	private ICloudvmRegionDao cloudvmRegionDao;

	@Autowired
	private SessionServiceImpl sessionService;

	public CloudvmRegionServiceImpl() {
		super(CloudvmRegion.class);
	}

	@Override
	public IBaseDao<CloudvmRegion> getDao() {
		return cloudvmRegionDao;
	}

	@Override
	public CloudvmRegion selectByCode(String code) {
		if (StringUtils.isBlank(code)) {
			throw new ValidateException("参数不合法");
		}
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("code", code);
		List<CloudvmRegion> resultList = this.cloudvmRegionDao
				.selectByCode(params);
		if (resultList.isEmpty()) {
			return null;
		} else {
			return resultList.get(0);
		}
	}

	@Override
	public void add(String code, String displayName) {
		if (StringUtils.isBlank(code) || StringUtils.isBlank(displayName)) {
			throw new ValidateException("参数不合法");
		}
		if (code.split("-").length != 3 || displayName.split("-").length != 3) {
			throw new ValidateException("参数不合法");
		}
		if (selectByCode(code) != null) {
			throw new ValidateException("地域编码不能重复");
		}
		CloudvmRegion region = new CloudvmRegion(code, displayName);
		// long userId = sessionService.getSession().getUserId();
		// region.setCreateUser(userId);
		// region.setUpdateUser(userId);
		insert(region);
	}

	@Override
	public void edit(Long id, String code, String displayName) {
		if (StringUtils.isBlank(code) || StringUtils.isBlank(displayName)) {
			throw new ValidateException("参数不合法");
		}
		if (code.split("-").length != 3 || displayName.split("-").length != 3) {
			throw new ValidateException("参数不合法");
		}
		CloudvmRegion region = this.selectById(id);
		if (region == null) {
			throw new ValidateException("地域不存在");
		}
		if (!code.equals(region.getCode())) {
			if (selectByCode(code) != null) {
				throw new ValidateException("地域编码不能重复");
			}
			region.setCode(code);
		}
		region.setDisplayName(displayName);
		// long userId = sessionService.getSession().getUserId();
		// region.setCreateUser(userId);
		// region.setUpdateUser(userId);
		this.update(region);
	}

	@Override
	public void remove(Long id) {
		CloudvmRegion region = this.selectById(id);
		if (region == null) {
			throw new ValidateException("地域不存在");
		}
		delete(region);
	}

	@Override
	public List<CloudvmRegion> selectAll() {
		List<CloudvmRegion> regions = cloudvmRegionDao
				.selectByMap(new HashMap<String, Object>());
		return regions;
	}
}
