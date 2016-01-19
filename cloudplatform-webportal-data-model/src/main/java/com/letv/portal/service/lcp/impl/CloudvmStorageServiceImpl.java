package com.letv.portal.service.lcp.impl;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.letv.common.dao.IBaseDao;
import com.letv.portal.dao.lcp.ICloudvmStorageDao;
import com.letv.portal.model.cloudvm.lcp.CloudvmStorageModel;
import com.letv.portal.service.common.impl.BaseServiceImpl;
import com.letv.portal.service.lcp.ICloudvmStorageService;

@Service("cloudvmStorageService")
public class CloudvmStorageServiceImpl extends BaseServiceImpl<CloudvmStorageModel>
        implements ICloudvmStorageService {

    @SuppressWarnings("unused")
    private final static Logger logger = LoggerFactory.getLogger(CloudvmStorageServiceImpl.class);

    @Resource
    private ICloudvmStorageDao cloudvmStorageDao;

    public CloudvmStorageServiceImpl() {
        super(CloudvmStorageModel.class);
    }

    @Override
    public IBaseDao<CloudvmStorageModel> getDao() {
        return cloudvmStorageDao;
    }

}
