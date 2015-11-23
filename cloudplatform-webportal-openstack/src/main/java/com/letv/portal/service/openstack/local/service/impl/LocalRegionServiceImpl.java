package com.letv.portal.service.openstack.local.service.impl;

import com.letv.common.exception.ValidateException;
import com.letv.portal.model.cloudvm.CloudvmRegion;
import com.letv.portal.service.cloudvm.ICloudvmRegionService;
import com.letv.portal.service.openstack.exception.OpenStackException;
import com.letv.portal.service.openstack.exception.RegionNotFoundException;
import com.letv.portal.service.openstack.local.service.LocalRegionService;
import com.letv.portal.service.openstack.resource.Region;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by zhouxianguang on 2015/9/30.
 */
@Service
public class LocalRegionServiceImpl implements LocalRegionService {
    @Autowired
    private ICloudvmRegionService cloudvmRegionService;

    @Override
    public Region get(String code) throws OpenStackException {
        if (StringUtils.isEmpty(code)) {
            throw new ValidateException("地域不能为空");
        }

        CloudvmRegion cloudvmRegion = cloudvmRegionService.selectByCode(code);
        if (cloudvmRegion == null) {
            throw new RegionNotFoundException(code);
        }
        Region region = new Region(cloudvmRegion.getCode(), cloudvmRegion.getDisplayName());
        return region;
    }
}
