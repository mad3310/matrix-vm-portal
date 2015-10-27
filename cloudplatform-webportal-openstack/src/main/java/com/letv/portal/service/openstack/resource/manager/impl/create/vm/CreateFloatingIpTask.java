package com.letv.portal.service.openstack.resource.manager.impl.create.vm;

import org.apache.commons.lang3.StringUtils;
import org.jclouds.openstack.neutron.v2.domain.FloatingIP;
import org.jclouds.openstack.neutron.v2.domain.Quota;
import org.jclouds.openstack.neutron.v2.domain.FloatingIP.CreateFloatingIP;
import org.jclouds.openstack.neutron.v2.extensions.FloatingIPApi;

import com.letv.portal.service.openstack.exception.OpenStackException;
import com.letv.portal.service.openstack.exception.UserOperationException;
import com.letv.portal.service.openstack.resource.manager.impl.NetworkManagerImpl;

public class CreateFloatingIpTask extends VmsCreateSubTask {

	@Override
	public void run(MultiVmCreateContext context) throws OpenStackException {
		if (!context.getVmCreateConf().getBindFloatingIp()) {
			return;
		}

		int floatingIpTotalCount = 0, floatingIpTotalBandWidth = 0;
		for (FloatingIP floatingIP : context.getApiCache()
				.getNeutronFloatingIpApi().list().concat().toList()) {
			if (!StringUtils.equals(floatingIP.getFixedIpAddress(),
					floatingIP.getFloatingIpAddress())) {
				floatingIpTotalBandWidth += NetworkManagerImpl
						.getBandWidth(floatingIP.getFipQos());
				floatingIpTotalCount++;
			}
		}

		Quota quota = context
				.getApiCache()
				.getNeutronQuotaApi()
				.getByTenant(
						context.getVmManager().getOpenStackUser().getTenantId());
		if (quota == null) {
			throw new OpenStackException("Floating IP quota is not available.",
					"公网IP配额不可用。");
		}
		if (floatingIpTotalCount + context.getVmCreateConf().getCount() > quota
				.getFloatingIp() - quota.getRouter()) {
			throw new UserOperationException(
					"Floating IP count exceeding the quota.", "公网IP数量超过配额。");
		}
		if (floatingIpTotalBandWidth + context.getVmCreateConf().getCount()
				* context.getVmCreateConf().getBandWidth() > quota
				.getBandWidth()
				- quota.getRouter()
				* context.getVmManager().getOpenStackConf()
						.getRouterGatewayBandWidth()) {
			throw new UserOperationException(
					"Floating IP band width exceeding the quota.",
					"公网IP带宽超过配额。");
		}

		for (VmCreateContext vmCreateContext : context.getVmCreateContexts()) {
			vmCreateContext
					.setFloatingIp(context
							.getApiCache()
							.getNeutronFloatingIpApi()
							.create(CreateFloatingIP
									.createBuilder(
											context.getFloatingNetwork()
													.getId())
									.fipQos(NetworkManagerImpl
											.createFipQos(context
													.getVmCreateConf()
													.getBandWidth())).build()));
		}
	}

	@Override
	public void rollback(MultiVmCreateContext context)
			throws OpenStackException {
		if (!context.getVmCreateConf().getBindFloatingIp()) {
			return;
		}

		for (VmCreateContext vmCreateContext : context.getVmCreateContexts()) {
			if (vmCreateContext.getServerCreated() == null
					&& vmCreateContext.getFloatingIp() != null) {
				ApiCache apiCache = context.getApiCache();
				FloatingIPApi floatingIPApi = apiCache
						.getNeutronFloatingIpApi();
				floatingIPApi.delete(vmCreateContext.getFloatingIp().getId());
			}
		}
	}

}
