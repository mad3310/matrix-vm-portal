package com.letv.portal.service.lcp.impl;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.letv.common.dao.IBaseDao;
import com.letv.portal.dao.lcp.ICloudvmServerOperatorDao;
import com.letv.portal.model.cloudvm.lcp.CloudvmServerOperatorModel;
import com.letv.portal.service.common.impl.BaseServiceImpl;
import com.letv.portal.service.lcp.ICloudvmServerOperatorService;

@Service("cloudvmServerOperatorService")
public class CloudvmServerOperatorServiceImpl extends BaseServiceImpl<CloudvmServerOperatorModel>
        implements ICloudvmServerOperatorService {

    @SuppressWarnings("unused")
    private final static Logger logger = LoggerFactory.getLogger(CloudvmServerOperatorServiceImpl.class);

    @Resource
    private ICloudvmServerOperatorDao cloudvmServerOperatorDao;


    public CloudvmServerOperatorServiceImpl() {
        super(CloudvmServerOperatorModel.class);
    }

    @Override
    public IBaseDao<CloudvmServerOperatorModel> getDao() {
        return cloudvmServerOperatorDao;
    }

}
