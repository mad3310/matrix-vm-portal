package com.letv.lcp.openstack.service.task.createvm;

import com.letv.lcp.openstack.exception.OpenStackException;
import com.letv.lcp.openstack.exception.RegionNotFoundException;


public class CreateApiCacheTask extends VmsCreateSubTask {

	private ApiSession apiSession;

	public CreateApiCacheTask(ApiSession apiSession) {
		this.apiSession = apiSession;
	}

	@Override
	public void run(MultiVmCreateContext context) throws OpenStackException {
		final String region = context.getVmCreateConf().getRegion();
		if (!apiSession.getNovaApi().getConfiguredRegions().contains(region)
				|| !apiSession.getNeutronApi().getConfiguredRegions()
						.contains(region)
				|| !apiSession.getCinderApi().getConfiguredRegions()
						.contains(region)) {
			throw new RegionNotFoundException(region);
		}
		context.setRegionDisplayName(context.getVmManager()
				.getRegionDisplayName(region));

		ApiCache apiCache = new ApiCache(apiSession, context.getVmCreateConf()
				.getRegion());
		context.setApiCache(apiCache);
	}

	@Override
	public void rollback(MultiVmCreateContext context) {
	}

}
