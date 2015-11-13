package com.letv.portal.service.openstack.local.service.impl;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.jclouds.openstack.neutron.v2.NeutronApi;
import org.jclouds.openstack.neutron.v2.domain.Network;
import org.jclouds.openstack.neutron.v2.features.NetworkApi;
import org.springframework.stereotype.Service;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.letv.common.exception.MatrixException;
import com.letv.portal.service.openstack.exception.OpenStackException;
import com.letv.portal.service.openstack.local.service.LocalNetworkService;
import com.letv.portal.service.openstack.util.ExceptionUtil;
import com.letv.portal.service.openstack.util.Ref;

@Service
public class LocalNetworkServiceImpl implements LocalNetworkService {

	private Cache<String, String> regionToPublicNetworkId;

	public LocalNetworkServiceImpl() {
		regionToPublicNetworkId = CacheBuilder.newBuilder().maximumSize(100)
				.expireAfterAccess(100, TimeUnit.DAYS).build();
	}

	private static class GetPublicNetworkIdTask implements Callable<String> {
		private NetworkApi networkApi;
		private Ref<Boolean> newFetchRef;

		GetPublicNetworkIdTask(NetworkApi networkApi, Ref<Boolean> newFetchRef) {
			this.networkApi = networkApi;
			this.newFetchRef = newFetchRef;
		}

		@Override
		public String call() throws Exception {
			for (Network network : networkApi.list().concat().toList()) {
				if (network.getExternal()) {
					newFetchRef.set(true);
					return network.getId();
				}
			}
			return null;
		}
	}

	@Override
	public String getPublicNetworkId(NeutronApi neutronApi, String region)
			throws OpenStackException {
		try {
			final NetworkApi networkApi = neutronApi.getNetworkApi(region);
			final Ref<Boolean> newFetchRef = new Ref<Boolean>(false);
			String networkId = regionToPublicNetworkId.get(region,
					new GetPublicNetworkIdTask(networkApi, newFetchRef));
			if (newFetchRef.get()) {
				return networkId;
			} else {
				Network network = networkApi.get(
						networkId);
				if (network != null) {
					return networkId;
				} else {
					regionToPublicNetworkId.invalidate(region);
					return regionToPublicNetworkId
							.get(region, new GetPublicNetworkIdTask(networkApi,
									newFetchRef));
				}
			}
		} catch (ExecutionException ex) {
			ExceptionUtil.throwException(ExceptionUtil.getCause(ex));
		}
		throw new MatrixException("后台错误");
	}

}
