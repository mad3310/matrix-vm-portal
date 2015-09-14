package com.letv.portal.service.openstack.resource.manager.impl.create.vm;

import org.jclouds.openstack.nova.v2_0.options.CreateServerOptions;

import com.letv.portal.service.openstack.exception.OpenStackException;

public class CreateVmsTask implements VmsCreateSubTask {

	@Override
	public void run(MultiVmCreateContext context) throws OpenStackException {
		for (int i = 0; i < context.getVmCreateConf().getCount(); i++) {
			createOneVm(context, i);
		}
	}

	private void createOneVm(MultiVmCreateContext context, int sequence)
			throws OpenStackException {
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

		if (context.getPrivateNetwork() != null) {
			// TODO set private network and subnet
		} else {
			options.networks(context.getSharedNetwork().getId());
		}

		context.getApiCache()
				.getServerApi()
				.create(context.getVmCreateConf().getName(), imageRef,
						context.getFlavor().getId(), options);

		context.getVmManager().incVmCount();
	}

	@Override
	public void rollback(MultiVmCreateContext context)
			throws OpenStackException {
	}

}
