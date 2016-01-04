package com.letv.lcp.openstack.service.local.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.letv.common.exception.ValidateException;
import com.letv.lcp.openstack.exception.OpenStackException;
import com.letv.lcp.openstack.exception.RegionNotFoundException;
import com.letv.lcp.openstack.model.region.Region;
import com.letv.lcp.openstack.service.local.ILocalRegionService;
import com.letv.portal.model.cloudvm.CloudvmRegion;
import com.letv.portal.service.cloudvm.ICloudvmRegionService;

/**
 * Created by zhouxianguang on 2015/9/30.
 */
@Service
public class LocalRegionServiceImpl implements ILocalRegionService {
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
