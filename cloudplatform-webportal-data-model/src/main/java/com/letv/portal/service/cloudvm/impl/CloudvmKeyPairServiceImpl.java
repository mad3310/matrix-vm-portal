package com.letv.portal.service.cloudvm.impl;

import com.letv.common.dao.IBaseDao;
import com.letv.common.exception.ValidateException;
import com.letv.common.paging.impl.Page;
import com.letv.portal.dao.cloudvm.ICloudvmKeyPairDao;
import com.letv.portal.dao.cloudvm.ICloudvmVolumeDao;
import com.letv.portal.model.cloudvm.CloudvmKeyPair;
import com.letv.portal.model.cloudvm.CloudvmVolume;
import com.letv.portal.model.cloudvm.CloudvmVolumeStatus;
import com.letv.portal.service.cloudvm.ICloudvmKeyPairService;
import com.letv.portal.service.cloudvm.ICloudvmVolumeService;
import com.letv.portal.service.impl.BaseServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("cloudvmKeyPairService")
public class CloudvmKeyPairServiceImpl extends BaseServiceImpl<CloudvmKeyPair>
        implements ICloudvmKeyPairService {

    @SuppressWarnings("unused")
    private final static Logger logger = LoggerFactory
            .getLogger(CloudvmKeyPairServiceImpl.class);

    @Resource
    private ICloudvmKeyPairDao cloudvmKeyPairDao;

    public CloudvmKeyPairServiceImpl() {
        super(CloudvmKeyPair.class);
    }

    @Override
    public IBaseDao<CloudvmKeyPair> getDao() {
        return cloudvmKeyPairDao;
    }

    @Override
    public CloudvmKeyPair getByName(long tenantId, String region, String name) {
        if (StringUtils.isEmpty(region)) {
            throw new ValidateException("地域不能为空");
        }
        if (StringUtils.isEmpty(name)) {
            throw new ValidateException("密钥名不能为空");
        }
        Map<String, Object> paras = new HashMap<String, Object>();
        paras.put("tenantId", tenantId);
        paras.put("region", region);
        paras.put("name", name);
        List<CloudvmKeyPair> resultList = selectByMap(paras);
        if (!resultList.isEmpty()) {
            return resultList.get(0);
        }
        return null;
    }

    @Override
    public List<CloudvmKeyPair> selectByName(long tenantId, String region, String name, Page page) {
        if (StringUtils.isEmpty(region)) {
            throw new ValidateException("地域不能为空");
        }
        Map<String, Object> paras = new HashMap<String, Object>();
        paras.put("tenantId", tenantId);
        paras.put("region", region);
        if (StringUtils.isNotEmpty(name)) {
            paras.put("likeName", name);
        }
        if (page != null) {
            paras.put("page", page);
        }
        return selectByMap(paras);
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
            paras.put("likeName", name);
        }
        return selectByMapCount(paras);
    }

}
