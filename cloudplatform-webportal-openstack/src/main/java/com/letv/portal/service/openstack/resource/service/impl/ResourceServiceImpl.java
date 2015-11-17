package com.letv.portal.service.openstack.resource.service.impl;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableSet;
import com.letv.common.email.ITemplateMessageSender;
import com.letv.common.email.bean.MailMessage;
import com.letv.common.email.impl.DefaultEmailSender;
import com.letv.common.paging.impl.Page;
import com.letv.portal.model.cloudvm.CloudvmImage;
import com.letv.portal.model.cloudvm.CloudvmVolume;
import com.letv.portal.model.common.CommonQuotaType;
import com.letv.portal.service.cloudvm.ICloudvmImageService;
import com.letv.portal.service.cloudvm.ICloudvmRegionService;
import com.letv.portal.service.cloudvm.ICloudvmVolumeService;
import com.letv.portal.service.openstack.exception.*;
import com.letv.portal.service.openstack.impl.OpenStackServiceImpl;
import com.letv.portal.service.openstack.local.resource.LocalImageResource;
import com.letv.portal.service.openstack.local.resource.LocalVolumeResource;
import com.letv.portal.service.openstack.local.service.LocalCommonQuotaSerivce;
import com.letv.portal.service.openstack.local.service.LocalKeyPairService;
import com.letv.portal.service.openstack.local.service.LocalRegionService;
import com.letv.portal.service.openstack.resource.IPAddresses;
import com.letv.portal.service.openstack.resource.SubnetIp;
import com.letv.portal.service.openstack.resource.VMResource;
import com.letv.portal.service.openstack.resource.VolumeResource;
import com.letv.portal.service.openstack.resource.impl.FlavorResourceImpl;
import com.letv.portal.service.openstack.resource.impl.SubnetResourceImpl;
import com.letv.portal.service.openstack.resource.impl.VMResourceImpl;
import com.letv.portal.service.openstack.resource.manager.impl.NetworkManagerImpl;
import com.letv.portal.service.openstack.resource.manager.impl.VMManagerImpl;
import com.letv.portal.service.openstack.resource.service.ResourceService;
import com.letv.portal.service.openstack.util.*;
import com.letv.portal.service.openstack.util.constants.OpenStackConstants;
import com.letv.portal.service.openstack.util.function.Function;
import com.letv.portal.service.openstack.util.function.Function1;
import com.letv.portal.service.openstack.util.tuple.Tuple2;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.type.TypeReference;
import org.jclouds.openstack.cinder.v1.CinderApi;
import org.jclouds.openstack.glance.v1_0.GlanceApi;
import org.jclouds.openstack.neutron.v2.NeutronApi;
import org.jclouds.openstack.neutron.v2.domain.*;
import org.jclouds.openstack.neutron.v2.domain.Network;
import org.jclouds.openstack.neutron.v2.extensions.RouterApi;
import org.jclouds.openstack.neutron.v2.features.NetworkApi;
import org.jclouds.openstack.neutron.v2.features.PortApi;
import org.jclouds.openstack.neutron.v2.features.SubnetApi;
import org.jclouds.openstack.nova.v2_0.NovaApi;
import org.jclouds.openstack.nova.v2_0.domain.*;
import org.jclouds.openstack.nova.v2_0.domain.FloatingIP;
import org.jclouds.openstack.nova.v2_0.domain.Quota;
import org.jclouds.openstack.nova.v2_0.extensions.AttachInterfaceApi;
import org.jclouds.openstack.nova.v2_0.extensions.FloatingIPApi;
import org.jclouds.openstack.nova.v2_0.extensions.KeyPairApi;
import org.jclouds.openstack.nova.v2_0.extensions.QuotaApi;
import org.jclouds.openstack.nova.v2_0.features.FlavorApi;
import org.jclouds.openstack.nova.v2_0.features.ServerApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Closeable;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;

/**
 * Created by zhouxianguang on 2015/10/30.
 */
@Service
public class ResourceServiceImpl implements ResourceService {

    @Autowired
    private LocalKeyPairService localKeyPairService;

    @Autowired
    private LocalCommonQuotaSerivce localCommonQuotaSerivce;

    @Autowired
    private ICloudvmImageService cloudvmImageService;

    @Autowired
    private ICloudvmVolumeService cloudvmVolumeService;

    @Autowired
    private ICloudvmRegionService cloudvmRegionService;

    @Autowired
    private ITemplateMessageSender emailSender;

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

    private FloatingIPApi getNovaFloatingIPApi(NovaApi novaApi, String region) throws APINotAvailableException {
        Optional<FloatingIPApi> floatingIPApiOptional = novaApi
                .getFloatingIPApi(region);
        if (!floatingIPApiOptional.isPresent()) {
            throw new APINotAvailableException(FloatingIPApi.class);
        }
        FloatingIPApi floatingIPApi = floatingIPApiOptional.get();
        return floatingIPApi;
    }

    private Network getPrivateNetwork(NetworkApi networkApi, String networkId) throws ResourceNotFoundException {
        Network network = networkApi.get(networkId);
        if (network == null || network.getExternal() || network.getShared()) {
            throw new ResourceNotFoundException("Private Network", "私有网络", networkId);
        }
        return network;
    }

    @Override
    public void attachVmsToSubnet(NovaApi novaApi, NeutronApi neutronApi, String region, String vmIds, final String subnetId, final Tuple2<List<String>, String> vmNamesAndSubnetName) throws OpenStackException {
        vmNamesAndSubnetName._1 = new CopyOnWriteArrayList<String>();

        checkRegion(region, novaApi, neutronApi);

        final ServerApi serverApi = novaApi.getServerApi(region);

        SubnetApi subnetApi = neutronApi.getSubnetApi(region);
        final Subnet privateSubnet = getSubnet(subnetApi, subnetId);
        vmNamesAndSubnetName._2 = privateSubnet.getName();
        final NetworkApi networkApi = neutronApi.getNetworkApi(region);
        final Network privateNetwork = getPrivateNetwork(networkApi, privateSubnet.getNetworkId());

        final AttachInterfaceApi attachInterfaceApi = getAttachInterfaceApi(novaApi, region);
        final PortApi portApi = neutronApi.getPortApi(region);

        List<String> vmIdList = JsonUtil.fromJson(vmIds, new TypeReference<List<String>>() {
        });
        List<Exception> exceptions = ThreadUtil.concurrentFilter(vmIdList, new Function1<Exception, String>() {
            @Override
            public Exception apply(String vmId) throws Exception {
                try {
                    Server server = getVm(serverApi, vmId);
                    vmNamesAndSubnetName._1.add(server.getName());
                    List<InterfaceAttachment> interfaceAttachments = attachInterfaceApi.list(vmId).toList();
                    for (InterfaceAttachment interfaceAttachment : interfaceAttachments) {
                        String attachedNetworkId = interfaceAttachment.getNetworkId();
                        if (attachedNetworkId != null) {
                            Network attachedNetwork = networkApi.get(attachedNetworkId);
                            if (attachedNetwork.getShared()) {
                                throw new UserOperationException("VM is attached to shared network:" + attachedNetworkId, MessageFormat.format("虚拟机“{0}”已加入基础网络：“{1}”，不能关联私有子网", vmId, attachedNetworkId));
                            } else if (!StringUtils.equals(privateNetwork.getId(), attachedNetwork.getId())) {
                                throw new UserOperationException("VM is attached to other private network:" + attachedNetworkId, MessageFormat.format("虚拟机“{0}”已加入私有网络：“{1}”，不能关联其他私有网络下的私有子网", vmId, attachedNetworkId));
                            } else {
                                if (interfaceAttachment.getFixedIps() != null && !interfaceAttachment.getFixedIps().isEmpty()) {
                                    for (FixedIP fixedIP : interfaceAttachment.getFixedIps()) {
                                        if (StringUtils.equals(fixedIP.getSubnetId(), subnetId)) {
                                            throw new UserOperationException("VM is attached to subnet:" + fixedIP.getSubnetId(), MessageFormat.format("虚拟机“{0}”已关联私有子网：“{1}”，不需要重复关联", vmId, fixedIP.getSubnetId()));
                                        }
                                    }
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
    public void detachVmsFromSubnet(NovaApi novaApi, NeutronApi neutronApi, String region, String vmIds, final String subnetId, final Tuple2<List<String>, String> vmNamesAndSubnetName) throws OpenStackException {
        vmNamesAndSubnetName._1 = new CopyOnWriteArrayList<String>();

        checkRegion(region, novaApi, neutronApi);

        final ServerApi serverApi = novaApi.getServerApi(region);

        SubnetApi subnetApi = neutronApi.getSubnetApi(region);
        final Subnet privateSubnet = getSubnet(subnetApi, subnetId);
        vmNamesAndSubnetName._2 = privateSubnet.getName();
        final NetworkApi networkApi = neutronApi.getNetworkApi(region);
        getPrivateNetwork(networkApi, privateSubnet.getNetworkId());
        final PortApi portApi = neutronApi.getPortApi(region);

        final AttachInterfaceApi attachInterfaceApi = getAttachInterfaceApi(novaApi, region);

        List<String> vmIdList = JsonUtil.fromJson(vmIds, new TypeReference<List<String>>() {
        });
        List<Exception> exceptions = ThreadUtil.concurrentFilter(vmIdList, new Function1<Exception, String>() {
            @Override
            public Exception apply(final String vmId) throws Exception {
                try {
                    Server server = getVm(serverApi, vmId);
                    vmNamesAndSubnetName._1.add(server.getName());
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

                    final String portId = findedInterfaceAttachment.getPortId();
                    ThreadUtil.waiting(new Function<Boolean>() {
                        @Override
                        public Boolean apply() throws Exception {
                            return portApi.get(portId) != null;
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

        localCommonQuotaSerivce.checkQuota(userVoUserId, region, CommonQuotaType.CLOUDVM_KEY_PAIR, keyPairs.size() + 1);

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

    private Tuple2<List<Server>, Integer> listServers(NovaApi novaApi, String region, String name, Integer currentPage, Integer recordsPerPage) {
        ServerApi serverApi = novaApi.getServerApi(region);
        final FlavorApi flavorApi = novaApi.getFlavorApi(region);

        List<Server> servers = serverApi.listInDetail()
                .concat().toList();

        List<Server> nameMatchedServers;
        if (StringUtils.isNotEmpty(name)) {
            nameMatchedServers = new LinkedList<Server>();
            for (Server server : servers) {
                if (StringUtils.contains(server.getName(), name)) {
                    nameMatchedServers.add(server);
                }
            }
        } else {
            nameMatchedServers = servers;
        }

        List<Server> pagedServers;
        if (currentPage != null && recordsPerPage != null) {
            if (currentPage > 0 && recordsPerPage > 0) {
                int serverBeginIndex = (currentPage - 1) * recordsPerPage;
                int serverEndIndex = serverBeginIndex + recordsPerPage;
                if (serverBeginIndex >= nameMatchedServers.size()) {
                    pagedServers = new LinkedList<Server>();
                } else {
                    if (serverEndIndex > nameMatchedServers.size()) {
                        serverEndIndex = nameMatchedServers.size();
                    }
                    pagedServers = nameMatchedServers.subList(serverBeginIndex, serverEndIndex);
                }
            } else {
                pagedServers = new LinkedList<Server>();
            }
        } else {
            pagedServers = nameMatchedServers;
        }

        return new Tuple2<List<Server>, Integer>(pagedServers, nameMatchedServers.size());
    }

    @Override
    public Page listVm(final NovaApi novaApi, final NeutronApi neutronApi, CinderApi cinderApi, final long userVoUserId, final String region, final String name, final Integer currentPage, final Integer recordsPerPage) throws OpenStackException {
        checkRegion(region, novaApi, neutronApi, cinderApi);

        List<Ref<Object>> objListRefList = ThreadUtil.concurrentRunAndWait(new Function<Object>() {
            @Override
            public Object apply() throws Exception {
                return listServers(novaApi, region, name, currentPage, recordsPerPage);
            }
        }, new Function<Object>() {
            @Override
            public Object apply() throws Exception {
                List<Port> portList = neutronApi.getPortApi(region).list().concat().toList();
                Map<String, List<Port>> vmIdToPortList = new HashMap<String, List<Port>>();
                for (Port port : portList) {
                    if (OpenStackConstants.PORT_DEVICE_OWNER_COMPUTE_NONE.equals(port.getDeviceOwner()) && StringUtils.isNotEmpty(port.getNetworkId())) {
                        List<Port> portsOfVm = vmIdToPortList.get(port.getDeviceId());
                        if (portsOfVm == null) {
                            portsOfVm = new LinkedList<Port>();
                            vmIdToPortList.put(port.getDeviceId(), portsOfVm);
                        }
                        portsOfVm.add(port);
                    }
                }
                return vmIdToPortList;
            }
        }, new Function<Object>() {
            @Override
            public Object apply() throws Exception {
                List<FloatingIP> floatingIPList = novaApi.getFloatingIPApi(region).get().list().toList();
                Map<String, FloatingIP> vmIdToFloatingIP = new HashMap<String, FloatingIP>();
                for (FloatingIP floatingIP : floatingIPList) {
                    if (floatingIP.getInstanceId() != null && VMManagerImpl.isPublicFloatingIp(floatingIP)) {
                        vmIdToFloatingIP.put(floatingIP.getInstanceId(), floatingIP);
                    }
                }
                return vmIdToFloatingIP;
            }
        }, new Function<Object>() {
            @Override
            public Object apply() throws Exception {
                List<Subnet> subnetList = neutronApi.getSubnetApi(region).list().concat().toList();
                Map<String, Subnet> idToSubnet = new HashMap<String, Subnet>();
                for (Subnet subnet : subnetList) {
                    idToSubnet.put(subnet.getId(), subnet);
                }
                return idToSubnet;
            }
        }, new Function<Object>() {
            @Override
            public Object apply() throws Exception {
                List<Network> networkList = neutronApi.getNetworkApi(region).list().concat().toList();
                Map<String, Network> idToNetwork = new HashMap<String, Network>();
                for (Network network : networkList) {
                    idToNetwork.put(network.getId(), network);
                }
                return idToNetwork;
            }
        }, new Function<Object>() {
            @Override
            public Object apply() throws Exception {
                List<Flavor> flavorList = novaApi.getFlavorApi(region).listInDetail().concat().toList();
                Map<String, Flavor> idToFlavor = new HashMap<String, Flavor>();
                for (Flavor flavor : flavorList) {
                    idToFlavor.put(flavor.getId(), flavor);
                }
                return idToFlavor;
            }
        }, new Function<Object>() {
            @Override
            public Object apply() throws Exception {
                List<CloudvmVolume> cloudvmVolumeList = cloudvmVolumeService.selectByName(userVoUserId, region, null, null);
                Map<String, List<CloudvmVolume>> vmIdToCloudvmVolumeList = new HashMap<String, List<CloudvmVolume>>();
                for (CloudvmVolume cloudvmVolume : cloudvmVolumeList) {
                    if (StringUtils.isNotEmpty(cloudvmVolume.getServerId())) {
                        List<CloudvmVolume> cloudvmVolumesOfVm = vmIdToCloudvmVolumeList.get(cloudvmVolume.getServerId());
                        if (cloudvmVolumesOfVm == null) {
                            cloudvmVolumesOfVm = new LinkedList<CloudvmVolume>();
                            vmIdToCloudvmVolumeList.put(cloudvmVolume.getServerId(), cloudvmVolumesOfVm);
                        }
                        cloudvmVolumesOfVm.add(cloudvmVolume);
                    }
                }
                return vmIdToCloudvmVolumeList;
            }
        }, new Function<Object>() {
            @Override
            public Object apply() throws Exception {
                List<CloudvmImage> cloudvmImageList = cloudvmImageService.selectAllImageOrVmSnapshot(userVoUserId, region);
                Map<String, CloudvmImage> imageIdToCloudvmImage = new HashMap<String, CloudvmImage>();
                for (CloudvmImage cloudvmImage : cloudvmImageList) {
                    imageIdToCloudvmImage.put(cloudvmImage.getImageId(), cloudvmImage);
                }
                return imageIdToCloudvmImage;
            }
        });

        Tuple2<List<Server>, Integer> serverListAndCountTuple = (Tuple2<List<Server>, Integer>) objListRefList.get(0).get();
        List<Server> serverList = serverListAndCountTuple._1;
        Integer serverCount = serverListAndCountTuple._2;

        Map<String, List<Port>> vmIdToPortList = (Map<String, List<Port>>) objListRefList.get(1).get();

        Map<String, FloatingIP> vmIdToFloatingIP = (Map<String, FloatingIP>) objListRefList.get(2).get();

        Map<String, Subnet> idToSubnet = (Map<String, Subnet>) objListRefList.get(3).get();

        Map<String, Network> idToNetwork = (Map<String, Network>) objListRefList.get(4).get();

        Map<String, Flavor> idToFlavor = (Map<String, Flavor>) objListRefList.get(5).get();

        Map<String, List<CloudvmVolume>> vmIdToCloudvmVolumeList = (Map<String, List<CloudvmVolume>>) objListRefList.get(6).get();

        Map<String, CloudvmImage> imageIdToCloudvmImage = (Map<String, CloudvmImage>) objListRefList.get(7).get();

        List<VMResource> vmResources = new LinkedList<VMResource>();
        for (Server server : serverList) {
            VMResourceImpl vmResource = new VMResourceImpl(region, server);

            Flavor flavor = idToFlavor.get(server.getFlavor().getId());
            if (flavor != null) {
                vmResource.setFlavor(new FlavorResourceImpl(region, flavor));
            }

            CloudvmImage cloudvmImage = imageIdToCloudvmImage.get(server.getImage().getId());
            if (cloudvmImage != null) {
                vmResource.setImage(new LocalImageResource(cloudvmImage));
            }

            List<CloudvmVolume> cloudvmVolumes = vmIdToCloudvmVolumeList.get(server.getId());
            if (cloudvmVolumes != null) {
                List<VolumeResource> volumeResources = new LinkedList<VolumeResource>();
                for (CloudvmVolume cloudvmVolume : cloudvmVolumes) {
                    volumeResources.add(new LocalVolumeResource(cloudvmVolume));
                }
                vmResource.setVolumes(volumeResources);
            }

            IPAddresses ipAddresses = new IPAddresses();

            Set<String> publicOrPrivateIps = new HashSet<String>();

            FloatingIP floatingIP = vmIdToFloatingIP.get(server.getId());
            if (floatingIP != null) {
                ipAddresses.getPublicIP().add(floatingIP.getIp());
                publicOrPrivateIps.add(floatingIP.getIp());
            }

            List<Port> portListOfVm = vmIdToPortList.get(server.getId());
            if (portListOfVm != null) {
                for (Port port : portListOfVm) {
                    Set<IP> fixedIps = port.getFixedIps();
                    if (fixedIps != null) {
                        for (IP fixedIp : fixedIps) {
                            String subnetId = fixedIp.getSubnetId();
                            if (StringUtils.isNotEmpty(subnetId)) {
                                Subnet subnet = idToSubnet.get(subnetId);
                                if (subnet != null) {
                                    Network network = idToNetwork.get(subnet.getNetworkId());
                                    if (network != null && !network.getShared() && !network.getExternal()) {
                                        ipAddresses.getPrivateIP().add(new SubnetIp(new SubnetResourceImpl(region, subnet), fixedIp.getIpAddress()));
                                        publicOrPrivateIps.add(fixedIp.getIpAddress());
                                    }
                                }
                            }
                        }
                    }
                }
            }

            for (Address address : server.getAddresses().values()) {
                String ip = address.getAddr();
                if (!publicOrPrivateIps.contains(ip)) {
                    ipAddresses.getSharedIP().add(ip);
                }
            }

            vmResource.setIpAddresses(ipAddresses);

            vmResources.add(vmResource);
        }

        Page page = new Page();
        page.setData(vmResources);
        page.setTotalRecords(serverCount);
        if (recordsPerPage != null) {
            page.setRecordsPerPage(recordsPerPage);
        } else {
            page.setRecordsPerPage(10);
        }
        if (currentPage != null) {
            page.setCurrentPage(currentPage);
        } else {
            page.setCurrentPage(1);
        }

        return page;
    }

    @Override
    public void bindFloatingIp(final NovaApi novaApi, NeutronApi neutronApi, final String region, final String vmId, String floatingIpId, final String email, final String userName) throws OpenStackException {
        checkRegion(region, novaApi, neutronApi);

        ServerApi serverApi = novaApi.getServerApi(region);
        final Server server = getVm(serverApi, vmId);

        if (server.getStatus() == Server.Status.ERROR) {
            throw new UserOperationException("VM status is \"ERROR\", cannot bind the floating IP.", "虚拟机状态为错误，不能绑定公网IP");
        }

        final FloatingIPApi floatingIPApi = getNovaFloatingIPApi(novaApi, region);

        final FloatingIP floatingIP = floatingIPApi.get(floatingIpId);
        if (floatingIP == null || !VMManagerImpl.isPublicFloatingIp(floatingIP)) {
            throw new ResourceNotFoundException("FloatingIP", "公网IP",
                    floatingIpId);
        }

        if (floatingIP.getInstanceId() != null) {
            throw new UserOperationException(MessageFormat.format(
                    "Floating IP is binded to the VM '{0}'.",
                    floatingIP.getInstanceId()), MessageFormat.format(
                    "公网IP已绑定到虚拟机“{0}”，请先解绑。",
                    floatingIP.getInstanceId()));
        }

        final AttachInterfaceApi attachInterfaceApi = novaApi.getAttachInterfaceApi(region).get();
        final NetworkApi networkApi = neutronApi.getNetworkApi(region);
        final SubnetApi subnetApi = neutronApi.getSubnetApi(region);
        final PortApi portApi = neutronApi.getPortApi(region);
        final RouterApi routerApi = neutronApi.getRouterApi(region).get();

        List<Ref<Object>> objListRefList = ThreadUtil.concurrentRunAndWait(new Function<Object>() {
            @Override
            public Object apply() throws Exception {
                List<FloatingIP> floatingIPList = floatingIPApi.list().toList();
                for (FloatingIP fip : floatingIPList) {
                    if (vmId.equals(fip.getInstanceId())) {
                        throw new UserOperationException(MessageFormat.format(
                                "VM is binded to the Floating IP '{0}'.",
                                fip.getId()), MessageFormat.format(
                                "虚拟机已绑定公网IP“{0}”，请先解绑。", fip.getId()));
                    }
                }
                return floatingIPList;
            }
        }, new Function<Object>() {
            @Override
            public Object apply() throws Exception {
                List<InterfaceAttachment> interfaceAttachments = attachInterfaceApi.list(vmId).toList();
                if (interfaceAttachments.isEmpty()) {
                    throw new UserOperationException("Vm is not in any network.", "虚拟机不属于任何一个网络，不能绑定公网IP。");
                }
                return interfaceAttachments;
            }
        }, new Function<Object>() {
            @Override
            public Object apply() throws Exception {
                List<Port> portList = portApi.list().concat().toList();
                return portList;
            }
        }, new Function<Object>() {
            @Override
            public Object apply() throws Exception {
                List<Network> networkList = networkApi.list().concat().toList();
                Map<String, Network> idToNetwork = new HashMap<String, Network>();
                for (Network network : networkList) {
                    idToNetwork.put(network.getId(), network);
                }
                return idToNetwork;
            }
        }, new Function<Object>() {
            @Override
            public Object apply() throws Exception {
                List<Router> routerList = routerApi.list().concat().toList();
                Map<String, Router> idToRouter = new HashMap<String, Router>();
                for (Router router : routerList) {
                    idToRouter.put(router.getId(), router);
                }
                return idToRouter;
            }
        });
        final List<InterfaceAttachment> interfaceAttachments = (List<InterfaceAttachment>) objListRefList.get(1).get();
        final List<Port> portList = (List<Port>) objListRefList.get(2).get();
        final Map<String, Network> idToNetwork = (Map<String, Network>) objListRefList.get(3).get();
        final Map<String, Router> idToRouter = (Map<String, Router>) objListRefList.get(4).get();

        final Ref<Boolean> isInSharedNetworkRef = new Ref<Boolean>(false);
        final Set<String> findedRouterIds = new ConcurrentSkipListSet<String>();
        ThreadUtil.concurrentFilter(interfaceAttachments, new Function1<Void, InterfaceAttachment>() {
            @Override
            public Void apply(InterfaceAttachment interfaceAttachment) throws Exception {
                final String networkId = interfaceAttachment.getNetworkId();
                Network network = idToNetwork.get(networkId);
                if (network != null) {
                    if (network.getShared()) {
                        isInSharedNetworkRef.set(true);
                    } else {
                        ThreadUtil.concurrentFilter(CollectionUtil.toList(interfaceAttachment.getFixedIps()), new Function1<Void, FixedIP>() {
                            @Override
                            public Void apply(FixedIP fixedIP) throws Exception {
                                String subnetId = fixedIP.getSubnetId();
                                if (subnetId != null) {
                                    String routerId = null;
                                    findRouterId:
                                    for (Port port : portList) {
                                        if (OpenStackConstants.PORT_DEVICE_OWNER_NETWORK_ROUTER_INTERFACE
                                                .equals(port.getDeviceOwner())) {
                                            if (networkId.equals(port.getNetworkId())) {
                                                ImmutableSet<IP> fixedIps = port.getFixedIps();
                                                if (fixedIps != null) {
                                                    for (IP ip : fixedIps) {
                                                        if (subnetId.equals(ip.getSubnetId())) {
                                                            routerId = port.getDeviceId();
                                                            break findRouterId;
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                    if (routerId != null) {
                                        Router router = idToRouter.get(routerId);
                                        if (router != null) {
                                            findedRouterIds.add(routerId);
                                        }
                                    }
                                }
                                return null;
                            }
                        });
                    }
                }
                return null;
            }
        });

        if(!isInSharedNetworkRef.get()) {
            if (findedRouterIds.isEmpty()) {
                throw new UserOperationException("Subnet is not associated with router.", "虚拟机所在的私有子网没有关联到路由");
            }
            boolean isEnableGateway = false;
            for (String routerId : findedRouterIds) {
                Router router = idToRouter.get(routerId);
                ExternalGatewayInfo gatewayInfo = router.getExternalGatewayInfo();
                if (gatewayInfo != null && gatewayInfo.getNetworkId() != null) {
                    isEnableGateway = true;
                    break;
                }
            }
            if (!isEnableGateway) {
                throw new UserOperationException("Router gateway is not enabled.", "虚拟机所在的私有子网关联的路由没有开启网关");
            }
        }

        floatingIPApi.addToServer(floatingIP.getIp(), vmId);

        ThreadUtil.asyncExec(new Function<Void>() {
            @Override
            public Void apply() throws Exception {
                emailBindIP(region, server, floatingIP.getIp(), email, userName);
                return null;
            }
        });

        OpenStackServiceImpl.getOpenStackServiceGroup().getVmSyncService().update(region, server);
    }

    private void emailBindIP(String region, Server server, String ip, String email, String userName) throws OpenStackException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("userName", userName);
        params.put("region", cloudvmRegionService.selectByCode(region).getDisplayName());
        params.put("vmId", server.getId());
        params.put("vmName", server.getName());
        params.put("ip", ip);
        params.put("port", 22);
        params.put("bindTime", format.format(new Date()));

        MailMessage mailMessage = new MailMessage("乐视云平台web-portal系统",
                email, "乐视云平台web-portal系统通知",
                "cloudvm/bindFloatingIp.ftl", params);
        mailMessage.setHtml(true);
        emailSender.sendMessage(mailMessage);
    }

}
