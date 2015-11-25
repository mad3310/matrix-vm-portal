package com.letv.portal.service.openstack.local.service.impl;

import com.letv.portal.model.cloudvm.CloudvmSubnetOption;
import com.letv.portal.service.cloudvm.ICloudvmSubnetOptionService;
import com.letv.portal.service.openstack.local.resource.LocalSubnetOptionResource;
import com.letv.portal.service.openstack.local.service.LocalSubnetOptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by zhouxianguang on 2015/11/25.
 */
@Service
public class LocalSubnetOptionServiceImpl implements LocalSubnetOptionService {

    @Autowired
    private ICloudvmSubnetOptionService cloudvmSubnetOptionService;

    @Override
    public List<LocalSubnetOptionResource> list() {
        List<LocalSubnetOptionResource> localSubnetOptionResourceList = new LinkedList<LocalSubnetOptionResource>();
        List<CloudvmSubnetOption> cloudvmSubnetOptionList = cloudvmSubnetOptionService.selectAll();
        for (CloudvmSubnetOption cloudvmSubnetOption : cloudvmSubnetOptionList) {
            localSubnetOptionResourceList.add(new LocalSubnetOptionResource(cloudvmSubnetOption));
        }
        return localSubnetOptionResourceList;
    }
}
