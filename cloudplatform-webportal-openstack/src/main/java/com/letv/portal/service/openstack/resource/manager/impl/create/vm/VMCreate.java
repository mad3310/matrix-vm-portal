package com.letv.portal.service.openstack.resource.manager.impl.create.vm;

import org.apache.commons.lang3.StringUtils;
import org.jclouds.openstack.cinder.v1.CinderApi;
import org.jclouds.openstack.neutron.v2.NeutronApi;
import org.jclouds.openstack.neutron.v2.domain.Network;
import org.jclouds.openstack.neutron.v2.domain.Subnet;
import org.jclouds.openstack.neutron.v2.features.NetworkApi;
import org.jclouds.openstack.neutron.v2.features.SubnetApi;
import org.jclouds.openstack.nova.v2_0.NovaApi;
import org.jclouds.openstack.nova.v2_0.domain.Flavor;
import org.jclouds.openstack.nova.v2_0.features.FlavorApi;

import com.letv.portal.service.openstack.exception.OpenStackException;
import com.letv.portal.service.openstack.exception.RegionNotFoundException;
import com.letv.portal.service.openstack.exception.ResourceNotFoundException;
import com.letv.portal.service.openstack.exception.UserOperationException;
import com.letv.portal.service.openstack.resource.manager.impl.VMManagerImpl;

public class VMCreate {

	private VMCreateConf2 vmCreateConf;
	private VMManagerImpl vmManager;

	private MultiVmCreateContext multiVmCreateContext;

	public VMCreate(VMCreateConf2 vmCreateConf, VMManagerImpl vmManager) {
		this.vmCreateConf = vmCreateConf;
		this.vmManager = vmManager;
	}

	public void run() throws OpenStackException {
		if (vmCreateConf.getCount() > 0) {
			try {
				ApiSession apiSession = new ApiSession(
						vmManager.getOpenStackConf(),
						vmManager.getOpenStackUser());
				try {
					checkVmCreateConf(apiSession);
					for (int i = 0; i < vmCreateConf.getCount(); i++) {
						createOneVm(apiSession);
					}
				} finally {
					apiSession.close();
				}
			} catch (OpenStackException ex) {
				throw ex;
			} catch (Exception ex) {
				throw new OpenStackException("后台错误", ex);
			}
		} else {
			throw new UserOperationException(
					"Virtual machine number cannot be less than or equal to 0.",
					"虚拟机数量不能小于或等于0");
		}
	}

	private void checkVmCreateConf(ApiSession apiSession)
			throws OpenStackException {
		this.multiVmCreateContext = new MultiVmCreateContext();
		this.multiVmCreateContext.setVmCreateConf(vmCreateConf);
		this.multiVmCreateContext.setVmManager(vmManager);

		NovaApi novaApi = apiSession.getNovaApi();
		NeutronApi neutronApi = apiSession.getNeutronApi();
		CinderApi cinderApi = apiSession.getCinderApi();

		final String region = vmCreateConf.getRegion();
		if (!novaApi.getConfiguredRegions().contains(region)
				|| !neutronApi.getConfiguredRegions().contains(region)
				|| !cinderApi.getConfiguredRegions().contains(region)) {
			throw new RegionNotFoundException(region);
		}

		NetworkApi networkApi = neutronApi.getNetworkApi(region);
		SubnetApi subnetApi = neutronApi.getSubnetApi(region);
		FlavorApi flavorApi = novaApi.getFlavorApi(region);

		this.multiVmCreateContext.setRegionDisplayName(vmManager
				.getRegionDisplayName(region));

		if (StringUtils.isNotEmpty(vmCreateConf.getPrivateSubnetId())) {
			Subnet privateSubnet = subnetApi.get(vmCreateConf
					.getPrivateSubnetId());
			if (privateSubnet == null) {

			}

		} else {
			Network sharedNetwork = networkApi.get(vmCreateConf
					.getSharedNetworkId());
			if (sharedNetwork == null || !sharedNetwork.getShared()) {

			}
		}

		Flavor flavor = flavorApi.get(vmCreateConf.getFlavorId());
		if (flavor == null) {
			throw new ResourceNotFoundException("Flavor", "云主机配置",
					vmCreateConf.getFlavorId());
		}

		
	}

	private void createOneVm(ApiSession apiSession) throws OpenStackException {

	}

}
