package com.letv.portal.service.openstack.billing.impl;

import com.letv.common.exception.MatrixException;
import com.letv.common.session.Session;
import com.letv.common.session.SessionServiceImpl;
import com.letv.portal.service.openstack.OpenStackSession;
import com.letv.portal.service.openstack.billing.ResourceLocator;
import com.letv.portal.service.openstack.billing.ResourceQueryService;
import com.letv.portal.service.openstack.exception.OpenStackException;
import com.letv.portal.service.openstack.impl.OpenStackSessionImpl;
import com.letv.portal.service.openstack.local.service.LocalVolumeTypeService;
import com.letv.portal.service.openstack.resource.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by zhouxianguang on 2015/10/28.
 */
@Service
public class ResourceQueryServiceImpl implements ResourceQueryService {

    @Autowired
    private SessionServiceImpl sessionService;

    @Autowired
    private LocalVolumeTypeService localVolumeTypeService;

    private OpenStackSession getOpenStackSession() throws OpenStackException {
        Session session = sessionService.getSession();
        OpenStackSessionImpl openStackSession = null;
        if (session != null) {
            openStackSession = (OpenStackSessionImpl) session.getOpenStackSession();
            openStackSession.init(session);
        }

//        if (openStackSession != null) {
//            return openStackSession;
//        } else {
//            return createOpenStackSession(userId);
//        }

        return openStackSession;
    }

    @Override
    public FlavorResource getFlavor(long userId, String region, String flavorId) throws MatrixException {
        try {
            OpenStackSession openStackSession = getOpenStackSession();
            return openStackSession.getVMManager().getFlavorResource(region, flavorId);
        } catch (OpenStackException e) {
            throw e.matrixException();
        }
    }

    @Override
    public VolumeTypeResource getVolumeType(long userId, String region, String volumeTypeId) throws MatrixException {
        try {
            return localVolumeTypeService.get(region, volumeTypeId);
        } catch (OpenStackException e) {
            throw e.matrixException();
        }
    }

    @Override
    public Map<ResourceLocator, VMResource> getVMResources(Iterable<ResourceLocator> resourceLocators) throws MatrixException {
        return null;
    }

    @Override
    public Map<ResourceLocator, VolumeResource> getVolumeResources(Iterable<ResourceLocator> resourceLocators) throws MatrixException {
        return null;
    }

    @Override
    public Map<ResourceLocator, RouterResource> getRouterResources(Iterable<ResourceLocator> resourceLocators) throws MatrixException {
        return null;
    }

    @Override
    public Map<ResourceLocator, FloatingIpResource> getFloatingIpResources(Iterable<ResourceLocator> resourceLocators) throws MatrixException {
        return null;
    }

}
