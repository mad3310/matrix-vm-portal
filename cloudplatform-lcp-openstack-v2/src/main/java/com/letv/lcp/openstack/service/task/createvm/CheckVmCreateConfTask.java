package com.letv.lcp.openstack.service.task.createvm;

import java.text.MessageFormat;

import org.apache.commons.lang3.StringUtils;
import org.jclouds.openstack.cinder.v1.domain.VolumeType;
import org.jclouds.openstack.neutron.v2.domain.IP;
import org.jclouds.openstack.neutron.v2.domain.Network;
import org.jclouds.openstack.neutron.v2.domain.Port;
import org.jclouds.openstack.neutron.v2.domain.Router;
import org.jclouds.openstack.neutron.v2.domain.Subnet;
import org.jclouds.openstack.nova.v2_0.domain.Flavor;
import org.jclouds.openstack.nova.v2_0.domain.Image;
import org.jclouds.openstack.nova.v2_0.domain.KeyPair;

import com.google.common.collect.ImmutableSet;
import com.letv.lcp.openstack.exception.OpenStackException;
import com.letv.lcp.openstack.exception.ResourceNotFoundException;
import com.letv.lcp.openstack.exception.UserOperationException;
import com.letv.lcp.openstack.service.base.impl.OpenStackServiceImpl;

public class CheckVmCreateConfTask extends VmsCreateSubTask {

	@Override
	public void run(MultiVmCreateContext multiVmCreateContext)
			throws OpenStackException {
		VMCreateConf2 vmCreateConf = multiVmCreateContext.getVmCreateConf();

		if (StringUtils.isNotEmpty(vmCreateConf.getPrivateSubnetId())) {
			Subnet privateSubnet = multiVmCreateContext.getApiCache()
					.getSubnetApi().get(vmCreateConf.getPrivateSubnetId());
			if (privateSubnet == null) {
				throw new ResourceNotFoundException("Subnet", "子网",
						vmCreateConf.getPrivateSubnetId());
			} else {
				Network privateNetwork = multiVmCreateContext.getApiCache()
						.getNetworkApi().get(privateSubnet.getNetworkId());
				if (privateNetwork == null || privateNetwork.getShared()
						|| privateNetwork.getExternal()) {
					throw new ResourceNotFoundException("Private Subnet",
							"私有子网", vmCreateConf.getPrivateSubnetId());
				} else {
					multiVmCreateContext.setPrivateNetwork(privateNetwork);
					multiVmCreateContext.setPrivateSubnet(privateSubnet);
				}
			}
		} else {
			Network sharedNetwork = multiVmCreateContext.getApiCache()
					.getNetworkApi().get(vmCreateConf.getSharedNetworkId());
			if (sharedNetwork == null || !sharedNetwork.getShared()) {
				throw new ResourceNotFoundException("Shared Network", "共享网络",
						vmCreateConf.getSharedNetworkId());
			}
			multiVmCreateContext.setSharedNetwork(sharedNetwork);
		}

		Flavor flavor = multiVmCreateContext.getApiCache().getFlavorApi()
				.get(vmCreateConf.getFlavorId());
		if (flavor == null) {
			throw new ResourceNotFoundException("Flavor", "云主机配置",
					vmCreateConf.getFlavorId());
		}
		multiVmCreateContext.setFlavor(flavor);

		if (StringUtils.isNotEmpty(vmCreateConf.getImageId())) {
			Image image = multiVmCreateContext.getApiCache().getNovaImageApi()
					.get(vmCreateConf.getImageId());
			if (image == null) {
				throw new ResourceNotFoundException("Image", "镜像",
						vmCreateConf.getImageId());
			}
			multiVmCreateContext.setImage(image);
		} else {
			Image snapshot = multiVmCreateContext.getApiCache()
					.getNovaImageApi().get(vmCreateConf.getSnapshotId());
			if (snapshot == null) {
				throw new ResourceNotFoundException("Image", "快照",
						vmCreateConf.getSnapshotId());
			}
			multiVmCreateContext.setSnapshot(snapshot);
		}

		if (vmCreateConf.getVolumeSize() < 0) {
			throw new UserOperationException(
					"Data disk size cannot be less than zero", "数据盘大小不能小于0");
		} else if (vmCreateConf.getVolumeSize() > 0) {
			VolumeType volumeType = multiVmCreateContext.getApiCache()
					.getVolumeTypeApi().get(vmCreateConf.getVolumeTypeId());
			if (volumeType == null) {
				throw new ResourceNotFoundException("Volume Type", "快照",
						vmCreateConf.getVolumeTypeId());
			}
			multiVmCreateContext.setVolumeType(volumeType);
		}

		if (vmCreateConf.getBindFloatingIp()) {
			if (vmCreateConf.getBandWidth() <= 0) {
				throw new UserOperationException(
						"The bandwidth must be greater than zero.", "带宽必须大于0");
			}

			if (StringUtils.isEmpty(vmCreateConf.getFloatingNetworkId())) {
				vmCreateConf.setFloatingNetworkId(OpenStackServiceImpl.getOpenStackServiceGroup()
                		.getLocalNetworkService().getPublicNetworkId(multiVmCreateContext.getApiCache().getApiSession().getNeutronApi(), vmCreateConf.getRegion()));
			}
			Network floatingNetwork = multiVmCreateContext.getApiCache()
					.getNetworkApi().get(vmCreateConf.getFloatingNetworkId());
			if (floatingNetwork == null || !floatingNetwork.getExternal()) {
				throw new ResourceNotFoundException("Floating Network", "公网",
						vmCreateConf.getFloatingNetworkId());
			}
			multiVmCreateContext.setFloatingNetwork(floatingNetwork);

			if (multiVmCreateContext.getPrivateSubnet() != null) {
				String routerId = null;
				findRouterId: for (Port port : multiVmCreateContext
						.getApiCache().getPortApi().list().concat().toList()) {
					if ("network:router_interface"
							.equals(port.getDeviceOwner())) {
						if (multiVmCreateContext.getPrivateSubnet()
								.getNetworkId().equals(port.getNetworkId())) {
							ImmutableSet<IP> fixedIps = port.getFixedIps();
							if (fixedIps != null) {
								for (IP ip : fixedIps) {
									if (multiVmCreateContext.getPrivateSubnet()
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
				Router router = multiVmCreateContext.getApiCache()
						.getRouterApi().get(routerId);
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
						.getNetworkId(), multiVmCreateContext
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
			KeyPair keyPair = multiVmCreateContext.getApiCache()
					.getKeyPairApi().get(vmCreateConf.getKeyPairName());
			if (keyPair == null) {
				throw new ResourceNotFoundException("KeyPair", "密钥对",
						vmCreateConf.getKeyPairName());
			}
			multiVmCreateContext.setKeyPair(keyPair);
		} else {
			OpenStackServiceImpl.getOpenStackServiceGroup().getPasswordService().validateUserAdminPass(vmCreateConf.getAdminPass());
		}

		if (vmCreateConf.getCount() <= 0) {
			throw new UserOperationException(
					"Virtual machine number must be greater than zero.",
					"虚拟机数量必须大于0");
		}
	}

	@Override
	public void rollback(MultiVmCreateContext context)
			throws OpenStackException {
	}

}
