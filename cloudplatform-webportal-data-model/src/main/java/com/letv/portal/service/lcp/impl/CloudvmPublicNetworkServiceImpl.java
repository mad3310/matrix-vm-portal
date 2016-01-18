package com.letv.portal.service.lcp.impl;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.letv.common.dao.IBaseDao;
import com.letv.common.session.SessionServiceImpl;
import com.letv.portal.dao.lcp.ICloudvmPublicNetworkDao;
import com.letv.portal.model.cloudvm.lcp.CloudvmPublicNetworkModel;
import com.letv.portal.service.common.impl.BaseServiceImpl;
import com.letv.portal.service.lcp.ICloudvmPublicNetworkService;

@Service("cloudvmPublicNetworkService")
public class CloudvmPublicNetworkServiceImpl extends BaseServiceImpl<CloudvmPublicNetworkModel>
        implements ICloudvmPublicNetworkService {

    @SuppressWarnings("unused")
    private final static Logger logger = LoggerFactory.getLogger(CloudvmPublicNetworkServiceImpl.class);

    @Resource
    private ICloudvmPublicNetworkDao cloudvmPublicNetworkDao;

    @Autowired
    private SessionServiceImpl sessionService;

    public CloudvmPublicNetworkServiceImpl() {
        super(CloudvmPublicNetworkModel.class);
    }

    @Override
    public IBaseDao<CloudvmPublicNetworkModel> getDao() {
        return cloudvmPublicNetworkDao;
    }

}
