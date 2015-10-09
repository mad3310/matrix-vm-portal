package com.letv.portal.service.cloudvm.impl;

import com.letv.common.dao.IBaseDao;
import com.letv.portal.dao.cloudvm.ICloudvmImageLinkDao;
import com.letv.portal.model.cloudvm.CloudvmImageLink;
import com.letv.portal.service.cloudvm.ICloudvmImageLinkService;
import com.letv.portal.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by zhouxianguang on 2015/10/8.
 */
@Service
public class CloudvmImageLinkServiceImpl extends BaseServiceImpl<CloudvmImageLink> implements ICloudvmImageLinkService {

    @Resource
    private ICloudvmImageLinkDao cloudvmImageLinkDao;

    public CloudvmImageLinkServiceImpl(){
        super(CloudvmImageLink.class);
    }

    @Override
    public IBaseDao<CloudvmImageLink> getDao() {
        return cloudvmImageLinkDao;
    }
}
