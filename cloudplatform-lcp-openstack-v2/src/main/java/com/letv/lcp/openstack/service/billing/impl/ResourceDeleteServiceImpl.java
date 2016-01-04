package com.letv.lcp.openstack.service.billing.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.jclouds.openstack.cinder.v1.CinderApi;
import org.jclouds.openstack.cinder.v1.domain.Snapshot;
import org.jclouds.openstack.cinder.v1.domain.Volume;
import org.jclouds.openstack.cinder.v1.domain.VolumeAttachment;
import org.jclouds.openstack.cinder.v1.features.SnapshotApi;
import org.jclouds.openstack.cinder.v1.features.VolumeApi;
import org.jclouds.openstack.neutron.v2.NeutronApi;
import org.jclouds.openstack.neutron.v2.domain.FloatingIP;
import org.jclouds.openstack.neutron.v2.domain.IP;
import org.jclouds.openstack.neutron.v2.domain.Port;
import org.jclouds.openstack.neutron.v2.domain.Router;
import org.jclouds.openstack.neutron.v2.extensions.FloatingIPApi;
import org.jclouds.openstack.neutron.v2.extensions.RouterApi;
import org.jclouds.openstack.neutron.v2.features.PortApi;
import org.jclouds.openstack.nova.v2_0.NovaApi;
import org.jclouds.openstack.nova.v2_0.domain.Flavor;
import org.jclouds.openstack.nova.v2_0.domain.Server;
import org.jclouds.openstack.nova.v2_0.domain.ServerExtendedStatus;
import org.jclouds.openstack.nova.v2_0.extensions.VolumeAttachmentApi;
import org.jclouds.openstack.nova.v2_0.features.ServerApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableSet;
import com.letv.common.exception.MatrixException;
import com.letv.lcp.openstack.exception.OpenStackException;
import com.letv.lcp.openstack.exception.RegionNotFoundException;
import com.letv.lcp.openstack.model.billing.BillingResource;
import com.letv.lcp.openstack.model.billing.ResourceLocator;
import com.letv.lcp.openstack.model.compute.VMResource;
import com.letv.lcp.openstack.model.network.FloatingIpResource;
import com.letv.lcp.openstack.model.network.RouterResource;
import com.letv.lcp.openstack.model.storage.VolumeResource;
import com.letv.lcp.openstack.service.base.impl.OpenStackServiceImpl;
import com.letv.lcp.openstack.service.billing.IResourceDeleteService;
import com.letv.lcp.openstack.service.cronjobs.IVmSyncService;
import com.letv.lcp.openstack.service.jclouds.IApiService;
import com.letv.lcp.openstack.service.local.ILocalRcCountService;
import com.letv.lcp.openstack.service.local.ILocalVolumeService;
import com.letv.lcp.openstack.service.manage.impl.NetworkManagerImpl;
import com.letv.lcp.openstack.service.resource.IResourceService;
import com.letv.lcp.openstack.util.ExceptionUtil;
import com.letv.lcp.openstack.util.ThreadUtil;
import com.letv.lcp.openstack.util.Timeout;
import com.letv.lcp.openstack.util.cache.UserApiCache;
import com.letv.lcp.openstack.util.function.Function0;
import com.letv.lcp.openstack.util.function.Function1;
import com.letv.portal.model.cloudvm.CloudvmRcCountType;

/**
 * Created by zhouxianguang on 2015/10/28.
 */
@Service
public class ResourceDeleteServiceImpl implements IResourceDeleteService {

    private static final Logger logger = LoggerFactory.getLogger(ResourceDeleteServiceImpl.class);

    @Autowired
    private IApiService apiService;

    @Autowired
    private IResourceService resourceService;

    @Autowired
    private IVmSyncService vmSyncService;

    @Autowired
    private ILocalVolumeService localVolumeService;

    @Autowired
    private ILocalRcCountService localRcCountService;

    @Override
    public void deleteResource(final long userId, Iterable<ResourceLocator> resourceLocators) throws MatrixException {
        try {
            final UserApiCache userApiCache = new UserApiCache(userId);
            try {
                userApiCache.loadApis(NovaApi.class, CinderApi.class, NeutronApi.class);

                Map<Class<? extends BillingResource>, List<ResourceLocator>> typeToLocators = new HashMap<Class<? extends BillingResource>, List<ResourceLocator>>();

                for (Iterator<ResourceLocator> it = resourceLocators.iterator(); it.hasNext(); ) {
                    ResourceLocator resourceLocator = it.next();
                    List<ResourceLocator> locatorList = typeToLocators.get(resourceLocator.type());
                    if (locatorList == null) {
                        locatorList = new LinkedList<ResourceLocator>();
                        typeToLocators.put(resourceLocator.type(), locatorList);
                    }
                    locatorList.add(resourceLocator);
                }

                for (Map.Entry<Class<? extends BillingResource>, List<ResourceLocator>> typeToLocatorsEntry : typeToLocators.entrySet()) {
                    ThreadUtil.concurrentFilter(typeToLocatorsEntry.getValue(), new Function1<ResourceLocator, Void>() {
                        @Override
                        public Void apply(ResourceLocator resourceLocator) throws Exception {
                            deleteBillingResource(userId, userApiCache, resourceLocator);
                            return null;
                        }
                    });
                }
            } finally {
                userApiCache.close();
            }
        } catch (Exception ex) {
            ExceptionUtil.throwMatrixException(ex);
        }
    }

    private void deleteBillingResource(long userId, UserApiCache userApiCache, ResourceLocator resourceLocator) throws OpenStackException {
        try {
            if (resourceLocator.getType() == VMResource.class) {
                deleteServer(userId, userApiCache, resourceLocator);
            } else if (resourceLocator.getType() == RouterResource.class) {
                deleteRouter(userId, userApiCache, resourceLocator);
            } else if (resourceLocator.getType() == FloatingIpResource.class) {
                deleteFloatingIp(userId, userApiCache, resourceLocator);
            } else if (resourceLocator.getType() == VolumeResource.class) {
                deleteVolume(userId, userApiCache, resourceLocator);
            }
        } catch (RegionNotFoundException ex) {
            logger.error(ex.getMessage(), ex);
        }
    }

    private void deleteVolumeSnapshot(long userId, UserApiCache userApiCache, final ResourceLocator locator) throws OpenStackException {
        CinderApi cinderApi = userApiCache.getApi(CinderApi.class);
        resourceService.checkRegion(locator.region(), cinderApi);
        final SnapshotApi snapshotApi = cinderApi.getSnapshotApi(locator.region());
        final Snapshot snapshot = snapshotApi.get(locator.getId());

        if (snapshot != null) {
            ThreadUtil.waiting(new Function0<Boolean>() {
                @Override
                public Boolean apply() throws Exception {
                    Snapshot latestSnapshot = snapshotApi.get(locator.id());
                    if (latestSnapshot == null) {
                        return false;
                    }
                    Volume.Status status = latestSnapshot.getStatus();
                    return status == Volume.Status.CREATING || status == Volume.Status.ATTACHING || status == Volume.Status.DELETING || status == Volume.Status.ERROR_DELETING;
                }
            }, new Timeout().time(10L).unit(TimeUnit.MINUTES));
            if (snapshotApi.get(locator.id()) == null) {
                return;
            }

            boolean isSuccess = snapshotApi.delete(snapshot.getId());
            if (isSuccess) {
                ThreadUtil.waiting(new Function0<Boolean>() {
                    @Override
                    public Boolean apply() throws Exception {
                        return snapshotApi.get(snapshot.getId()) != null;
                    }
                }, new Timeout().time(10L).unit(TimeUnit.MINUTES));
                localRcCountService.decRcCount(userId, locator.region(), CloudvmRcCountType.VOLUME_SNAPSHOT);
            }
        }
    }

    private void deleteVolume(long userId, UserApiCache userApiCache, final ResourceLocator locator) throws OpenStackException {
        CinderApi cinderApi = userApiCache.getApi(CinderApi.class);
        resourceService.checkRegion(locator.region(), cinderApi);
        final VolumeApi volumeApi = cinderApi.getVolumeApi(locator.region());
        Volume volume = volumeApi.get(locator.id());
        if (volume != null) {
            if (!volume.getAttachments().isEmpty()) {
                NovaApi novaApi = userApiCache.getApi(NovaApi.class);
                final VolumeAttachmentApi volumeAttachmentApi = novaApi.getVolumeAttachmentApi(locator.region()).get();
                for (final VolumeAttachment volumeAttachment : volume.getAttachments()) {
                    boolean isSuccess = volumeAttachmentApi.detachVolumeFromServer(volumeAttachment.getVolumeId(), volumeAttachment.getServerId());
                    if (isSuccess) {
                        localVolumeService.update(userId, userId, locator.region(), volume);
                        ThreadUtil.waiting(new Function0<Boolean>() {
                            @Override
                            public Boolean apply() throws Exception {
                                return volumeAttachmentApi.getAttachmentForVolumeOnServer(volumeAttachment.getVolumeId(), volumeAttachment.getServerId()) != null;
                            }
                        }, new Timeout().time(10L).unit(TimeUnit.MINUTES));
                        localVolumeService.update(userId, userId, locator.region(), volume);
                    }
                }
            }

            final SnapshotApi snapshotApi = cinderApi.getSnapshotApi(locator.region());
            for (final Snapshot snapshot : snapshotApi.list().toList()) {
                if (volume.getId().equals(snapshot.getVolumeId())) {
                    deleteVolumeSnapshot(userId, userApiCache, new ResourceLocator().region(locator.region()).id(snapshot.getId()));
                }
            }

            ThreadUtil.waiting(new Function0<Boolean>() {
                @Override
                public Boolean apply() throws Exception {
                    Volume latestVolume = volumeApi.get(locator.id());
                    if (latestVolume == null) {
                        return false;
                    }
                    Volume.Status status = latestVolume.getStatus();
                    return status == Volume.Status.CREATING || status == Volume.Status.ATTACHING || status == Volume.Status.DELETING || status == Volume.Status.ERROR_DELETING;
                }
            }, new Timeout().time(10L).unit(TimeUnit.MINUTES));
            if (volumeApi.get(locator.id()) == null) {
                return;
            }

            boolean isSuccess = volumeApi.delete(locator.id());
            if (isSuccess) {
                ThreadUtil.waiting(new Function0<Boolean>() {
                    @Override
                    public Boolean apply() throws Exception {
                        return volumeApi.get(locator.id()) != null;
                    }
                }, new Timeout().time(10L).unit(TimeUnit.MINUTES));
                OpenStackServiceImpl.getOpenStackServiceGroup().getLocalVolumeService().delete(userId, locator.region(), volume.getId());
            }
        }
    }

    private void deleteFloatingIp(long userId, UserApiCache userApiCache, final ResourceLocator locator) throws OpenStackException {
        NeutronApi neutronApi = userApiCache.getApi(NeutronApi.class);
        resourceService.checkRegion(locator.region(), neutronApi);
        final FloatingIPApi floatingIPApi = neutronApi.getFloatingIPApi(locator.region()).get();
        final FloatingIP floatingIP = floatingIPApi.get(locator.id());
        if (floatingIP != null) {
            boolean isSuccess = floatingIPApi.delete(locator.id());
            if (isSuccess) {
                ThreadUtil.waiting(new Function0<Boolean>() {
                    @Override
                    public Boolean apply() throws Exception {
                        return floatingIPApi.get(locator.id()) != null;
                    }
                }, new Timeout().time(10L).unit(TimeUnit.MINUTES));
                localRcCountService.decRcCount(userId, locator.region(), CloudvmRcCountType.FLOATING_IP);
                localRcCountService.decRcCount(userId, locator.region(), CloudvmRcCountType.BAND_WIDTH
                        , NetworkManagerImpl.getBandWidth(floatingIP.getFipQos()));
            }
        }
    }

    private void deleteRouter(long userId, UserApiCache userApiCache, final ResourceLocator locator) throws OpenStackException {
        NeutronApi neutronApi = userApiCache.getApi(NeutronApi.class);
        resourceService.checkRegion(locator.region(), neutronApi);
        final RouterApi routerApi = neutronApi.getRouterApi(locator.region()).get();
        Router router = routerApi.get(locator.id());
        if (router != null) {
            final PortApi portApi = neutronApi.getPortApi(locator.region());
            for (final Port port : portApi.list().concat()
                    .toList()) {
                if ("network:router_interface"
                        .equals(port.getDeviceOwner())
                        && router.getId().equals(port.getDeviceId())) {
                    ImmutableSet<IP> fixedIps = port.getFixedIps();
                    if (fixedIps != null) {
                        for (IP ip : fixedIps) {
                            if (ip.getSubnetId() != null) {
                                boolean isSuccess = routerApi.removeInterfaceForSubnet(router.getId(), ip.getSubnetId());
                                if (isSuccess) {
                                    ThreadUtil.waiting(new Function0<Boolean>() {
                                        @Override
                                        public Boolean apply() throws Exception {
                                            return portApi.get(port.getId()) != null;
                                        }
                                    }, new Timeout().time(10L).unit(TimeUnit.MINUTES));
                                }
                            }
                        }
                    }
                }
            }

            if (routerApi.get(locator.id()) == null) {
                return;
            }

            boolean isSuccess = routerApi.delete(locator.id());
            if (isSuccess) {
                ThreadUtil.waiting(new Function0<Boolean>() {
                    @Override
                    public Boolean apply() throws Exception {
                        return routerApi.get(locator.id()) != null;
                    }
                }, new Timeout().time(10L).unit(TimeUnit.MINUTES));
                localRcCountService.decRcCount(userId,locator.region(),CloudvmRcCountType.ROUTER);
            }
        }
    }

    private void deleteServer(long userId, UserApiCache userApiCache, final ResourceLocator locator) throws OpenStackException {
        NovaApi novaApi = userApiCache.getApi(NovaApi.class);
        resourceService.checkRegion(locator.region(), novaApi);
        final ServerApi serverApi = novaApi.getServerApi(locator.region());
        Server server = serverApi.get(locator.id());
        if (server != null) {
            Flavor flavor = novaApi.getFlavorApi(locator.region()).get(server.getFlavor().getId());

            ThreadUtil.waiting(new Function0<Boolean>() {
                @Override
                public Boolean apply() throws Exception {
                    Server latestServer = serverApi.get(locator.id());
                    if (latestServer == null) {
                        return false;
                    }
                    Optional<ServerExtendedStatus> serverExtendedStatusOptional = latestServer.getExtendedStatus();
                    if (serverExtendedStatusOptional.isPresent()) {
                        if (serverExtendedStatusOptional.get().getTaskState() != null) {
                            return true;
                        }
                    }
                    Server.Status status = latestServer.getStatus();
                    return status == Server.Status.HARD_REBOOT
                            || status == Server.Status.REBOOT
                            || status == Server.Status.BUILD
                            || status == Server.Status.REBUILD
                            || status == Server.Status.PASSWORD
                            || status == Server.Status.DELETED
                            || status == Server.Status.MIGRATING;
                }
            }, new Timeout().time(60L).unit(TimeUnit.MINUTES));
            if (serverApi.get(locator.id()) == null) {
                return;
            }

            boolean isSuccess = serverApi.delete(locator.id());
            if (isSuccess) {
                ThreadUtil.waiting(new Function0<Boolean>() {
                    @Override
                    public Boolean apply() throws Exception {
                        return serverApi.get(locator.id()) != null;
                    }
                }, new Timeout().time(60L).unit(TimeUnit.MINUTES));
                vmSyncService.recordVmDeleted(userId, locator.region(), server.getId(), flavor);
            }
        }
    }

}
