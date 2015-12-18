package com.letv.portal.service.openstack.billing.impl;

import com.letv.common.exception.MatrixException;
import com.letv.common.session.Session;
import com.letv.common.session.SessionServiceImpl;
import com.letv.portal.model.cloudvm.CloudvmVolume;
import com.letv.portal.service.common.IUserService;
import com.letv.portal.service.cloudvm.ICloudvmVolumeService;
import com.letv.portal.service.openstack.OpenStackService;
import com.letv.portal.service.openstack.OpenStackSession;
import com.letv.portal.service.openstack.billing.BillingResource;
import com.letv.portal.service.openstack.billing.ResourceLocator;
import com.letv.portal.service.openstack.billing.ResourceQueryService;
import com.letv.portal.service.openstack.exception.OpenStackException;
import com.letv.portal.service.openstack.impl.OpenStackSessionImpl;
import com.letv.portal.service.openstack.jclouds.cache.UserApiCache;
import com.letv.portal.service.openstack.jclouds.service.ApiService;
import com.letv.portal.service.openstack.local.resource.LocalVolumeResource;
import com.letv.portal.service.openstack.local.service.LocalVolumeTypeService;
import com.letv.portal.service.openstack.resource.*;
import com.letv.portal.service.openstack.resource.impl.FloatingIpResourceImpl;
import com.letv.portal.service.openstack.resource.impl.RouterResourceImpl;
import com.letv.portal.service.openstack.resource.impl.VMResourceImpl;
import com.letv.portal.service.openstack.resource.impl.VolumeResourceImpl;
import com.letv.portal.service.openstack.util.*;
import com.letv.portal.service.openstack.util.function.Function0;
import com.letv.portal.service.openstack.util.function.Function1;
import com.letv.portal.service.openstack.util.tuple.Tuple2;

import org.jclouds.openstack.cinder.v1.CinderApi;
import org.jclouds.openstack.cinder.v1.domain.Volume;
import org.jclouds.openstack.neutron.v2.NeutronApi;
import org.jclouds.openstack.neutron.v2.domain.FloatingIP;
import org.jclouds.openstack.neutron.v2.domain.Router;
import org.jclouds.openstack.nova.v2_0.NovaApi;
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

    @Autowired
    private OpenStackService openStackService;

    @Autowired
    private IUserService userService;

    @Autowired
    private ICloudvmVolumeService cloudvmVolumeService;

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

    private BillingResource getServer(UserApiCache userApiCache, ResourceLocator locator) throws OpenStackException {
        NovaApi novaApi = userApiCache.getApi(NovaApi.class);
        if (novaApi.getConfiguredRegions().contains(locator.region())) {
            Server server = novaApi
                    .getServerApi(locator.region()).get(locator.id());
            if (server != null) {
                return new VMResourceImpl(server);
            }
        }
        return null;
    }

    @SuppressWarnings("unused")
    private BillingResource getVolume(UserApiCache userApiCache, ResourceLocator locator) throws OpenStackException {
        CinderApi cinderApi = userApiCache.getApi(CinderApi.class);
        if (cinderApi.getConfiguredRegions().contains(locator.region())) {
            Volume volume = cinderApi
                    .getVolumeApi(locator.region()).get(locator.id());
            if (volume != null) {
                return new VolumeResourceImpl(volume);
            }
        }
        return null;
    }

    private BillingResource getRouter(UserApiCache userApiCache, ResourceLocator locator) throws OpenStackException {
        NeutronApi neutronApi = userApiCache.getApi(NeutronApi.class);
        if (neutronApi.getConfiguredRegions().contains(locator.region())) {
            Router router = neutronApi
                    .getRouterApi(locator.region()).get().get(locator.id());
            if (router != null) {
                return new RouterResourceImpl(router);
            }
        }
        return null;
    }

    private BillingResource getFloatingIp(UserApiCache userApiCache, ResourceLocator locator) throws OpenStackException {
        NeutronApi neutronApi = userApiCache.getApi(NeutronApi.class);
        if (neutronApi.getConfiguredRegions().contains(locator.region())) {
            FloatingIP floatingIP = neutronApi
                    .getFloatingIPApi(locator.region()).get().get(locator.id());
            if (floatingIP != null) {
                return new FloatingIpResourceImpl(floatingIP);
            }
        }
        return null;
    }

    @Override
    public Map<ResourceLocator, BillingResource> getResources(final long userId, final Iterable<ResourceLocator> resourceLocators) throws MatrixException {
        final Map<ResourceLocator, BillingResource> map = new HashMap<ResourceLocator, BillingResource>();
        try {
            final UserApiCache userApiCache = new UserApiCache(userId);
            try {
                userApiCache.loadApis(NovaApi.class, NeutronApi.class);
                @SuppressWarnings("unchecked")
                List<Ref<List<Tuple2<ResourceLocator, BillingResource>>>> resultRefList = ThreadUtil.concurrentRunAndWait(new Timeout().time(40L).unit(TimeUnit.MINUTES), new Function0<List<Tuple2<ResourceLocator, BillingResource>>>() {
                    @Override
                    public List<Tuple2<ResourceLocator, BillingResource>> apply() throws Exception {
                        List<Tuple2<ResourceLocator, BillingResource>> entries = ThreadUtil.concurrentFilter(
                                CollectionUtil.toList(resourceLocators),
                                new Function1<ResourceLocator, Tuple2<ResourceLocator, BillingResource>>() {
                                    @Override
                                    public Tuple2<ResourceLocator, BillingResource> apply(ResourceLocator locator)
                                            throws Exception {
                                        BillingResource billingResource = null;
                                        if (locator.getType() == VMResource.class) {
                                            billingResource = getServer(userApiCache, locator);
                                        } else if (locator.getType() == RouterResource.class) {
                                            billingResource = getRouter(userApiCache, locator);
                                        } else if (locator.getType() == FloatingIpResource.class) {
                                            billingResource = getFloatingIp(userApiCache, locator);
                                        }
                                        if (billingResource != null) {
                                            return new Tuple2<ResourceLocator, BillingResource>(locator, billingResource);
                                        }
                                        return null;
                                    }
                                }, new Timeout().time(30L).unit(TimeUnit.MINUTES));
                        return entries;
                    }
                }, new Function0<List<Tuple2<ResourceLocator, BillingResource>>>() {
                    @Override
                    public List<Tuple2<ResourceLocator, BillingResource>> apply() throws Exception {
                        Set<String> regions = new HashSet<String>();
                        Set<String> volumeIds = new HashSet<String>();
                        for (Iterator<ResourceLocator> it = resourceLocators.iterator(); it.hasNext(); ) {
                            ResourceLocator locator = it.next();
                            if (locator.getType() == VolumeResource.class) {
                                regions.add(locator.region());
                                volumeIds.add(locator.id());
                            }
                        }
                        List<CloudvmVolume> cloudvmVolumes = cloudvmVolumeService.selectByRegionsAndVolumeIds(userId, CollectionUtil.toList(regions), CollectionUtil.toList(volumeIds));
                        List<Tuple2<ResourceLocator, BillingResource>> entries = new LinkedList<Tuple2<ResourceLocator, BillingResource>>();
                        for (CloudvmVolume cloudvmVolume : cloudvmVolumes) {
                            entries.add(new Tuple2<ResourceLocator, BillingResource>(new ResourceLocator().region(cloudvmVolume.getRegion()).id(cloudvmVolume.getVolumeId()).type(VolumeResource.class), new LocalVolumeResource(cloudvmVolume)));
                        }
                        return entries;
                    }
                });
                for (Ref<List<Tuple2<ResourceLocator, BillingResource>>> entriesRef : resultRefList) {
                    if (entriesRef.get() != null) {
                        for (Tuple2<ResourceLocator, BillingResource> entry : entriesRef.get()) {
                            map.put(entry._1, entry._2);
                        }
                    }
                }
            } finally {
                userApiCache.close();
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
//            apiService.loadAllApiForRandomSessionFromBackend(userId, sessionId);
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
