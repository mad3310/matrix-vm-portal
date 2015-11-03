package com.letv.portal.service.openstack.billing.impl;

import com.letv.common.exception.MatrixException;
import com.letv.common.session.Session;
import com.letv.common.session.SessionServiceImpl;
import com.letv.portal.service.openstack.OpenStackSession;
import com.letv.portal.service.openstack.billing.BillingResource;
import com.letv.portal.service.openstack.billing.ResourceLocator;
import com.letv.portal.service.openstack.billing.ResourceQueryService;
import com.letv.portal.service.openstack.exception.OpenStackException;
import com.letv.portal.service.openstack.impl.OpenStackSessionImpl;
import com.letv.portal.service.openstack.jclouds.service.ApiService;
import com.letv.portal.service.openstack.local.service.LocalVolumeTypeService;
import com.letv.portal.service.openstack.resource.*;
import com.letv.portal.service.openstack.resource.impl.FloatingIpResourceImpl;
import com.letv.portal.service.openstack.resource.impl.RouterResourceImpl;
import com.letv.portal.service.openstack.resource.impl.VMResourceImpl;
import com.letv.portal.service.openstack.resource.impl.VolumeResourceImpl;
import com.letv.portal.service.openstack.util.*;
import com.letv.portal.service.openstack.util.function.Function1;
import com.letv.portal.service.openstack.util.tuple.Tuple2;
import org.jclouds.openstack.cinder.v1.domain.Volume;
import org.jclouds.openstack.neutron.v2.domain.FloatingIP;
import org.jclouds.openstack.neutron.v2.domain.Router;
import org.jclouds.openstack.nova.v2_0.domain.Server;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.TimeUnit;

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
    public Map<ResourceLocator, BillingResource> getResources(final long userId, Iterable<ResourceLocator> resourceLocators) throws MatrixException {
        Map<ResourceLocator, BillingResource> map = new HashMap<ResourceLocator, BillingResource>();
        final String randomSessionId = RandomUtil.generateRandomSessionId();
        try {
            apiService.loadAllApiForRandomSession(userId, randomSessionId);
            try {
                List<Tuple2<ResourceLocator, BillingResource>> entries = ThreadUtil.concurrentFilter(
                        CollectionUtil.toList(resourceLocators),
                        new Function1<Tuple2<ResourceLocator, BillingResource>, ResourceLocator>() {
                            @Override
                            public Tuple2<ResourceLocator, BillingResource> apply(ResourceLocator locator)
                                    throws Exception {
                                BillingResource billingResource = null;
                                if (locator.getType() == VMResource.class) {
                                    Server server = apiService.getNovaApi(userId, randomSessionId)
                                            .getServerApi(locator.region()).get(locator.getId());
                                    billingResource = new VMResourceImpl(server);
                                } else if (locator.getType() == VolumeResource.class) {
                                    Volume volume = apiService.getCinderApi(userId, randomSessionId)
                                            .getVolumeApi(locator.region()).get(locator.getId());
                                    billingResource = new VolumeResourceImpl(volume);
                                } else if (locator.getType() == RouterResource.class) {
                                    Router router = apiService.getNeutronApi(userId, randomSessionId)
                                            .getRouterApi(locator.region()).get().get(locator.getId());
                                    billingResource = new RouterResourceImpl(router);
                                } else if (locator.getType() == FloatingIpResource.class) {
                                    FloatingIP floatingIP = apiService.getNeutronApi(userId, randomSessionId)
                                            .getFloatingIPApi(locator.region()).get().get(locator.getId());
                                    billingResource = new FloatingIpResourceImpl(floatingIP);
                                }
                                if (billingResource != null) {
                                    return new Tuple2<ResourceLocator, BillingResource>(locator, billingResource);
                                }
                                return null;
                            }
                        }, new Timeout().time(30L).unit(TimeUnit.MINUTES));

                for (Tuple2<ResourceLocator, BillingResource> entry : entries) {
                    map.put(entry._1, entry._2);
                }
            } finally {
                apiService.clearCache(userId, randomSessionId);
            }
        } catch (Exception ex) {
            ExceptionUtil.throwMatrixException(ex);
        }
        return map;
    }

//    @Override
//    public Map<ResourceLocator, VMResource> getVMResources(long userId, Iterable<ResourceLocator> resourceLocators) throws MatrixException {
//        try {
//            String sessionId = RandomUtil.generateRandomSessionId();
//            apiService.loadAllApiForRandomSession(userId, sessionId);
//            List<VMResource> vmResources = ThreadUtil.concurrentFilter(CollectionUtil.toList(resourceLocators), new Function1<VMResource, ResourceLocator>() {
//                @Override
//                public VMResource apply(ResourceLocator locator) throws Exception {
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
}
