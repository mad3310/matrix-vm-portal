package com.letv.portal.service.cloudvm.impl;

import com.letv.common.dao.IBaseDao;
import com.letv.portal.dao.cloudvm.ICloudvmSubnetOptionDao;
import com.letv.portal.model.cloudvm.CloudvmSubnetOption;
import com.letv.portal.service.cloudvm.ICloudvmSubnetOptionService;
import com.letv.portal.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

/**
 * Created by zhouxianguang on 2015/11/25.
 */
@Service("cloudvmSubnetOptionService")
public class CloudvmSubnetOptionServiceImpl extends BaseServiceImpl<CloudvmSubnetOption> implements ICloudvmSubnetOptionService {

    @Resource
    private ICloudvmSubnetOptionDao cloudvmSubnetOptionDao;

    public CloudvmSubnetOptionServiceImpl() {
        super(CloudvmSubnetOption.class);
    }

    @Override
    public IBaseDao<CloudvmSubnetOption> getDao() {
        return cloudvmSubnetOptionDao;
    }

    @Override
    public List<CloudvmSubnetOption> selectAll() {
        return selectByMap(new HashMap<Object, Object>());
    }
}
