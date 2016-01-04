package com.letv.lcp.openstack.service.local.impl;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.letv.lcp.openstack.model.network.LocalSubnetOptionResource;
import com.letv.lcp.openstack.service.local.ILocalSubnetOptionService;
import com.letv.portal.model.cloudvm.CloudvmSubnetOption;
import com.letv.portal.service.cloudvm.ICloudvmSubnetOptionService;

/**
 * Created by zhouxianguang on 2015/11/25.
 */
@Service
public class LocalSubnetOptionServiceImpl implements ILocalSubnetOptionService {

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
