package com.letv.portal.service.cloudvm.impl;

import com.letv.common.dao.IBaseDao;
import com.letv.portal.dao.cloudvm.ICloudvmImagePropertyDao;
import com.letv.portal.model.cloudvm.CloudvmImageProperty;
import com.letv.portal.service.cloudvm.ICloudvmImagePropertyService;
import com.letv.portal.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by zhouxianguang on 2015/10/8.
 */
@Service
public class CloudvmImagePropertyServiceImpl extends BaseServiceImpl<CloudvmImageProperty> implements ICloudvmImagePropertyService {

    @Resource
    private ICloudvmImagePropertyDao cloudvmImagePropertyDao;

    public CloudvmImagePropertyServiceImpl() {
        super(CloudvmImageProperty.class);
    }

    @Override
    public IBaseDao<CloudvmImageProperty> getDao() {
        return cloudvmImagePropertyDao;
    }
}
