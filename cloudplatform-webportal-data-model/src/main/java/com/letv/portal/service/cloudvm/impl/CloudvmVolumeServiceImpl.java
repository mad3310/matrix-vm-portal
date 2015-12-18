package com.letv.portal.service.cloudvm.impl;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.letv.common.dao.IBaseDao;
import com.letv.common.exception.ValidateException;
import com.letv.common.paging.impl.Page;
import com.letv.portal.dao.cloudvm.ICloudvmVolumeDao;
import com.letv.portal.model.cloudvm.CloudvmVolume;
import com.letv.portal.model.cloudvm.CloudvmVolumeStatus;
import com.letv.portal.service.cloudvm.ICloudvmVolumeService;
import com.letv.portal.service.common.impl.BaseServiceImpl;

@Service("cloudvmVolumeService")
public class CloudvmVolumeServiceImpl extends BaseServiceImpl<CloudvmVolume>
        implements ICloudvmVolumeService {

    @SuppressWarnings("unused")
    private final static Logger logger = LoggerFactory
            .getLogger(CloudvmVolumeServiceImpl.class);

    @Resource
    private ICloudvmVolumeDao cloudvmVolumeDao;

    public CloudvmVolumeServiceImpl() {
        super(CloudvmVolume.class);
    }

    @Override
    public IBaseDao<CloudvmVolume> getDao() {
        return cloudvmVolumeDao;
    }

    @Override
    public List<CloudvmVolume> selectByRegion(long tenantId, String region) {
        if (StringUtils.isEmpty(region)) {
            throw new ValidateException("地域不能为空");
        }
        Map<String, Object> paras = new HashMap<String, Object>();
        paras.put("tenantId", tenantId);
        paras.put("region", region);
        return cloudvmVolumeDao.selectByMap(paras);
    }

    @Override
    public CloudvmVolume selectByVolumeId(long tenantId, String region, String volumeId) {
        if (StringUtils.isEmpty(region)) {
            throw new ValidateException("地域不能为空");
        }
        if (StringUtils.isEmpty(volumeId)) {
            throw new ValidateException("云硬盘ID不能为空");
        }
        Map<String, Object> paras = new HashMap<String, Object>();
        paras.put("tenantId", tenantId);
        paras.put("region", region);
        paras.put("volumeId", volumeId);
        List<CloudvmVolume> resultList = selectByMap(paras);
        if (!resultList.isEmpty()) {
            return resultList.get(0);
        }
        return null;
    }

    @Override
    public List<CloudvmVolume> selectForSync(Long minId, Page page) {
        Map<String, Object> paras = new HashMap<String, Object>();
        if (minId != null) {
            paras.put("id", minId);
        }
        if (page != null) {
            paras.put("page", page);
        }
        return cloudvmVolumeDao.selectByMapForSync(paras);
    }

    @Override
    public List<CloudvmVolume> selectByName(long tenantId, String region, String name, Page page) {
        if (StringUtils.isEmpty(region)) {
            throw new ValidateException("地域不能为空");
        }
        Map<String, Object> paras = new HashMap<String, Object>();
        paras.put("tenantId", tenantId);
        paras.put("region", region);
        if (StringUtils.isNotEmpty(name)) {
            paras.put("name", name);
        }
        if (page != null) {
            paras.put("page", page);
        }
        return cloudvmVolumeDao.selectByMap(paras);
    }

    @Override
    public int selectCountByName(long tenantId, String region, String name) {
        if (StringUtils.isEmpty(region)) {
            throw new ValidateException("地域不能为空");
        }
        Map<String, Object> paras = new HashMap<String, Object>();
        paras.put("tenantId", tenantId);
        paras.put("region", region);
        if (StringUtils.isNotEmpty(name)) {
            paras.put("name", name);
        }
        return cloudvmVolumeDao.selectByMapCount(paras);
    }

    @Override
    public List<CloudvmVolume> selectByServerIdAndStatus(long tenantId, String region, String serverId, CloudvmVolumeStatus status) {
        if (StringUtils.isEmpty(region)) {
            throw new ValidateException("地域不能为空");
        }
        if (StringUtils.isEmpty(serverId)) {
            throw new ValidateException("虚拟机ID不能为空");
        }
        Map<String, Object> paras = new HashMap<String, Object>();
        paras.put("tenantId", tenantId);
        paras.put("region", region);
        paras.put("serverId", serverId);
        if (status != null) {
            paras.put("status", status);
        }
        return cloudvmVolumeDao.selectByMap(paras);
    }

    @Override
    public List<CloudvmVolume> selectByRegionsAndVolumeIds(long tenantId, List<String> regions, List<String> volumeIds) {
		if (regions.isEmpty() || volumeIds.isEmpty()) {
			return new LinkedList<CloudvmVolume>();
		}
        Map<String, Object> paras = new HashMap<String, Object>();
        paras.put("tenantId", tenantId);
        paras.put("regions", regions);
        paras.put("volumeIds", volumeIds);
        return cloudvmVolumeDao.selectByMap(paras);
    }

    @Override
    public long selectCountSizeByRegion(long tenantId, String region) {
        if (StringUtils.isEmpty(region)) {
            throw new ValidateException("地域不能为空");
        }
        Map<String, Object> paras = new HashMap<String, Object>();
        paras.put("tenantId", tenantId);
        paras.put("region", region);
        Integer countSize = cloudvmVolumeDao.selectByMapCountSize(paras);
        if (countSize != null) {
            return countSize;
        } else {
            return 0;
        }
    }

}
