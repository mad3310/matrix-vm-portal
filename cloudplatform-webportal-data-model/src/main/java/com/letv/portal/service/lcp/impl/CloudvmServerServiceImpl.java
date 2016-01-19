package com.letv.portal.service.lcp.impl;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.letv.common.dao.IBaseDao;
import com.letv.common.session.SessionServiceImpl;
import com.letv.portal.dao.lcp.ICloudvmServer1Dao;
import com.letv.portal.model.cloudvm.lcp.CloudvmServerModel;
import com.letv.portal.service.common.impl.BaseServiceImpl;
import com.letv.portal.service.lcp.ICloudvmServerService;

@Service("cloudvmServerService1")
public class CloudvmServerServiceImpl extends BaseServiceImpl<CloudvmServerModel>
        implements ICloudvmServerService {

    @SuppressWarnings("unused")
    private final static Logger logger = LoggerFactory.getLogger(CloudvmServerServiceImpl.class);

    @Resource
    private ICloudvmServer1Dao cloudvmServerDao;

    @Autowired
    private SessionServiceImpl sessionService;

    public CloudvmServerServiceImpl() {
        super(CloudvmServerModel.class);
    }

    @Override
    public IBaseDao<CloudvmServerModel> getDao() {
        return cloudvmServerDao;
    }

}
