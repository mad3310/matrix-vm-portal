package com.letv.portal.service.openstack.billing.impl;

import com.letv.common.exception.MatrixException;
import com.letv.common.session.Session;
import com.letv.common.session.SessionServiceImpl;
import com.letv.portal.service.openstack.OpenStackSession;
import com.letv.portal.service.openstack.billing.ResourceLocator;
import com.letv.portal.service.openstack.billing.ResourceQueryService;
import com.letv.portal.service.openstack.exception.OpenStackException;
import com.letv.portal.service.openstack.impl.OpenStackSessionImpl;
import com.letv.portal.service.openstack.jclouds.service.ApiService;
import com.letv.portal.service.openstack.local.service.LocalVolumeTypeService;
import com.letv.portal.service.openstack.resource.*;
import com.letv.portal.service.openstack.util.CollectionUtil;
import com.letv.portal.service.openstack.util.ExceptionUtil;
import com.letv.portal.service.openstack.util.RandomUtil;
import com.letv.portal.service.openstack.util.ThreadUtil;
import com.letv.portal.service.openstack.util.function.Function1;
import com.mchange.v2.lang.ThreadUtils;
import org.apache.commons.collections.ListUtils;
import org.jclouds.openstack.nova.v2_0.NovaApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by zhouxianguang on 2015/10/28.
 */
@Service
public class ResourceQueryServiceImpl implements ResourceQueryService {

    @Autowired
    private SessionServiceImpl sessionService;

    @Autowired
    private LocalVolumeTypeService localVolumeTypeService;

    @Autowired
    private ApiService apiService;

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
    public Map<Class<? extends Resource>, Map<ResourceLocator, Resource>> getResources(long userId, Map<Class<? extends Resource>, Iterable<ResourceLocator>> resourceLocators) throws MatrixException {
        return null;
    }

//    @Override
//    public Map<ResourceLocator, VMResource> getVMResources(long userId, Iterable<ResourceLocator> resourceLocators) throws MatrixException {
//        try {
//            String sessionId = RandomUtil.generateRandomSessionId();
//            apiService.loadAllApiForRandomSession(userId, sessionId);
//            List<VMResource> vmResources = ThreadUtil.concurrentFilter(CollectionUtil.toList(resourceLocators), new Function1<VMResource, ResourceLocator>() {
//                @Override
//                public VMResource apply(ResourceLocator resourceLocator) throws Exception {
//
//                    return null;
//                }
//            });
//            Map<ResourceLocator, VMResource> map = new HashMap<ResourceLocator, VMResource>();
//            apiService.clearCache(userId,sessionId);
//        } catch (OpenStackException e) {
//            ExceptionUtil.processBillingException(e);
//        }
//        return null;
//    }
//
//    @Override
//    public Map<ResourceLocator, VolumeResource> getVolumeResources(long userId, Iterable<ResourceLocator> resourceLocators) throws MatrixException {
//        return null;
//    }
//
//    @Override
//    public Map<ResourceLocator, RouterResource> getRouterResources(long userId, Iterable<ResourceLocator> resourceLocators) throws MatrixException {
//        return null;
//    }
//
//    @Override
//    public Map<ResourceLocator, FloatingIpResource> getFloatingIpResources(long userId, Iterable<ResourceLocator> resourceLocators) throws MatrixException {
//        return null;
//    }



}
