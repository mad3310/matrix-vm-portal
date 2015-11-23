package com.letv.portal.service.cloudvm.impl;

import com.letv.common.dao.IBaseDao;
import com.letv.portal.dao.cloudvm.ICloudvmServerMetadataDao;
import com.letv.portal.model.cloudvm.CloudvmServerMetadata;
import com.letv.portal.service.cloudvm.ICloudvmServerMetadataService;
import com.letv.portal.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhouxianguang on 2015/9/28.
 */
@Service
public class CloudvmServerMetadataServiceImpl extends BaseServiceImpl<CloudvmServerMetadata> implements ICloudvmServerMetadataService {

    @Resource
    private ICloudvmServerMetadataDao cloudvmServerMetadataDao;

    public CloudvmServerMetadataServiceImpl() {
        super(CloudvmServerMetadata.class);
    }

    @Override
    public IBaseDao<CloudvmServerMetadata> getDao() {
        return cloudvmServerMetadataDao;
    }

    @Override
    public List<CloudvmServerMetadata> selectByRegionAndServerId(String region, String serverId) {
        Map<String, Object> paras = new HashMap<String, Object>();
        paras.put("region", region);
        paras.put("serverId", serverId);
        return cloudvmServerMetadataDao.selectByMap(paras);
    }

    @Override
    public void deleteByRegionAndServerId(String region, String serverId) {
        Map<String, Object> paras = new HashMap<String, Object>();
        paras.put("region", region);
        paras.put("serverId", serverId);
        cloudvmServerMetadataDao.deleteByRegionAndServerId(paras);
    }
}
