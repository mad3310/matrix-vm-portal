package com.letv.portal.service.openstack.resource.manager.impl;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.net.util.SubnetUtils;
import org.apache.commons.net.util.SubnetUtils.SubnetInfo;
import org.jclouds.openstack.neutron.v2.NeutronApi;
import org.jclouds.openstack.neutron.v2.domain.AllocationPool;
import org.jclouds.openstack.neutron.v2.domain.ExternalGatewayInfo;
import org.jclouds.openstack.neutron.v2.domain.IP;
import org.jclouds.openstack.neutron.v2.domain.Network;
import org.jclouds.openstack.neutron.v2.domain.NetworkStatus;
import org.jclouds.openstack.neutron.v2.domain.Port;
import org.jclouds.openstack.neutron.v2.domain.Quota;
import org.jclouds.openstack.neutron.v2.domain.Router;
import org.jclouds.openstack.neutron.v2.domain.Subnet;
import org.jclouds.openstack.neutron.v2.extensions.QuotaApi;
import org.jclouds.openstack.neutron.v2.extensions.RouterApi;
import org.jclouds.openstack.neutron.v2.features.NetworkApi;
import org.jclouds.openstack.neutron.v2.features.PortApi;
import org.jclouds.openstack.neutron.v2.features.SubnetApi;

import com.google.common.base.FinalizablePhantomReference;
import com.google.common.base.Optional;
import com.google.common.collect.ImmutableSet;
import com.letv.common.paging.impl.Page;
import com.letv.portal.service.openstack.exception.APINotAvailableException;
import com.letv.portal.service.openstack.exception.OpenStackException;
import com.letv.portal.service.openstack.exception.PollingInterruptedException;
import com.letv.portal.service.openstack.exception.RegionNotFoundException;
import com.letv.portal.service.openstack.exception.ResourceNotFoundException;
import com.letv.portal.service.openstack.exception.UserOperationException;
import com.letv.portal.service.openstack.impl.OpenStackConf;
import com.letv.portal.service.openstack.impl.OpenStackUser;
import com.letv.portal.service.openstack.resource.NetworkResource;
import com.letv.portal.service.openstack.resource.RouterResource;
import com.letv.portal.service.openstack.resource.SubnetResource;
import com.letv.portal.service.openstack.resource.impl.NetworkResourceImpl;
import com.letv.portal.service.openstack.resource.impl.RouterResourceImpl;
import com.letv.portal.service.openstack.resource.impl.SubnetResourceImpl;
import com.letv.portal.service.openstack.resource.manager.NetworkManager;

public class NetworkManagerImpl extends AbstractResourceManager<NeutronApi>
		implements NetworkManager {

	/**
	 * 
	 */
	private static final long serialVersionUID = -705565951531564369L;

	// private NeutronApi neutronApi;

	public NetworkManagerImpl() {
	}

	public NetworkManagerImpl(OpenStackConf openStackConf,
			OpenStackUser openStackUser) {
		super(openStackConf, openStackUser);

		// Iterable<Module> modules = ImmutableSet
		// .<Module>of(new SLF4JLoggingModule());
		//
		// neutronApi = ContextBuilder.newBuilder("openstack-neutron")
		// .endpoint(openStackConf.getPublicEndpoint())
		// .credentials(openStackUser.getUserId() + ":" +
		// openStackUser.getUserId(), openStackUser.getPassword())
		// .modules(modules).buildApi(NeutronApi.class);
	}

	@Override
	public void close() throws IOException {
		// neutronApi.close();
	}

	@Override
	public Set<String> getRegions() throws OpenStackException {
		return runWithApi(new ApiRunnable<NeutronApi, Set<String>>() {

			@Override
			public Set<String> run(NeutronApi neutronApi) throws Exception {
				return neutronApi.getConfiguredRegions();
			}
		});
	}

	@Override
	public List<NetworkResource> list(final String region)
			throws OpenStackException {
		return runWithApi(new ApiRunnable<NeutronApi, List<NetworkResource>>() {

			@Override
			public List<NetworkResource> run(NeutronApi neutronApi)
					throws Exception {
				checkRegion(region);
				final String regionDisplayName = getRegionDisplayName(region);

				NetworkApi networkApi = neutronApi.getNetworkApi(region);
				SubnetApi subnetApi = neutronApi.getSubnetApi(region);

				List<Network> networks = networkApi.list().concat().toList();
				List<Subnet> subnets = subnetApi.list().concat().toList();

				Map<String, NetworkResourceImpl> idToNetwork = new HashMap<String, NetworkResourceImpl>();
				for (Network network : networks) {
					idToNetwork.put(network.getId(), new NetworkResourceImpl(
							region, regionDisplayName, network,
							new LinkedList<SubnetResource>()));
				}

				for (Subnet subnet : subnets) {
					idToNetwork
							.get(subnet.getNetworkId())
							.getSubnets()
							.add(new SubnetResourceImpl(region,
									regionDisplayName, subnet));
				}

				List<NetworkResource> networkResources = new ArrayList<NetworkResource>(
						idToNetwork.size());
				for (NetworkResourceImpl networkResourceImpl : idToNetwork
						.values()) {
					networkResources.add(networkResourceImpl);
				}

				return networkResources;
			}
		});
	}

	@Override
	public NetworkResource get(final String region, final String id)
			throws OpenStackException {
		return runWithApi(new ApiRunnable<NeutronApi, NetworkResource>() {

			@Override
			public NetworkResource run(NeutronApi neutronApi) throws Exception {
				checkRegion(region);

				NetworkApi networkApi = neutronApi.getNetworkApi(region);
				Network network = networkApi.get(id);

				if (network != null) {
					final String regionDisplayName = getRegionDisplayName(region);
					NetworkResourceImpl networkResourceImpl = new NetworkResourceImpl(
							region, regionDisplayName, network,
							new LinkedList<SubnetResource>());

					ImmutableSet<String> subnetIds = network.getSubnets();
					List<Subnet> allSubnets = neutronApi.getSubnetApi(region)
							.list().concat().toList();

					for (Subnet subnet : allSubnets) {
						if (subnetIds.contains(subnet.getId())) {
							networkResourceImpl.getSubnets().add(
									new SubnetResourceImpl(region,
											regionDisplayName, subnet));
						}
					}
					return networkResourceImpl;
				} else {
					throw new ResourceNotFoundException("Network", "网络", id);
				}
			}

		});
	}

	@Override
	public NetworkResource getPrivate(final String region, final String id)
			throws OpenStackException {
		return runWithApi(new ApiRunnable<NeutronApi, NetworkResource>() {

			@Override
			public NetworkResource run(NeutronApi neutronApi) throws Exception {
				checkRegion(region);

				NetworkApi networkApi = neutronApi.getNetworkApi(region);
				Network network = networkApi.get(id);

				if (network != null && !network.getShared()
						&& !network.getExternal()) {
					final String regionDisplayName = getRegionDisplayName(region);
					NetworkResourceImpl networkResourceImpl = new NetworkResourceImpl(
							region, regionDisplayName, network,
							new LinkedList<SubnetResource>());

					ImmutableSet<String> subnetIds = network.getSubnets();
					List<Subnet> allSubnets = neutronApi.getSubnetApi(region)
							.list().concat().toList();

					for (Subnet subnet : allSubnets) {
						if (subnetIds.contains(subnet.getId())) {
							networkResourceImpl.getSubnets().add(
									new SubnetResourceImpl(region,
											regionDisplayName, subnet));
						}
					}
					return networkResourceImpl;
				} else {
					throw new ResourceNotFoundException("Private Network",
							"私有网络", id);
				}
			}

		});
	}

	// public NeutronApi getNeutronApi() {
	// return neutronApi;
	// }

	public Network getPublicNetwork(final String region)
			throws OpenStackException {
		return runWithApi(new ApiRunnable<NeutronApi, Network>() {

			@Override
			public Network run(NeutronApi neutronApi) throws Exception {
				return neutronApi.getNetworkApi(region).get(
						openStackConf.getGlobalPublicNetworkId());
			}
		});
	}

	public Subnet getUserPrivateSubnet(final String region)
			throws OpenStackException {
		return runWithApi(new ApiRunnable<NeutronApi, Subnet>() {

			@Override
			public Subnet run(NeutronApi neutronApi) throws Exception {
				SubnetApi subnetApi = neutronApi.getSubnetApi(region);
				Subnet privateSubnet = null;
				for (Subnet subnet : subnetApi.list().concat().toList()) {
					if (openStackConf.getUserPrivateNetworkSubnetName().equals(
							subnet.getName())) {
						privateSubnet = subnet;
						break;
					}
				}
				return privateSubnet;
			}
		});
	}

	public Network getUserPrivateNetwork(final String region)
			throws OpenStackException {
		return runWithApi(new ApiRunnable<NeutronApi, Network>() {

			@Override
			public Network run(NeutronApi neutronApi) throws Exception {
				NetworkApi networkApi = neutronApi.getNetworkApi(region);
				Network privateNetwork = null;
				for (Network network : networkApi.list().concat().toList()) {
					if (openStackConf.getUserPrivateNetworkName().equals(
							network.getName())) {
						privateNetwork = network;
						break;
					}
				}
				return privateNetwork;
			}
		});
	}

	public Network getOrCreateUserPrivateNetwork(final String region)
			throws OpenStackException {
		return runWithApi(new ApiRunnable<NeutronApi, Network>() {

			@Override
			public Network run(NeutronApi neutronApi) throws Exception {

				NetworkApi networkApi = neutronApi.getNetworkApi(region);

				Network privateNetwork = getUserPrivateNetwork(region);
				if (privateNetwork == null) {
					privateNetwork = networkApi.create(Network.CreateNetwork
							.createBuilder("")
							.name(openStackConf.getUserPrivateNetworkName())
							.build());
				}

				SubnetApi subnetApi = neutronApi.getSubnetApi(region);

				Subnet privateSubnet = getUserPrivateSubnet(region);
				if (privateSubnet == null) {
					privateSubnet = subnetApi.create(Subnet.CreateSubnet
							.createBuilder(
									privateNetwork.getId(),
									openStackConf
											.getUserPrivateNetworkSubnetCidr())
							.enableDhcp(true)
							.name(openStackConf
									.getUserPrivateNetworkSubnetName())
							.ipVersion(4).build());
				}

				return privateNetwork;

			}
		});
	}

	public Router getOrCreateUserPrivateRouter(final String region)
			throws OpenStackException {
		return runWithApi(new ApiRunnable<NeutronApi, Router>() {

			@Override
			public Router run(NeutronApi neutronApi) throws Exception {
				RouterApi routerApi = neutronApi.getRouterApi(region).get();

				Router privateRouter = null;
				for (Router router : routerApi.list().concat().toList()) {
					if (openStackConf.getUserPrivateRouterName().equals(
							router.getName())) {
						privateRouter = router;
						break;
					}
				}
				if (privateRouter == null) {
					privateRouter = routerApi.create(Router.CreateRouter
							.createBuilder()
							.name(openStackConf.getUserPrivateRouterName())
							.externalGatewayInfo(
									ExternalGatewayInfo.builder()
											// .enableSnat(true)
											.networkId(
													getPublicNetwork(region)
															.getId()).build())
							.build());
					try {
						routerApi.addInterfaceForSubnet(privateRouter.getId(),
								getUserPrivateSubnet(region).getId());
					} catch (Exception ex) {
						routerApi.delete(privateRouter.getId());
						throw new OpenStackException("后台服务异常", ex);
					}
				}

				// Router router = routerApi.create(Router.CreateRouter
				// .createBuilder().name(openStackConf.getUserPrivateRouterName())
				// .build());
				// .externalGatewayInfo(
				// ExternalGatewayInfo.builder().enableSnat(true)
				// .networkId(publicNetwork.getId())
				// .build())

				return privateRouter;
			}
		});
	}

	/**
	 * 
	 * @param regions
	 * @param name
	 * @param currentPagePara
	 *            从1开始
	 * @param recordsPerPage
	 * @return
	 * @throws OpenStackException
	 */
	private Page listByRegions(final Set<String> regions, final String name,
			final Integer currentPagePara, final Integer recordsPerPage,
			final NetworkChecker networkFilter) throws OpenStackException {
		return runWithApi(new ApiRunnable<NeutronApi, Page>() {

			@Override
			public Page run(NeutronApi neutronApi) throws Exception {
				Integer currentPage;
				if (currentPagePara != null) {
					currentPage = currentPagePara - 1;
				} else {
					currentPage = null;
				}

				Map<String, String> transMap = getRegionCodeToDisplayNameMap();
				List<NetworkResource> networkResources = new LinkedList<NetworkResource>();
				int networkCount = 0;
				boolean needCollect = true;
				for (String region : regions) {
					NetworkApi networkApi = neutronApi.getNetworkApi(region);
					if (needCollect) {
						String regionDisplayName = transMap.get(region);
						List<Network> resources = networkApi.list().concat()
								.toList();
						Map<String, Subnet> idToSubnet = new HashMap<String, Subnet>();
						for (Subnet subnet : neutronApi.getSubnetApi(region)
								.list().concat().toList()) {
							idToSubnet.put(subnet.getId(), subnet);
						}
						for (Network resource : resources) {
							if (networkFilter.check(resource)) {
								if (name == null
										|| (resource.getName() != null && resource
												.getName().contains(name))) {
									if (currentPage == null
											|| recordsPerPage == null) {
										networkResources
												.add(new NetworkResourceImpl(
														region,
														regionDisplayName,
														resource, idToSubnet));
									} else {
										if (needCollect) {
											if (networkCount >= (currentPage + 1)
													* recordsPerPage) {
												needCollect = false;
											} else if (networkCount >= currentPage
													* recordsPerPage) {
												networkResources
														.add(new NetworkResourceImpl(
																region,
																regionDisplayName,
																resource,
																idToSubnet));
											}
										}
									}
									networkCount++;
								}
							}
						}
					} else {
						for (Network resource : networkApi.list().concat()
								.toList()) {
							if (networkFilter.check(resource)) {
								if (name == null
										|| (resource.getName() != null && resource
												.getName().contains(name))) {
									networkCount++;
								}
							}
						}
					}
				}

				Page page = new Page();
				page.setData(networkResources);
				page.setTotalRecords(networkCount);
				if (recordsPerPage != null) {
					page.setRecordsPerPage(recordsPerPage);
				} else {
					page.setRecordsPerPage(10);
				}
				if (currentPage != null) {
					page.setCurrentPage(currentPage + 1);
				} else {
					page.setCurrentPage(1);
				}

				return page;
			}
		});
	}

	private boolean isPrivateNetwork(Network network) {
		return !network.getShared() && !network.getExternal();
	}

	@Override
	public Page listPrivate(String regionGroup, String name,
			Integer currentPage, Integer recordsPerPage)
			throws OpenStackException {
		NetworkChecker privateNetworkFilter = new NetworkChecker() {

			@Override
			public boolean check(Network network) {
				return isPrivateNetwork(network);
			}
		};
		if (StringUtils.isEmpty(regionGroup)) {
			return listByRegions(getRegions(), name, currentPage,
					recordsPerPage, privateNetworkFilter);
		} else {
			return listByRegions(getGroupRegions(regionGroup), name,
					currentPage, recordsPerPage, privateNetworkFilter);
		}
	}

	@Override
	public void createPrivate(final String region, final String name)
			throws OpenStackException {
		runWithApi(new ApiRunnable<NeutronApi, Void>() {

			@Override
			public Void run(NeutronApi neutronApi) throws Exception {
				checkRegion(region);

				NetworkApi networkApi = neutronApi.getNetworkApi(region);

				int privateNetworkCount = 0;
				List<Network> networkList = networkApi.list().concat().toList();
				for (Network network : networkList) {
					if (isPrivateNetwork(network)) {
						privateNetworkCount++;
					}
				}

				Optional<QuotaApi> quotaApiOptional = neutronApi
						.getQuotaApi(region);
				if (!quotaApiOptional.isPresent()) {
					throw new APINotAvailableException(QuotaApi.class);
				}
				QuotaApi quotaApi = quotaApiOptional.get();

				Quota quota = quotaApi.getByTenant(openStackUser.getTenantId());

				if (quota.getNetwork() <= privateNetworkCount) {
					throw new UserOperationException(
							"Private network count exceeding the quota.",
							"私有网络数量超过配额。");
				}

				networkApi.create(Network.createBuilder(name).build());

				return null;
			}
		});
	}

	@Override
	public void editPrivate(final String region, final String networkId,
			final String name) throws OpenStackException {
		runWithApi(new ApiRunnable<NeutronApi, Void>() {

			@Override
			public Void run(NeutronApi neutronApi) throws Exception {
				checkRegion(region);

				NetworkApi networkApi = neutronApi.getNetworkApi(region);

				Network network = networkApi.get(networkId);

				if (network == null || !isPrivateNetwork(network)) {
					throw new ResourceNotFoundException("Private Network",
							"私有网络", networkId);
				}

				networkApi.update(networkId, Network.updateBuilder().name(name)
						.build());

				return null;
			}
		});
	}

	@Override
	public void deletePrivate(final String region, final String networkId)
			throws OpenStackException {
		runWithApi(new ApiRunnable<NeutronApi, Void>() {

			@Override
			public Void run(NeutronApi neutronApi) throws Exception {
				checkRegion(region);

				NetworkApi networkApi = neutronApi.getNetworkApi(region);

				Network network = networkApi.get(networkId);

				if (network == null || !isPrivateNetwork(network)) {
					throw new ResourceNotFoundException("Private Network",
							"私有网络", networkId);
				}

				PortApi portApi = neutronApi.getPortApi(region);

				for (Port port : portApi.list().concat().toList()) {
					if (networkId.equals(port.getNetworkId())) {
						throw new UserOperationException(
								"There are router interface or vm connected to the network.",
								"有路由的接口或虚拟机连接着这个网络");
					}
				}

				boolean isSuccess = networkApi.delete(networkId);
				if (!isSuccess) {
					throw new OpenStackException(
							MessageFormat.format(
									"Private network \"{0}\" delete failed.",
									networkId), MessageFormat.format(
									"私有网络“{0}”删除失败。", networkId));
				}

				waitingNetwork(networkId, networkApi, new NetworkChecker() {

					@Override
					public boolean check(Network network) throws Exception {
						return isDeleteFinished(network);
					}
				});

				return null;
			}
		});
	}

	private boolean isDeleteFinished(Network network) throws OpenStackException {
		if (network == null) {
			return true;
		}
		return network.getStatus() == NetworkStatus.ERROR;
	}

	private void waitingNetwork(String networkId, NetworkApi networkApi,
			NetworkChecker checker) throws OpenStackException {
		try {
			Network network = null;
			while (true) {
				network = networkApi.get(networkId);
				if (checker.check(network)) {
					break;
				}
				Thread.sleep(1000);
			}
		} catch (OpenStackException e) {
			throw e;
		} catch (InterruptedException e) {
			throw new PollingInterruptedException(e);
		} catch (Exception e) {
			throw new OpenStackException("后台错误", e);
		}
	}

	private long ipStrToNum(String ip) throws UnknownHostException {
		byte[] bytes = Inet4Address.getByName(ip).getAddress();
		byte[] newBytes = new byte[8];
		System.arraycopy(bytes, 0, newBytes, 4, 4);
		return ByteBuffer.wrap(newBytes).getLong();
	}

	@Override
	public void createPrivateSubnet(final String region,
			final String networkId, final String name, final String cidr,
			final boolean autoGatewayIp, final String gatewayIp,
			final boolean enableDhcp) throws OpenStackException {
		runWithApi(new ApiRunnable<NeutronApi, Void>() {

			@Override
			public Void run(NeutronApi neutronApi) throws Exception {
				checkRegion(region);

				NetworkApi networkApi = neutronApi.getNetworkApi(region);

				Network parentNetwork = networkApi.get(networkId);
				if (parentNetwork == null) {
					throw new ResourceNotFoundException("Network", "网络",
							networkId);
				}
				if (!isPrivateNetwork(parentNetwork)) {
					throw new UserOperationException(
							"Unable to create under the private network subnet.",
							"不能在非私有网络下创建子网");
				}

				if (!isSubnetCidrValid(cidr)) {
					throw new UserOperationException(
							"The subnet segment is not correct.", "子网的网段不正确");
				}

				Set<String> privateNetworkIds = new HashSet<String>();
				for (Network network : networkApi.list().concat().toList()) {
					if (isPrivateNetwork(network)) {
						privateNetworkIds.add(network.getId());
					}
				}

				SubnetApi subnetApi = neutronApi.getSubnetApi(region);

				SubnetInfo subnetInfo = new SubnetUtils(cidr).getInfo();
				long subnetLowAddress = ipStrToNum(subnetInfo.getLowAddress());
				long subnetHighAddress = ipStrToNum(subnetInfo.getHighAddress());

				int privateSubnetCount = 0;
				List<Subnet> existsSubnetList = subnetApi.list().concat()
						.toList();
				for (Subnet existsSubnet : existsSubnetList) {
					if (privateNetworkIds.contains(existsSubnet.getNetworkId())) {
						privateSubnetCount++;
					}
					if (networkId.equals(existsSubnet.getNetworkId())) {
						SubnetInfo existsSubnetInfo = new SubnetUtils(
								existsSubnet.getCidr()).getInfo();
						long existsSubnetLowAddress = ipStrToNum(existsSubnetInfo
								.getLowAddress());
						long existsSubnetHighAddress = ipStrToNum(existsSubnetInfo
								.getHighAddress());
						if (!(subnetLowAddress > existsSubnetHighAddress || subnetHighAddress < existsSubnetLowAddress)) {
							throw new UserOperationException(
									"Subnet segment and the scope of other subnet segment overlap.",
									"子网的网段和其他子网的网段的范围有重叠");
						}
					}
				}

				if (!autoGatewayIp && !subnetInfo.isInRange(gatewayIp)) {
					throw new UserOperationException(
							"The gateway IP network segment is not in the subnet.",
							"网关IP不在子网的网段内");
				}

				Optional<QuotaApi> quotaApiOptional = neutronApi
						.getQuotaApi(region);
				if (!quotaApiOptional.isPresent()) {
					throw new APINotAvailableException(QuotaApi.class);
				}
				QuotaApi quotaApi = quotaApiOptional.get();

				Quota quota = quotaApi.getByTenant(openStackUser.getTenantId());

				if (quota.getSubnet() <= privateSubnetCount) {
					throw new UserOperationException(
							"Private subnet count exceeding the quota.",
							"私有子网数量超过配额。");
				}

				Subnet.CreateBuilder createBuilder = Subnet
						.createBuilder(networkId, cidr).ipVersion(4).name(name)
						.cidr(cidr).enableDhcp(enableDhcp);
				if (!autoGatewayIp) {
					createBuilder.gatewayIp(gatewayIp);
				}
				subnetApi.create(createBuilder.build());

				return null;
			}

		});
	}

	private boolean isSubnetCidrValid(String cidr) {
		try {
			String[] ipAndNum = cidr.split("/");
			if (ipAndNum.length != 2) {
				return false;
			}
			String[] ipFragmentStrs = ipAndNum[0].split("\\.");
			if (ipFragmentStrs.length != 4) {
				return false;
			}
			int[] ipFragments = new int[ipFragmentStrs.length];
			for (int i = 0; i < ipFragmentStrs.length; i++) {
				ipFragments[i] = Integer.parseInt(ipFragmentStrs[i]);
			}
			String numStr = ipAndNum[1];
			int num = Integer.parseInt(numStr);
			return isSubnetCidrValid1(ipFragments, num)
					|| isSubnetCidrValid2(ipFragments, num)
					|| isSubnetCidrValid3(ipFragments, num);
		} catch (NumberFormatException e) {
			return false;
		}
	}

	private boolean isSubnetCidrValid1(int[] ipFragments, int num) {
		if (ipFragments[0] != 192) {
			return false;
		}
		if (ipFragments[1] != 168) {
			return false;
		}
		if (ipFragments[2] < 0 || ipFragments[2] > 255) {
			return false;
		}
		if (ipFragments[3] != 0) {
			return false;
		}
		if (num != 24) {
			return false;
		}
		return true;
	}

	private boolean isSubnetCidrValid2(int[] ipFragments, int num) {
		if (ipFragments[0] != 10) {
			return false;
		}
		if (ipFragments[1] < 0 || ipFragments[1] > 255) {
			return false;
		}
		if (ipFragments[2] < 0 || ipFragments[2] > 255) {
			return false;
		}
		if (ipFragments[3] < 0 || ipFragments[3] > 255) {
			return false;
		}
		if (num < 8 || num > 30) {
			return false;
		}
		return true;
	}

	private boolean isSubnetCidrValid3(int[] ipFragments, int num) {
		if (ipFragments[0] != 172) {
			return false;
		}
		if (ipFragments[1] < 16 || ipFragments[1] > 31) {
			return false;
		}
		if (ipFragments[2] < 0 || ipFragments[2] > 255) {
			return false;
		}
		if (ipFragments[3] < 0 || ipFragments[3] > 255) {
			return false;
		}
		if (num < 12 || num > 30) {
			return false;
		}
		return true;
	}

	private Subnet getPrivateSubnet(NeutronApi neutronApi, String region,
			String subnetId) throws OpenStackException {
		Subnet subnet = neutronApi.getSubnetApi(region).get(subnetId);
		if (subnet == null) {
			throw new ResourceNotFoundException("Subnet", "子网", subnetId);
		}

		Network network = neutronApi.getNetworkApi(region).get(
				subnet.getNetworkId());
		if (network == null) {
			throw new ResourceNotFoundException("Network", "网络",
					subnet.getNetworkId());
		}

		if (!isPrivateNetwork(network)) {
			throw new ResourceNotFoundException("Private Subnet", "私有子网",
					subnetId);
		}

		return subnet;
	}

	@Override
	public SubnetResource getPrivateSubnet(final String region,
			final String subnetId) throws OpenStackException {
		return runWithApi(new ApiRunnable<NeutronApi, SubnetResource>() {

			@Override
			public SubnetResource run(NeutronApi neutronApi) throws Exception {
				checkRegion(region);

				return new SubnetResourceImpl(region,
						getRegionDisplayName(region), getPrivateSubnet(
								neutronApi, region, subnetId));
			}
		});
	}

	@Override
	public void editPrivateSubnet(final String region, final String subnetId,
			final String name, final String gatewayIp, final boolean enableDhcp)
			throws OpenStackException {
		runWithApi(new ApiRunnable<NeutronApi, Void>() {

			@Override
			public Void run(NeutronApi neutronApi) throws Exception {
				checkRegion(region);

				Subnet subnet = getPrivateSubnet(neutronApi, region, subnetId);

				SubnetInfo subnetInfo = new SubnetUtils(subnet.getCidr())
						.getInfo();
				if (!subnetInfo.isInRange(gatewayIp)) {
					throw new UserOperationException(
							"The gateway IP network segment is not in the subnet.",
							"网关IP不在子网的网段内");
				}

				long gatewayIpNum = ipStrToNum(gatewayIp);
				for (AllocationPool allocationPool : subnet
						.getAllocationPools()) {
					long startIpNum = ipStrToNum(allocationPool.getStart());
					long endIpNum = ipStrToNum(allocationPool.getEnd());
					if (gatewayIpNum >= startIpNum && gatewayIpNum <= endIpNum) {
						throw new UserOperationException(
								"Gateway ip conflicts with allocation pool.",
								"网关IP不能在子网的IP分配池内");
					}
				}

				Subnet.UpdateBuilder updateBuilder = Subnet.updateBuilder()
						.name(name).enableDhcp(enableDhcp);

				updateBuilder.gatewayIp(gatewayIp);

				neutronApi.getSubnetApi(region).update(subnetId,
						updateBuilder.build());

				return null;
			}

		});
	}

	@Override
	public void deletePrivateSubnet(final String region, final String subnetId)
			throws OpenStackException {
		runWithApi(new ApiRunnable<NeutronApi, Void>() {

			@Override
			public Void run(NeutronApi neutronApi) throws Exception {
				checkRegion(region);

				Subnet subnet = getPrivateSubnet(neutronApi, region, subnetId);

				for (Port port : neutronApi.getPortApi(region).list().concat()
						.toList()) {
					if (subnet.getNetworkId().equals(port.getNetworkId())) {
						if (port.getFixedIps() != null) {
							for (IP ip : port.getFixedIps()) {
								if (subnet.getId().equals(ip.getSubnetId())) {
									throw new UserOperationException(
											"There are router interface or vm connected to the subnet.",
											"有路由的接口或虚拟机连接着这个子网");
								}
							}
						}
					}
				}

				SubnetApi subnetApi = neutronApi.getSubnetApi(region);

				boolean isSuccess = subnetApi.delete(subnetId);
				if (!isSuccess) {
					throw new OpenStackException(MessageFormat.format(
							"Private subnet \"{0}\" delete failed.", subnetId),
							MessageFormat.format("私有子网“{0}”删除失败。", subnetId));
				}

				waitingSubnet(subnetId, subnetApi, new Checker<Subnet>() {

					@Override
					public boolean check(Subnet subnet) throws Exception {
						return isSubnetDeleteFinished(subnet);
					}
				});

				return null;
			}

		});
	}

	private boolean isSubnetDeleteFinished(Subnet subnet) {
		return subnet == null;
	}

	private void waitingSubnet(String subnetId, SubnetApi subnetApi,
			Checker<Subnet> checker) throws OpenStackException {
		try {
			Subnet subnet = null;
			while (true) {
				subnet = subnetApi.get(subnetId);
				if (checker.check(subnet)) {
					break;
				}
				Thread.sleep(1000);
			}
		} catch (OpenStackException e) {
			throw e;
		} catch (InterruptedException e) {
			throw new PollingInterruptedException(e);
		} catch (Exception e) {
			throw new OpenStackException("后台错误", e);
		}
	}

	/**
	 * 
	 * @param regions
	 * @param name
	 * @param currentPagePara
	 *            从1开始
	 * @param recordsPerPage
	 * @return
	 * @throws RegionNotFoundException
	 * @throws ResourceNotFoundException
	 * @throws APINotAvailableException
	 * @throws OpenStackException
	 */
	private Page listRouterByRegions(final Set<String> regions,
			final String name, final Integer currentPagePara,
			final Integer recordsPerPage) throws RegionNotFoundException,
			ResourceNotFoundException, APINotAvailableException,
			OpenStackException {
		return runWithApi(new ApiRunnable<NeutronApi, Page>() {

			@Override
			public Page run(NeutronApi neutronApi) throws Exception {
				Integer currentPage;
				if (currentPagePara != null) {
					currentPage = currentPagePara - 1;
				} else {
					currentPage = null;
				}

				Map<String, String> transMap = getRegionCodeToDisplayNameMap();
				List<RouterResource> routerResources = new LinkedList<RouterResource>();
				int routerCount = 0;
				boolean needCollect = true;
				for (String region : regions) {
					Optional<RouterApi> routerApiOptional = neutronApi
							.getRouterApi(region);
					if (!routerApiOptional.isPresent()) {
						throw new APINotAvailableException(RouterApi.class);
					}
					RouterApi routerApi = routerApiOptional.get();
					if (needCollect) {
						String regionDisplayName = transMap.get(region);
						List<Router> resources = routerApi.list().concat()
								.toList();
						for (Router resource : resources) {
							if (name == null
									|| (resource.getName() != null && resource
											.getName().contains(name))) {
								if (currentPage == null
										|| recordsPerPage == null) {
									routerResources
											.add(new RouterResourceImpl(region,
													regionDisplayName, resource));
								} else {
									if (needCollect) {
										if (routerCount >= (currentPage + 1)
												* recordsPerPage) {
											needCollect = false;
										} else if (routerCount >= currentPage
												* recordsPerPage) {
											routerResources
													.add(new RouterResourceImpl(
															region,
															regionDisplayName,
															resource));
										}
									}
								}
								routerCount++;
							}
						}
					} else {
						for (Router resource : routerApi.list().concat()
								.toList()) {
							if (name == null
									|| (resource.getName() != null && resource
											.getName().contains(name))) {
								routerCount++;
							}
						}
					}
				}

				Page page = new Page();
				page.setData(routerResources);
				page.setTotalRecords(routerCount);
				if (recordsPerPage != null) {
					page.setRecordsPerPage(recordsPerPage);
				} else {
					page.setRecordsPerPage(10);
				}
				if (currentPage != null) {
					page.setCurrentPage(currentPage + 1);
				} else {
					page.setCurrentPage(1);
				}

				return page;
			}
		});
	}

	@Override
	public Page listRouter(String regionGroup, String name,
			Integer currentPage, Integer recordsPerPage)
			throws OpenStackException {
		return listRouterByRegions(getGroupRegions(regionGroup), name,
				currentPage, recordsPerPage);
	}

	@Override
	public RouterResource getRouter(final String region, final String routerId)
			throws OpenStackException {
		return runWithApi(new ApiRunnable<NeutronApi, RouterResource>() {

			@Override
			public RouterResource run(NeutronApi neutronApi) throws Exception {
				checkRegion(region);

				Optional<RouterApi> routerApiOptional = neutronApi
						.getRouterApi(region);
				if (!routerApiOptional.isPresent()) {
					throw new APINotAvailableException(RouterApi.class);
				}
				RouterApi routerApi = routerApiOptional.get();

				Router router = routerApi.get(routerId);
				if (router == null) {
					throw new ResourceNotFoundException("Router", "路由",
							routerId);
				}

				return new RouterResourceImpl(region,
						getRegionDisplayName(region), router);
			}
		});
	}

	@Override
	protected String getProviderOrApi() {
		return "openstack-neutron";
	}

	@Override
	protected Class<NeutronApi> getApiClass() {
		return NeutronApi.class;
	}

}
