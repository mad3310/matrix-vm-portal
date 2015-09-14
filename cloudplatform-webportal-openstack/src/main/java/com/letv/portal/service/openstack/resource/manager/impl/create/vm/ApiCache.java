package com.letv.portal.service.openstack.resource.manager.impl.create.vm;

import java.util.HashMap;
import java.util.Map;

import org.jclouds.openstack.cinder.v1.features.VolumeTypeApi;
import org.jclouds.openstack.glance.v1_0.features.ImageApi;
import org.jclouds.openstack.neutron.v2.extensions.FloatingIPApi;
import org.jclouds.openstack.neutron.v2.features.NetworkApi;
import org.jclouds.openstack.neutron.v2.features.SubnetApi;
import org.jclouds.openstack.nova.v2_0.extensions.KeyPairApi;
import org.jclouds.openstack.nova.v2_0.extensions.QuotaApi;
import org.jclouds.openstack.nova.v2_0.features.FlavorApi;
import org.jclouds.openstack.nova.v2_0.features.ServerApi;

import com.google.common.base.Optional;
import com.letv.portal.service.openstack.exception.APINotAvailableException;

public class ApiCache {

	private ApiSession apiSession;
	private String region;
	private Map<Class<?>, Object> cache;

	public ApiCache(ApiSession apiSession, String region) {
		this.apiSession = apiSession;
		this.region = region;
		this.cache = new HashMap<Class<?>, Object>();
	}

	public ApiSession getApiSession() {
		return apiSession;
	}

	public NetworkApi getNetworkApi() {
		NetworkApi networkApi = (NetworkApi) this.cache.get(NetworkApi.class);
		if (networkApi == null) {
			networkApi = apiSession.getNeutronApi().getNetworkApi(region);
			this.cache.put(NetworkApi.class, networkApi);
		}
		return networkApi;
	}

	public SubnetApi getSubnetApi() {
		SubnetApi subnetApi = (SubnetApi) this.cache.get(SubnetApi.class);
		if (subnetApi == null) {
			subnetApi = apiSession.getNeutronApi().getSubnetApi(region);
			this.cache.put(SubnetApi.class, subnetApi);
		}
		return subnetApi;
	}

	public FlavorApi getFlavorApi() {
		FlavorApi flavorApi = (FlavorApi) this.cache.get(FlavorApi.class);
		if (flavorApi == null) {
			flavorApi = apiSession.getNovaApi().getFlavorApi(region);
			this.cache.put(FlavorApi.class, flavorApi);
		}
		return flavorApi;
	}

	public ImageApi getImageApi() {
		ImageApi imageApi = (ImageApi) this.cache.get(ImageApi.class);
		if (imageApi == null) {
			imageApi = apiSession.getGlanceApi().getImageApi(region);
			this.cache.put(ImageApi.class, imageApi);
		}
		return imageApi;
	}

	public org.jclouds.openstack.nova.v2_0.features.ImageApi getNovaImageApi() {
		org.jclouds.openstack.nova.v2_0.features.ImageApi imageApi = (org.jclouds.openstack.nova.v2_0.features.ImageApi) this.cache
				.get(org.jclouds.openstack.nova.v2_0.features.ImageApi.class);
		if (imageApi == null) {
			imageApi = apiSession.getNovaApi().getImageApi(region);
			this.cache.put(
					org.jclouds.openstack.nova.v2_0.features.ImageApi.class,
					imageApi);
		}
		return imageApi;
	}

	public VolumeTypeApi getVolumeTypeApi() {
		VolumeTypeApi volumeTypeApi = (VolumeTypeApi) this.cache
				.get(VolumeTypeApi.class);
		if (volumeTypeApi == null) {
			volumeTypeApi = apiSession.getCinderApi().getVolumeTypeApi(region);
			this.cache.put(VolumeTypeApi.class, volumeTypeApi);
		}
		return volumeTypeApi;
	}

	public ServerApi getServerApi() {
		ServerApi serverApi = (ServerApi) this.cache.get(ServerApi.class);
		if (serverApi == null) {
			serverApi = apiSession.getNovaApi().getServerApi(region);
			this.cache.put(ServerApi.class, serverApi);
		}
		return serverApi;
	}

	public KeyPairApi getKeyPairApi() throws APINotAvailableException {
		KeyPairApi keyPairApi = (KeyPairApi) this.cache.get(KeyPairApi.class);
		if (keyPairApi == null) {
			Optional<KeyPairApi> keyPairApiOptional = apiSession.getNovaApi()
					.getKeyPairApi(region);
			if (!keyPairApiOptional.isPresent()) {
				throw new APINotAvailableException(KeyPairApi.class);
			}
			keyPairApi = keyPairApiOptional.get();
			this.cache.put(KeyPairApi.class, keyPairApi);
		}
		return keyPairApi;
	}

	public QuotaApi getNovaQuotaApi() throws APINotAvailableException {
		QuotaApi quotaApi = (QuotaApi) this.cache.get(QuotaApi.class);
		if (quotaApi == null) {
			Optional<QuotaApi> quotaApiOptional = apiSession.getNovaApi()
					.getQuotaApi(region);
			if (!quotaApiOptional.isPresent()) {
				throw new APINotAvailableException(QuotaApi.class);
			}
			quotaApi = quotaApiOptional.get();
			this.cache.put(QuotaApi.class, quotaApi);
		}
		return quotaApi;
	}

	public org.jclouds.openstack.neutron.v2.extensions.QuotaApi getNeutronQuotaApi()
			throws APINotAvailableException {
		org.jclouds.openstack.neutron.v2.extensions.QuotaApi quotaApi = (org.jclouds.openstack.neutron.v2.extensions.QuotaApi) this.cache
				.get(org.jclouds.openstack.neutron.v2.extensions.QuotaApi.class);
		if (quotaApi == null) {
			Optional<org.jclouds.openstack.neutron.v2.extensions.QuotaApi> quotaApiOptional = apiSession
					.getNeutronApi().getQuotaApi(region);
			if (!quotaApiOptional.isPresent()) {
				throw new APINotAvailableException(
						org.jclouds.openstack.neutron.v2.extensions.QuotaApi.class);
			}
			quotaApi = quotaApiOptional.get();
			this.cache.put(
					org.jclouds.openstack.neutron.v2.extensions.QuotaApi.class,
					quotaApi);
		}
		return quotaApi;
	}

	public org.jclouds.openstack.cinder.v1.features.QuotaApi getCinderQuotaApi() {
		org.jclouds.openstack.cinder.v1.features.QuotaApi quotaApi = (org.jclouds.openstack.cinder.v1.features.QuotaApi) this.cache
				.get(org.jclouds.openstack.cinder.v1.features.QuotaApi.class);
		if (quotaApi == null) {
			quotaApi = apiSession.getCinderApi().getQuotaApi(region);
			this.cache.put(
					org.jclouds.openstack.cinder.v1.features.QuotaApi.class,
					quotaApi);
		}
		return quotaApi;
	}

	public FloatingIPApi getNeutronFloatingIpApi()
			throws APINotAvailableException {
		FloatingIPApi floatingIPApi = (FloatingIPApi) this.cache
				.get(FloatingIPApi.class);
		if (floatingIPApi == null) {
			Optional<FloatingIPApi> floatingIPApiOptional = apiSession
					.getNeutronApi().getFloatingIPApi(region);
			if (!floatingIPApiOptional.isPresent()) {
				throw new APINotAvailableException(FloatingIPApi.class);
			}
			floatingIPApi = floatingIPApiOptional.get();
			this.cache.put(FloatingIPApi.class, FloatingIPApi.class);
		}
		return floatingIPApi;
	}

}
