package com.letv.portal.service.cloudvm.impl;

import com.letv.common.dao.IBaseDao;
import com.letv.portal.dao.cloudvm.ICloudvmServerAddressDao;
import com.letv.portal.dao.cloudvm.ICloudvmServerLinkDao;
import com.letv.portal.model.cloudvm.CloudvmServerAddress;
import com.letv.portal.model.cloudvm.CloudvmServerLink;
import com.letv.portal.service.cloudvm.ICloudvmServerAddressService;
import com.letv.portal.service.cloudvm.ICloudvmServerLinkService;
import com.letv.portal.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by zhouxianguang on 2015/9/28.
 */
@Service
public class CloudvmServerAddressServiceImpl extends BaseServiceImpl<CloudvmServerAddress> implements ICloudvmServerAddressService {

    @Resource
    private ICloudvmServerAddressDao cloudvmServerAddressDao;

    public CloudvmServerAddressServiceImpl() {
        super(CloudvmServerAddress.class);
    }

    @Override
    public IBaseDao<CloudvmServerAddress> getDao() {
        return cloudvmServerAddressDao;
    }
}
