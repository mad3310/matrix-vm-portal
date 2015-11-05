package com.letv.portal.service.openstack.resource.manager.impl.create.vm.check;

import com.google.common.collect.ImmutableSet;
import com.letv.portal.service.openstack.exception.OpenStackException;
import com.letv.portal.service.openstack.exception.RegionNotFoundException;
import com.letv.portal.service.openstack.exception.ResourceNotFoundException;
import com.letv.portal.service.openstack.exception.UserOperationException;
import com.letv.portal.service.openstack.impl.OpenStackServiceImpl;
import com.letv.portal.service.openstack.resource.manager.impl.create.vm.VMCreateConf2;
import org.apache.commons.lang3.CharUtils;
import org.apache.commons.lang3.StringUtils;
import org.jclouds.openstack.cinder.v1.domain.VolumeType;
import org.jclouds.openstack.neutron.v2.domain.*;
import org.jclouds.openstack.nova.v2_0.domain.Flavor;
import org.jclouds.openstack.nova.v2_0.domain.Image;
import org.jclouds.openstack.nova.v2_0.domain.KeyPair;

import java.text.MessageFormat;

/**
 * Created by zhouxianguang on 2015/10/20.
 */
public class CheckVmCreateConfTask implements VmsCreateCheckSubTask{
    @Override
    public void run(MultiVmCreateCheckContext context) throws OpenStackException {
        VMCreateConf2 vmCreateConf = context.getVmCreateConf();

        final String region = context.getVmCreateConf().getRegion();
        if (!context.getNovaApi().getConfiguredRegions().contains(region)
                || !context.getNeutronApi().getConfiguredRegions()
                .contains(region)
                || !context.getCinderApi().getConfiguredRegions()
                .contains(region)) {
            throw new RegionNotFoundException(region);
        }

        if (StringUtils.isNotEmpty(vmCreateConf.getPrivateSubnetId())) {
            Subnet privateSubnet = context.getNeutronApi()
                    .getSubnetApi(region).get(vmCreateConf.getPrivateSubnetId());
            if (privateSubnet == null) {
                throw new ResourceNotFoundException("Subnet", "子网",
                        vmCreateConf.getPrivateSubnetId());
            } else {
                Network privateNetwork = context.getNeutronApi()
                        .getNetworkApi(region).get(privateSubnet.getNetworkId());
                if (privateNetwork == null || privateNetwork.getShared()
                        || privateNetwork.getExternal()) {
                    throw new ResourceNotFoundException("Private Subnet",
                            "私有子网", vmCreateConf.getPrivateSubnetId());
                } else {
                    context.setPrivateNetwork(privateNetwork);
                    context.setPrivateSubnet(privateSubnet);
                }
            }
        } else {
            Network sharedNetwork = context.getNeutronApi()
                    .getNetworkApi(region).get(vmCreateConf.getSharedNetworkId());
            if (sharedNetwork == null || !sharedNetwork.getShared()) {
                throw new ResourceNotFoundException("Shared Network", "共享网络",
                        vmCreateConf.getSharedNetworkId());
            }
            context.setSharedNetwork(sharedNetwork);
        }

        Flavor flavor = context.getNovaApi().getFlavorApi(region)
                .get(vmCreateConf.getFlavorId());
        if (flavor == null) {
            throw new ResourceNotFoundException("Flavor", "云主机配置",
                    vmCreateConf.getFlavorId());
        }
        context.setFlavor(flavor);

        if (StringUtils.isNotEmpty(vmCreateConf.getImageId())) {
            Image image = context.getNovaApi().getImageApi(region)
                    .get(vmCreateConf.getImageId());
            if (image == null) {
                throw new ResourceNotFoundException("Image", "镜像",
                        vmCreateConf.getImageId());
            }
            context.setImage(image);
        } else {
            Image snapshot = context.getNovaApi()
                    .getImageApi(region).get(vmCreateConf.getSnapshotId());
            if (snapshot == null) {
                throw new ResourceNotFoundException("Image", "快照",
                        vmCreateConf.getSnapshotId());
            }
            context.setSnapshot(snapshot);
        }

        if (vmCreateConf.getVolumeSize() < 0) {
            throw new UserOperationException(
                    "Data disk size cannot be less than zero", "数据盘大小不能小于0");
        } else if (vmCreateConf.getVolumeSize() > 0) {
            VolumeType volumeType = context.getCinderApi()
                    .getVolumeTypeApi(region).get(vmCreateConf.getVolumeTypeId());
            if (volumeType == null) {
                throw new ResourceNotFoundException("Volume Type", "快照",
                        vmCreateConf.getVolumeTypeId());
            }
            context.setVolumeType(volumeType);
        }

        if (vmCreateConf.getBindFloatingIp()) {
            if (vmCreateConf.getBandWidth() <= 0) {
                throw new UserOperationException(
                        "The bandwidth must be greater than zero.", "带宽必须大于0");
            }

            if (StringUtils.isEmpty(vmCreateConf.getFloatingNetworkId())) {
                vmCreateConf.setFloatingNetworkId(context
                        .getVmManager().getOpenStackConf()
                        .getGlobalPublicNetworkId());
            }
            Network floatingNetwork = context.getNeutronApi()
                    .getNetworkApi(region).get(vmCreateConf.getFloatingNetworkId());
            if (floatingNetwork == null || !floatingNetwork.getExternal()) {
                throw new ResourceNotFoundException("Floating Network", "公网",
                        vmCreateConf.getFloatingNetworkId());
            }
            context.setFloatingNetwork(floatingNetwork);

            if (context.getPrivateSubnet() != null) {
                String routerId = null;
                findRouterId: for (Port port : context
                        .getNeutronApi().getPortApi(region).list().concat().toList()) {
                    if ("network:router_interface"
                            .equals(port.getDeviceOwner())) {
                        if (context.getPrivateSubnet()
                                .getNetworkId().equals(port.getNetworkId())) {
                            ImmutableSet<IP> fixedIps = port.getFixedIps();
                            if (fixedIps != null) {
                                for (IP ip : fixedIps) {
                                    if (context.getPrivateSubnet()
                                            .getId().equals(ip.getSubnetId())) {
                                        routerId = port.getDeviceId();
                                        break findRouterId;
                                    }
                                }
                            }
                        }
                    }
                }
                if (routerId == null) {
                    throw new UserOperationException(
                            "Private subnet is not associate with router.",
                            "私有子网未关联路由");
                }
                Router router = context.getNeutronApi()
                        .getRouterApi(region).get().get(routerId);
                if (router == null) {
                    throw new ResourceNotFoundException("Router", "路由",
                            routerId);
                }
                if (router.getExternalGatewayInfo() == null
                        || StringUtils.isEmpty(router.getExternalGatewayInfo()
                        .getNetworkId())) {
                    throw new UserOperationException(MessageFormat.format(
                            "Router \"{0}\" is not enable gateway.", routerId),
                            MessageFormat.format("私有子网关联的路由“{0}”未设置网关",
                                    routerId));
                }
                if (!StringUtils.equals(router.getExternalGatewayInfo()
                        .getNetworkId(), context
                        .getFloatingNetwork().getId())) {
                    throw new UserOperationException(
                            MessageFormat.format(
                                    "The carrier of Router \"{0}\" is not the carrier of Floating IP.",
                                    routerId), MessageFormat.format(
                            "路由“{0}”的线路和公网IP的线路不是同一个线路", routerId));
                }
            }
        }

        if (StringUtils.isNotEmpty(vmCreateConf.getKeyPairName())) {
            KeyPair keyPair = context.getNovaApi()
                    .getKeyPairApi(region).get().get(vmCreateConf.getKeyPairName());
            if (keyPair == null) {
                throw new ResourceNotFoundException("KeyPair", "密钥对",
                        vmCreateConf.getKeyPairName());
            }
            context.setKeyPair(keyPair);
        } else {
            OpenStackServiceImpl.getOpenStackServiceGroup().getPasswordService().validateUserAdminPass(vmCreateConf.getAdminPass());
        }

        if (vmCreateConf.getCount() <= 0) {
            throw new UserOperationException(
                    "Virtual machine number must be greater than zero.",
                    "虚拟机数量必须大于0");
        }
    }
}
