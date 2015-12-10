package com.letv.portal.service.openstack.resource.service.impl;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableSet;
import com.letv.common.email.ITemplateMessageSender;
import com.letv.common.email.bean.MailMessage;
import com.letv.common.exception.ValidateException;
import com.letv.common.paging.impl.Page;
import com.letv.portal.model.cloudvm.CloudvmImage;
import com.letv.portal.model.cloudvm.CloudvmVolume;
import com.letv.portal.model.cloudvm.CloudvmVolumeStatus;
import com.letv.portal.model.common.CommonQuotaType;
import com.letv.portal.service.cloudvm.ICloudvmImageService;
import com.letv.portal.service.cloudvm.ICloudvmRegionService;
import com.letv.portal.service.cloudvm.ICloudvmVolumeService;
import com.letv.portal.service.openstack.billing.ResourceLocator;
import com.letv.portal.service.openstack.billing.event.service.EventPublishService;
import com.letv.portal.service.openstack.cronjobs.VmSyncService;
import com.letv.portal.service.openstack.exception.*;
import com.letv.portal.service.openstack.impl.OpenStackServiceImpl;
import com.letv.portal.service.openstack.local.resource.LocalImageResource;
import com.letv.portal.service.openstack.local.resource.LocalVolumeResource;
import com.letv.portal.service.openstack.local.service.LocalCommonQuotaSerivce;
import com.letv.portal.service.openstack.local.service.LocalKeyPairService;
import com.letv.portal.service.openstack.local.service.LocalRegionService;
import com.letv.portal.service.openstack.local.service.LocalVolumeService;
import com.letv.portal.service.openstack.resource.*;
import com.letv.portal.service.openstack.resource.impl.*;
import com.letv.portal.service.openstack.resource.manager.impl.NetworkManagerImpl;
import com.letv.portal.service.openstack.resource.manager.impl.VMManagerImpl;
import com.letv.portal.service.openstack.resource.service.ResourceService;
import com.letv.portal.service.openstack.resource.service.impl.task.rule.create.PingRuleCreateTask;
import com.letv.portal.service.openstack.resource.service.impl.task.rule.create.RuleCreateTask;
import com.letv.portal.service.openstack.resource.service.impl.task.rule.create.SshRuleCreateTask;
import com.letv.portal.service.openstack.util.*;
import com.letv.portal.service.openstack.util.constants.OpenStackConstants;
import com.letv.portal.service.openstack.util.function.Function0;
import com.letv.portal.service.openstack.util.function.Function1;
import com.letv.portal.service.openstack.util.tuple.Tuple2;
import com.letv.portal.service.openstack.util.tuple.Tuple3;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.type.TypeReference;
import org.jclouds.openstack.cinder.v1.CinderApi;
import org.jclouds.openstack.cinder.v1.domain.Snapshot;
import org.jclouds.openstack.cinder.v1.domain.Volume;
import org.jclouds.openstack.cinder.v1.features.SnapshotApi;
import org.jclouds.openstack.cinder.v1.features.VolumeApi;
import org.jclouds.openstack.glance.v1_0.GlanceApi;
import org.jclouds.openstack.neutron.v2.NeutronApi;
import org.jclouds.openstack.neutron.v2.domain.*;
import org.jclouds.openstack.neutron.v2.domain.Network;
import org.jclouds.openstack.neutron.v2.domain.SecurityGroup;
import org.jclouds.openstack.neutron.v2.extensions.RouterApi;
import org.jclouds.openstack.neutron.v2.extensions.SecurityGroupApi;
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
    private LocalVolumeService localVolumeService;

    @Autowired
    private ICloudvmRegionService cloudvmRegionService;

    @Autowired
    private ITemplateMessageSender emailSender;

    @Autowired
    private EventPublishService eventPublishService;

    @Autowired
    private LocalRegionService localRegionService;

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
    public void attachVmsToSubnet(NovaApi novaApi, NeutronApi neutronApi, String region, String vmIds, final String subnetId, final Ref<Tuple2<List<String>, String>> vmNamesAndSubnetName) throws OpenStackException {
        final List<String> vmNames = new CopyOnWriteArrayList<String>();

        checkRegion(region, novaApi, neutronApi);

        final ServerApi serverApi = novaApi.getServerApi(region);

        SubnetApi subnetApi = neutronApi.getSubnetApi(region);
        final Subnet privateSubnet = getSubnet(subnetApi, subnetId);
        final String subnetName = privateSubnet.getName();
        final NetworkApi networkApi = neutronApi.getNetworkApi(region);
        final Network privateNetwork = getPrivateNetwork(networkApi, privateSubnet.getNetworkId());

        final AttachInterfaceApi attachInterfaceApi = getAttachInterfaceApi(novaApi, region);
        final PortApi portApi = neutronApi.getPortApi(region);

        List<String> vmIdList = JsonUtil.fromJson(vmIds, new TypeReference<List<String>>() {
        });
        List<Exception> exceptions = ThreadUtil.concurrentFilter(vmIdList, new Function1<String, Exception>() {
            @Override
            public Exception apply(String vmId) throws Exception {
                try {
                    Server server = getVm(serverApi, vmId);
                    vmNames.add(server.getName());
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
        vmNamesAndSubnetName.set(new Tuple2<List<String>, String>(vmNames, subnetName));
        if (!exceptions.isEmpty()) {
            throw new OpenStackCompositeException(exceptions);
        }
    }

    @Override
    public void detachVmsFromSubnet(NovaApi novaApi, NeutronApi neutronApi, String region, String vmIds, final String subnetId, final Ref<Tuple2<List<String>, String>> vmNamesAndSubnetName) throws OpenStackException {
        final List<String> vmNames = new CopyOnWriteArrayList<String>();

        checkRegion(region, novaApi, neutronApi);

        final ServerApi serverApi = novaApi.getServerApi(region);

        SubnetApi subnetApi = neutronApi.getSubnetApi(region);
        final Subnet privateSubnet = getSubnet(subnetApi, subnetId);
        final String subnetName = privateSubnet.getName();
        final NetworkApi networkApi = neutronApi.getNetworkApi(region);
        getPrivateNetwork(networkApi, privateSubnet.getNetworkId());
        final PortApi portApi = neutronApi.getPortApi(region);

        final AttachInterfaceApi attachInterfaceApi = getAttachInterfaceApi(novaApi, region);

        List<String> vmIdList = JsonUtil.fromJson(vmIds, new TypeReference<List<String>>() {
        });
        List<Exception> exceptions = ThreadUtil.concurrentFilter(vmIdList, new Function1<String, Exception>() {
            @Override
            public Exception apply(final String vmId) throws Exception {
                try {
                    Server server = getVm(serverApi, vmId);
                    vmNames.add(server.getName());
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
                    ThreadUtil.waiting(new Function0<Boolean>() {
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
        vmNamesAndSubnetName.set(new Tuple2<List<String>, String>(vmNames, subnetName));
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
        try {
            getKeyPair(keyPairApi, name);
        } catch (ResourceNotFoundException ex) {
            if (localKeyPairService.delete(userVoUserId, region, name)) {
                return;
            } else {
                throw ex;
            }
        }

        boolean isSuccess = keyPairApi.delete(name);
        if (!isSuccess) {
            throw new OpenStackException("KeyPair delete failed.",
                    "密钥删除失败。");
        }

        localKeyPairService.delete(userVoUserId, region, name);

        ThreadUtil.waiting(new Function0<Boolean>() {
            @Override
            public Boolean apply() throws Exception {
                return keyPairApi.get(name) != null;
            }
        }, new Timeout().time(5L).unit(TimeUnit.MINUTES));
    }

    private Tuple2<List<Server>, Integer> listServers(NovaApi novaApi, String region, String name, Integer currentPage, Integer recordsPerPage) throws Exception {
        return listResourcesByNameAndPage(novaApi.getServerApi(region).listInDetail().concat().toList(), new Function1<Server, String>() {
            @Override
            public String apply(Server server) throws Exception {
                return server.getName();
            }
        }, name, currentPage, recordsPerPage);
    }

    @Override
    public Page listVm(final NovaApi novaApi, final NeutronApi neutronApi, final long userVoUserId, final String region, final String name, final Integer currentPage, final Integer recordsPerPage) throws OpenStackException {
        checkRegion(region, novaApi, neutronApi);

        List<Ref<Object>> objListRefList = ThreadUtil.concurrentRunAndWait(new Function0<Object>() {
            @Override
            public Object apply() throws Exception {
                return listServers(novaApi, region, name, currentPage, recordsPerPage);
            }
        }, new Function0<Object>() {
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
        }, new Function0<Object>() {
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
        }, new Function0<Object>() {
            @Override
            public Object apply() throws Exception {
                List<Subnet> subnetList = neutronApi.getSubnetApi(region).list().concat().toList();
                Map<String, Subnet> idToSubnet = new HashMap<String, Subnet>();
                for (Subnet subnet : subnetList) {
                    idToSubnet.put(subnet.getId(), subnet);
                }
                return idToSubnet;
            }
        }, new Function0<Object>() {
            @Override
            public Object apply() throws Exception {
                List<Network> networkList = neutronApi.getNetworkApi(region).list().concat().toList();
                Map<String, Network> idToNetwork = new HashMap<String, Network>();
                for (Network network : networkList) {
                    idToNetwork.put(network.getId(), network);
                }
                return idToNetwork;
            }
        }, new Function0<Object>() {
            @Override
            public Object apply() throws Exception {
                List<Flavor> flavorList = novaApi.getFlavorApi(region).listInDetail().concat().toList();
                Map<String, Flavor> idToFlavor = new HashMap<String, Flavor>();
                for (Flavor flavor : flavorList) {
                    idToFlavor.put(flavor.getId(), flavor);
                }
                return idToFlavor;
            }
        }, new Function0<Object>() {
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
        }, new Function0<Object>() {
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
    public VMResource getVm(final NovaApi novaApi, final NeutronApi neutronApi, final long userVoUserId, final String region, final String vmId) throws OpenStackException {
        checkRegion(region, novaApi, neutronApi);

        final Server server = getVm(novaApi.getServerApi(region), vmId);

        List<Ref<Object>> objListRefList = ThreadUtil.concurrentRunAndWait(new Function0<Object>() {
            @Override
            public Object apply() throws Exception {
                List<Port> portList = neutronApi.getPortApi(region).list().concat().toList();
                List<Port> vmPortList = new LinkedList<Port>();
                for (Port port : portList) {
                    if (OpenStackConstants.PORT_DEVICE_OWNER_COMPUTE_NONE.equals(port.getDeviceOwner()) && StringUtils.isNotEmpty(port.getNetworkId())) {
                        if (StringUtils.equals(vmId, port.getDeviceId())) {
                            vmPortList.add(port);
                        }
                    }
                }
                return vmPortList;
            }
        }, new Function0<Object>() {
            @Override
            public Object apply() throws Exception {
                List<FloatingIP> floatingIPList = novaApi.getFloatingIPApi(region).get().list().toList();
                List<FloatingIP> vmFloatingIPList = new LinkedList<FloatingIP>();
                for (FloatingIP floatingIP : floatingIPList) {
                    if (StringUtils.equals(floatingIP.getInstanceId(), vmId) && VMManagerImpl.isPublicFloatingIp(floatingIP)) {
                        vmFloatingIPList.add(floatingIP);
                    }
                }
                return vmFloatingIPList;
            }
        }, new Function0<Object>() {
            @Override
            public Object apply() throws Exception {
                List<Subnet> subnetList = neutronApi.getSubnetApi(region).list().concat().toList();
                Map<String, Subnet> idToSubnet = new HashMap<String, Subnet>();
                for (Subnet subnet : subnetList) {
                    idToSubnet.put(subnet.getId(), subnet);
                }
                return idToSubnet;
            }
        }, new Function0<Object>() {
            @Override
            public Object apply() throws Exception {
                List<Network> networkList = neutronApi.getNetworkApi(region).list().concat().toList();
                Map<String, Network> idToNetwork = new HashMap<String, Network>();
                for (Network network : networkList) {
                    idToNetwork.put(network.getId(), network);
                }
                return idToNetwork;
            }
        }, new Function0<Object>() {
            @Override
            public Object apply() throws Exception {
                return novaApi.getFlavorApi(region).get(server.getFlavor().getId());
            }
        }, new Function0<Object>() {
            @Override
            public Object apply() throws Exception {
                return cloudvmVolumeService.selectByServerIdAndStatus(userVoUserId, region, vmId, null);
            }
        }, new Function0<Object>() {
            @Override
            public Object apply() throws Exception {
                return cloudvmImageService.getImageOrVmSnapshot(region, server.getImage().getId());
            }
        });

        List<Port> vmPortList = (List<Port>) objListRefList.get(0).get();
        List<FloatingIP> vmFloatingIPList = (List<FloatingIP>) objListRefList.get(1).get();
        Map<String, Subnet> idToSubnet = (Map<String, Subnet>) objListRefList.get(2).get();
        Map<String, Network> idToNetwork = (Map<String, Network>) objListRefList.get(3).get();
        Flavor flavor = (Flavor) objListRefList.get(4).get();
        List<CloudvmVolume> vmCloudvmVolumeList = (List<CloudvmVolume>) objListRefList.get(5).get();
        CloudvmImage cloudvmImage = (CloudvmImage) objListRefList.get(6).get();

        VMResourceImpl vmResource = new VMResourceImpl(region, server);

        if (flavor != null) {
            vmResource.setFlavor(new FlavorResourceImpl(region, flavor));
        }

        if (cloudvmImage != null) {
            vmResource.setImage(new LocalImageResource(cloudvmImage));
        }

        if (vmCloudvmVolumeList != null) {
            List<VolumeResource> volumeResources = new LinkedList<VolumeResource>();
            for (CloudvmVolume cloudvmVolume : vmCloudvmVolumeList) {
                volumeResources.add(new LocalVolumeResource(cloudvmVolume));
            }
            vmResource.setVolumes(volumeResources);
        }

        IPAddresses ipAddresses = new IPAddresses();

        Set<String> publicOrPrivateIps = new HashSet<String>();

        if (vmFloatingIPList != null) {
            for (FloatingIP floatingIP : vmFloatingIPList) {
                ipAddresses.getPublicIP().add(floatingIP.getIp());
                publicOrPrivateIps.add(floatingIP.getIp());
            }
        }

        if (vmPortList != null) {
            for (Port port : vmPortList) {
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

        return vmResource;
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

        List<Ref<Object>> objListRefList = ThreadUtil.concurrentRunAndWait(new Function0<Object>() {
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
        }, new Function0<Object>() {
            @Override
            public Object apply() throws Exception {
                List<InterfaceAttachment> interfaceAttachments = attachInterfaceApi.list(vmId).toList();
                if (interfaceAttachments.isEmpty()) {
                    throw new UserOperationException("Vm is not in any network.", "虚拟机不属于任何一个网络，不能绑定公网IP。");
                }
                return interfaceAttachments;
            }
        }, new Function0<Object>() {
            @Override
            public Object apply() throws Exception {
                List<Port> portList = portApi.list().concat().toList();
                return portList;
            }
        }, new Function0<Object>() {
            @Override
            public Object apply() throws Exception {
                List<Network> networkList = networkApi.list().concat().toList();
                Map<String, Network> idToNetwork = new HashMap<String, Network>();
                for (Network network : networkList) {
                    idToNetwork.put(network.getId(), network);
                }
                return idToNetwork;
            }
        }, new Function0<Object>() {
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
        ThreadUtil.concurrentFilter(interfaceAttachments, new Function1<InterfaceAttachment, Void>() {
            @Override
            public Void apply(InterfaceAttachment interfaceAttachment) throws Exception {
                final String networkId = interfaceAttachment.getNetworkId();
                Network network = idToNetwork.get(networkId);
                if (network != null) {
                    if (network.getShared()) {
                        isInSharedNetworkRef.set(true);
                    } else {
                        ThreadUtil.concurrentFilter(CollectionUtil.toList(interfaceAttachment.getFixedIps()), new Function1<FixedIP, Void>() {
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

        if (!isInSharedNetworkRef.get()) {
            if (findedRouterIds.isEmpty()) {
                throw new UserOperationException("Subnet is not associated with router.", "虚拟机所在的私有子网没有关联到路由");
            }
//            boolean isEnableGateway = true;
            for (String routerId : findedRouterIds) {
                Router router = idToRouter.get(routerId);
                ExternalGatewayInfo gatewayInfo = router.getExternalGatewayInfo();
                if (gatewayInfo == null || gatewayInfo.getNetworkId() == null) {
//                    isEnableGateway = false;
                    throw new UserOperationException("Router gateway is not enabled.", "虚拟机所在的私有子网关联的路由没有都开启网关");
//                    break;
                }
            }
//            if (!isEnableGateway) {
//                throw new UserOperationException("Router gateway is not enabled.", "虚拟机所在的私有子网关联的路由没有开启网关");
//            }
        }

//        floatingIPApi.addToServer(floatingIP.getIp(), vmId);
        org.jclouds.openstack.neutron.v2.extensions.FloatingIPApi neutronFloatingIPApi =
                getNeutronFloatingIPApi(neutronApi, region);
        neutronFloatingIPApi.update(floatingIpId
                , org.jclouds.openstack.neutron.v2.domain.FloatingIP.UpdateFloatingIP.updateBuilder().portId(
                interfaceAttachments.get(0).getPortId()).build());

        ThreadUtil.asyncExec(new Function0<Void>() {
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

    @Override
    public void renameVm(NovaApi novaApi, long userVoUserId, String region, String vmId, String name) throws OpenStackException {
        checkRegion(region, novaApi);

        if (StringUtils.isEmpty(name)) {
            throw new UserOperationException("vm name is empty", "虚拟机名称为空");
        }

        ServerApi serverApi = novaApi.getServerApi(region);
        Server server = getVm(serverApi, vmId);

        serverApi.rename(vmId, name);

        server = getVm(serverApi, vmId);
        VmSyncService vmSyncService = OpenStackServiceImpl.getOpenStackServiceGroup().getVmSyncService();
        vmSyncService.update(region, server);
        vmSyncService.onVmRenamed(userVoUserId, region, server.getId(), server.getName());
    }

    private Volume getVolume(VolumeApi volumeApi, String volumeId) throws ResourceNotFoundException {
        Volume volume = volumeApi.get(volumeId);
        if (volume == null) {
            throw new ResourceNotFoundException("Volume", "云硬盘", volumeId);
        }
        return volume;
    }

    @Override
    public void deleteVolume(CinderApi cinderApi, long tenantId, String region, final String volumeId) throws OpenStackException {
        checkRegion(region, cinderApi);

        final VolumeApi volumeApi = cinderApi.getVolumeApi(region);
        Volume volume;
        try {
            volume = getVolume(volumeApi, volumeId);
        } catch (ResourceNotFoundException ex) {
            if (localVolumeService.delete(tenantId, region, volumeId)) {
                return;
            } else {
                throw ex;
            }
        }

        checkVolumeOperational(tenantId, region, volumeId);

        Volume.Status status = volume.getStatus();
        if (status != Volume.Status.AVAILABLE && status != Volume.Status.ERROR) {
            throw new UserOperationException(
                    "Volume is not removable.", "云硬盘不是可删除的状态。");
        }

        List<? extends Snapshot> snapshots = cinderApi.getSnapshotApi(region).list().toList();
        for (Snapshot snapshot : snapshots) {
            if (org.apache.commons.lang3.StringUtils.equals(snapshot.getVolumeId(), volumeId)) {
                throw new UserOperationException("There is a snapshot of the volume.", "云硬盘有快照，请先把云硬盘的快照删掉，再删除云硬盘。");
            }
        }

        boolean isSuccess = volumeApi.delete(volumeId);
        if (!isSuccess) {
            throw new OpenStackException(MessageFormat.format(
                    "Volume \"{0}\" delete failed.",
                    volumeId), MessageFormat.format(
                    "云硬盘“{0}”删除失败。", volumeId));
        }

        eventPublishService.onDelete(new ResourceLocator().region(region).id(volumeId).type(VolumeResource.class));

        ThreadUtil.waiting(new Function0<Boolean>() {
            @Override
            public Boolean apply() throws Exception {
                return volumeApi.get(volumeId) != null;
            }
        }, new Timeout().time(60L).unit(TimeUnit.MINUTES));

        localVolumeService.delete(tenantId, region, volumeId);
    }

    @Override
    public void checkVolumeOperational(long tenantId, String region, String volumeId) throws OpenStackException {
        CloudvmVolume cloudvmVolume = OpenStackServiceImpl.getOpenStackServiceGroup().getCloudvmVolumeService().selectByVolumeId(tenantId, region, volumeId);
        if (cloudvmVolume == null) {
            throw new ResourceNotFoundException("Volume", "云硬盘", volumeId);
        } else {
            if (cloudvmVolume.getStatus() == CloudvmVolumeStatus.WAITING_ATTACHING) {
                throw new UserOperationException("volume.status==WAITING_ATTACHING", "云硬盘正在挂载中，请稍后操作");
            }
        }
    }

    private RouterApi getRouterApi(NeutronApi neutronApi, String region) throws APINotAvailableException {
        Optional<RouterApi> routerApiOptional = neutronApi
                .getRouterApi(region);
        if (!routerApiOptional.isPresent()) {
            throw new APINotAvailableException(RouterApi.class);
        }
        return routerApiOptional.get();
    }

    private Router getRouter(RouterApi routerApi, String routerId) throws ResourceNotFoundException {
        Router router = routerApi.get(routerId);
        if (router == null) {
            throw new ResourceNotFoundException("Router", "路由",
                    routerId);
        }
        return router;
    }

    public Network getPublicNetwork(NetworkApi networkApi, String networkId) throws ResourceNotFoundException {
        Network network = networkApi.get(networkId);
        if (network == null || !network.getExternal()) {
            throw new ResourceNotFoundException("Public Network",
                    "线路", networkId);
        }
        return network;
    }

    @Override
    public void editRouter(NovaApi novaApi, NeutronApi neutronApi, String region, String routerId, String name, boolean enablePublicNetworkGateway, String publicNetworkId) throws OpenStackException {
        checkRegion(region, neutronApi);

        RouterApi routerApi = getRouterApi(neutronApi, region);

        Router router = getRouter(routerApi, routerId);

        Router.UpdateBuilder updateBuilder = Router.updateBuilder()
                .name(name);
        if (enablePublicNetworkGateway
                && (router.getExternalGatewayInfo() == null || router
                .getExternalGatewayInfo().getNetworkId() == null)) {
            NetworkApi networkApi = neutronApi.getNetworkApi(region);
            getPublicNetwork(networkApi, publicNetworkId);
            updateBuilder.externalGatewayInfo(ExternalGatewayInfo
                    .builder().networkId(publicNetworkId).build());
        } else if (!enablePublicNetworkGateway
                && (router.getExternalGatewayInfo() != null && router
                .getExternalGatewayInfo().getNetworkId() != null)) {
            checkRouterDisableGateway(novaApi, neutronApi, region, routerId);
            updateBuilder.externalGatewayInfo(ExternalGatewayInfo
                    .builder().build());
        } else if (enablePublicNetworkGateway
                && (router.getExternalGatewayInfo() != null && router
                .getExternalGatewayInfo().getNetworkId() != null)
                && !router.getExternalGatewayInfo().getNetworkId()
                .equals(publicNetworkId)) {
            routerApi.update(
                    routerId,
                    Router.updateBuilder()
                            .externalGatewayInfo(
                                    ExternalGatewayInfo.builder()
                                            .build()).build());
            routerApi.update(
                    routerId,
                    Router.updateBuilder()
                            .externalGatewayInfo(
                                    ExternalGatewayInfo.builder()
                                            .networkId(publicNetworkId)
                                            .build()).build());
        }
        routerApi.update(routerId, updateBuilder.build());
    }

    public org.jclouds.openstack.neutron.v2.extensions.FloatingIPApi getNeutronFloatingIPApi(NeutronApi neutronApi, String region) throws APINotAvailableException {
        Optional<org.jclouds.openstack.neutron.v2.extensions.FloatingIPApi> floatingIPApiOptional = neutronApi.getFloatingIPApi(region);
        if (!floatingIPApiOptional.isPresent()) {
            throw new APINotAvailableException(org.jclouds.openstack.neutron.v2.extensions.FloatingIPApi.class);
        }
        return floatingIPApiOptional.get();
    }

    private void checkRouterDisableGateway(NovaApi novaApi, NeutronApi neutronApi, String region, String routerId) throws OpenStackException {
        final org.jclouds.openstack.neutron.v2.extensions.FloatingIPApi floatingIPApi = getNeutronFloatingIPApi(neutronApi, region);
        final PortApi portApi = neutronApi.getPortApi(region);

        List<Ref<Object>> objListRefList = ThreadUtil.concurrentRunAndWait(new Function0<Object>() {
            @Override
            public Object apply() throws Exception {
                return portApi.list().concat().toList();
            }
        }, new Function0<Object>() {
            @Override
            public Object apply() throws Exception {
                return floatingIPApi.list().concat().toList();
            }
        });

        final List<Port> portList = (List<Port>) objListRefList.get(0).get();
        final List<org.jclouds.openstack.neutron.v2.domain.FloatingIP> floatingIPList = (List<org.jclouds.openstack.neutron.v2.domain.FloatingIP>) objListRefList.get(1).get();

        Set<String> routerSubnetIds = new HashSet<String>();
        for (Port port : portList) {
            if (OpenStackConstants.PORT_DEVICE_OWNER_NETWORK_ROUTER_INTERFACE.equals(port.getDeviceOwner()) && routerId.equals(port.getDeviceId())) {
                ImmutableSet<IP> fixedIps = port.getFixedIps();
                if (fixedIps != null) {
                    for (IP fixedIP : fixedIps) {
                        String subnetId = fixedIP.getSubnetId();
                        if (subnetId != null) {
                            routerSubnetIds.add(subnetId);
                        }
                    }
                }
            }
        }

        Set<String> subnetServerPortIds = new HashSet<String>();
        for (Port port : portList) {
            if (OpenStackConstants.PORT_DEVICE_OWNER_COMPUTE_NONE.equals(port.getDeviceOwner())) {
                String deviceId = port.getDeviceId();
                if (deviceId != null) {
                    ImmutableSet<IP> fixedIps = port.getFixedIps();
                    if (fixedIps != null) {
                        for (IP fixedIP : fixedIps) {
                            String subnetId = fixedIP.getSubnetId();
                            if (subnetId != null) {
                                if (routerSubnetIds.contains(subnetId)) {
                                    subnetServerPortIds.add(port.getId());
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }

        for (org.jclouds.openstack.neutron.v2.domain.FloatingIP floatingIP : floatingIPList) {
            String portId = floatingIP.getPortId();
            if (portId != null && subnetServerPortIds.contains(portId)) {
                throw new UserOperationException("Can not disable gateway of router.", "路由器关联的子网网卡绑定了公网IP，不允许关闭网关");
            }
        }
    }

    @Override
    public void separateSubnetFromRouter(NeutronApi neutronApi, String region, final String routerId, final String subnetId) throws OpenStackException {
        checkRegion(region, neutronApi);

        final RouterApi routerApi = getRouterApi(neutronApi, region);
        final PortApi portApi = neutronApi.getPortApi(region);
        final SubnetApi subnetApi = neutronApi.getSubnetApi(region);
        final NetworkApi networkApi = neutronApi.getNetworkApi(region);
        final org.jclouds.openstack.neutron.v2.extensions.FloatingIPApi floatingIPApi = getNeutronFloatingIPApi(neutronApi, region);

        List<Ref<Object>> objListRefList = ThreadUtil.concurrentRunAndWait(
                new Function0<Object>() {
                    @Override
                    public Object apply() throws Exception {
                        return portApi.list().concat().toList();
                    }
                }, new Function0<Object>() {
                    @Override
                    public Object apply() throws Exception {
                        return floatingIPApi.list().concat().toList();
                    }
                }, new Function0<Object>() {
                    @Override
                    public Object apply() throws Exception {
                        Subnet subnet = getSubnet(subnetApi, subnetId);
                        getPrivateNetwork(networkApi, subnet.getNetworkId());
                        return subnet;
                    }
                }, new Function0<Object>() {
                    @Override
                    public Object apply() throws Exception {
                        return getRouter(routerApi, routerId);
                    }
                });
        List<Port> portList = (List<Port>) objListRefList.get(0).get();
        List<org.jclouds.openstack.neutron.v2.domain.FloatingIP> floatingIPList = (List<org.jclouds.openstack.neutron.v2.domain.FloatingIP>) objListRefList.get(1).get();

        Port associatedPort = null;
        Set<String> subnetServerPortIds = new HashSet<String>();
        for (Port port : portList) {
            String deviceOwner = port.getDeviceOwner();
            if (OpenStackConstants.PORT_DEVICE_OWNER_NETWORK_ROUTER_INTERFACE
                    .equals(deviceOwner)
                    && routerId.equals(port.getDeviceId())) {
                ImmutableSet<IP> fixedIps = port.getFixedIps();
                if (fixedIps != null) {
                    for (IP ip : fixedIps) {
                        if (subnetId.equals(ip.getSubnetId())) {
                            associatedPort = port;
                            break;
                        }
                    }
                }
            } else if (OpenStackConstants.PORT_DEVICE_OWNER_COMPUTE_NONE.equals(deviceOwner)) {
                String deviceId = port.getDeviceId();
                if (deviceId != null) {
                    ImmutableSet<IP> fixedIps = port.getFixedIps();
                    if (fixedIps != null) {
                        for (IP fixedIP : fixedIps) {
                            if (subnetId.equals(fixedIP.getSubnetId())) {
                                subnetServerPortIds.add(port.getId());
                                break;
                            }
                        }
                    }
                }
            }
        }
        if (associatedPort == null) {
            throw new UserOperationException(
                    "Subnet is not associated with router.",
                    "子网和路由之间不存在关联");
        }
        for (org.jclouds.openstack.neutron.v2.domain.FloatingIP floatingIP : floatingIPList) {
            String portId = floatingIP.getPortId();
            if (portId != null && subnetServerPortIds.contains(portId)) {
                throw new UserOperationException("Can not disable gateway of router.", "子网的虚拟机网卡绑定了公网IP，不允许解除子网和路由的关联");
            }
        }

        boolean isSuccess = routerApi.removeInterfaceForSubnet(
                routerId, subnetId);
        if (!isSuccess) {
            throw new OpenStackException(
                    "Separate subnets and routing failure.",
                    "子网和路由解除关联失败");
        }

        final String portId = associatedPort.getId();
        ThreadUtil.waiting(new Function0<Boolean>() {
            @Override
            public Boolean apply() throws Exception {
                return portApi.get(portId) != null;
            }
        });
    }

    @Override
    public void createDefaultSecurityGroupAndRule(final NeutronApi neutronApi) throws OpenStackException {
        ThreadUtil.concurrentFilter(CollectionUtil.toList(neutronApi.getConfiguredRegions()), new Function1<String, Void>() {
            @Override
            public Void apply(String region) throws Exception {
                Optional<SecurityGroupApi> securityGroupApiOptional = neutronApi
                        .getSecurityGroupApi(region);
                if (!securityGroupApiOptional.isPresent()) {
                    throw new APINotAvailableException(SecurityGroupApi.class).matrixException();
                }
                final SecurityGroupApi securityGroupApi = securityGroupApiOptional.get();

                SecurityGroup defaultSecurityGroup = null;
                for (SecurityGroup securityGroup : securityGroupApi
                        .listSecurityGroups().concat().toList()) {
                    if ("default".equals(securityGroup.getName())) {
                        defaultSecurityGroup = securityGroup;
                        break;
                    }
                }
                if (defaultSecurityGroup == null) {
                    defaultSecurityGroup = securityGroupApi
                            .create(SecurityGroup.CreateSecurityGroup
                                    .createBuilder().name("default").build());
                }

                List<RuleCreateTask> ruleCreateTaskList = new LinkedList<RuleCreateTask>();
                ruleCreateTaskList.add(new PingRuleCreateTask());
                ruleCreateTaskList.add(new SshRuleCreateTask());

                for (Rule rule : defaultSecurityGroup.getRules()) {
                    for (RuleCreateTask ruleCreateTask : ruleCreateTaskList.toArray(new RuleCreateTask[0])) {
                        if (ruleCreateTask.isMatch(rule)) {
                            ruleCreateTaskList.remove(ruleCreateTask);
                        }
                    }
                }

                if (ruleCreateTaskList.size() > 1) {
                    final SecurityGroup securityGroup = defaultSecurityGroup;
                    ThreadUtil.concurrentFilter(ruleCreateTaskList, new Function1<RuleCreateTask, Void>() {
                        @Override
                        public Void apply(RuleCreateTask ruleCreateTask) throws Exception {
                            ruleCreateTask.create(securityGroupApi, securityGroup);
                            return null;
                        }
                    });
                } else {
                    for (RuleCreateTask ruleCreateTask : ruleCreateTaskList) {
                        ruleCreateTask.create(securityGroupApi, defaultSecurityGroup);
                    }
                }

                return null;
            }
        });
    }

    private <T> Tuple2<List<T>, Integer> listResourcesByNameAndPage(List<T> resources, Function1<T, String> resourceNameGetter, String name, Integer currentPage, Integer recordsPerPage) throws Exception {
        List<T> nameMatchedResources;
        if (StringUtils.isNotEmpty(name)) {
            nameMatchedResources = new LinkedList<T>();
            for (T resource : resources) {
                if (StringUtils.contains(resourceNameGetter.apply(resource), name)) {
                    nameMatchedResources.add(resource);
                }
            }
        } else {
            nameMatchedResources = resources;
        }

        List<T> pagedResources;
        if (currentPage != null && recordsPerPage != null) {
            if (currentPage > 0 && recordsPerPage > 0) {
                int resourceBeginIndex = (currentPage - 1) * recordsPerPage;
                int resourceEndIndex = resourceBeginIndex + recordsPerPage;
                if (resourceBeginIndex >= nameMatchedResources.size()) {
                    pagedResources = new LinkedList<T>();
                } else {
                    if (resourceEndIndex > nameMatchedResources.size()) {
                        resourceEndIndex = nameMatchedResources.size();
                    }
                    pagedResources = nameMatchedResources.subList(resourceBeginIndex, resourceEndIndex);
                }
            } else {
                pagedResources = new LinkedList<T>();
            }
        } else {
            pagedResources = nameMatchedResources;
        }

        return new Tuple2<List<T>, Integer>(pagedResources, nameMatchedResources.size());
    }

    @Override
    public Page listPrivateSubnet(final NeutronApi neutronApi, final String region, final String name, final Integer currentPage, final Integer recordsPerPage) throws OpenStackException {
        checkRegion(region, neutronApi);
        if (currentPage != null && currentPage < 1) {
            throw new OpenStackException("currentPage < 1", "当前页不能小于1");
        }
        if (recordsPerPage != null && recordsPerPage < 1) {
            throw new OpenStackException("recordsPerPage < 1", "每页子网数不能小于1");
        }

        final PortApi portApi = neutronApi.getPortApi(region);
        final SubnetApi subnetApi = neutronApi.getSubnetApi(region);
        final NetworkApi networkApi = neutronApi.getNetworkApi(region);
        final RouterApi routerApi = getRouterApi(neutronApi, region);

        List<Ref<Object>> objRefList = ThreadUtil.concurrentRunAndWait(
                new Function0<Object>() {
                    @Override
                    public Object apply() throws Exception {
                        Map<String, Port> subnetIdToPort = new HashMap<String, Port>();
                        for (Port port : portApi.list()
                                .concat().toList()) {
                            if (OpenStackConstants.PORT_DEVICE_OWNER_NETWORK_ROUTER_INTERFACE.equals(port
                                    .getDeviceOwner())) {
                                ImmutableSet<IP> fixedIps = port.getFixedIps();
                                if (fixedIps != null) {
                                    for (IP ip : fixedIps) {
                                        subnetIdToPort.put(ip.getSubnetId(),
                                                port);
                                    }
                                }
                            }
                        }
                        return subnetIdToPort;
                    }
                }, new Function0<Object>() {
                    @Override
                    public Object apply() throws Exception {
                        Map<String, Network> idToPrivateNetwork = new HashMap<String, Network>();
                        for (Network network : networkApi
                                .list().concat().toList()) {
                            if (NetworkManagerImpl.isPrivateNetwork(network)) {
                                idToPrivateNetwork.put(network.getId(), network);
                            }
                        }

                        List<Subnet> subnetList = subnetApi.list().concat().toList();
                        List<Subnet> privateSubnetList = new LinkedList<Subnet>();
                        for (Subnet subnet : subnetList) {
                            if (idToPrivateNetwork.containsKey(subnet.getNetworkId())) {
                                privateSubnetList.add(subnet);
                            }
                        }
                        Collections.sort(privateSubnetList, new Comparator<Subnet>() {
                            @Override
                            public int compare(Subnet o1, Subnet o2) {
                                return o2.getCreated().compareTo(o1.getCreated());
                            }
                        });

                        Tuple2<List<Subnet>, Integer> pagedSubnetListAndTotalCount = listResourcesByNameAndPage(privateSubnetList, new Function1<Subnet, String>() {
                            @Override
                            public String apply(Subnet subnet) throws Exception {
                                return subnet.getName();
                            }
                        }, name, currentPage, recordsPerPage);

                        return new Tuple3<Map<String, Network>, List<Subnet>, Integer>(idToPrivateNetwork, pagedSubnetListAndTotalCount._1, pagedSubnetListAndTotalCount._2);
                    }
                }, new Function0<Object>() {
                    @Override
                    public Object apply() throws Exception {
                        Map<String, Router> idToRouter = new HashMap<String, Router>();
                        for (Router router : routerApi.list().concat().toList()) {
                            idToRouter.put(router.getId(), router);
                        }
                        return idToRouter;
                    }
                });

        Map<String, Port> subnetIdToPort = (Map<String, Port>) objRefList.get(0).get();

        Tuple3<Map<String, Network>, List<Subnet>, Integer> idToPrivateNetworkAndSubnetListAndTotalCount = (Tuple3<Map<String, Network>, List<Subnet>, Integer>) objRefList.get(1).get();
        Map<String, Network> idToPrivateNetwork = idToPrivateNetworkAndSubnetListAndTotalCount._1;
        List<Subnet> subnetList = idToPrivateNetworkAndSubnetListAndTotalCount._2;
        Integer subnetTotalCount = idToPrivateNetworkAndSubnetListAndTotalCount._3;

        Map<String, Router> idToRouter = (Map<String, Router>) objRefList.get(2).get();

        List<SubnetResource> subnetResourceList = new LinkedList<SubnetResource>();
        for (Subnet subnet : subnetList) {
            NetworkResource networkResource = null;
            Network network = idToPrivateNetwork.get(subnet.getNetworkId());
            if (network != null) {
                networkResource = new NetworkResourceImpl(region, network);
            }

            RouterResource routerResource = null;
            Router router = null;
            Port port = subnetIdToPort.get(subnet.getId());
            if (port != null) {
                String routerId = port.getDeviceId();
                if (routerId != null) {
                    router = idToRouter.get(routerId);
                }
            }
            if (router != null) {
                routerResource = new RouterResourceImpl(region, router);
            }

            SubnetResource subnetResource = new SubnetResourceImpl(region, "", subnet, networkResource, routerResource);
            subnetResourceList.add(subnetResource);
        }

        Page page;
        if (currentPage != null && recordsPerPage != null) {
            page = new Page(currentPage, recordsPerPage);
        } else {
            page = new Page();
        }
        page.setData(subnetResourceList);
        page.setTotalRecords(subnetTotalCount);
        return page;
    }

    private Snapshot getVolumeSnapshot(SnapshotApi snapshotApi, String volumeSnapshotId) throws ResourceNotFoundException {
        Snapshot snapshot = snapshotApi.get(volumeSnapshotId);
        if (snapshot == null) {
            throw new ResourceNotFoundException("Volume Snapshot", "云硬盘快照", volumeSnapshotId);
        }
        return snapshot;
    }

    @Override
    public VolumeSnapshotResource getVolumeSnapshot(CinderApi cinderApi, long userVoUserId, String region, String volumeSnapshotId) throws OpenStackException {
        checkRegion(region, cinderApi);

        SnapshotApi snapshotApi = cinderApi.getSnapshotApi(region);

        Snapshot snapshot = getVolumeSnapshot(snapshotApi, volumeSnapshotId);

        String volumeId = snapshot.getVolumeId();
        VolumeResource volumeResource = null;
        if (StringUtils.isNotEmpty(volumeId)) {
            CloudvmVolume cloudvmVolume = cloudvmVolumeService.selectByVolumeId(userVoUserId, region, volumeId);
            if (cloudvmVolume != null) {
                volumeResource = new LocalVolumeResource(cloudvmVolume);
            }
        }

        if (volumeResource != null) {
            return new VolumeSnapshotResourceImpl(region, snapshot, volumeResource);
        } else {
            return new VolumeSnapshotResourceImpl(region, snapshot);
        }
    }

    @Override
    public Page listVolume(CinderApi cinderApi, long userVoUserId, String region, String name, Integer currentPage, Integer recordsPerPage) throws OpenStackException {
        localRegionService.get(region);

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
        List<CloudvmVolume> cloudvmVolumes = cloudvmVolumeService.selectByName(userVoUserId, region, name, page);

        List<VolumeResource> volumeResources = new LinkedList<VolumeResource>();
        Map<String, LocalVolumeResource> idToLocalVolumeResource = new HashMap<String, LocalVolumeResource>();
        for (CloudvmVolume cloudvmVolume : cloudvmVolumes) {
            LocalVolumeResource localVolumeResource = new LocalVolumeResource(cloudvmVolume);
            volumeResources.add(localVolumeResource);
            idToLocalVolumeResource.put(localVolumeResource.getId(), localVolumeResource);
        }

        if (!cloudvmVolumes.isEmpty()) {
            checkRegion(region, cinderApi);

            SnapshotApi snapshotApi = cinderApi.getSnapshotApi(region);
            for (Snapshot snapshot : snapshotApi.list().toList()) {
                String snapshotVolumeId = snapshot.getVolumeId();
                if (StringUtils.isNotEmpty(snapshotVolumeId)) {
                    LocalVolumeResource localVolumeResource = idToLocalVolumeResource.get(snapshotVolumeId);
                    if (localVolumeResource != null) {
                        List<VolumeSnapshotResource> volumeSnapshotResources = localVolumeResource.getSnapshots();
                        if (volumeSnapshotResources == null) {
                            volumeSnapshotResources = new LinkedList<VolumeSnapshotResource>();
                            localVolumeResource.setSnapshots(volumeSnapshotResources);
                        }
                        volumeSnapshotResources.add(new VolumeSnapshotResourceImpl(region, snapshot));
                    }
                }
            }
        }

        if (page == null) {
            page = new Page();
        }
        page.setTotalRecords(cloudvmVolumeService.selectCountByName(userVoUserId, region, name));
        page.setData(volumeResources);
        return page;
    }

}
