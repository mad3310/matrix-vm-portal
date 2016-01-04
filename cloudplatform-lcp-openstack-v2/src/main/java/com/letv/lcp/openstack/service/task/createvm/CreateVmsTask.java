package com.letv.lcp.openstack.service.task.createvm;

import org.jclouds.openstack.nova.v2_0.domain.Network;
import org.jclouds.openstack.nova.v2_0.domain.ServerCreated;
import org.jclouds.openstack.nova.v2_0.options.CreateServerOptions;

import com.google.common.collect.ImmutableSet;
import com.letv.lcp.openstack.exception.OpenStackException;
import com.letv.lcp.openstack.service.base.impl.OpenStackServiceImpl;
import com.letv.lcp.openstack.service.local.ILocalVolumeService;

public class CreateVmsTask extends VmsCreateSubTask {

	@Override
	public void run(MultiVmCreateContext context) throws OpenStackException {
		for (int i = 0; i < context.getVmCreateConf().getCount(); i++) {
			createOneVm(context, context.getVmCreateContexts().get(i), i);
		}
	}

	private void createOneVm(MultiVmCreateContext context,
							 VmCreateContext vmContext, int vmIndex) throws OpenStackException {
		CreateServerOptions options = new CreateServerOptions();

		if (context.getKeyPair() != null) {
			options.keyPairName(context.getKeyPair().getName());
		} else {
			options.adminPass(context.getVmCreateConf().getAdminPass());
		}

		String imageRef = null;
		if (context.getImage() != null) {
			imageRef = context.getImage().getId();
		} else {
			imageRef = context.getSnapshot().getId();
		}

		options.securityGroupNames("default");

		if (context.getPrivateSubnet() != null) {
			options.novaNetworks(ImmutableSet.<Network> of(Network.builder()
					.portUuid(vmContext.getSubnetPort().getId()).build()));
		} else {
			options.networks(context.getSharedNetwork().getId());
		}

		ServerCreated serverCreated = context
				.getApiCache()
				.getServerApi()
				.create(vmContext.getResourceName(), imageRef,
						context.getFlavor().getId(), options);
		vmContext.setServerCreated(serverCreated);

		vmContext.setServer(context.getApiCache().getServerApi().get(serverCreated.getId()));

		context.getVmManager().recordVmCreated(context.getUserId(), context.getVmCreateConf().getRegion(), vmContext.getServer(), context.getFlavor());

        if (vmContext.getVolume() != null) {
            ILocalVolumeService localVolumeService = OpenStackServiceImpl.getOpenStackServiceGroup().getLocalVolumeService();
            localVolumeService.updateVmIdAndVmName(context.getUserId()
					, context.getUserId()
					, context.getVmCreateConf().getRegion()
					, vmContext.getVolume().getId()
					, vmContext.getServer().getId()
			        , vmContext.getServer().getName());
        }
    }

	@Override
	public void rollback(MultiVmCreateContext context)
			throws OpenStackException {
	}

	@Override
	boolean needContinueAfterException() {
		return true;
	}
}
