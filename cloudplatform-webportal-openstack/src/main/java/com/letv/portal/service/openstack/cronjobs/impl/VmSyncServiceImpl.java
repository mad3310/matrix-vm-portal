package com.letv.portal.service.openstack.cronjobs.impl;

import java.util.List;
import java.util.Map;

import com.letv.portal.model.cloudvm.*;
import com.letv.portal.service.cloudvm.*;
import com.letv.portal.service.openstack.impl.OpenStackServiceGroup;
import com.letv.portal.service.openstack.impl.OpenStackServiceImpl;
import com.letv.portal.service.openstack.local.service.LocalRcCountService;
import org.jclouds.openstack.nova.v2_0.NovaApi;
import org.jclouds.openstack.nova.v2_0.domain.Address;
import org.jclouds.openstack.nova.v2_0.domain.Flavor;
import org.jclouds.openstack.nova.v2_0.domain.Server;
import org.jclouds.openstack.v2_0.domain.Link;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.letv.common.exception.MatrixException;
import com.letv.common.paging.impl.Page;
import com.letv.portal.service.common.IUserService;
import com.letv.portal.service.openstack.OpenStackService;
import com.letv.portal.service.openstack.cronjobs.VmSyncService;
import com.letv.portal.service.openstack.cronjobs.impl.cache.SyncLocalApiCache;
import com.letv.portal.service.openstack.exception.OpenStackException;

/**
 * Created by zhouxianguang on 2015/9/28.
 */
@Service
public class VmSyncServiceImpl extends AbstractSyncServiceImpl implements VmSyncService {

    @Autowired
    private ICloudvmServerService cloudvmServerService;

    @Autowired
    private ICloudvmServerAddressService cloudvmServerAddressService;

    @Autowired
    private ICloudvmServerMetadataService cloudvmServerMetadataService;

    @Autowired
    private ICloudvmServerLinkService cloudvmServerLinkService;

    @Autowired
    private ICloudvmVolumeService cloudvmVolumeService;

    @Autowired
    private ICloudvmImageService cloudvmImageService;

    @Autowired
    private IUserService userService;

    @Autowired
    private OpenStackService openStackService;

    @Override
    public void sync(int recordsPerPage) throws MatrixException {
        SyncLocalApiCache apiCache = new SyncLocalApiCache();
        try {
//            Map<Long, OpenStackSession> userIdToOpenStackSession = new HashMap<Long, OpenStackSession>();
            Long minId = null;
            int currentPage = 1;
            List<CloudvmServer> cloudvmServers = cloudvmServerService.selectForSync(minId, new Page(currentPage, recordsPerPage));
            while (!cloudvmServers.isEmpty()) {
                for (final CloudvmServer cloudvmServer : cloudvmServers) {
                    NovaApi novaApi = apiCache.getApi(cloudvmServer.getCreateUser(), NovaApi.class);
//                    OpenStackSession openStackSession = userIdToOpenStackSession.get(cloudvmServer.getCreateUser());
//                    if (openStackSession == null) {
//                        openStackSession = createOpenStackSession(cloudvmServer.getCreateUser());
//                    }
//                    VMManagerImpl vmManager = (VMManagerImpl) openStackSession.getVMManager();
//                    vmManager.runWithApi(new ApiRunnable<NovaApi, Void>() {
//                        @Override
//                        public Void run(NovaApi novaApi) throws Exception {
                    Server server = novaApi.getServerApi(cloudvmServer.getRegion()).get(cloudvmServer.getServerId());
                    if (server == null) {
                        delete(cloudvmServer);
                    } else {
                        update(cloudvmServer, server);
                    }
//                            return null;
//                        }
//                    });
                }
                minId = cloudvmServers.get(cloudvmServers.size() - 1).getId();
                currentPage++;
                cloudvmServers = cloudvmServerService.selectForSync(minId, new Page(currentPage, recordsPerPage));
            }
        } catch (OpenStackException e) {
            throw e.matrixException();
        } finally {
            apiCache.close();
        }
    }

    public void update(CloudvmServer cloudvmServer, Server server) {
//        boolean needUpdate = false;
//
//        if (!StringUtils.equals(cloudvmServer.getName(), server.getName())) {
//            cloudvmServer.setName(server.getName());
//            needUpdate = true;
//        }
//        if (!StringUtils.equals(cloudvmServer.getStatus(), server.getStatus().name())) {
//            cloudvmServer.setStatus(server.getStatus().name());
//            needUpdate = true;
//        }
//        String taskState = null;
//        String vmState = null;
//        Integer powerState = null;
//        Optional<ServerExtendedStatus> serverExtendedStatusOptional = server
//                .getExtendedStatus();
//        if (!serverExtendedStatusOptional.isPresent()) {
//            ServerExtendedStatus serverExtendedStatus = serverExtendedStatusOptional.get();
//            taskState = serverExtendedStatus.getTaskState();
//            vmState = serverExtendedStatus.getVmState();
//            powerState = serverExtendedStatus.getPowerState();
//        }
//        if (!StringUtils.equals(cloudvmServer.getExtendedStatusTaskState(), taskState)) {
//            cloudvmServer.setExtendedStatusTaskState(taskState);
//            needUpdate = true;
//        }
//        if (!StringUtils.equals(cloudvmServer.getExtendedStatusVmState(), vmState)) {
//            cloudvmServer.setExtendedStatusVmState(vmState);
//            needUpdate = true;
//        }
//        if (!ObjectUtils.equals(cloudvmServer.getExtendedPowerState(), powerState)) {
//            cloudvmServer.setExtendedPowerState(powerState);
//            needUpdate = true;
//        }
//
//        if (needUpdate) {
//            cloudvmServerService.update(cloudvmServer);
//        }
//
//        syncAddress(cloudvmServer, server);
//        syncMetadata(cloudvmServer, server);
//        syncLink(cloudvmServer, server);
    }

//    private void syncLink(CloudvmServer cloudvmServer, Server server) {
//        Set<Link> remoteLinks = server.getLinks();
//        List<CloudvmServerLink> localLinks = cloudvmServerLinkService.selectByRegionAndServerId(cloudvmServer.getRegion(), server.getId());
//
//        for (CloudvmServerLink localLink : localLinks) {
//            boolean isRemoteLinkExists = false;
//            for (Link remoteLink : remoteLinks) {
//                if (CloudvmServerLinkUtil.equal(localLink, remoteLink)) {
//                    isRemoteLinkExists = true;
//                }
//            }
//            if (!isRemoteLinkExists) {
//                cloudvmServerLinkService.delete(localLink);
//            }
//        }
//
//        for (Link remoteLink : remoteLinks) {
//            boolean isLocalLinkExists = false;
//            for (CloudvmServerLink localLink : localLinks) {
//                if (CloudvmServerLinkUtil.equal(localLink, remoteLink)) {
//                    isLocalLinkExists = true;
//                }
//            }
//            if (!isLocalLinkExists) {
//                CloudvmServerLink localServerLink = new CloudvmServerLink();
//                localServerLink.setRegion(cloudvmServer.getRegion());
//                localServerLink.setServerId(cloudvmServer.getServerId());
//                localServerLink.setHref(remoteLink.getHref().toString());
//                localServerLink.setRelation(remoteLink.getRelation().value());
//                if (remoteLink.getType().isPresent()) {
//                    localServerLink.setType(remoteLink.getType().get());
//                }
//                localServerLink.setCreateUser(cloudvmServer.getCreateUser());
//                cloudvmServerLinkService.insert(localServerLink);
//            }
//        }
//    }
//
//    private void syncMetadata(CloudvmServer cloudvmServer, Server server) {
//        Map<String, String> remoteMetadatas = server.getMetadata();
//        List<CloudvmServerMetadata> localMetadatas = cloudvmServerMetadataService.selectByRegionAndServerId(cloudvmServer.getRegion(), server.getId());
//
//        for (CloudvmServerMetadata localMetadata : localMetadatas) {
//            boolean isRemoteMetadataExists = false;
//            for (Map.Entry<String, String> remoteMetadata : remoteMetadatas.entrySet()) {
//                if (CloudvmServerMetadataUtil.equal(localMetadata, remoteMetadata)) {
//                    isRemoteMetadataExists = true;
//                }
//            }
//            if (!isRemoteMetadataExists) {
//                cloudvmServerMetadataService.delete(localMetadata);
//            }
//        }
//
//        for (Map.Entry<String, String> remoteMetadata : remoteMetadatas.entrySet()) {
//            boolean isLocalMetadataExists = false;
//            for (CloudvmServerMetadata localMetadata : localMetadatas) {
//                if (CloudvmServerMetadataUtil.equal(localMetadata, remoteMetadata)) {
//                    isLocalMetadataExists = true;
//                }
//            }
//            if (!isLocalMetadataExists) {
//                CloudvmServerMetadata localServerMetadata = new CloudvmServerMetadata();
//                localServerMetadata.setRegion(cloudvmServer.getRegion());
//                localServerMetadata.setServerId(cloudvmServer.getServerId());
//                localServerMetadata.setKey(remoteMetadata.getKey());
//                localServerMetadata.setValue(remoteMetadata.getValue());
//                localServerMetadata.setCreateUser(cloudvmServer.getCreateUser());
//                cloudvmServerMetadataService.insert(localServerMetadata);
//            }
//        }
//    }
//
//    private void syncAddress(CloudvmServer cloudvmServer, Server server) {
//        Multimap<String, Address> remoteAddresses = server.getAddresses();
//        List<CloudvmServerAddress> localAddresses = cloudvmServerAddressService.selectByRegionAndServerId(cloudvmServer.getRegion(), server.getId());
//
//        for (CloudvmServerAddress localAddress : localAddresses) {
//            boolean isRemoteAddressExists = false;
//            for (Map.Entry<String, Address> remoteMapEntry : remoteAddresses.entries()) {
//                if (CloudvmServerAddressUtil.equal(localAddress, remoteMapEntry)) {
//                    isRemoteAddressExists = true;
//                }
//            }
//            if (!isRemoteAddressExists) {
//                cloudvmServerAddressService.delete(localAddress);
//            }
//        }
//
//        for (Map.Entry<String, Address> remoteMapEntry : remoteAddresses.entries()) {
//            boolean isLocalAddressExists = false;
//            for (CloudvmServerAddress localAddress : localAddresses) {
//                if (CloudvmServerAddressUtil.equal(localAddress, remoteMapEntry)) {
//                    isLocalAddressExists = true;
//                }
//            }
//            if (!isLocalAddressExists) {
//                CloudvmServerAddress localServerAddress = new CloudvmServerAddress();
//                localServerAddress.setRegion(cloudvmServer.getRegion());
//                localServerAddress.setServerId(cloudvmServer.getServerId());
//                localServerAddress.setNetworkName(remoteMapEntry.getKey());
//                localServerAddress.setAddr(remoteMapEntry.getValue().getAddr());
//                localServerAddress.setVersion(remoteMapEntry.getValue().getVersion());
//                localServerAddress.setCreateUser(cloudvmServer.getCreateUser());
//                cloudvmServerAddressService.insert(localServerAddress);
//            }
//        }
//    }

    public void delete(CloudvmServer cloudvmServer) {
        cloudvmServerAddressService.deleteByRegionAndServerId(cloudvmServer.getRegion(), cloudvmServer.getServerId());
        cloudvmServerMetadataService.deleteByRegionAndServerId(cloudvmServer.getRegion(), cloudvmServer.getServerId());
        cloudvmServerLinkService.deleteByRegionAndServerId(cloudvmServer.getRegion(), cloudvmServer.getServerId());
        cloudvmServerService.delete(cloudvmServer);
    }

    @Override
    public void create(long userId, String region, Server server) {
//        CloudvmServer cloudvmServer = new CloudvmServer();
//        cloudvmServer.setRegion(region);
//        cloudvmServer.setServerId(server.getId());
//        cloudvmServer.setFlavorId(server.getFlavor().getId());
//        cloudvmServer.setName(server.getName());
//        cloudvmServer.setServerUuid(server.getUuid());
//        cloudvmServer.setTenantId(server.getTenantId());
//        cloudvmServer.setUserId(server.getUserId());
//        cloudvmServer.setUpdated(server.getUpdated());
//        cloudvmServer.setCreated(server.getCreated());
//        cloudvmServer.setHostId(server.getHostId());
//        cloudvmServer.setAccessIpv4(server.getAccessIPv4());
//        cloudvmServer.setAccessIpv6(server.getAccessIPv6());
//        cloudvmServer.setStatus(server.getStatus().value());
//        cloudvmServer.setImageId(server.getImage().getId());
//        cloudvmServer.setKeyName(server.getKeyName());
//        cloudvmServer.setConfigDrive(server.getConfigDrive());
//        if (server.getExtendedStatus().isPresent()) {
//            ServerExtendedStatus serverExtendedStatus = server.getExtendedStatus().get();
//            cloudvmServer.setExtendedStatusTaskState(serverExtendedStatus.getTaskState());
//            cloudvmServer.setExtendedStatusVmState(serverExtendedStatus.getVmState());
//            cloudvmServer.setExtendedPowerState(serverExtendedStatus.getPowerState());
//        }
//        if (server.getExtendedAttributes().isPresent()) {
//            ServerExtendedAttributes serverExtendedAttributes = server.getExtendedAttributes().get();
//            cloudvmServer.setExtendedAttributesHostName(serverExtendedAttributes.getHostName());
//            cloudvmServer.setExtendedAttributesHypervisorHostName(serverExtendedAttributes.getHypervisorHostName());
//            cloudvmServer.setExtendedAttributesInstanceName(serverExtendedAttributes.getInstanceName());
//        }
//        if (server.getDiskConfig().isPresent()) {
//            cloudvmServer.setDiskConfig(server.getDiskConfig().get());
//        }
//        if (server.getAvailabilityZone().isPresent()) {
//            cloudvmServer.setAvailabilityZone(server.getAvailabilityZone().get());
//        }
//        cloudvmServer.setCreateUser(userId);
//        cloudvmServerService.insert(cloudvmServer);
//
//        createAddress(userId, region, server);
//        createMetadata(userId, region, server);
//        createLink(userId, region, server);
    }

    @Override
    public void update(String region, Server server) {
        CloudvmServer cloudvmServer = cloudvmServerService.selectByServerId(region, server.getId());
        if (cloudvmServer != null) {
            update(cloudvmServer, server);
        }
    }

    @Override
    public void delete(String region, String vmId) {
        CloudvmServer cloudvmServer = cloudvmServerService.selectByServerId(region, vmId);
        if (cloudvmServer != null) {
            delete(cloudvmServer);
        }
    }

    @Override
    public void recordVmDeleted(long userVoUserId, String region, String vmId, Flavor flavor) throws OpenStackException {
        LocalRcCountService localRcCountService = OpenStackServiceImpl.getOpenStackServiceGroup().getLocalRcCountService();
        localRcCountService.decRcCount(userVoUserId, userVoUserId, region, CloudvmRcCountType.SERVER);
        localRcCountService.decRcCount(userVoUserId, region, CloudvmRcCountType.CPU, flavor.getVcpus());
        localRcCountService.decRcCount(userVoUserId, region, CloudvmRcCountType.MEMORY, flavor.getRam());

        OpenStackServiceGroup openStackServiceGroup = OpenStackServiceImpl.getOpenStackServiceGroup();
        openStackServiceGroup.getVolumeSyncService()
                .syncStatusAfterServerDeleted(userVoUserId, region, vmId);
        openStackServiceGroup.getImageSyncService().cleanServerIdAfterServerDeleted(userVoUserId, region, vmId);
    }

    @Override
    public void onVmRenamed(long tenantId, String region, String vmId, String name) {
        List<CloudvmVolume> cloudvmVolumes = cloudvmVolumeService.selectByServerIdAndStatus(tenantId, region, vmId, null);
        for (CloudvmVolume cloudvmVolume : cloudvmVolumes) {
            cloudvmVolume.setServerName(name);
            cloudvmVolumeService.update(cloudvmVolume);
        }
        List<CloudvmImage> cloudvmImages = cloudvmImageService.selectVmSnapshotByServerId(tenantId, region, vmId);
        for (CloudvmImage cloudvmImage : cloudvmImages) {
            cloudvmImage.setServerName(name);
            cloudvmImageService.update(cloudvmImage);
        }
    }

    @SuppressWarnings("unused")
	private void createLink(long userId, String region, Server server) {
        for (Link remoteLink : server.getLinks()) {
            CloudvmServerLink localServerLink = new CloudvmServerLink();
            localServerLink.setRegion(region);
            localServerLink.setServerId(server.getId());
            localServerLink.setHref(remoteLink.getHref().toString());
            localServerLink.setRelation(remoteLink.getRelation().value());
            if (remoteLink.getType().isPresent()) {
                localServerLink.setType(remoteLink.getType().get());
            }
            localServerLink.setCreateUser(userId);
            cloudvmServerLinkService.insert(localServerLink);
        }
    }

    @SuppressWarnings("unused")
	private void createMetadata(long userId, String region, Server server) {
        for (Map.Entry<String, String> remoteMetadata : server.getMetadata().entrySet()) {
            CloudvmServerMetadata localServerMetadata = new CloudvmServerMetadata();
            localServerMetadata.setRegion(region);
            localServerMetadata.setServerId(server.getId());
            localServerMetadata.setKey(remoteMetadata.getKey());
            localServerMetadata.setValue(remoteMetadata.getValue());
            localServerMetadata.setCreateUser(userId);
            cloudvmServerMetadataService.insert(localServerMetadata);
        }
    }

    @SuppressWarnings("unused")
	private void createAddress(long userId, String region, Server server) {
        for (Map.Entry<String, Address> mapEntry : server.getAddresses().entries()) {
            CloudvmServerAddress localServerAddress = new CloudvmServerAddress();
            localServerAddress.setRegion(region);
            localServerAddress.setServerId(server.getId());
            localServerAddress.setNetworkName(mapEntry.getKey());
            localServerAddress.setAddr(mapEntry.getValue().getAddr());
            localServerAddress.setVersion(mapEntry.getValue().getVersion());
            localServerAddress.setCreateUser(userId);
            cloudvmServerAddressService.insert(localServerAddress);
        }
    }

}
