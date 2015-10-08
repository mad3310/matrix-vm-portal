package com.letv.portal.service.cloudvm.impl;

import com.letv.common.dao.IBaseDao;
import com.letv.portal.dao.cloudvm.ICloudvmImageDao;
import com.letv.portal.model.cloudvm.CloudvmImage;
import com.letv.portal.service.cloudvm.ICloudvmImageService;
import com.letv.portal.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by zhouxianguang on 2015/10/8.
 */
@Service
public class CloudvmImageServiceImpl extends BaseServiceImpl<CloudvmImage>
        implements ICloudvmImageService {

    @Resource
    private ICloudvmImageDao cloudvmImageDao;

    public CloudvmImageServiceImpl() {
        super(CloudvmImage.class);
    }

    @Override
    public IBaseDao<CloudvmImage> getDao() {
        return cloudvmImageDao;
    }
}
