package com.letv.portal.service.openstack.resource.manager.impl.create.vm;

import java.util.HashMap;
import java.util.Map;

import org.jclouds.openstack.cinder.v1.features.SnapshotApi;
import org.jclouds.openstack.cinder.v1.features.VolumeApi;
import org.jclouds.openstack.cinder.v1.features.VolumeTypeApi;
import org.jclouds.openstack.glance.v1_0.features.ImageApi;
import org.jclouds.openstack.neutron.v2.domain.Router;
import org.jclouds.openstack.neutron.v2.extensions.FloatingIPApi;
import org.jclouds.openstack.neutron.v2.extensions.RouterApi;
import org.jclouds.openstack.neutron.v2.features.NetworkApi;
import org.jclouds.openstack.neutron.v2.features.PortApi;
import org.jclouds.openstack.neutron.v2.features.SubnetApi;
import org.jclouds.openstack.nova.v2_0.extensions.AttachInterfaceApi;
import org.jclouds.openstack.nova.v2_0.extensions.KeyPairApi;
import org.jclouds.openstack.nova.v2_0.extensions.QuotaApi;
import org.jclouds.openstack.nova.v2_0.extensions.VolumeAttachmentApi;
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
			this.cache.put(FloatingIPApi.class, floatingIPApi);
		}
		return floatingIPApi;
	}

	public org.jclouds.openstack.nova.v2_0.extensions.FloatingIPApi getNovaFloatingIPApi()
			throws APINotAvailableException {
		org.jclouds.openstack.nova.v2_0.extensions.FloatingIPApi floatingIPApi = (org.jclouds.openstack.nova.v2_0.extensions.FloatingIPApi) this.cache
				.get(org.jclouds.openstack.nova.v2_0.extensions.FloatingIPApi.class);
		if (floatingIPApi == null) {
			Optional<org.jclouds.openstack.nova.v2_0.extensions.FloatingIPApi> floatingIPApiOptional = apiSession
					.getNovaApi().getFloatingIPApi(region);
			if (!floatingIPApiOptional.isPresent()) {
				throw new APINotAvailableException(
						org.jclouds.openstack.nova.v2_0.extensions.FloatingIPApi.class);
			}
			floatingIPApi = floatingIPApiOptional.get();
			this.cache
					.put(org.jclouds.openstack.nova.v2_0.extensions.FloatingIPApi.class,
                            floatingIPApi);
		}
		return floatingIPApi;
	}

	public VolumeApi getVolumeApi() {
		VolumeApi volumeApi = (VolumeApi) this.cache.get(VolumeApi.class);
		if (volumeApi == null) {
			volumeApi = apiSession.getCinderApi().getVolumeApi(region);
			this.cache.put(VolumeApi.class, volumeApi);
		}
		return volumeApi;
	}

	public PortApi getPortApi() {
		PortApi portApi = (PortApi) this.cache.get(PortApi.class);
		if (portApi == null) {
			portApi = apiSession.getNeutronApi().getPortApi(region);
			this.cache.put(PortApi.class, portApi);
		}
		return portApi;
	}

	public RouterApi getRouterApi() throws APINotAvailableException {
		RouterApi routerApi = (RouterApi) this.cache.get(RouterApi.class);
		if (routerApi == null) {
			Optional<RouterApi> routerApiOptional = apiSession.getNeutronApi()
					.getRouterApi(region);
			if (!routerApiOptional.isPresent()) {
				throw new APINotAvailableException(RouterApi.class);
			}
			routerApi = routerApiOptional.get();
			this.cache.put(Router.class, routerApi);
		}
		return routerApi;
	}

	public VolumeAttachmentApi getVolumeAttachmentApi()
			throws APINotAvailableException {
		VolumeAttachmentApi volumeAttachmentApi = (VolumeAttachmentApi) this.cache
				.get(VolumeAttachmentApi.class);
		if (volumeAttachmentApi == null) {
			Optional<VolumeAttachmentApi> volumeAttachmentApiOptional = apiSession
					.getNovaApi().getVolumeAttachmentApi(region);
			if (!volumeAttachmentApiOptional.isPresent()) {
				throw new APINotAvailableException(VolumeAttachmentApi.class);
			}
			volumeAttachmentApi = volumeAttachmentApiOptional.get();
			this.cache.put(VolumeAttachmentApi.class, volumeAttachmentApi);
		}
		return volumeAttachmentApi;
	}

	public SnapshotApi getVolumeSnapshotApi() {
		SnapshotApi snapshotApi = (SnapshotApi) this.cache.get(SnapshotApi.class);
		if (snapshotApi == null) {
			snapshotApi = apiSession.getCinderApi().getSnapshotApi(region);
			this.cache.put(SnapshotApi.class, snapshotApi);
		}
		return snapshotApi;
	}

    public AttachInterfaceApi getAttachInterfaceApi() throws APINotAvailableException {
        AttachInterfaceApi attachInterfaceApi = (AttachInterfaceApi) this.cache.get(AttachInterfaceApi.class);
        if (attachInterfaceApi == null) {
            Optional<AttachInterfaceApi> attachInterfaceApiOptional = apiSession.getNovaApi().getAttachInterfaceApi(region);
            if (!attachInterfaceApiOptional.isPresent()) {
                throw new APINotAvailableException(AttachInterfaceApi.class);
            }
            attachInterfaceApi = attachInterfaceApiOptional.get();
            this.cache.put(AttachInterfaceApi.class, attachInterfaceApi);
        }
        return attachInterfaceApi;
    }
}
