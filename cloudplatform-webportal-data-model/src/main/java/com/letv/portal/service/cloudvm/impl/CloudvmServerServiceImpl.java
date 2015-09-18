package com.letv.portal.service.cloudvm.impl;

import com.letv.common.dao.IBaseDao;
import com.letv.common.session.SessionServiceImpl;
import com.letv.portal.dao.cloudvm.ICloudvmServerDao;
import com.letv.portal.model.cloudvm.CloudvmServer;
import com.letv.portal.service.cloudvm.ICloudvmServerService;
import com.letv.portal.service.impl.BaseServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("cloudvmServerService")
public class CloudvmServerServiceImpl extends BaseServiceImpl<CloudvmServer>
        implements ICloudvmServerService {

    @SuppressWarnings("unused")
    private final static Logger logger = LoggerFactory
            .getLogger(CloudvmServerServiceImpl.class);

    @Resource
    private ICloudvmServerDao cloudvmServerDao;

    @Autowired
    private SessionServiceImpl sessionService;

    public CloudvmServerServiceImpl() {
        super(CloudvmServer.class);
    }

    @Override
    public IBaseDao<CloudvmServer> getDao() {
        return cloudvmServerDao;
    }

}
