package com.letv.portal.service.cloudvm.impl;

import com.letv.common.dao.IBaseDao;
import com.letv.portal.dao.cloudvm.ICloudvmServerLinkDao;
import com.letv.portal.model.cloudvm.CloudvmServerLink;
import com.letv.portal.service.cloudvm.ICloudvmServerLinkService;
import com.letv.portal.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by zhouxianguang on 2015/9/28.
 */
@Service
public class CloudvmServerLinkServiceImpl extends BaseServiceImpl<CloudvmServerLink> implements ICloudvmServerLinkService {

    @Resource
    private ICloudvmServerLinkDao cloudvmServerLinkDao;

    public CloudvmServerLinkServiceImpl() {
        super(CloudvmServerLink.class);
    }

    @Override
    public IBaseDao<CloudvmServerLink> getDao() {
        return cloudvmServerLinkDao;
    }
}
