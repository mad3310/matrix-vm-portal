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

import com.letv.portal.model.cloudvm.CloudvmRcCountType;
import com.letv.portal.model.common.CommonQuotaType;
import com.letv.portal.service.openstack.billing.ResourceLocator;
import com.letv.portal.service.openstack.billing.listeners.FloatingIpCreateListener;
import com.letv.portal.service.openstack.billing.listeners.RouterCreateListener;
import com.letv.portal.service.openstack.billing.listeners.event.*;
import com.letv.portal.service.openstack.impl.OpenStackServiceImpl;
import com.letv.portal.service.openstack.local.service.LocalRcCountService;
import com.letv.portal.service.openstack.resource.manager.FloatingIpCreateConf;
import com.letv.portal.service.openstack.resource.manager.RouterCreateConf;
import com.letv.portal.service.openstack.util.ExceptionUtil;
import com.letv.portal.service.openstack.util.RetryUtil;
import com.letv.portal.service.openstack.util.constants.OpenStackConstants;
import com.letv.portal.service.openstack.util.function.Function;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.net.util.SubnetUtils;
import org.apache.commons.net.util.SubnetUtils.SubnetInfo;
import org.jclouds.openstack.neutron.v2.NeutronApi;
import org.jclouds.openstack.neutron.v2.domain.AllocationPool;
import org.jclouds.openstack.neutron.v2.domain.ExternalGatewayInfo;
import org.jclouds.openstack.neutron.v2.domain.FipQos;
import org.jclouds.openstack.neutron.v2.domain.FloatingIP;
import org.jclouds.openstack.neutron.v2.domain.IP;
import org.jclouds.openstack.neutron.v2.domain.Network;
import org.jclouds.openstack.neutron.v2.domain.NetworkStatus;
import org.jclouds.openstack.neutron.v2.domain.Port;
import org.jclouds.openstack.neutron.v2.domain.Quota;
import org.jclouds.openstack.neutron.v2.domain.Router;
import org.jclouds.openstack.neutron.v2.domain.Subnet;
import org.jclouds.openstack.neutron.v2.extensions.FloatingIPApi;
import org.jclouds.openstack.neutron.v2.extensions.QuotaApi;
import org.jclouds.openstack.neutron.v2.extensions.RouterApi;
import org.jclouds.openstack.neutron.v2.features.NetworkApi;
import org.jclouds.openstack.neutron.v2.features.PortApi;
import org.jclouds.openstack.neutron.v2.features.SubnetApi;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableSet;
import com.letv.common.exception.MatrixException;
import com.letv.common.paging.impl.Page;
import com.letv.portal.service.openstack.exception.APINotAvailableException;
import com.letv.portal.service.openstack.exception.OpenStackException;
import com.letv.portal.service.openstack.exception.PollingInterruptedException;
import com.letv.portal.service.openstack.exception.RegionNotFoundException;
import com.letv.portal.service.openstack.exception.ResourceNotFoundException;
import com.letv.portal.service.openstack.exception.UserOperationException;
import com.letv.portal.service.openstack.impl.OpenStackConf;
import com.letv.portal.service.openstack.impl.OpenStackUser;
import com.letv.portal.service.openstack.resource.FloatingIpResource;
import com.letv.portal.service.openstack.resource.NetworkResource;
import com.letv.portal.service.openstack.resource.PortResource;
import com.letv.portal.service.openstack.resource.RouterResource;
import com.letv.portal.service.openstack.resource.SubnetResource;
import com.letv.portal.service.openstack.resource.VMResource;
import com.letv.portal.service.openstack.resource.impl.FloatingIpResourceImpl;
import com.letv.portal.service.openstack.resource.impl.NetworkResourceImpl;
import com.letv.portal.service.openstack.resource.impl.PortResourceImpl;
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

	private VMManagerImpl vmManager;

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

	public void checkRegion(NeutronApi neutronApi, String region) throws OpenStackException,RegionNotFoundException {
		if (!neutronApi.getConfiguredRegions().contains(region)) {
			throw new RegionNotFoundException(region);
		}
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

//	public Network getPublicNetwork(final String region)
//			throws OpenStackException {
//		return runWithApi(new ApiRunnable<NeutronApi, Network>() {
//
//			@Override
//			public Network run(NeutronApi neutronApi) throws Exception {
//				return getPublicNetwork(neutronApi, region);
//			}
//		});
//	}

//	public Network getPublicNetwork(NeutronApi neutronApi, String region) {
//		return neutronApi.getNetworkApi(region).get(
//				openStackConf.getGlobalPublicNetworkId());
//	}

//	public Subnet getUserPrivateSubnet(final String region)
//			throws OpenStackException {
//		return runWithApi(new ApiRunnable<NeutronApi, Subnet>() {
//
//			@Override
//			public Subnet run(NeutronApi neutronApi) throws Exception {
//				SubnetApi subnetApi = neutronApi.getSubnetApi(region);
//				Subnet privateSubnet = null;
//				for (Subnet subnet : subnetApi.list().concat().toList()) {
//					if (openStackConf.getUserPrivateNetworkSubnetName().equals(
//							subnet.getName())) {
//						privateSubnet = subnet;
//						break;
//					}
//				}
//				return privateSubnet;
//			}
//		});
//	}

//	public Network getUserPrivateNetwork(final String region)
//			throws OpenStackException {
//		return runWithApi(new ApiRunnable<NeutronApi, Network>() {
//
//			@Override
//			public Network run(NeutronApi neutronApi) throws Exception {
//				NetworkApi networkApi = neutronApi.getNetworkApi(region);
//				Network privateNetwork = null;
//				for (Network network : networkApi.list().concat().toList()) {
//					if (openStackConf.getUserPrivateNetworkName().equals(
//							network.getName())) {
//						privateNetwork = network;
//						break;
//					}
//				}
//				return privateNetwork;
//			}
//		});
//	}

//	public Network getOrCreateUserPrivateNetwork(final String region)
//			throws OpenStackException {
//		return runWithApi(new ApiRunnable<NeutronApi, Network>() {
//
//			@Override
//			public Network run(NeutronApi neutronApi) throws Exception {
//
//				NetworkApi networkApi = neutronApi.getNetworkApi(region);
//
//				Network privateNetwork = getUserPrivateNetwork(region);
//				if (privateNetwork == null) {
//					privateNetwork = networkApi.create(Network.CreateNetwork
//							.createBuilder("")
//							.name(openStackConf.getUserPrivateNetworkName())
//							.build());
//				}
//
//				SubnetApi subnetApi = neutronApi.getSubnetApi(region);
//
//				Subnet privateSubnet = getUserPrivateSubnet(region);
//				if (privateSubnet == null) {
//					privateSubnet = subnetApi.create(Subnet.CreateSubnet
//							.createBuilder(
//                                    privateNetwork.getId(),
//                                    openStackConf
//                                            .getUserPrivateNetworkSubnetCidr())
//							.enableDhcp(true)
//							.name(openStackConf
//                                    .getUserPrivateNetworkSubnetName())
//							.ipVersion(4).build());
//				}
//
//				return privateNetwork;
//
//			}
//		});
//	}

//	public Router getOrCreateUserPrivateRouter(final String region)
//			throws OpenStackException {
//		return runWithApi(new ApiRunnable<NeutronApi, Router>() {
//
//			@Override
//			public Router run(NeutronApi neutronApi) throws Exception {
//				RouterApi routerApi = neutronApi.getRouterApi(region).get();
//
//				Router privateRouter = null;
//				for (Router router : routerApi.list().concat().toList()) {
//					if (openStackConf.getUserPrivateRouterName().equals(
//							router.getName())) {
//						privateRouter = router;
//						break;
//					}
//				}
//				if (privateRouter == null) {
//					privateRouter = routerApi.create(Router.CreateRouter
//							.createBuilder()
//							.name(openStackConf.getUserPrivateRouterName())
//							.externalGatewayInfo(
//                                    ExternalGatewayInfo.builder()
//                                            // .enableSnat(true)
//                                            .networkId(
//                                                    getPublicNetwork(region)
//                                                            .getId()).build())
//							.build());
//					try {
//						routerApi.addInterfaceForSubnet(privateRouter.getId(),
//								getUserPrivateSubnet(region).getId());
//					} catch (Exception ex) {
//						routerApi.delete(privateRouter.getId());
//						throw new OpenStackException("后台服务异常", ex);
//					}
//				}
//
//				// Router router = routerApi.create(Router.CreateRouter
//				// .createBuilder().name(openStackConf.getUserPrivateRouterName())
//				// .build());
//				// .externalGatewayInfo(
//				// ExternalGatewayInfo.builder().enableSnat(true)
//				// .networkId(publicNetwork.getId())
//				// .build())
//
//				return privateRouter;
//			}
//		});
//	}

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

				OpenStackServiceImpl.getOpenStackServiceGroup().getLocalCommonQuotaSerivce()
						.checkQuota(openStackUser.getUserVoUserId(), region, CommonQuotaType.CLOUDVM_NETWORK, privateNetworkCount + 1);

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

				LocalRcCountService localRcCountService = OpenStackServiceImpl.getOpenStackServiceGroup().getLocalRcCountService();
				localRcCountService.incRcCount(openStackUser.getUserVoUserId(), region, CloudvmRcCountType.PRIVATE_NETWORK);

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

				LocalRcCountService localRcCountService = OpenStackServiceImpl.getOpenStackServiceGroup().getLocalRcCountService();
				localRcCountService.decRcCount(openStackUser.getUserVoUserId(), region, CloudvmRcCountType.PRIVATE_NETWORK);

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

				OpenStackServiceImpl.getOpenStackServiceGroup().getLocalCommonQuotaSerivce()
						.checkQuota(openStackUser.getUserVoUserId(), region, CommonQuotaType.CLOUDVM_SUBNET, existsSubnetList.size() + 1);

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

				LocalRcCountService localRcCountService = OpenStackServiceImpl.getOpenStackServiceGroup().getLocalRcCountService();
				localRcCountService.incRcCount(openStackUser.getUserVoUserId(), region, CloudvmRcCountType.PRIVATE_SUBNET);

				return null;
			}

		});
	}

	@Override
	public void createPrivateNetworkAndSubnet(final String region, final String networkName, final String subnetName
			, final String cidr, final boolean autoGatewayIp, final String gatewayIp, final boolean enableDhcp)
			throws OpenStackException {
		runWithApi(new ApiRunnable<NeutronApi, Void>() {
			@Override
			public Void run(NeutronApi neutronApi) throws Exception {
				checkRegion(region);

				if (!isSubnetCidrValid(cidr)) {
					throw new UserOperationException(
							"The subnet segment is not correct.", "子网的网段不正确");
				}

				SubnetInfo subnetInfo = new SubnetUtils(cidr).getInfo();
				if (!autoGatewayIp && !subnetInfo.isInRange(gatewayIp)) {
					throw new UserOperationException(
							"The gateway IP network segment is not in the subnet.",
							"网关IP不在子网的网段内");
				}

				NetworkApi networkApi = neutronApi.getNetworkApi(region);
				SubnetApi subnetApi=neutronApi.getSubnetApi(region);
				Optional<QuotaApi> quotaApiOptional = neutronApi
						.getQuotaApi(region);
				if (!quotaApiOptional.isPresent()) {
					throw new APINotAvailableException(QuotaApi.class);
				}
				QuotaApi quotaApi = quotaApiOptional.get();

				Quota quota = quotaApi.getByTenant(openStackUser.getTenantId());

				Set<String> privateNetworkIds = new HashSet<String>();
				List<Network> networkList = networkApi.list().concat().toList();
				for (Network network : networkList) {
					if (isPrivateNetwork(network)) {
						privateNetworkIds.add(network.getId());
					}
				}

				int privateSubnetCount = 0;
				List<Subnet> existsSubnetList = subnetApi.list().concat()
						.toList();
				for (Subnet existsSubnet : existsSubnetList) {
					if (privateNetworkIds.contains(existsSubnet.getNetworkId())) {
						privateSubnetCount++;
					}
				}

				OpenStackServiceImpl.getOpenStackServiceGroup().getLocalCommonQuotaSerivce()
						.checkQuota(openStackUser.getUserVoUserId(), region, CommonQuotaType.CLOUDVM_NETWORK, privateNetworkIds.size() + 1);

				if (quota.getNetwork() <= privateNetworkIds.size()) {
					throw new UserOperationException(
							"Private network count exceeding the quota.",
							"私有网络数量超过配额。");
				}

				OpenStackServiceImpl.getOpenStackServiceGroup().getLocalCommonQuotaSerivce()
						.checkQuota(openStackUser.getUserVoUserId(), region, CommonQuotaType.CLOUDVM_SUBNET, existsSubnetList.size() + 1);

				if (quota.getSubnet() <= privateSubnetCount) {
					throw new UserOperationException(
							"Private subnet count exceeding the quota.",
							"私有子网数量超过配额。");
				}

				Network privateNetwork = networkApi.create(Network.createBuilder(networkName).build());
				Subnet.CreateBuilder createBuilder = Subnet
						.createBuilder(privateNetwork.getId(), cidr).ipVersion(4).name(subnetName)
						.cidr(cidr).enableDhcp(enableDhcp);
				if (!autoGatewayIp) {
					createBuilder.gatewayIp(gatewayIp);
				}
				subnetApi.create(createBuilder.build());

				LocalRcCountService localRcCountService = OpenStackServiceImpl.getOpenStackServiceGroup().getLocalRcCountService();
				localRcCountService.incRcCount(openStackUser.getUserVoUserId(),region,CloudvmRcCountType.PRIVATE_NETWORK);
				localRcCountService.incRcCount(openStackUser.getUserVoUserId(), region, CloudvmRcCountType.PRIVATE_SUBNET);

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
			return ipFragments[0] != 10 && (isSubnetCidrValid1(ipFragments, num)
					|| isSubnetCidrValid2(ipFragments, num)
					|| isSubnetCidrValid3(ipFragments, num));
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

				SubnetApi subnetApi = neutronApi.getSubnetApi(region);
				Subnet subnet = subnetApi.get(subnetId);
				if (subnet == null) {
					throw new ResourceNotFoundException("Subnet", "子网",
							subnetId);
				}

				NetworkApi networkApi = neutronApi.getNetworkApi(region);
				Network network = networkApi.get(subnet.getNetworkId());
				if (network == null) {
					throw new ResourceNotFoundException("Network", "网络",
							subnet.getNetworkId());
				}

				if (!isPrivateNetwork(network)) {
					throw new ResourceNotFoundException("Private Subnet",
							"私有子网", subnetId);
				}

				PortApi portApi = neutronApi.getPortApi(region);
				String routerId = null;
				findRouterId: for (Port port : portApi.list().concat().toList()) {
					if (OpenStackConstants.PORT_DEVICE_OWNER_NETWORK_ROUTER_INTERFACE
							.equals(port.getDeviceOwner())) {
						if (subnet.getNetworkId().equals(port.getNetworkId())) {
							ImmutableSet<IP> fixedIps = port.getFixedIps();
							if (fixedIps != null) {
								for (IP ip : fixedIps) {
									if (subnetId.equals(ip.getSubnetId())) {
										routerId = port.getDeviceId();
										break findRouterId;
									}
								}
							}
						}
					}
				}
				Router router = null;
				if (routerId != null) {
					Optional<RouterApi> routerApiOptional = neutronApi
							.getRouterApi(region);
					if (!routerApiOptional.isPresent()) {
						throw new APINotAvailableException(RouterApi.class);
					}
					RouterApi routerApi = routerApiOptional.get();
					router = routerApi.get(routerId);
				}

				String regionDisplayName = getRegionDisplayName(region);

				NetworkResource networkResource = new NetworkResourceImpl(
						region, regionDisplayName, network,
						new ArrayList<SubnetResource>());

				RouterResource routerResource = null;
				if (router != null) {
					routerResource = new RouterResourceImpl(region,
							regionDisplayName, router);
				}

				SubnetResource subnetResource = null;
				if (routerResource != null) {
					subnetResource = new SubnetResourceImpl(region,
							regionDisplayName, subnet, networkResource,
							routerResource);
				} else {
					subnetResource = new SubnetResourceImpl(region,
							regionDisplayName, subnet, networkResource);
				}

				return subnetResource;
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

//				SubnetInfo subnetInfo = new SubnetUtils(subnet.getCidr())
//						.getInfo();
//				if (!subnetInfo.isInRange(gatewayIp)) {
//					throw new UserOperationException(
//							"The gateway IP network segment is not in the subnet.",
//							"网关IP不在子网的网段内");
//				}
//
//				long gatewayIpNum = ipStrToNum(gatewayIp);
//				for (AllocationPool allocationPool : subnet
//						.getAllocationPools()) {
//					long startIpNum = ipStrToNum(allocationPool.getStart());
//					long endIpNum = ipStrToNum(allocationPool.getEnd());
//					if (gatewayIpNum >= startIpNum && gatewayIpNum <= endIpNum) {
//						throw new UserOperationException(
//								"Gateway ip conflicts with allocation pool.",
//								"网关IP不能在子网的IP分配池内");
//					}
//				}

				Subnet.UpdateBuilder updateBuilder = Subnet.updateBuilder()
						.name(name)/*.enableDhcp(enableDhcp)*/;

//				updateBuilder.gatewayIp(gatewayIp);

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

				LocalRcCountService localRcCountService = OpenStackServiceImpl.getOpenStackServiceGroup().getLocalRcCountService();
				localRcCountService.decRcCount(openStackUser.getUserVoUserId(), region, CloudvmRcCountType.PRIVATE_SUBNET);

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
					PortApi portApi = neutronApi.getPortApi(region);
                    Optional<FloatingIPApi> floatingIPApiOptional = neutronApi.getFloatingIPApi(region);
                    if(!floatingIPApiOptional.isPresent()) {
                        throw new APINotAvailableException(FloatingIPApi.class);
                    }
                    FloatingIPApi floatingIPApi = floatingIPApiOptional.get();
					if (needCollect) {
						String regionDisplayName = transMap.get(region);
						List<Router> resources = routerApi.list().concat()
								.toList();
						Map<String, Subnet> idToSubnet = new HashMap<String, Subnet>();
						for (Subnet subnet : neutronApi.getSubnetApi(region)
								.list().concat().toList()) {
							idToSubnet.put(subnet.getId(), subnet);
						}
						List<Port> portList = new LinkedList<Port>();
						for (Port port : portApi.list().concat().toList()) {
							if (OpenStackConstants.PORT_DEVICE_OWNER_NETWORK_ROUTER_INTERFACE.equals(port
									.getDeviceOwner())) {
								portList.add(port);
							}
						}
						for (Router resource : resources) {
							if (name == null
									|| (resource.getName() != null && resource
											.getName().contains(name))) {
								boolean needAdd = false;
								if (currentPage == null
										|| recordsPerPage == null) {
									needAdd = true;
								} else {
									if (needCollect) {
										if (routerCount >= (currentPage + 1)
												* recordsPerPage) {
											needCollect = false;
										} else if (routerCount >= currentPage
												* recordsPerPage) {
											needAdd = true;
										}
									}
								}
								if (needAdd) {
									List<SubnetResource> subnetResources = new LinkedList<SubnetResource>();
									for (Port port : portList) {
										if (StringUtils.equals(
												resource.getId(),
												port.getDeviceId())) {
											ImmutableSet<IP> fixedIps = port
													.getFixedIps();
											if (fixedIps != null) {
												for (IP ip : fixedIps) {
													String subnetId = ip
															.getSubnetId();
													if (subnetId != null) {
														Subnet subnet = idToSubnet
																.get(subnetId);
														if (subnet != null) {
															SubnetResource subnetResource = new SubnetResourceImpl(
																	region,
																	regionDisplayName,
																	subnet);
															subnetResources
																	.add(subnetResource);
														}
													}
												}
											}
										}
									}
									NetworkResource networkResource = null;
									if (resource.getExternalGatewayInfo() != null
											&& resource
													.getExternalGatewayInfo()
													.getNetworkId() != null) {
										networkResource = new NetworkResourceImpl(
												neutronApi
														.getNetworkApi(region)
														.get(resource
                                                                .getExternalGatewayInfo()
                                                                .getNetworkId()));
									}
                                    String gatewayIp = null;
                                    if(networkResource != null) {
                                        FloatingIP floatingIP = floatingIPApi.get(resource.getId());
                                        if(floatingIP != null) {
                                            gatewayIp = floatingIP.getFixedIpAddress();
                                        }
                                    }
									RouterResource routerResource;
									if (networkResource != null) {
										routerResource = new RouterResourceImpl(
												region, regionDisplayName,
												resource, networkResource, gatewayIp,
												subnetResources);
									} else {
										routerResource = new RouterResourceImpl(
												region, regionDisplayName,
												resource, subnetResources);
									}
									routerResources.add(routerResource);
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
		Set<String> regions = null;
		if (StringUtils.isEmpty(regionGroup)) {
			regions = getRegions();
		} else {
			regions = getGroupRegions(regionGroup);
		}
		return listRouterByRegions(regions, name, currentPage, recordsPerPage);
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

				String regionDisplayName = getRegionDisplayName(region);

				NetworkResource networkResource = null;
				if (router.getExternalGatewayInfo() != null
						&& router.getExternalGatewayInfo().getNetworkId() != null) {
					networkResource = new NetworkResourceImpl(neutronApi
							.getNetworkApi(region).get(
									router.getExternalGatewayInfo()
											.getNetworkId()));
				}

                String gatewayIp = null;
				if(networkResource != null) {
					Optional<FloatingIPApi> floatingIPApiOptional = neutronApi.getFloatingIPApi(region);
					if(!floatingIPApiOptional.isPresent()) {
						throw new APINotAvailableException(FloatingIPApi.class);
					}
                    FloatingIPApi floatingIPApi = floatingIPApiOptional.get();
                    FloatingIP floatingIP = floatingIPApi.get(routerId);
                    if(floatingIP != null) {
                        gatewayIp = floatingIP.getFixedIpAddress();
				    }
                }

				List<SubnetResource> subnetResources = new LinkedList<SubnetResource>();
				PortApi portApi = neutronApi.getPortApi(region);
				SubnetApi subnetApi = neutronApi.getSubnetApi(region);
				for (Port port : portApi.list().concat().toList()) {
					if (OpenStackConstants.PORT_DEVICE_OWNER_NETWORK_ROUTER_INTERFACE
							.equals(port.getDeviceOwner())) {
						if (StringUtils.equals(routerId, port.getDeviceId())) {
							ImmutableSet<IP> fixedIps = port.getFixedIps();
							if (fixedIps != null) {
								for (IP ip : fixedIps) {
									String subnetId = ip.getSubnetId();
									if (subnetId != null) {
										Subnet subnet = subnetApi.get(subnetId);
										if (subnet != null) {
											SubnetResource subnetResource = new SubnetResourceImpl(
													region, regionDisplayName,
													subnet);
											subnetResources.add(subnetResource);
										}
									}
								}
							}
						}
					}
				}

				RouterResource routerResource;
				if (networkResource != null) {
					routerResource = new RouterResourceImpl(region,
							getRegionDisplayName(region), router,
							networkResource, gatewayIp, subnetResources);
				} else {
					routerResource = new RouterResourceImpl(region,
							getRegionDisplayName(region), router,
							subnetResources);
				}
				return routerResource;
			}
		});
	}

	@Override
	public void checkCreateRouter(final RouterCreateConf routerCreateConf)throws OpenStackException {
		runWithApi(new ApiRunnable<NeutronApi, Void>() {
			@Override
			public Void run(NeutronApi neutronApi) throws Exception {
				checkCreateRouter(neutronApi,routerCreateConf);
				return null;
			}
		});
	}

	private void checkCreateRouter(NeutronApi neutronApi, RouterCreateConf routerCreateConf) throws OpenStackException {
		final String region = routerCreateConf.getRegion();
//		final String name = routerCreateConf.getName();
		final boolean enablePublicNetworkGateway = routerCreateConf.getEnablePublicNetworkGateway();
		final String publicNetworkId = routerCreateConf.getPublicNetworkId();

		checkRegion(region);

		Optional<RouterApi> routerApiOptional = neutronApi
				.getRouterApi(region);
		if (!routerApiOptional.isPresent()) {
			throw new APINotAvailableException(RouterApi.class);
		}
		RouterApi routerApi = routerApiOptional.get();

		int existsRouterCount = routerApi.list().concat().toList()
				.size();

		OpenStackServiceImpl.getOpenStackServiceGroup().getLocalCommonQuotaSerivce()
				.checkQuota(openStackUser.getUserVoUserId(), region, CommonQuotaType.CLOUDVM_ROUTER, existsRouterCount + 1);

		Optional<QuotaApi> quotaApiOptional = neutronApi
				.getQuotaApi(region);
		if (!quotaApiOptional.isPresent()) {
			throw new APINotAvailableException(QuotaApi.class);
		}
		QuotaApi quotaApi = quotaApiOptional.get();

		Quota quota = quotaApi.getByTenant(openStackUser.getTenantId());

		if (quota.getRouter() <= existsRouterCount) {
			throw new UserOperationException(
					"Router count exceeding the quota.", "路由数量超过配额");
		}

		if (enablePublicNetworkGateway) {
			NetworkApi networkApi = neutronApi.getNetworkApi(region);
			Network publicNetwork = networkApi.get(publicNetworkId);
			if (publicNetwork == null || !publicNetwork.getExternal()) {
				throw new ResourceNotFoundException("Public Network",
						"线路", publicNetworkId);
			}
		}
	}

	private void createRouter(NeutronApi neutronApi,RouterCreateConf routerCreateConf,List<Router> successCreatedRouters) throws OpenStackException{
		final String region = routerCreateConf.getRegion();
		final String name = routerCreateConf.getName();
		final boolean enablePublicNetworkGateway = routerCreateConf.getEnablePublicNetworkGateway();
		final String publicNetworkId = routerCreateConf.getPublicNetworkId();

		checkRegion(neutronApi,region);

		Optional<RouterApi> routerApiOptional = neutronApi
				.getRouterApi(region);
		if (!routerApiOptional.isPresent()) {
			throw new APINotAvailableException(RouterApi.class);
		}
		RouterApi routerApi = routerApiOptional.get();

		int existsRouterCount = routerApi.list().concat().toList()
				.size();

		OpenStackServiceImpl.getOpenStackServiceGroup().getLocalCommonQuotaSerivce()
				.checkQuota(openStackUser.getUserVoUserId(), region, CommonQuotaType.CLOUDVM_ROUTER, existsRouterCount + 1);

		Optional<QuotaApi> quotaApiOptional = neutronApi
				.getQuotaApi(region);
		if (!quotaApiOptional.isPresent()) {
			throw new APINotAvailableException(QuotaApi.class);
		}
		QuotaApi quotaApi = quotaApiOptional.get();

		Quota quota = quotaApi.getByTenant(openStackUser.getTenantId());

		if (quota.getRouter() <= existsRouterCount) {
			throw new UserOperationException(
					"Router count exceeding the quota.", "路由数量超过配额");
		}

		Router.CreateBuilder createBuilder = Router.createBuilder()
				.name(name);
		if (enablePublicNetworkGateway) {
			NetworkApi networkApi = neutronApi.getNetworkApi(region);
			Network publicNetwork = networkApi.get(publicNetworkId);
			if (publicNetwork == null || !publicNetwork.getExternal()) {
				throw new ResourceNotFoundException("Public Network",
						"线路", publicNetworkId);
			}
			createBuilder.externalGatewayInfo(ExternalGatewayInfo
					.builder().networkId(publicNetworkId).build());
		}
		Router router = routerApi.create(createBuilder.build());
		if (enablePublicNetworkGateway) {
			Optional<FloatingIPApi> floatingIPApiOptional = neutronApi
					.getFloatingIPApi(region);
			if (!floatingIPApiOptional.isPresent()) {
				throw new APINotAvailableException(FloatingIPApi.class);
			}
			FloatingIPApi floatingIPApi = floatingIPApiOptional.get();
			floatingIPApi.update(
					router.getId(),
					FloatingIP
							.updateBuilder()
							.fipQos(createFipQos(openStackConf
                                    .getRouterGatewayBandWidth()))
							.build());
		}

		if(successCreatedRouters != null){
			successCreatedRouters.add(router);
		}

		long userVoUserId = openStackUser.getUserVoUserId();
		OpenStackServiceImpl.getOpenStackServiceGroup().getLocalRcCountService()
				.incRcCount(userVoUserId, userVoUserId, region, CloudvmRcCountType.ROUTER);
	}

	public void createRouter(NeutronApi neutronApi, RouterCreateConf routerCreateConf, RouterCreateListener listener, Object listenerUserData) throws OpenStackException {
		List<Router> successCreatedRouters = null;
		if (listener != null) {
			successCreatedRouters = new LinkedList<Router>();
		}

		try {
			createRouter(neutronApi, routerCreateConf, successCreatedRouters);
		} catch (Exception e) {
			notifyRouterCreateListener(routerCreateConf, successCreatedRouters, e, listener, listenerUserData);
			ExceptionUtil.throwException(e);
		}
		notifyRouterCreateListener(routerCreateConf, successCreatedRouters, null, listener, listenerUserData);
	}

	private void notifyRouterCreateListener(final RouterCreateConf routerCreateConf, final List<Router> successCreatedRouters,Exception exception, final RouterCreateListener listener, final Object listenerUserData){
		if(listener != null) {
			int successCreatedRoutersCount = successCreatedRouters.size();
			int routersCount = 1;
			int routerIndex = 0;

			for (; routerIndex < successCreatedRoutersCount; routerIndex++) {
				try {
					final int routerIndexRef = routerIndex;
					RetryUtil.retry(new Function<Boolean>() {
						@Override
						public Boolean apply() throws Exception {
							listener.routerCreated(new RouterCreateEvent(routerCreateConf.getRegion(), successCreatedRouters.get(routerIndexRef).getId(), routerIndexRef, listenerUserData));
							return true;
						}
					}, 3, "路由器监听器实现方错误：重试超过3次");
				} catch (Exception e) {
					ExceptionUtil.processBillingException(e);
				}
			}

			final String reason = exception != null ? ExceptionUtil.getUserMessage(exception) : "后台错误";
			for (; routerIndex < routersCount; routerIndex++) {
				try {
					final int routerIndexRef = routerIndex;
					RetryUtil.retry(new Function<Boolean>() {
						@Override
						public Boolean apply() throws Exception {
							listener.routerCreateFailed(new RouterCreateFailEvent(routerCreateConf.getRegion(), routerIndexRef, reason, listenerUserData));
							return true;
						}
					}, 3, "路由器监听器实现方错误：重试超过3次");
				} catch (Exception e) {
					ExceptionUtil.processBillingException(e);
				}
			}
		}
	}

	@Override
	public void createRouter(final RouterCreateConf routerCreateConf) throws OpenStackException {
		runWithApi(new ApiRunnable<NeutronApi, Void>() {

			@Override
			public Void run(NeutronApi neutronApi) throws Exception {
                createRouter(neutronApi, routerCreateConf, null, null);

				return null;
			}
		});
	}

	public static FipQos createFipQos(int bandWidth) {
		return FipQos.createBuilder().ingressBurstRate(bandWidth + "Mb")
				.egressBurstRate(bandWidth + "Mb")
				.ingressMaxRate(bandWidth + "Mbit")
				.egressMaxRate(bandWidth + "Mbit").build();
	}

	public static Integer getBandWidth(FipQos fipQos) {
		try {
			if (fipQos != null) {
				String egressBurstRate = fipQos.getEgressBurstRate();
				if (egressBurstRate != null) {
					String bandWidthStr = egressBurstRate.substring(0,
							egressBurstRate.length() - "Mb".length());
					return Integer.parseInt(bandWidthStr);
				}
			}
			return null;
		} catch (Exception e) {
			throw new MatrixException("后台错误", e);
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
	 * @throws OpenStackException
	 */
	private Page listPrivateSubnetByRegions(final Set<String> regions,
			final String name, final Integer currentPagePara,
			final Integer recordsPerPage) throws OpenStackException {
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
				List<SubnetResource> subnetResources = new LinkedList<SubnetResource>();
				int subnetCount = 0;
				boolean needCollect = true;
				for (String region : regions) {
					SubnetApi subnetApi = neutronApi.getSubnetApi(region);
					Map<String, Network> idToPrivateNetwork = new HashMap<String, Network>();
					for (Network network : neutronApi.getNetworkApi(region)
							.list().concat().toList()) {
						if (isPrivateNetwork(network)) {
							idToPrivateNetwork.put(network.getId(), network);
						}
					}
					if (needCollect) {
						String regionDisplayName = transMap.get(region);
						Map<String, Port> subnetIdToPort = new HashMap<String, Port>();
						for (Port port : neutronApi.getPortApi(region).list()
								.concat().toList()) {
							if (OpenStackConstants.PORT_DEVICE_OWNER_NETWORK_ROUTER_INTERFACE.equals(port
									.getDeviceOwner())) {
								ImmutableSet<IP> fixedIps = port.getFixedIps();
								if (fixedIps != null) {
									for (IP ip : fixedIps) {
										subnetIdToPort.put(ip.getSubnetId(),
												port);
									}
								}
							}
						}
						Optional<RouterApi> routerApiOptional = neutronApi
								.getRouterApi(region);
						if (!routerApiOptional.isPresent()) {
							throw new APINotAvailableException(RouterApi.class);
						}
						RouterApi routerApi = routerApiOptional.get();
						Map<String, Router> idToRouter = new HashMap<String, Router>();
						for (Router router : routerApi.list().concat().toList()) {
							idToRouter.put(router.getId(), router);
						}
						List<Subnet> resources = subnetApi.list().concat()
								.toList();
						for (Subnet resource : resources) {
							if (idToPrivateNetwork.containsKey(resource
									.getNetworkId())) {
								if (name == null
										|| (resource.getName() != null && resource
												.getName().contains(name))) {
									boolean needAdd = false;
									if (currentPage == null
											|| recordsPerPage == null) {
										needAdd = true;
									} else {
										if (needCollect) {
											if (subnetCount >= (currentPage + 1)
													* recordsPerPage) {
												needCollect = false;
											} else if (subnetCount >= currentPage
													* recordsPerPage) {
												needAdd = true;
											}
										}
									}
									if (needAdd) {
										RouterResource routerResource = null;
										Port port = subnetIdToPort.get(resource
												.getId());
										if (port != null) {
											String routerId = port
													.getDeviceId();
											if (routerId != null) {
												Router router = idToRouter
														.get(routerId);
												if (router != null) {
													routerResource = new RouterResourceImpl(
															region,
															regionDisplayName,
															router);
												}
											}
										}
										NetworkResource networkResource = new NetworkResourceImpl(
												region, regionDisplayName,
												idToPrivateNetwork.get(resource
														.getNetworkId()),
												new ArrayList<SubnetResource>());
										SubnetResource subnetResource = null;
										if (routerResource != null) {
											subnetResource = new SubnetResourceImpl(
													region, regionDisplayName,
													resource, networkResource,
													routerResource);
										} else {
											subnetResource = new SubnetResourceImpl(
													region, regionDisplayName,
													resource, networkResource);
										}
										subnetResources.add(subnetResource);
									}
									subnetCount++;
								}
							}
						}
					} else {
						for (Subnet resource : subnetApi.list().concat()
								.toList()) {
							if (idToPrivateNetwork.containsKey(resource
									.getNetworkId())) {
								if (name == null
										|| (resource.getName() != null && resource
												.getName().contains(name))) {
									subnetCount++;
								}
							}
						}
					}
				}

				Page page = new Page();
				page.setData(subnetResources);
				page.setTotalRecords(subnetCount);
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
	public Page listPrivateSubnet(String regionGroup, String name,
			Integer currentPage, Integer recordsPerPage)
			throws OpenStackException {
		if (StringUtils.isEmpty(regionGroup)) {
			return listPrivateSubnetByRegions(getRegions(), name, currentPage,
					recordsPerPage);
		} else {
			return listPrivateSubnetByRegions(getGroupRegions(regionGroup),
					name, currentPage, recordsPerPage);
		}
	}

	@Override
	public void editRouter(final String region, final String routerId,
			final String name, final boolean enablePublicNetworkGateway,
			final String publicNetworkId) throws OpenStackException {
		runWithApi(new ApiRunnable<NeutronApi, Void>() {

			@Override
			public Void run(NeutronApi neutronApi) throws Exception {
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

				Router.UpdateBuilder updateBuilder = Router.updateBuilder()
						.name(name);
				boolean needSetGatewayQos = false;
				if (enablePublicNetworkGateway
						&& (router.getExternalGatewayInfo() == null || router
								.getExternalGatewayInfo().getNetworkId() == null)) {
					NetworkApi networkApi = neutronApi.getNetworkApi(region);
					Network publicNetwork = networkApi.get(publicNetworkId);
					if (publicNetwork == null || !publicNetwork.getExternal()) {
						throw new ResourceNotFoundException("Public Network",
								"线路", publicNetworkId);
					}
					updateBuilder.externalGatewayInfo(ExternalGatewayInfo
							.builder().networkId(publicNetworkId).build());
					needSetGatewayQos = true;
				} else if (!enablePublicNetworkGateway
						&& (router.getExternalGatewayInfo() != null && router
								.getExternalGatewayInfo().getNetworkId() != null)) {
					updateBuilder.externalGatewayInfo(ExternalGatewayInfo
							.builder().build());
				} else if (enablePublicNetworkGateway
						&& (router.getExternalGatewayInfo() != null && router
								.getExternalGatewayInfo().getNetworkId() != null)
						&& !router.getExternalGatewayInfo().getNetworkId()
								.equals(publicNetworkId)) {
					routerApi.update(
							routerId,
							Router.updateBuilder()
									.externalGatewayInfo(
                                            ExternalGatewayInfo.builder()
                                                    .build()).build());
					routerApi.update(
							routerId,
							Router.updateBuilder()
									.externalGatewayInfo(
                                            ExternalGatewayInfo.builder()
                                                    .networkId(publicNetworkId)
                                                    .build()).build());
					needSetGatewayQos = true;
				}
				routerApi.update(routerId, updateBuilder.build());
				if (needSetGatewayQos) {
					Optional<FloatingIPApi> floatingIPApiOptional = neutronApi
							.getFloatingIPApi(region);
					if (!floatingIPApiOptional.isPresent()) {
						throw new APINotAvailableException(FloatingIPApi.class);
					}
					FloatingIPApi floatingIPApi = floatingIPApiOptional.get();
					floatingIPApi.update(
							router.getId(),
							FloatingIP
									.updateBuilder()
									.fipQos(createFipQos(openStackConf
                                            .getRouterGatewayBandWidth()))
									.build());
				}

				return null;
			}
		});
	}

	@Override
	public void deleteRouter(final String region, final String routerId)
			throws OpenStackException {
		runWithApi(new ApiRunnable<NeutronApi, Void>() {

			@Override
			public Void run(NeutronApi neutronApi) throws Exception {
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

				PortApi portApi = neutronApi.getPortApi(region);
				for (Port port : portApi.list().concat().toList()) {
					if (OpenStackConstants.PORT_DEVICE_OWNER_NETWORK_ROUTER_INTERFACE
							.equals(port.getDeviceOwner())) {
						if (StringUtils.equals(routerId, port.getDeviceId())) {
							throw new UserOperationException(
									"This router can not be deleted,because this router has at least one interface.",
									"路由有接口，不能删除 。");
						}
					}
				}

				boolean isSuccess = routerApi.delete(routerId);
				if (!isSuccess) {
					throw new OpenStackException(MessageFormat.format(
							"Router \"{0}\" delete failed.", routerId),
							MessageFormat.format("路由“{0}”删除失败。", routerId));
				}

				OpenStackServiceImpl.getOpenStackServiceGroup().getEventPublishService()
						.onDelete(new ResourceLocator().region(region).id(routerId).type(RouterResource.class));

				long userVoUserId = openStackUser.getUserVoUserId();
				OpenStackServiceImpl.getOpenStackServiceGroup().getLocalRcCountService()
						.decRcCount(userVoUserId, userVoUserId, region, CloudvmRcCountType.ROUTER);

				waitingRouter(routerId, routerApi, new Checker<Router>() {

					@Override
					public boolean check(Router router) throws Exception {
						return isDeleteFinished(router);
					}
				});

				return null;
			}

		});
	}

	private void waitingRouter(String routerId, RouterApi routerApi,
			Checker<Router> checker) throws OpenStackException {
		try {
			Router router = null;
			while (true) {
				router = routerApi.get(routerId);
				if (checker.check(router)) {
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

	private boolean isDeleteFinished(Router router) {
		return router == null;
	}

	@Override
	public PortResource getPort(final String region, final String portId)
			throws OpenStackException {
		return runWithApi(new ApiRunnable<NeutronApi, PortResource>() {

			@Override
			public PortResource run(NeutronApi neutronApi) throws Exception {
				checkRegion(region);

				PortApi portApi = neutronApi.getPortApi(region);
				Port port = portApi.get(portId);
				if (port == null) {
					throw new ResourceNotFoundException("Port", "端口", portId);
				}

				return new PortResourceImpl(region,
						getRegionDisplayName(region), port);
			}
		});
	}

	@Override
	public List<NetworkResource> listAvailableSubnetsForRouterInterface(
			final String region) throws OpenStackException {
		return runWithApi(new ApiRunnable<NeutronApi, List<NetworkResource>>() {
			@Override
			public List<NetworkResource> run(NeutronApi neutronApi)
					throws Exception {
				checkRegion(region);

				String regionDisplayName = getRegionDisplayName(region);

				PortApi portApi = neutronApi.getPortApi(region);
				Set<String> unAvailableSubnetIds = new HashSet<String>();
				for (Port port : portApi.list().concat().toList()) {
					if (OpenStackConstants.PORT_DEVICE_OWNER_NETWORK_ROUTER_INTERFACE
							.equals(port.getDeviceOwner())) {
						ImmutableSet<IP> fixedIps = port.getFixedIps();
						if (fixedIps != null) {
							for (IP ip : fixedIps) {
								unAvailableSubnetIds.add(ip.getSubnetId());
							}
						}
					}
				}

				SubnetApi subnetApi = neutronApi.getSubnetApi(region);
				Map<String, SubnetResource> idToAvailableSubnetResource = new HashMap<String, SubnetResource>();
				for (Subnet subnet : subnetApi.list().concat().toList()) {
					if (!unAvailableSubnetIds.contains(subnet.getId())) {
						idToAvailableSubnetResource.put(subnet.getId(),
								new SubnetResourceImpl(region,
										regionDisplayName, subnet));
					}
				}

				NetworkApi networkApi = neutronApi.getNetworkApi(region);
				List<NetworkResource> networkResources = new LinkedList<NetworkResource>();
				for (Network network : networkApi.list().concat().toList()) {
					if (isPrivateNetwork(network)
							&& network.getSubnets() != null) {
						List<SubnetResource> subnetResources = new LinkedList<SubnetResource>();
						for (String subnetId : network.getSubnets()) {
							SubnetResource subnetResource = idToAvailableSubnetResource
									.get(subnetId);
							if (subnetResource != null) {
								subnetResources.add(subnetResource);
							}
						}
						if (!subnetResources.isEmpty()) {
							NetworkResource networkResource = new NetworkResourceImpl(
									region, regionDisplayName, network,
									subnetResources);
							networkResources.add(networkResource);
						}
					}
				}

				return networkResources;
			}
		});
	}

	@Override
	public void associateSubnetWithRouter(final String region,
			final String routerId, final String subnetId)
			throws OpenStackException {
		runWithApi(new ApiRunnable<NeutronApi, Void>() {
			@Override
			public Void run(NeutronApi neutronApi) throws Exception {
				checkRegion(region);

				Optional<RouterApi> routerApiOptional = neutronApi
						.getRouterApi(region);
				if (!routerApiOptional.isPresent()) {
					throw new APINotAvailableException(RouterApi.class);
				}
				RouterApi routerApi = routerApiOptional.get();
				Optional<QuotaApi> quotaApiOptional = neutronApi
						.getQuotaApi(region);
				if (!quotaApiOptional.isPresent()) {
					throw new APINotAvailableException(QuotaApi.class);
				}
				QuotaApi quotaApi = quotaApiOptional.get();
				PortApi portApi = neutronApi.getPortApi(region);
				SubnetApi subnetApi = neutronApi.getSubnetApi(region);
				NetworkApi networkApi = neutronApi.getNetworkApi(region);

				Router router = routerApi.get(routerId);
				if (router == null) {
					throw new ResourceNotFoundException("Router", "路由",
							routerId);
				}

				Subnet subnet = subnetApi.get(subnetId);
				if (subnet == null) {
					throw new ResourceNotFoundException("Subnet", "子网",
							subnetId);
				}
				Network network = networkApi.get(subnet.getNetworkId());
				if (network == null) {
					throw new ResourceNotFoundException("Network", "网络",
							subnet.getNetworkId());
				}
				if (!isPrivateNetwork(network)) {
					throw new ResourceNotFoundException("Private Subnet",
							"私有子网", subnetId);
				}

				int routerSubnetPortCount = 0;
				for (Port port : portApi.list().concat().toList()) {
					if (OpenStackConstants.PORT_DEVICE_OWNER_NETWORK_ROUTER_INTERFACE
							.equals(port.getDeviceOwner())) {
                        if (router.getId().equals(port.getDeviceId()) && !subnet.getNetworkId().equals(port.getNetworkId())) {
                            throw new UserOperationException(
                                    "Router can not associate with subnets under different private networks"
                                    , MessageFormat.format("路由已关联子网“{0}”，不能同时关联不同私有网络下的子网",subnetId));
                        }
                        ImmutableSet<IP> fixedIps = port.getFixedIps();
						if (fixedIps != null) {
							for (IP ip : fixedIps) {
								if (subnetId.equals(ip.getSubnetId())) {
									throw new UserOperationException(
											MessageFormat.format(
													"Subnet is associated with router \"{0}\"",
													port.getDeviceId()),
											MessageFormat.format(
													"子网已关联到路由器“{0}”，不能再次关联",
													port.getDeviceId()));
								}
							}
						}
					}
					routerSubnetPortCount++;
				}

				Quota quota = quotaApi.getByTenant(openStackUser.getTenantId());
				if (quota == null) {
					throw new OpenStackException(MessageFormat.format(
							"Quota of user '{0}' is null.",
							openStackUser.getUserId()), "用户配额不可用");
				}
				if (quota.getPort() <= routerSubnetPortCount) {
					throw new UserOperationException(
							"Port count exceeding the quota.", "端口数量超过配额。");
				}

				routerApi.addInterfaceForSubnet(routerId, subnetId);

				return null;
			}
		});
	}

	@Override
	public void separateSubnetFromRouter(final String region,
			final String routerId, final String subnetId)
			throws OpenStackException {
		runWithApi(new ApiRunnable<NeutronApi, Void>() {

			@Override
			public Void run(NeutronApi neutronApi) throws Exception {
				checkRegion(region);

				Optional<RouterApi> routerApiOptional = neutronApi
						.getRouterApi(region);
				if (!routerApiOptional.isPresent()) {
					throw new APINotAvailableException(RouterApi.class);
				}
				RouterApi routerApi = routerApiOptional.get();
				PortApi portApi = neutronApi.getPortApi(region);
				SubnetApi subnetApi = neutronApi.getSubnetApi(region);
				NetworkApi networkApi = neutronApi.getNetworkApi(region);

				Router router = routerApi.get(routerId);
				if (router == null) {
					throw new ResourceNotFoundException("Router", "路由",
							routerId);
				}

				Subnet subnet = subnetApi.get(subnetId);
				if (subnet == null) {
					throw new ResourceNotFoundException("Subnet", "子网",
							subnetId);
				}
				Network network = networkApi.get(subnet.getNetworkId());
				if (network == null) {
					throw new ResourceNotFoundException("Network", "网络",
							subnet.getNetworkId());
				}
				if (!isPrivateNetwork(network)) {
					throw new ResourceNotFoundException("Private Subnet",
							"私有子网", subnetId);
				}

				Port associatedPort = null;
				checkPortExist: for (Port port : portApi.list().concat()
						.toList()) {
					if (OpenStackConstants.PORT_DEVICE_OWNER_NETWORK_ROUTER_INTERFACE
							.equals(port.getDeviceOwner())
							&& routerId.equals(port.getDeviceId())) {
						ImmutableSet<IP> fixedIps = port.getFixedIps();
						if (fixedIps != null) {
							for (IP ip : fixedIps) {
								if (subnetId.equals(ip.getSubnetId())) {
									associatedPort = port;
									break checkPortExist;
								}
							}
						}
					}
				}
				if (associatedPort == null) {
					throw new UserOperationException(
							"Subnet is not associated with router.",
							"子网和路由之间不存在关联");
				}

				boolean isSuccess = routerApi.removeInterfaceForSubnet(
						routerId, subnetId);
				if (!isSuccess) {
					throw new OpenStackException(
							"Separate subnets and routing failure.",
							"子网和路由解除关联失败");
				}

				waitingPort(associatedPort.getId(), portApi,
						new Checker<Port>() {

							@Override
							public boolean check(Port port) throws Exception {
								return isDeleteFinished(port);
							}
						});

				return null;
			}
		});
	}

	private void waitingPort(String portId, PortApi portApi,
			Checker<Port> checker) throws OpenStackException {
		try {
			Port port = null;
			while (true) {
				port = portApi.get(portId);
				if (checker.check(port)) {
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

	private boolean isDeleteFinished(Port port) {
		return port == null;
	}

	@Override
	public FloatingIpResource getFloatingIp(final String region,
			final String floatingIpId) throws OpenStackException {
		return runWithApi(new ApiRunnable<NeutronApi, FloatingIpResource>() {

			@Override
			public FloatingIpResource run(NeutronApi neutronApi)
					throws Exception {
				checkRegion(region);

				Optional<FloatingIPApi> floatingIPApiOptional = neutronApi
						.getFloatingIPApi(region);
				if (!floatingIPApiOptional.isPresent()) {
					throw new APINotAvailableException(FloatingIPApi.class);
				}
				FloatingIPApi floatingIPApi = floatingIPApiOptional.get();

				FloatingIP floatingIP = floatingIPApi.get(floatingIpId);
				if (floatingIP == null) {
					throw new ResourceNotFoundException("FloatingIP", "公网IP",
							floatingIpId);
				}

				if (StringUtils.equals(floatingIP.getFixedIpAddress(),
						floatingIP.getFloatingIpAddress())) {
					throw new ResourceNotFoundException("FloatingIP", "公网IP",
							floatingIpId);
				}

				String regionDisplayName = getRegionDisplayName(region);

				NetworkResource networkResource = new NetworkResourceImpl(
						neutronApi.getNetworkApi(region).get(
								floatingIP.getFloatingNetworkId()));

				String portId = floatingIP.getPortId();
				VMResource vmResource = null;
				if (portId != null) {
					PortApi portApi = neutronApi.getPortApi(region);
					Port port = portApi.get(portId);
					if (port == null) {
						throw new ResourceNotFoundException("Port", "公网端口",
								portId);
					}
					String vmId = port.getDeviceId();
					if (vmId == null) {
						throw new ResourceNotFoundException(
								"vm binded by floating IP", "公网绑定的虚拟机", portId);
					}
					vmResource = vmManager.get(region, vmId);
				}

				FloatingIpResource floatingIpResource = null;
				if (vmResource != null) {
					floatingIpResource = new FloatingIpResourceImpl(region,
							regionDisplayName, floatingIP, networkResource,
							vmResource);
				} else {
					floatingIpResource = new FloatingIpResourceImpl(region,
							regionDisplayName, floatingIP, networkResource);
				}
				return floatingIpResource;
			}
		});
	}

	@Override
	public void deleteFloaingIp(final String region, final String floatingIpId)
			throws OpenStackException {
		runWithApi(new ApiRunnable<NeutronApi, Void>() {

			@Override
			public Void run(NeutronApi neutronApi) throws Exception {
				checkRegion(region);

				Optional<FloatingIPApi> floatingIPApiOptional = neutronApi
						.getFloatingIPApi(region);
				if (!floatingIPApiOptional.isPresent()) {
					throw new APINotAvailableException(FloatingIPApi.class);
				}
				FloatingIPApi floatingIPApi = floatingIPApiOptional.get();

				FloatingIP floatingIP = floatingIPApi.get(floatingIpId);
				if (floatingIP == null) {
					throw new ResourceNotFoundException("FloatingIP", "公网IP",
							floatingIpId);
				}

				if (StringUtils.equals(floatingIP.getFixedIpAddress(),
						floatingIP.getFloatingIpAddress())) {
					throw new ResourceNotFoundException("FloatingIP", "公网IP",
							floatingIpId);
				}

				String portId = floatingIP.getPortId();
				if (portId != null) {
					PortApi portApi = neutronApi.getPortApi(region);
					Port port = portApi.get(portId);
					if (port == null) {
						throw new ResourceNotFoundException("Port", "公网端口",
								portId);
					}
					String vmId = port.getDeviceId();
					if (vmId != null) {
						throw new UserOperationException(MessageFormat.format(
								"Floating IP is binded to VM \"{0}\".", vmId),
								MessageFormat.format("公网IP已经绑定到虚拟机“{0}”，请先解绑。",
										vmId));
					}
				}

				boolean isSuccess = floatingIPApi.delete(floatingIpId);
				if (!isSuccess) {
					throw new OpenStackException("Floating IP delete failed.",
							"公网IP删除失败");
				}

				OpenStackServiceImpl.getOpenStackServiceGroup().getEventPublishService()
						.onDelete(new ResourceLocator().region(region).id(floatingIpId).type(FloatingIpResource.class));

				long userVoUserId = openStackUser.getUserVoUserId();
				LocalRcCountService localRcCountService = OpenStackServiceImpl.getOpenStackServiceGroup().getLocalRcCountService();
				localRcCountService.decRcCount(userVoUserId, userVoUserId, region, CloudvmRcCountType.FLOATING_IP);
				localRcCountService.decRcCount(userVoUserId, region, CloudvmRcCountType.BAND_WIDTH
						, getBandWidth(floatingIP.getFipQos()));

				waitingFloatingIP(floatingIpId, floatingIPApi,
						new Checker<FloatingIP>() {

							@Override
							public boolean check(FloatingIP floatingIP)
									throws Exception {
								return isDeleteFinished(floatingIP);
							}
						});

				return null;
			}
		});
	}

	private void waitingFloatingIP(String floatingIpId,
			FloatingIPApi floatingIPApi, Checker<FloatingIP> checker)
			throws OpenStackException {
		try {
			FloatingIP floatingIP = null;
			while (true) {
				floatingIP = floatingIPApi.get(floatingIpId);
				if (checker.check(floatingIP)) {
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

	private boolean isDeleteFinished(FloatingIP floatingIP) {
		return floatingIP == null;
	}

	public void setVmManager(VMManagerImpl vmManager) {
		this.vmManager = vmManager;
	}

	@Override
	public List<NetworkResource> listPublic(final String region)
			throws OpenStackException {
		return runWithApi(new ApiRunnable<NeutronApi, List<NetworkResource>>() {

			@Override
			public List<NetworkResource> run(NeutronApi neutronApi)
					throws Exception {
				checkRegion(region);

				NetworkApi networkApi = neutronApi.getNetworkApi(region);

				List<NetworkResource> networkResources = new LinkedList<NetworkResource>();
				for (Network network : networkApi.list().concat().toList()) {
					if (network.getExternal()) {
						networkResources.add(new NetworkResourceImpl(network));
					}
				}
				return networkResources;
			}
		});
	}

	private static String getNameOfFloatingIp(FloatingIP floatingIP) {
		return floatingIP.getName();
	}

	private Page listFloatingIpByRegions(final Set<String> regions,
			final String name, final Integer currentPagePara,
			final Integer recordsPerPage,
			final Checker<FloatingIP> floatingIpFilter)
			throws OpenStackException {
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
				List<FloatingIpResource> floatingIpResources = new LinkedList<FloatingIpResource>();
				int floatingIpCount = 0;
				boolean needCollect = true;
				for (String region : regions) {
					Optional<FloatingIPApi> floatingIpApiOptional = neutronApi
							.getFloatingIPApi(region);
					if (!floatingIpApiOptional.isPresent()) {
						throw new APINotAvailableException(FloatingIPApi.class);
					}
					FloatingIPApi floatingIPApi = floatingIpApiOptional.get();
					if (needCollect) {
						String regionDisplayName = transMap.get(region);
						List<FloatingIP> resources = floatingIPApi.list()
								.concat().toList();
						for (FloatingIP resource : resources) {
							if (floatingIpFilter.check(resource)) {
								if (name == null
										|| (getNameOfFloatingIp(resource) != null && getNameOfFloatingIp(
												resource).contains(name))) {
									boolean needAdd = false;
									if (currentPage == null
											|| recordsPerPage == null) {
										needAdd = true;
									} else {
										if (needCollect) {
											if (floatingIpCount >= (currentPage + 1)
													* recordsPerPage) {
												needCollect = false;
											} else if (floatingIpCount >= currentPage
													* recordsPerPage) {
												needAdd = true;
											}
										}
									}
									if (needAdd) {
										NetworkResource networkResource = new NetworkResourceImpl(
												neutronApi
														.getNetworkApi(region)
														.get(resource
                                                                .getFloatingNetworkId()));

										String portId = resource.getPortId();
										VMResource vmResource = null;
										if (portId != null) {
											PortApi portApi = neutronApi
													.getPortApi(region);
											Port port = portApi.get(portId);
											if (port == null) {
												throw new ResourceNotFoundException(
														"Port", "公网端口", portId);
											}
											String vmId = port.getDeviceId();
											if (vmId == null) {
												throw new ResourceNotFoundException(
														"vm binded by floating IP",
														"公网绑定的虚拟机", portId);
											}
											vmResource = vmManager.get(region,
													vmId);
										}

										FloatingIpResource floatingIpResource = null;
										if (vmResource != null) {
											floatingIpResource = new FloatingIpResourceImpl(
													region, regionDisplayName,
													resource, networkResource,
													vmResource);
										} else {
											floatingIpResource = new FloatingIpResourceImpl(
													region, regionDisplayName,
													resource, networkResource);
										}

										floatingIpResources
												.add(floatingIpResource);
									}
									floatingIpCount++;
								}
							}
						}
					} else {
						for (FloatingIP resource : floatingIPApi.list()
								.concat().toList()) {
							if (floatingIpFilter.check(resource)) {
								if (name == null
										|| (getNameOfFloatingIp(resource) != null && getNameOfFloatingIp(
												resource).contains(name))) {
									floatingIpCount++;
								}
							}
						}
					}
				}

				Page page = new Page();
				page.setData(floatingIpResources);
				page.setTotalRecords(floatingIpCount);
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
	public Page listFloatingIp(String regionGroup, String name,
			Integer currentPage, Integer recordsPerPage)
			throws OpenStackException {
		Set<String> regions = null;
		if (StringUtils.isEmpty(regionGroup)) {
			regions = getRegions();
		} else {
			regions = getGroupRegions(regionGroup);
		}
		return listFloatingIpByRegions(regions, name, currentPage,
				recordsPerPage, new Checker<FloatingIP>() {

					@Override
					public boolean check(FloatingIP floatingIP)
							throws Exception {
						return !StringUtils.equals(
								floatingIP.getFloatingIpAddress(),
								floatingIP.getFixedIpAddress());
					}
				});
	}

	@Override
	public void checkCreateFloatingIp(final FloatingIpCreateConf createConf) throws OpenStackException{
		runWithApi(new ApiRunnable<NeutronApi, Void>() {
			@Override
			public Void run(NeutronApi neutronApi) throws Exception {
				checkCreateFloatingIp(neutronApi,createConf);
				return null;
			}
		});
	}

	public void checkCreateFloatingIp(NeutronApi neutronApi, FloatingIpCreateConf createConf) throws OpenStackException {
		final String region = createConf.getRegion();
//		final String name = createConf.getName();
		final String publicNetworkId = createConf.getPublicNetworkId();
		final Integer bandWidth = createConf.getBandWidth();
		final Integer count = createConf.getCount();

		checkRegion(region);

		NetworkApi networkApi = neutronApi.getNetworkApi(region);

		Network publicNetwork = networkApi.get(publicNetworkId);
		if (publicNetwork == null || !publicNetwork.getExternal()) {
			throw new ResourceNotFoundException("Public Network", "线路",
					publicNetworkId);
		}

		if (bandWidth <= 0) {
			throw new UserOperationException("bandWidth <= 0",
					"带宽必须大于0");
		}

		if (count <= 0) {
			throw new UserOperationException(
					"The count of floating IP is less than or equal to zero.",
					"公网IP的数量不能小于或等于0");
		}

		Optional<QuotaApi> quotaApiOptional = neutronApi
				.getQuotaApi(region);
		if (!quotaApiOptional.isPresent()) {
			throw new APINotAvailableException(QuotaApi.class);
		}
		QuotaApi quotaApi = quotaApiOptional.get();

		Optional<FloatingIPApi> floatingIPApiOptional = neutronApi
				.getFloatingIPApi(region);
		if (!floatingIPApiOptional.isPresent()) {
			throw new APINotAvailableException(FloatingIPApi.class);
		}
		FloatingIPApi floatingIPApi = floatingIPApiOptional.get();

		int totalBandWidth = 0;
		int floatingIpCount = 0;
		for (FloatingIP floatingIP : floatingIPApi.list().concat()
				.toList()) {
			if (!StringUtils.equals(floatingIP.getFixedIpAddress(),
					floatingIP.getFloatingIpAddress())) {
				floatingIpCount++;
				totalBandWidth += getBandWidth(floatingIP.getFipQos());
			}
		}

		Quota quota = quotaApi.getByTenant(openStackUser.getTenantId());
		if (quota == null) {
			throw new OpenStackException(
					"User quota is not available.", "用户配额不可用");
		}

		final int floatingIpCountQuota = quota.getFloatingIp()
				- quota.getRouter();
		OpenStackServiceImpl.getOpenStackServiceGroup().getLocalCommonQuotaSerivce()
				.checkQuota(openStackUser.getUserVoUserId(), region, CommonQuotaType.CLOUDVM_FLOATING_IP, floatingIpCount + count);
		if (floatingIpCount + count > floatingIpCountQuota) {
			throw new UserOperationException(
					"Floating IP count exceeding the quota.",
					"公网IP数量超过配额");
		}

		final int floatingIpBandWidthQuota = quota.getBandWidth()
				- quota.getRouter()
				* openStackConf.getRouterGatewayBandWidth();
		OpenStackServiceImpl.getOpenStackServiceGroup().getLocalCommonQuotaSerivce()
				.checkQuota(openStackUser.getUserVoUserId(), region, CommonQuotaType.CLOUDVM_BAND_WIDTH, totalBandWidth + bandWidth * count);
		if (totalBandWidth + bandWidth * count > floatingIpBandWidthQuota) {
			throw new UserOperationException(
					"Floating IP band width exceeding the quota.",
					"公网IP带宽超过配额");
		}
	}

	private void createFloatingIp(NeutronApi neutronApi, FloatingIpCreateConf createConf, List<FloatingIP> successCreatedFloatingIps) throws OpenStackException {
		final String region = createConf.getRegion();
		final String name = createConf.getName();
		final String publicNetworkId = createConf.getPublicNetworkId();
		final Integer bandWidth = createConf.getBandWidth();
		final Integer count = createConf.getCount();

		checkRegion(neutronApi,region);

		NetworkApi networkApi = neutronApi.getNetworkApi(region);

		Network publicNetwork = networkApi.get(publicNetworkId);
		if (publicNetwork == null || !publicNetwork.getExternal()) {
			throw new ResourceNotFoundException("Public Network", "线路",
					publicNetworkId);
		}

		if (bandWidth <= 0) {
			throw new UserOperationException("bandWidth <= 0",
					"带宽必须大于0");
		}

		if (count <= 0) {
			throw new UserOperationException(
					"The count of floating IP is less than or equal to zero.",
					"公网IP的数量不能小于或等于0");
		}

		Optional<QuotaApi> quotaApiOptional = neutronApi
				.getQuotaApi(region);
		if (!quotaApiOptional.isPresent()) {
			throw new APINotAvailableException(QuotaApi.class);
		}
		QuotaApi quotaApi = quotaApiOptional.get();

		Optional<FloatingIPApi> floatingIPApiOptional = neutronApi
				.getFloatingIPApi(region);
		if (!floatingIPApiOptional.isPresent()) {
			throw new APINotAvailableException(FloatingIPApi.class);
		}
		FloatingIPApi floatingIPApi = floatingIPApiOptional.get();

		int totalBandWidth = 0;
		int floatingIpCount = 0;
		for (FloatingIP floatingIP : floatingIPApi.list().concat()
				.toList()) {
			if (!StringUtils.equals(floatingIP.getFixedIpAddress(),
					floatingIP.getFloatingIpAddress())) {
				floatingIpCount++;
				totalBandWidth += getBandWidth(floatingIP.getFipQos());
			}
		}

		Quota quota = quotaApi.getByTenant(openStackUser.getTenantId());
		if (quota == null) {
			throw new OpenStackException(
					"User quota is not available.", "用户配额不可用");
		}

		final int floatingIpCountQuota = quota.getFloatingIp()
				- quota.getRouter();
		OpenStackServiceImpl.getOpenStackServiceGroup().getLocalCommonQuotaSerivce()
				.checkQuota(openStackUser.getUserVoUserId(), region, CommonQuotaType.CLOUDVM_FLOATING_IP, floatingIpCount + count);
		if (floatingIpCount + count > floatingIpCountQuota) {
			throw new UserOperationException(
					"Floating IP count exceeding the quota.",
					"公网IP数量超过配额");
		}

		final int floatingIpBandWidthQuota = quota.getBandWidth()
				- quota.getRouter()
				* openStackConf.getRouterGatewayBandWidth();
		OpenStackServiceImpl.getOpenStackServiceGroup().getLocalCommonQuotaSerivce()
				.checkQuota(openStackUser.getUserVoUserId(), region, CommonQuotaType.CLOUDVM_BAND_WIDTH, totalBandWidth + bandWidth * count);
		if (totalBandWidth + bandWidth * count > floatingIpBandWidthQuota) {
			throw new UserOperationException(
					"Floating IP band width exceeding the quota.",
					"公网IP带宽超过配额");
		}

		long userVoUserId = openStackUser.getUserVoUserId();
		LocalRcCountService localRcCountService = OpenStackServiceImpl.getOpenStackServiceGroup().getLocalRcCountService();
		for (int i = 0; i < count; i++) {
			final String floatingIpName;
			if (count > 1) {
				floatingIpName = MessageFormat.format("{0}({1})", name, i + 1);
			} else {
				floatingIpName = name;
			}
			FloatingIP floatingIP = floatingIPApi.create(FloatingIP.createBuilder(publicNetworkId)
					.name(floatingIpName).fipQos(createFipQos(bandWidth)).build());
			if (successCreatedFloatingIps != null) {
				successCreatedFloatingIps.add(floatingIP);
			}
			localRcCountService.incRcCount(userVoUserId, userVoUserId, region, CloudvmRcCountType.FLOATING_IP);
			localRcCountService.incRcCount(userVoUserId, region, CloudvmRcCountType.BAND_WIDTH, bandWidth);
		}
	}

	public void createFloatingIp(NeutronApi neutronApi, FloatingIpCreateConf createConf, FloatingIpCreateListener listener, Object listenerUserData) throws OpenStackException {
		List<FloatingIP> successCreatedFloatingIps = null;
		if(listener!=null){
			successCreatedFloatingIps = new LinkedList<FloatingIP>();
		}

		try {
			createFloatingIp(neutronApi,createConf,successCreatedFloatingIps);
		} catch (Exception e){
			notifyFloatingIpCreateListener(createConf,successCreatedFloatingIps,e,listener,listenerUserData);
			ExceptionUtil.throwException(e);
		}
		notifyFloatingIpCreateListener(createConf,successCreatedFloatingIps,null,listener,listenerUserData);
	}

	private void notifyFloatingIpCreateListener(final FloatingIpCreateConf createConf, final List<FloatingIP> successCreatedFloatingIps, Exception exception, final FloatingIpCreateListener listener, final Object listenerUserData){
		if (listener != null) {
			int successCreatedFloatingIpsCount = successCreatedFloatingIps.size();
			int floatingIpCount = createConf.getCount();
			int floatingIpIndex = 0;

			for (; floatingIpIndex < successCreatedFloatingIpsCount; floatingIpIndex++) {
				try {
					final int floatingIpIndexRef = floatingIpIndex;
					RetryUtil.retry(new Function<Boolean>() {
						@Override
						public Boolean apply() throws Exception {
							listener.floatingIpCreated(new FloatingIpCreateEvent(createConf.getRegion(), successCreatedFloatingIps.get(floatingIpIndexRef).getId(), floatingIpIndexRef, listenerUserData));
							return true;
						}
					}, 3, "公网IP监听器实现方错误：重试超过3次");
				} catch (Exception e) {
					ExceptionUtil.processBillingException(e);
				}
			}

			final String reason = exception != null ? ExceptionUtil.getUserMessage(exception) : "后台错误";
			for (; floatingIpIndex < floatingIpCount; floatingIpIndex++) {
				try {
					final int floatingIpIndexRef = floatingIpIndex;
					RetryUtil.retry(new Function<Boolean>() {
						@Override
						public Boolean apply() throws Exception {
							listener.floatingIpCreateFailed(new FloatingIpCreateFailEvent(createConf.getRegion(), floatingIpIndexRef, reason, listenerUserData));
							return true;
						}
					}, 3, "公网IP监听器实现方错误：重试超过3次");
				} catch (Exception e) {
					ExceptionUtil.processBillingException(e);
				}
			}
		}
	}

	@Override
	public void createFloatingIp(final FloatingIpCreateConf floatingIpCreateConf)
			throws OpenStackException {
		runWithApi(new ApiRunnable<NeutronApi, Void>() {

			@Override
			public Void run(NeutronApi neutronApi) throws Exception {
				createFloatingIp(neutronApi, floatingIpCreateConf, null, null);

				return null;
			}
		});
	}

	@Override
	public void editFloatingIp(final String region, final String floatingIpId,
			final String name/*, final int bandWidth*/) throws OpenStackException {
		runWithApi(new ApiRunnable<NeutronApi, Void>() {

			@Override
			public Void run(NeutronApi neutronApi) throws Exception {
				checkRegion(region);

				/*
				if (bandWidth <= 0) {
					throw new UserOperationException("bandWidth <= 0",
							"带宽必须大于0");
				}
				*/

				Optional<FloatingIPApi> floatingIPApiOptional = neutronApi
						.getFloatingIPApi(region);
				if (!floatingIPApiOptional.isPresent()) {
					throw new APINotAvailableException(FloatingIPApi.class);
				}
				FloatingIPApi floatingIPApi = floatingIPApiOptional.get();

				FloatingIP floatingIP = floatingIPApi.get(floatingIpId);
				if (floatingIP == null
						|| StringUtils.equals(
								floatingIP.getFloatingIpAddress(),
								floatingIP.getFixedIpAddress())) {
					throw new ResourceNotFoundException("FloatingIP", "公网IP",
							floatingIpId);
				}

				/*
				int othersBandWidth = 0;
				for (FloatingIP fip : floatingIPApi.list().concat().toList()) {
					if (!StringUtils.equals(fip.getFixedIpAddress(),
							fip.getFloatingIpAddress())) {
						if (!StringUtils.equals(fip.getId(), floatingIpId)) {
							othersBandWidth += getBandWidth(fip.getFipQos());
						}
					}
				}

				Optional<QuotaApi> quotaApiOptional = neutronApi
						.getQuotaApi(region);
				if (!quotaApiOptional.isPresent()) {
					throw new APINotAvailableException(QuotaApi.class);
				}
				QuotaApi quotaApi = quotaApiOptional.get();

				Quota quota = quotaApi.getByTenant(openStackUser.getTenantId());
				if (quota == null) {
					throw new OpenStackException(
							"User quota is not available.", "用户配额不可用");
				}

				final int floatingIpBandWidthQuota = quota.getBandWidth()
						- quota.getRouter()
						* openStackConf.getRouterGatewayBandWidth();
				if (othersBandWidth + bandWidth > floatingIpBandWidthQuota) {
					throw new UserOperationException(
							"Floating IP band width exceeding the quota.",
							"公网IP带宽超过配额");
				}
				*/

				floatingIPApi.update(floatingIpId, (FloatingIP.updateBuilder()
						.name(name)/*.fipQos(createFipQos(bandWidth))*/.build()));

				return null;
			}
		});
	}

	@Override
	public List<NetworkResource> listShared(final String region)
			throws OpenStackException {
		return runWithApi(new ApiRunnable<NeutronApi, List<NetworkResource>>() {

			@Override
			public List<NetworkResource> run(NeutronApi neutronApi)
					throws Exception {
				checkRegion(region);

				NetworkApi networkApi = neutronApi.getNetworkApi(region);

				List<NetworkResource> networkResources = new LinkedList<NetworkResource>();
				for (Network network : networkApi.list().concat().toList()) {
					if (network.getShared()) {
//							&& !StringUtils.equals(network.getId(),
//									openStackConf.getGlobalSharedNetworkId())) {
						networkResources.add(new NetworkResourceImpl(network));
					}
				}
				return networkResources;
			}
		});
	}

	Set<String> listPublicFloatingIpAddressAsSet(final String region) throws OpenStackException {
		return runWithApi(new ApiRunnable<NeutronApi, Set<String>>() {
			@Override
			public Set<String> run(NeutronApi neutronApi) throws Exception {
				Optional<FloatingIPApi> floatingIPApiOptional = neutronApi.getFloatingIPApi(region);
				if (!floatingIPApiOptional.isPresent()) {
					throw new APINotAvailableException(FloatingIPApi.class);
				}
				FloatingIPApi floatingIPApi = floatingIPApiOptional.get();

				Set<String> ipAddressSet = new HashSet<String>();
				List<FloatingIP> floatingIPs = floatingIPApi.list().concat().toList();
				for (FloatingIP floatingIP : floatingIPs) {
					if (isPublicFloatingIp(floatingIP)) {
						ipAddressSet.add(floatingIP.getFloatingIpAddress());
					}
				}
				return ipAddressSet;
			}
		});
	}

	public static boolean isPublicFloatingIp(FloatingIP floatingIP) {
		return !StringUtils.equals(floatingIP.getFixedIpAddress(), floatingIP.getFloatingIpAddress());
	}

	@Override
	protected String getProviderOrApi() {
		return "openstack-neutron";
	}

	@Override
	protected Class<NeutronApi> getApiClass() {
		return NeutronApi.class;
	}

	@Override
	public <ReturnType> ReturnType runWithApi(ApiRunnable<NeutronApi, ReturnType> task) throws OpenStackException {
		try {
			NeutronApi api = OpenStackServiceImpl.getOpenStackServiceGroup().getApiService().getNeutronApi();
			return task.run(api);
		} catch (OpenStackException ex) {
			throw ex;
		} catch (Exception ex) {
			throw new OpenStackException("后台错误", ex);
		}
	}
}
