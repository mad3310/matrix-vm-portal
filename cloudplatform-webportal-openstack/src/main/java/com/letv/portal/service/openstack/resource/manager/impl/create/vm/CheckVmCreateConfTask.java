package com.letv.portal.service.openstack.resource.manager.impl.create.vm;

import org.apache.commons.lang3.CharUtils;
import org.apache.commons.lang3.StringUtils;
import org.jclouds.openstack.cinder.v1.domain.VolumeType;
import org.jclouds.openstack.neutron.v2.domain.Network;
import org.jclouds.openstack.neutron.v2.domain.Subnet;
import org.jclouds.openstack.nova.v2_0.domain.Flavor;
import org.jclouds.openstack.nova.v2_0.domain.Image;
import org.jclouds.openstack.nova.v2_0.domain.KeyPair;

import ch.qos.logback.core.Context;

import com.letv.portal.service.openstack.exception.OpenStackException;
import com.letv.portal.service.openstack.exception.ResourceNotFoundException;
import com.letv.portal.service.openstack.exception.UserOperationException;

public class CheckVmCreateConfTask implements VmsCreateSubTask {

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
				vmCreateConf.setFloatingNetworkId(multiVmCreateContext
						.getVmManager().getOpenStackConf()
						.getGlobalPublicNetworkId());
			}
			Network floatingNetwork = multiVmCreateContext.getApiCache()
					.getNetworkApi().get(vmCreateConf.getFloatingNetworkId());
			if (floatingNetwork == null || !floatingNetwork.getExternal()) {
				throw new ResourceNotFoundException("Floating Network", "公网",
						vmCreateConf.getFloatingNetworkId());
			}
			multiVmCreateContext.setFloatingNetwork(floatingNetwork);
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
			for (char ch : vmCreateConf.getAdminPass().toCharArray()) {
				if (!CharUtils.isAsciiAlphanumeric(ch) && ch != '_') {
					throw new UserOperationException(
							"User password contains illegal characters.",
							"用户密码包含不合法的字符");
				}
			}
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
