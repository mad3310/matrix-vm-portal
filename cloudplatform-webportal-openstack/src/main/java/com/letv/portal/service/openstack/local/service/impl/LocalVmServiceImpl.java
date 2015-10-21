package com.letv.portal.service.openstack.local.service.impl;

import com.letv.common.exception.ValidateException;
import com.letv.common.paging.impl.Page;
import com.letv.common.session.SessionServiceImpl;
import com.letv.portal.model.cloudvm.CloudvmServer;
import com.letv.portal.service.cloudvm.ICloudvmServerService;
import com.letv.portal.service.openstack.exception.OpenStackException;
import com.letv.portal.service.openstack.exception.ResourceNotFoundException;
import com.letv.portal.service.openstack.local.resource.LocalVmResource;
import com.letv.portal.service.openstack.local.service.LocalRegionService;
import com.letv.portal.service.openstack.local.service.LocalVmService;
import com.letv.portal.service.openstack.resource.Region;
import com.letv.portal.service.openstack.resource.VMResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by zhouxianguang on 2015/9/30.
 */
@Service
public class LocalVmServiceImpl implements LocalVmService {

    @Autowired
    private ICloudvmServerService cloudvmServerService;

    @Autowired
    private LocalRegionService regionQueryService;

    @Autowired
    private SessionServiceImpl sessionService;

    protected long getCurrentUserId(){
        return sessionService.getSession().getUserId();
    }

    @Override
    public VMResource get(String regionCode, String vmId) throws OpenStackException {
        Region region = regionQueryService.get(regionCode);

        CloudvmServer cloudvmServer = cloudvmServerService.selectByServerId(getCurrentUserId(), regionCode, vmId);
        if (cloudvmServer == null) {
            throw new ResourceNotFoundException("VM", "虚拟机", vmId);
        }
        return new LocalVmResource(region, cloudvmServer);
    }

    @Override
    public Page list(String regionCode, String name, Integer currentPage, Integer recordsPerPage) throws OpenStackException {
        Region region = regionQueryService.get(regionCode);

        Page page = null;
        if (currentPage != null && recordsPerPage != null) {
            if (currentPage <= 0) {
                throw new ValidateException("当前页数不能小于或等于0");
            }
            if (recordsPerPage <= 0) {
                throw new ValidateException("每页记录数不能小于或等于0");
            }
            page = new Page(currentPage, recordsPerPage);
        }
        List<CloudvmServer> cloudvmServers = cloudvmServerService.selectByName(getCurrentUserId(), regionCode, name, page);
        List<VMResource> vmResources = new LinkedList<VMResource>();
        for (CloudvmServer cloudvmServer : cloudvmServers) {
            vmResources.add(new LocalVmResource(region, cloudvmServer));
        }

        if (page == null) {
            page = new Page();
        }
        page.setTotalRecords(cloudvmServerService.countByName(getCurrentUserId(), regionCode, name));
        page.setData(vmResources);
        return page;
    }
}
