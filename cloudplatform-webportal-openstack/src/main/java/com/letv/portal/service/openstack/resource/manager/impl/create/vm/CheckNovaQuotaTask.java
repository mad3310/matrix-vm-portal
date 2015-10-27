package com.letv.portal.service.openstack.resource.manager.impl.create.vm;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.jclouds.openstack.nova.v2_0.domain.Flavor;
import org.jclouds.openstack.nova.v2_0.domain.Quota;
import org.jclouds.openstack.nova.v2_0.domain.Server;

import com.letv.portal.service.openstack.exception.OpenStackException;
import com.letv.portal.service.openstack.exception.UserOperationException;

public class CheckNovaQuotaTask extends VmsCreateSubTask {

	@Override
	public void run(MultiVmCreateContext context) throws OpenStackException {
		int serverTotalCount = 0, serverTotalVcpus = 0, serverTotalRam = 0;
		List<Server> servers = context.getApiCache().getServerApi()
				.listInDetail().concat().toList();
		Map<String, Flavor> idToFlavor = new HashMap<String, Flavor>();
		for (Server server : servers) {
			serverTotalCount++;
			String flavorId = server.getFlavor().getId();
			Flavor flavor = idToFlavor.get(flavorId);
			if (flavor == null) {
				flavor = context.getApiCache().getFlavorApi().get(flavorId);
				idToFlavor.put(flavorId, flavor);
			}
			serverTotalVcpus += flavor.getVcpus();
			serverTotalRam += flavor.getRam();
		}

		Quota novaQuota = context
				.getApiCache()
				.getNovaQuotaApi()
				.getByTenant(
						context.getVmManager().getOpenStackUser().getTenantId());
		if (novaQuota == null) {
			throw new OpenStackException("VM quota is not available.",
					"虚拟机配额不可用。");
		}

		if (serverTotalCount + context.getVmCreateConf().getCount() > novaQuota
				.getInstances()) {
			throw new UserOperationException("VM count exceeding the quota.",
					"虚拟机数量超过配额。");
		}

		if (serverTotalVcpus + context.getVmCreateConf().getCount()
				* context.getFlavor().getVcpus() > novaQuota.getCores()) {
			throw new UserOperationException("Vcpu count exceeding the quota.",
					"虚拟CPU数量超过配额。");
		}

		if (serverTotalRam + context.getVmCreateConf().getCount()
				* context.getFlavor().getRam() > novaQuota.getRam()) {
			throw new UserOperationException(
					"Ram amounts exceeding the quota.", "内存总量超过配额。");
		}

		List<VmCreateContext> vmCreateContexts = new LinkedList<VmCreateContext>();
		for (int i = 0; i < context.getVmCreateConf().getCount(); i++) {
			vmCreateContexts.add(new VmCreateContext());
		}
		context.setVmCreateContexts(vmCreateContexts);
	}

	@Override
	public void rollback(MultiVmCreateContext context)
			throws OpenStackException {
	}

}
