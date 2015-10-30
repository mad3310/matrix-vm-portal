package com.letv.portal.service.openstack.resource.service.impl;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableSet;
import com.letv.portal.service.openstack.exception.*;
import com.letv.portal.service.openstack.resource.service.ResourceService;
import com.letv.portal.service.openstack.util.Function;
import com.letv.portal.service.openstack.util.Util;
import org.apache.commons.lang.StringUtils;
import org.jclouds.openstack.cinder.v1.CinderApi;
import org.jclouds.openstack.glance.v1_0.GlanceApi;
import org.jclouds.openstack.neutron.v2.NeutronApi;
import org.jclouds.openstack.neutron.v2.domain.*;
import org.jclouds.openstack.neutron.v2.features.NetworkApi;
import org.jclouds.openstack.neutron.v2.features.PortApi;
import org.jclouds.openstack.neutron.v2.features.SubnetApi;
import org.jclouds.openstack.nova.v2_0.NovaApi;
import org.jclouds.openstack.nova.v2_0.domain.FixedIP;
import org.jclouds.openstack.nova.v2_0.domain.InterfaceAttachment;
import org.jclouds.openstack.nova.v2_0.domain.Server;
import org.jclouds.openstack.nova.v2_0.extensions.AttachInterfaceApi;
import org.jclouds.openstack.nova.v2_0.features.ServerApi;
import org.springframework.stereotype.Service;

import java.io.Closeable;
import java.util.List;

/**
 * Created by zhouxianguang on 2015/10/30.
 */
@Service
public class ResourceServiceImpl implements ResourceService {

    private void checkRegion(NovaApi novaApi, String region) throws RegionNotFoundException {
        if (!novaApi.getConfiguredRegions().contains(region)) {
            throw new RegionNotFoundException(region);
        }
    }

    private void checkRegion(NeutronApi neutronApi, String region) throws RegionNotFoundException {
        if (!neutronApi.getConfiguredRegions().contains(region)) {
            throw new RegionNotFoundException(region);
        }
    }

    private void checkRegion(GlanceApi glanceApi
            , String region) throws RegionNotFoundException {
        if (!glanceApi.getConfiguredRegions().contains(region)) {
            throw new RegionNotFoundException(region);
        }
    }

    private void checkRegion(CinderApi cinderApi, String region) throws RegionNotFoundException {
        if (!cinderApi.getConfiguredRegions().contains(region)) {
            throw new RegionNotFoundException(region);
        }
    }

    private void checkRegion(String region, Closeable... apis) throws RegionNotFoundException {
        for (Closeable api : apis) {
            if (api instanceof NovaApi) {
                checkRegion((NovaApi) api, region);
            } else if (api instanceof NeutronApi) {
                checkRegion((NeutronApi) api, region);
            } else if (api instanceof CinderApi) {
                checkRegion((CinderApi) api, region);
            } else if (api instanceof GlanceApi) {
                checkRegion((GlanceApi) api, region);
            }
        }
    }

    private Server getVm(ServerApi serverApi, String vmId) throws ResourceNotFoundException {
        Server server = serverApi.get(vmId);
        if (server == null) {
            throw new ResourceNotFoundException("Vm", "虚拟机", vmId);
        }
        return server;
    }

    private Subnet getSubnet(SubnetApi subnetApi, String subnetId) throws ResourceNotFoundException {
        Subnet subnet = subnetApi.get(subnetId);
        if (subnet == null) {
            throw new ResourceNotFoundException("Subnet", "子网", subnetId);
        }
        return subnet;
    }

    private AttachInterfaceApi getAttachInterfaceApi(NovaApi novaApi, String region) throws APINotAvailableException {
        Optional<AttachInterfaceApi> attachInterfaceApiOptional = novaApi.getAttachInterfaceApi(region);
        if (!attachInterfaceApiOptional.isPresent()) {
            throw new APINotAvailableException(AttachInterfaceApi.class);
        }
        return attachInterfaceApiOptional.get();
    }

    private Network getPrivateNetwork(NetworkApi networkApi, String networkId) throws ResourceNotFoundException {
        Network network = networkApi.get(networkId);
        if (network == null || network.getExternal() || network.getShared()) {
            throw new ResourceNotFoundException("Private Network", "私有网络", networkId);
        }
        return network;
    }

    @Override
    public void attachVmToSubnet(NovaApi novaApi, NeutronApi neutronApi, String region, String vmId, String subnetId) throws OpenStackException {
        checkRegion(region, novaApi, neutronApi);

        ServerApi serverApi = novaApi.getServerApi(region);
        getVm(serverApi, vmId);

        SubnetApi subnetApi = neutronApi.getSubnetApi(region);
        Subnet privateSubnet = getSubnet(subnetApi, subnetId);
        NetworkApi networkApi = neutronApi.getNetworkApi(region);
        getPrivateNetwork(networkApi, privateSubnet.getNetworkId());

        AttachInterfaceApi attachInterfaceApi = getAttachInterfaceApi(novaApi, region);
        List<InterfaceAttachment> interfaceAttachments = attachInterfaceApi.list(vmId).toList();
        for (InterfaceAttachment interfaceAttachment : interfaceAttachments) {
            String attachedNetworkId = interfaceAttachment.getNetworkId();
            if (attachedNetworkId != null) {
                Network attachedNetwork = networkApi.get(attachedNetworkId);
                if (attachedNetwork.getShared()) {
                    throw new UserOperationException("VM is attached to shared network:" + attachedNetworkId, "虚拟机已加入基础网络：" + attachedNetworkId + "，不能关联私有子网");
                } else {
                    if (interfaceAttachment.getFixedIps() != null && !interfaceAttachment.getFixedIps().isEmpty()) {
                        String attachedSubnetId = interfaceAttachment.getFixedIps().iterator().next().getSubnetId();
                        throw new UserOperationException("VM is attached to subnet:" + attachedSubnetId, "虚拟机已关联私有子网：" + attachedSubnetId + "，需要先解除关联才能关联私有子网");
                    }
                }
            }
        }

        PortApi portApi = neutronApi.getPortApi(region);
        Port subnetPort = portApi
                .create(Port
                        .createBuilder(privateSubnet.getNetworkId())
                        .fixedIps(
                                ImmutableSet.<IP>of(IP
                                        .builder()
                                        .subnetId(
                                                subnetId).build()))
                        .build());
        attachInterfaceApi.create(vmId, subnetPort.getId());
    }

    @Override
    public void detachVmFromSubnet(NovaApi novaApi, NeutronApi neutronApi, String region, final String vmId, String subnetId) throws OpenStackException {
        checkRegion(region, novaApi, neutronApi);

        ServerApi serverApi = novaApi.getServerApi(region);
        getVm(serverApi, vmId);

        SubnetApi subnetApi = neutronApi.getSubnetApi(region);
        Subnet privateSubnet = getSubnet(subnetApi, subnetId);
        NetworkApi networkApi = neutronApi.getNetworkApi(region);
        getPrivateNetwork(networkApi, privateSubnet.getNetworkId());

        final AttachInterfaceApi attachInterfaceApi = getAttachInterfaceApi(novaApi, region);
        List<InterfaceAttachment> interfaceAttachments = attachInterfaceApi.list(vmId).toList();
        InterfaceAttachment findedInterfaceAttachment = null;
        FindedInterfaceAttachment:
        for (InterfaceAttachment interfaceAttachment : interfaceAttachments) {
            String attachedNetworkId = interfaceAttachment.getNetworkId();
            if (StringUtils.equals(attachedNetworkId, privateSubnet.getNetworkId())) {
                if (interfaceAttachment.getFixedIps() != null && !interfaceAttachment.getFixedIps().isEmpty()) {
                    for (FixedIP fixedIP : interfaceAttachment.getFixedIps()) {
                        if (StringUtils.equals(fixedIP.getSubnetId(), privateSubnet.getId())) {
                            findedInterfaceAttachment = interfaceAttachment;
                            break FindedInterfaceAttachment;
                        }
                    }
                }
            }
        }
        if (findedInterfaceAttachment == null) {
            throw new UserOperationException("VM is not attached to subnet:" + subnetId, "虚拟机未关联到私有子网：" + subnetId + "，不能解除与这个私有子网的关联");
        }

        boolean isSuccess = attachInterfaceApi.delete(vmId, findedInterfaceAttachment.getPortId());
        if (!isSuccess) {
            throw new OpenStackException("InterfaceAttachment delete failed.",
                    "虚拟机和子网解除关联失败。");
        }

        final String attachmentId = findedInterfaceAttachment.getPortId();
        waitingUtil(new Function<Boolean>() {
            @Override
            public Boolean apply() throws Exception {
                return attachInterfaceApi.get(vmId, attachmentId) == null;
            }
        });
    }

    private void waitingUtil(Function<Boolean> checker)
            throws OpenStackException {
        try {
            while (!checker.apply()) {
                Thread.sleep(1000);
            }
        } catch (Exception e) {
            Util.throwException(e);
        }
    }
}
