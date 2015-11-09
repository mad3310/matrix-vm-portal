package com.letv.portal.service.openstack.resource.service.impl;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableSet;
import com.letv.portal.service.openstack.exception.*;
import com.letv.portal.service.openstack.local.service.LocalKeyPairService;
import com.letv.portal.service.openstack.resource.VMResource;
import com.letv.portal.service.openstack.resource.impl.VMResourceImpl;
import com.letv.portal.service.openstack.resource.service.ResourceService;
import com.letv.portal.service.openstack.util.JsonUtil;
import com.letv.portal.service.openstack.util.ThreadUtil;
import com.letv.portal.service.openstack.util.Timeout;
import com.letv.portal.service.openstack.util.function.Function;
import com.letv.portal.service.openstack.util.function.Function1;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.type.TypeReference;
import org.jclouds.openstack.cinder.v1.CinderApi;
import org.jclouds.openstack.glance.v1_0.GlanceApi;
import org.jclouds.openstack.neutron.v2.NeutronApi;
import org.jclouds.openstack.neutron.v2.domain.IP;
import org.jclouds.openstack.neutron.v2.domain.Network;
import org.jclouds.openstack.neutron.v2.domain.Port;
import org.jclouds.openstack.neutron.v2.domain.Subnet;
import org.jclouds.openstack.neutron.v2.features.NetworkApi;
import org.jclouds.openstack.neutron.v2.features.PortApi;
import org.jclouds.openstack.neutron.v2.features.SubnetApi;
import org.jclouds.openstack.nova.v2_0.NovaApi;
import org.jclouds.openstack.nova.v2_0.domain.*;
import org.jclouds.openstack.nova.v2_0.extensions.AttachInterfaceApi;
import org.jclouds.openstack.nova.v2_0.extensions.KeyPairApi;
import org.jclouds.openstack.nova.v2_0.extensions.QuotaApi;
import org.jclouds.openstack.nova.v2_0.features.ServerApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Closeable;
import java.text.MessageFormat;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by zhouxianguang on 2015/10/30.
 */
@Service
public class ResourceServiceImpl implements ResourceService {

    @Autowired
    private LocalKeyPairService localKeyPairService;

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

    @Override
    public void checkRegion(String region, Closeable... apis) throws RegionNotFoundException {
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

    private KeyPairApi getKeyPairApi(NovaApi novaApi, String region) throws APINotAvailableException {
        Optional<KeyPairApi> keyPairApiOptional = novaApi.getKeyPairApi(region);
        if (!keyPairApiOptional.isPresent()) {
            throw new APINotAvailableException(KeyPairApi.class);
        }
        return keyPairApiOptional.get();
    }

    private QuotaApi getNovaQuotaApi(NovaApi novaApi, String region) throws APINotAvailableException {
        Optional<QuotaApi> quotaApiOptional = novaApi.getQuotaApi(region);
        if (!quotaApiOptional.isPresent()) {
            throw new APINotAvailableException(QuotaApi.class);
        }
        return quotaApiOptional.get();
    }

    private Network getPrivateNetwork(NetworkApi networkApi, String networkId) throws ResourceNotFoundException {
        Network network = networkApi.get(networkId);
        if (network == null || network.getExternal() || network.getShared()) {
            throw new ResourceNotFoundException("Private Network", "私有网络", networkId);
        }
        return network;
    }

    @Override
    public void attachVmsToSubnet(NovaApi novaApi, NeutronApi neutronApi, String region, String vmIds, final String subnetId) throws OpenStackException {
        checkRegion(region, novaApi, neutronApi);

        final ServerApi serverApi = novaApi.getServerApi(region);

        SubnetApi subnetApi = neutronApi.getSubnetApi(region);
        final Subnet privateSubnet = getSubnet(subnetApi, subnetId);
        final NetworkApi networkApi = neutronApi.getNetworkApi(region);
        getPrivateNetwork(networkApi, privateSubnet.getNetworkId());

        final AttachInterfaceApi attachInterfaceApi = getAttachInterfaceApi(novaApi, region);
        final PortApi portApi = neutronApi.getPortApi(region);

        List<String> vmIdList = JsonUtil.fromJson(vmIds, new TypeReference<List<String>>() {
        });
        List<Exception> exceptions = ThreadUtil.concurrentFilter(vmIdList, new Function1<Exception, String>() {
            @Override
            public Exception apply(String vmId) throws Exception {
                try {
                    getVm(serverApi, vmId);
                    List<InterfaceAttachment> interfaceAttachments = attachInterfaceApi.list(vmId).toList();
                    for (InterfaceAttachment interfaceAttachment : interfaceAttachments) {
                        String attachedNetworkId = interfaceAttachment.getNetworkId();
                        if (attachedNetworkId != null) {
                            Network attachedNetwork = networkApi.get(attachedNetworkId);
                            if (attachedNetwork.getShared()) {
                                throw new UserOperationException("VM is attached to shared network:" + attachedNetworkId, MessageFormat.format("虚拟机“{0}”已加入基础网络：“{1}”，不能关联私有子网", vmId, attachedNetworkId));
                            } else {
                                if (interfaceAttachment.getFixedIps() != null && !interfaceAttachment.getFixedIps().isEmpty()) {
                                    String attachedSubnetId = interfaceAttachment.getFixedIps().iterator().next().getSubnetId();
                                    throw new UserOperationException("VM is attached to subnet:" + attachedSubnetId, MessageFormat.format("虚拟机“{0}”已关联私有子网：“{1}”，需要先解除关联才能关联私有子网", vmId, attachedSubnetId));
                                }
                            }
                        }
                    }
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
                } catch (Exception ex) {
                    return ex;
                }
                return null;
            }
        });
        if (!exceptions.isEmpty()) {
            throw new OpenStackCompositeException(exceptions);
        }
    }

    @Override
    public void detachVmsFromSubnet(NovaApi novaApi, NeutronApi neutronApi, String region, String vmIds, final String subnetId) throws OpenStackException {
        checkRegion(region, novaApi, neutronApi);

        final ServerApi serverApi = novaApi.getServerApi(region);

        SubnetApi subnetApi = neutronApi.getSubnetApi(region);
        final Subnet privateSubnet = getSubnet(subnetApi, subnetId);
        final NetworkApi networkApi = neutronApi.getNetworkApi(region);
        getPrivateNetwork(networkApi, privateSubnet.getNetworkId());

        final AttachInterfaceApi attachInterfaceApi = getAttachInterfaceApi(novaApi, region);

        List<String> vmIdList = JsonUtil.fromJson(vmIds, new TypeReference<List<String>>() {
        });
        List<Exception> exceptions = ThreadUtil.concurrentFilter(vmIdList, new Function1<Exception, String>() {
            @Override
            public Exception apply(final String vmId) throws Exception {
                try {
                    getVm(serverApi, vmId);
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
                        throw new UserOperationException("VM is not attached to subnet:" + subnetId, MessageFormat.format("虚拟机“{0}”未关联到私有子网“{1}”，不能解除与这个私有子网的关联。", vmId, subnetId));
                    }

                    boolean isSuccess = attachInterfaceApi.delete(vmId, findedInterfaceAttachment.getPortId());
                    if (!isSuccess) {
                        throw new OpenStackException("InterfaceAttachment delete failed.",
                                MessageFormat.format("虚拟机“{0}”和子网“{1}”解除关联失败。", vmId, subnetId));
                    }

                    final String attachmentId = findedInterfaceAttachment.getPortId();
                    ThreadUtil.waiting(new Function<Boolean>() {
                        @Override
                        public Boolean apply() throws Exception {
                            return attachInterfaceApi.get(vmId, attachmentId) != null;
                        }
                    }, new Timeout().time(5L).unit(TimeUnit.MINUTES));
                } catch (Exception ex) {
                    return ex;
                }
                return null;
            }
        });
        if (!exceptions.isEmpty()) {
            throw new OpenStackCompositeException(exceptions);
        }
    }

    @Override
    public List<VMResource> listVmNotInAnyNetwork(NovaApi novaApi, NeutronApi neutronApi, String region) throws OpenStackException {
        checkRegion(region, novaApi, neutronApi);
        ServerApi serverApi = novaApi.getServerApi(region);
        final AttachInterfaceApi attachInterfaceApi = getAttachInterfaceApi(novaApi, region);

        List<Server> servers = ThreadUtil.concurrentFilter(serverApi.listInDetail().concat().toList(), new Function1<Server, Server>() {
            @Override
            public Server apply(Server server) throws Exception {
                for (InterfaceAttachment interfaceAttachment : attachInterfaceApi.list(server.getId())) {
                    if (interfaceAttachment.getNetworkId() != null) {
                        return null;
                    }
                }
                return server;
            }
        });

        List<VMResource> vmResources = new LinkedList<VMResource>();
        for (Server server : servers) {
            vmResources.add(new VMResourceImpl(region, server));
        }
        return vmResources;
    }

    @Override
    public List<VMResource> listVmCouldAttachSubnet(NovaApi novaApi, NeutronApi neutronApi, String region, final String subnetId) throws OpenStackException {
        checkRegion(region, novaApi, neutronApi);
        ServerApi serverApi = novaApi.getServerApi(region);
        final AttachInterfaceApi attachInterfaceApi = getAttachInterfaceApi(novaApi, region);
        SubnetApi subnetApi = neutronApi.getSubnetApi(region);
        NetworkApi networkApi = neutronApi.getNetworkApi(region);
        Subnet subnet = getSubnet(subnetApi, subnetId);
        final Network network = getPrivateNetwork(networkApi, subnet.getNetworkId());

        List<Server> servers = ThreadUtil.concurrentFilter(serverApi.listInDetail().concat().toList(), new Function1<Server, Server>() {
            @Override
            public Server apply(Server server) throws Exception {
                for (InterfaceAttachment interfaceAttachment : attachInterfaceApi.list(server.getId())) {
                    if (interfaceAttachment.getNetworkId() != null) {
                        if (StringUtils.equals(interfaceAttachment.getNetworkId(), network.getId())) {
                            ImmutableSet<FixedIP> fixedIPs = interfaceAttachment.getFixedIps();
                            for (FixedIP fixedIP : fixedIPs) {
                                if (StringUtils.equals(fixedIP.getSubnetId(), subnetId)) {
                                    return null;
                                }
                            }
                        } else {
                            return null;
                        }
                    }
                }
                return server;
            }
        });

        List<VMResource> vmResources = new LinkedList<VMResource>();
        for (Server server : servers) {
            vmResources.add(new VMResourceImpl(region, server));
        }
        return vmResources;
    }

    @Override
    public List<VMResource> listVmAttachedSubnet(NovaApi novaApi, NeutronApi neutronApi, String region, final String subnetId) throws OpenStackException {
        checkRegion(region, novaApi, neutronApi);
        ServerApi serverApi = novaApi.getServerApi(region);
        final AttachInterfaceApi attachInterfaceApi = getAttachInterfaceApi(novaApi, region);

        List<Server> servers = ThreadUtil.concurrentFilter(serverApi.listInDetail().concat().toList(), new Function1<Server, Server>() {
            @Override
            public Server apply(Server server) throws Exception {
                for (InterfaceAttachment interfaceAttachment : attachInterfaceApi.list(server.getId())) {
                    if (interfaceAttachment.getNetworkId() != null && interfaceAttachment.getFixedIps() != null && !interfaceAttachment.getFixedIps().isEmpty()) {
                        for (FixedIP fixedIP : interfaceAttachment.getFixedIps()) {
                            if (StringUtils.equals(fixedIP.getSubnetId(), subnetId)) {
                                return server;
                            }
                        }
                    }
                }
                return null;
            }
        });

        List<VMResource> vmResources = new LinkedList<VMResource>();
        for (Server server : servers) {
            vmResources.add(new VMResourceImpl(region, server));
        }
        return vmResources;
    }

    @Override
    public String createKeyPair(NovaApi novaApi, long userVoUserId, String tenantId, String region, String name) throws OpenStackException {
        checkRegion(region, novaApi);
        if (StringUtils.isEmpty(name)) {
            throw new UserOperationException("Name can not be empty or null", "名称不能为空");
        }

        KeyPairApi keyPairApi = getKeyPairApi(novaApi, region);
        List<KeyPair> keyPairs = keyPairApi.list().toList();
        for (KeyPair keyPair : keyPairs) {
            if (StringUtils.equals(keyPair.getName(), name)) {
                throw new UserOperationException("Name cannot be repeated.", "名称不能重复");
            }
        }

        QuotaApi quotaApi = getNovaQuotaApi(novaApi, region);
        Quota quota = quotaApi.getByTenant(tenantId);
        if (quota == null) {
            throw new OpenStackException("KeyPair quota is not available.", "密钥配额不可用。");
        }
        if (keyPairs.size() + 1 > quota.getKeyPairs()) {
            throw new UserOperationException(
                    "KeyPair count exceeding the quota.",
                    "密钥数量超过配额。");
        }

        KeyPair keyPair = keyPairApi.create(name);

        localKeyPairService.create(userVoUserId, userVoUserId, region, keyPair);

        return keyPair.getPrivateKey();
    }

    @Override
    public void checkCreateKeyPair(NovaApi novaApi, long userVoUserId, String tenantId, String region, String name) throws OpenStackException {
        checkRegion(region, novaApi);
        if (StringUtils.isEmpty(name)) {
            throw new UserOperationException("Name can not be empty or null", "名称不能为空");
        }

        KeyPairApi keyPairApi = getKeyPairApi(novaApi, region);
        List<KeyPair> keyPairs = keyPairApi.list().toList();
        for (KeyPair keyPair : keyPairs) {
            if (StringUtils.equals(keyPair.getName(), name)) {
                throw new UserOperationException("Name cannot be repeated.", "名称不能重复");
            }
        }

        QuotaApi quotaApi = getNovaQuotaApi(novaApi, region);
        Quota quota = quotaApi.getByTenant(tenantId);
        if (quota == null) {
            throw new OpenStackException("KeyPair quota is not available.", "密钥配额不可用。");
        }
        if (keyPairs.size() + 1 > quota.getKeyPairs()) {
            throw new UserOperationException(
                    "KeyPair count exceeding the quota.",
                    "密钥数量超过配额。");
        }
    }

    private KeyPair getKeyPair(KeyPairApi keyPairApi, String name) throws ResourceNotFoundException {
        KeyPair keyPair = keyPairApi.get(name);
        if (keyPair == null) {
            throw new ResourceNotFoundException("KeyPair", "密钥", name);
        }
        return keyPair;
    }

    @Override
    public void deleteKeyPair(NovaApi novaApi, long userVoUserId, String tenantId, String region, final String name) throws OpenStackException {
        checkRegion(region, novaApi);
        if (StringUtils.isEmpty(name)) {
            throw new UserOperationException("Name can not be empty or null", "名称不能为空");
        }

        final KeyPairApi keyPairApi = getKeyPairApi(novaApi, region);
        getKeyPair(keyPairApi, name);

        boolean isSuccess = keyPairApi.delete(name);
        if (!isSuccess) {
            throw new OpenStackException("KeyPair delete failed.",
                    "密钥删除失败。");
        }

        localKeyPairService.delete(userVoUserId, region, name);

        ThreadUtil.waiting(new Function<Boolean>() {
            @Override
            public Boolean apply() throws Exception {
                return keyPairApi.get(name) != null;
            }
        }, new Timeout().time(5L).unit(TimeUnit.MINUTES));
    }

}
