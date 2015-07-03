package com.letv.portal.service.openstack.resource.manager.impl;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jclouds.ContextBuilder;
import org.jclouds.logging.slf4j.config.SLF4JLoggingModule;
import org.jclouds.openstack.neutron.v2.NeutronApi;
import org.jclouds.openstack.neutron.v2.domain.Network;
import org.jclouds.openstack.neutron.v2.features.NetworkApi;
import org.jclouds.openstack.nova.v2_0.NovaApi;
import org.jclouds.openstack.nova.v2_0.domain.Flavor;
import org.jclouds.openstack.nova.v2_0.domain.FloatingIP;
import org.jclouds.openstack.nova.v2_0.domain.Server;
import org.jclouds.openstack.nova.v2_0.domain.Server.Status;
import org.jclouds.openstack.nova.v2_0.domain.ServerCreated;
import org.jclouds.openstack.nova.v2_0.extensions.FloatingIPApi;
import org.jclouds.openstack.nova.v2_0.features.FlavorApi;
import org.jclouds.openstack.nova.v2_0.features.ServerApi;
import org.jclouds.openstack.nova.v2_0.options.CreateServerOptions;
import org.slf4j.Logger;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableSet;
import com.google.inject.Module;
import com.letv.common.email.bean.MailMessage;
import com.letv.common.util.PasswordRandom;
import com.letv.portal.service.openstack.exception.APINotAvailableException;
import com.letv.portal.service.openstack.exception.OpenStackException;
import com.letv.portal.service.openstack.exception.PollingInterruptedException;
import com.letv.portal.service.openstack.exception.RegionNotFoundException;
import com.letv.portal.service.openstack.exception.ResourceNotFoundException;
import com.letv.portal.service.openstack.exception.TaskNotFinishedException;
import com.letv.portal.service.openstack.exception.VMDeleteException;
import com.letv.portal.service.openstack.exception.VMStatusException;
import com.letv.portal.service.openstack.impl.OpenStackConf;
import com.letv.portal.service.openstack.impl.OpenStackServiceGroup;
import com.letv.portal.service.openstack.impl.OpenStackUser;
import com.letv.portal.service.openstack.resource.FlavorResource;
import com.letv.portal.service.openstack.resource.NetworkResource;
import com.letv.portal.service.openstack.resource.VMResource;
import com.letv.portal.service.openstack.resource.impl.FlavorResourceImpl;
import com.letv.portal.service.openstack.resource.impl.VMResourceImpl;
import com.letv.portal.service.openstack.resource.manager.ImageManager;
import com.letv.portal.service.openstack.resource.manager.NetworkManager;
import com.letv.portal.service.openstack.resource.manager.VMCreateConf;
import com.letv.portal.service.openstack.resource.manager.VMManager;

public class VMManagerImpl extends AbstractResourceManager implements VMManager {

	private static final Logger logger = org.slf4j.LoggerFactory
			.getLogger(VMManager.class);

	private NovaApi novaApi;

	private ImageManager imageManager;

	private NetworkManager networkManager;

	public VMManagerImpl(OpenStackServiceGroup openStackServiceGroup,
			OpenStackConf openStackConf, OpenStackUser openStackUser) {
		super(openStackServiceGroup, openStackConf, openStackUser);

		Iterable<Module> modules = ImmutableSet
				.<Module> of(new SLF4JLoggingModule());

		novaApi = ContextBuilder
				.newBuilder("openstack-nova")
				.endpoint(openStackConf.getPublicEndpoint())
				.credentials(
						openStackUser.getUserId() + ":"
								+ openStackUser.getUserId(),
						openStackUser.getPassword()).modules(modules)
				.buildApi(NovaApi.class);
	}

	@Override
	public void close() throws IOException {
		novaApi.close();
	}

	@Override
	public Set<String> getRegions() {
		return novaApi.getConfiguredRegions();
	}

	@Override
	public List<VMResource> list(String region) throws OpenStackException {
		checkRegion(region);

		String regionDisplayName = getRegionDisplayName(region);

		ServerApi serverApi = novaApi.getServerApi(region);
		List<Server> resources = serverApi.listInDetail().concat().toList();
		List<VMResource> vmResources = new ArrayList<VMResource>(
				resources.size());
		for (Server resource : resources) {
			vmResources.add(new VMResourceImpl(region, regionDisplayName,
					resource, this, imageManager, openStackUser));
		}
		return vmResources;
	}

	@Override
	public List<VMResource> listAll() throws RegionNotFoundException,
			ResourceNotFoundException, APINotAvailableException {
		Set<String> regions = getRegions();
		Map<String, String> transMap = getRegionCodeToDisplayNameMap();
		List<VMResource> vmResources = new LinkedList<VMResource>();
		for (String region : regions) {
			String regionDisplayName = transMap.get(region);
			ServerApi serverApi = novaApi.getServerApi(region);
			List<Server> resources = serverApi.listInDetail().concat().toList();
			for (Server resource : resources) {
				vmResources.add(new VMResourceImpl(region, regionDisplayName,
						resource, this, imageManager, openStackUser));
			}
		}
		return vmResources;
	}

	@Override
	public List<VMResource> listByRegionGroup(String regionGroup)
			throws RegionNotFoundException, ResourceNotFoundException,
			APINotAvailableException, OpenStackException {
		Set<String> groupRegions = getGroupRegions(regionGroup);

		Map<String, String> transMap = getRegionCodeToDisplayNameMap();

		List<VMResource> vmResources = new LinkedList<VMResource>();
		for (String region : groupRegions) {
			ServerApi serverApi = novaApi.getServerApi(region);
			List<Server> resources = serverApi.listInDetail().concat().toList();
			for (Server resource : resources) {
				vmResources.add(new VMResourceImpl(region,
						transMap.get(region), resource, this, imageManager,
						openStackUser));
			}
		}
		return vmResources;
	}

	@Override
	public VMResource get(String region, String id) throws OpenStackException {
		checkRegion(region);

		ServerApi serverApi = novaApi.getServerApi(region);
		Server server = serverApi.get(id);
		if (server != null) {
			return new VMResourceImpl(region, getRegionDisplayName(region),
					server, this, imageManager, openStackUser);
		} else {
			throw new ResourceNotFoundException("VM", "虚拟机", id);
		}
	}

	@Override
	public VMResource create(final String region, VMCreateConf conf)
			throws OpenStackException {
		checkRegion(region);

		CreateServerOptions createServerOptions = new CreateServerOptions();

		Set<String> networks = new HashSet<String>();
		List<NetworkResource> networkResources = conf.getNetworkResources();
		if (networkResources != null) {
			for (int i = 0; i < networkResources.size(); i++) {
				networks.add(networkResources.get(i).getId());
			}
		}
		// test code begin(ssh login)
		{
			NetworkManagerImpl networkManagerImpl = (NetworkManagerImpl) networkManager;
			NeutronApi neutronApi = networkManagerImpl.getNeutronApi();
			NetworkApi networkApi = neutronApi.getNetworkApi(region);
			for (Network network : networkApi.list().concat().toList()) {
				if (openStackConf.getUserPrivateNetworkName().equals(
						network.getName())) {
					networks.add(network.getId());
					break;
				}
			}

			if (openStackUser.getInternalUser()) {
				networks.add(openStackConf.getGlobalSharedNetworkId());
			}
		}
		// test code end
		createServerOptions.networks(networks);

		if (conf.getAdminPass() == null) {
			conf.setAdminPass(PasswordRandom.genStr(10));
		}
		createServerOptions.adminPass(conf.getAdminPass());

		// test code begin(ssh login)
		{
			// Optional<SecurityGroupApi> securityGroupApi = novaApi
			// .getSecurityGroupApi(region);
			// if (securityGroupApi.isPresent()) {
			// SecurityGroup defaultSecurityGroup = null;
			// List<SecurityGroup> securityGroups = securityGroupApi.get()
			// .list().toList();
			// for (SecurityGroup securityGroup : securityGroups) {
			// if ("default".equals(securityGroup.getName())) {
			// defaultSecurityGroup = securityGroup;
			// break;
			// }
			// }
			// if (defaultSecurityGroup != null) {
			// } else {
			// throw new
			// OpenStackException("Default security group is not found.");
			// }
			// } else {
			// throw new APINotAvailableException(SecurityGroupApi.class);
			// }

			createServerOptions.securityGroupNames("default");
		}
		// test code end

		final ServerApi serverApi = novaApi.getServerApi(region);
		final ServerCreated serverCreated = serverApi.create(conf.getName(),
				conf.getImageResource().getId(), conf.getFlavorResource()
						.getId(), createServerOptions);
		Server server = serverApi.get(serverCreated.getId());
		VMResourceImpl vmResourceImpl = new VMResourceImpl(region,
				getRegionDisplayName(region), server, this, imageManager,
				openStackUser);

		emailVmCreated(vmResourceImpl, conf);

		if (conf.getBindFloatingIP()) {
			new Thread() {
				public void run() {
					try {
						Server server = serverApi.get(serverCreated.getId());
						while (server.getAddresses()
								.get(openStackConf.getUserPrivateNetworkName())
								.isEmpty()) {
							try {
								Thread.sleep(1000);
							} catch (InterruptedException e) {
								throw new PollingInterruptedException(e);
							}
							server = serverApi.get(serverCreated.getId());
						}
						String floatingIP = bindFloatingIP(region,
								serverCreated.getId());
						emailBindIP(
								new VMResourceImpl(region,
										getRegionDisplayName(region), server,
										VMManagerImpl.this, imageManager,
										openStackUser), floatingIP);
					} catch (Exception e) {
						logger.error(e.getMessage(), e);
					}
				}
			}.start();
		}

		// test code begin(ssh login)
		// {
		// Optional<FloatingIPApi> floatingIPApiOptional = novaApi
		// .getFloatingIPApi(region);
		// if (!floatingIPApiOptional.isPresent()) {
		// throw new APINotAvailableException(FloatingIPApi.class);
		// }
		// FloatingIPApi floatingIPApi = floatingIPApiOptional.get();
		// // FloatingIP floatingIP = floatingIPApi.create();
		// for(Entry<String,Address> entry:server.getAddresses().entries()){
		// logger.info(MessageFormat.format("server address: {0} => {1}",
		// entry.getKey(),entry.getValue().getAddr()));
		// }
		// // floatingIPApi.addToServer(floatingIP.getIp(), server.getId());
		// }
		// test code end

		return vmResourceImpl;
	}

	/**
	 * send email to user after vm creating
	 */
	private void emailVmCreated(VMResource vm, VMCreateConf conf)
			throws OpenStackException {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userName", openStackUser.getUserName());
		params.put("region", getRegionDisplayName(vm.getRegion()));
		params.put("vmId", vm.getId());
		params.put("vmName", vm.getName());
		params.put("adminUserName", "root");
		params.put("password", conf.getAdminPass());
		params.put("createTime", format.format(new Date(vm.getCreated())));

		MailMessage mailMessage = new MailMessage("乐视云平台web-portal系统",
				openStackUser.getEmail(), "乐视云平台web-portal系统通知",
				"cloudvm/createVm.ftl", params);
		mailMessage.setHtml(true);
		defaultEmailSender.sendMessage(mailMessage);
	}

	/**
	 * send email to user after vm binding floating IP
	 */
	private void emailBindIP(VMResource vm, String floatingIP)
			throws OpenStackException {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userName", openStackUser.getUserName());
		params.put("region", getRegionDisplayName(vm.getRegion()));
		params.put("vmId", vm.getId());
		params.put("vmName", vm.getName());
		params.put("ip", floatingIP);
		params.put("port", 22);
		params.put("bindTime", format.format(new Date()));

		MailMessage mailMessage = new MailMessage("乐视云平台web-portal系统",
				openStackUser.getEmail(), "乐视云平台web-portal系统通知",
				"cloudvm/bindFloatingIp.ftl", params);
		mailMessage.setHtml(true);
		defaultEmailSender.sendMessage(mailMessage);
	}

	@Override
	public void unpublish(String region, VMResource vm)
			throws RegionNotFoundException, APINotAvailableException,
			TaskNotFinishedException, VMStatusException, OpenStackException {
		checkRegion(region);

		if (vm.getTaskState() != null) {
			throw new TaskNotFinishedException();
		}

		Optional<FloatingIPApi> floatingIPApiOptional = novaApi
				.getFloatingIPApi(region);
		if (!floatingIPApiOptional.isPresent()) {
			throw new APINotAvailableException(FloatingIPApi.class);
		}
		FloatingIPApi floatingIPApi = floatingIPApiOptional.get();

		for (FloatingIP floatingIP : floatingIPApi.list().toList()) {
			if (vm.getId().equals(floatingIP.getInstanceId())) {
				floatingIPApi.removeFromServer(floatingIP.getIp(), vm.getId());
				floatingIPApi.delete(floatingIP.getId());
			}
		}
	}

	private String bindFloatingIP(String region, String vmId)
			throws OpenStackException {
		Optional<FloatingIPApi> floatingIPApiOptional = novaApi
				.getFloatingIPApi(region);
		if (!floatingIPApiOptional.isPresent()) {
			throw new APINotAvailableException(FloatingIPApi.class);
		}
		FloatingIPApi floatingIPApi = floatingIPApiOptional.get();

		for (FloatingIP floatingIP : floatingIPApi.list().toList()) {
			if (vmId.equals(floatingIP.getInstanceId())) {
				throw new OpenStackException(
						"Virtual machine has been binding public IP, cannot repeat binding.",
						"虚拟机已经绑定公网IP，不能重复绑定。");
			}
		}

		FloatingIP floatingIP = floatingIPApi.allocateFromPool(openStackConf
				.getGlobalPublicNetworkId());
		floatingIPApi.addToServer(floatingIP.getIp(), vmId);
		return floatingIP.getIp();
	}

	@Override
	public void publish(String region, VMResource vm) throws OpenStackException {
		checkRegion(region);

		// Server server = ((VMResourceImpl) (vm)).server;

		if (vm.getTaskState() != null) {
			throw new TaskNotFinishedException();
		}
		// Status currentServerStatus = server.getStatus();
		// if (currentServerStatus != Server.Status.ACTIVE) {
		// throw new VMStatusException("The status of vm is not active.",
		// "虚拟机的状态不是活跃的，不能绑定公网IP。");
		// }

		// Collection<Address> addresses = server.getAddresses().get(
		// openStackConf.getUserPrivateNetworkName());
		// // for (Entry<String, Address> entry : ((VMResourceImpl) (vm)).server
		// // .getAddresses().entries()) {
		// //
		// System.out.println(MessageFormat.format("server address: {0} => {1}",
		// // entry.getKey(), entry.getValue().getAddr()));
		// // }
		// if (addresses.isEmpty()) {
		// throw new OpenStackException(
		// "Virtual machine is not assigned IP address of user private network.");
		// }
		// Address address = addresses.iterator().next();
		// String ip = address.getAddr();

		String floatingIP = bindFloatingIP(region, vm.getId());
		emailBindIP(vm, floatingIP);

		// NetworkManagerImpl networkManagerImpl = (NetworkManagerImpl)
		// networkManager;
		// NeutronApi neutronApi = networkManagerImpl.getNeutronApi();
		// neutronApi.getPortApi(region).create(CreatePort.createBuilder(""))
		// neutronApi
		// .getFloatingIPApi(region)
		// .get()
		// .create(CreateFloatingIP.createBuilder(
		// openStackConf.getGlobalPublicNetworkId()).portId("sss").build());
	}

	/**
	 * call before deleting vm
	 * 
	 * @param region
	 * @param vmId
	 * @throws APINotAvailableException
	 */
	private void removeAndDeleteFloatingIPOfVM(String region, VMResource vm)
			throws APINotAvailableException {
		final String vmId = vm.getId();
		Optional<FloatingIPApi> floatingIPApiOptional = novaApi
				.getFloatingIPApi(region);
		if (!floatingIPApiOptional.isPresent()) {
			throw new APINotAvailableException(FloatingIPApi.class);
		}
		FloatingIPApi floatingIPApi = floatingIPApiOptional.get();
		for (FloatingIP floatingIP : floatingIPApi.list().toList()) {
			if (vmId.equals(floatingIP.getInstanceId())) {
				floatingIPApi.removeFromServer(floatingIP.getIp(), vmId);
				floatingIPApi.delete(floatingIP.getId());
			}
		}
	}

	@Deprecated
	@Override
	public void delete(String region, VMResource vm)
			throws RegionNotFoundException, VMDeleteException,
			APINotAvailableException {
		checkRegion(region);

		ServerApi serverApi = novaApi.getServerApi(region);
		removeAndDeleteFloatingIPOfVM(region, vm);
		boolean isSuccess = serverApi.delete(vm.getId());
		if (!isSuccess) {
			throw new VMDeleteException(vm.getId());
		}
	}

	@Deprecated
	@Override
	public void start(String region, VMResource vm)
			throws RegionNotFoundException, VMStatusException {
		checkRegion(region);

		if (((VMResourceImpl) vm).server.getStatus() != Server.Status.SHUTOFF) {
			throw new VMStatusException("The status of vm is not shut off.",
					"虚拟机的状态不是关闭的。");
		}

		ServerApi serverApi = novaApi.getServerApi(region);
		serverApi.start(vm.getId());
	}

	@Deprecated
	@Override
	public void stop(String region, VMResource vm)
			throws RegionNotFoundException {
		checkRegion(region);

		ServerApi serverApi = novaApi.getServerApi(region);
		serverApi.stop(vm.getId());
	}

	@Override
	public List<FlavorResource> listFlavorResources(String region)
			throws RegionNotFoundException {
		checkRegion(region);

		FlavorApi flavorApi = novaApi.getFlavorApi(region);
		List<Flavor> resources = flavorApi.listInDetail().concat().toList();
		List<FlavorResource> flavorResources = new ArrayList<FlavorResource>(
				resources.size());
		for (Flavor resource : resources) {
			flavorResources.add(new FlavorResourceImpl(region, resource));
		}
		return flavorResources;
	}

	@Override
	public FlavorResource getFlavorResource(String region, String id)
			throws RegionNotFoundException, ResourceNotFoundException {
		checkRegion(region);

		FlavorApi flavorApi = novaApi.getFlavorApi(region);
		Flavor flavor = flavorApi.get(id);
		if (flavor != null) {
			return new FlavorResourceImpl(region, flavor);
		} else {
			throw new ResourceNotFoundException("Flavor", "规格", id);
		}
	}

	public void setImageManager(ImageManager imageManager) {
		this.imageManager = imageManager;
	}

	public void setNetworkManager(NetworkManager networkManager) {
		this.networkManager = networkManager;
	}

	@Override
	public void deleteSync(String region, VMResource vm)
			throws VMDeleteException, RegionNotFoundException,
			APINotAvailableException, TaskNotFinishedException,
			PollingInterruptedException {
		checkRegion(region);

		if (vm.getTaskState() != null) {
			throw new TaskNotFinishedException();
		}

		removeAndDeleteFloatingIPOfVM(region, vm);
		ServerApi serverApi = novaApi.getServerApi(region);
		boolean isSuccess = serverApi.delete(vm.getId());
		if (!isSuccess) {
			throw new VMDeleteException(vm.getId());
		}

		try {
			Server server = null;
			while (true) {
				server = serverApi.get(vm.getId());
				if (server == null || server.getStatus() == Status.ERROR) {
					break;
				}
				Thread.sleep(1000);
			}
		} catch (InterruptedException e) {
			throw new PollingInterruptedException(e);
		}
	}

	@Override
	public void startSync(String region, VMResource vm)
			throws RegionNotFoundException, TaskNotFinishedException,
			VMStatusException, PollingInterruptedException {
		checkRegion(region);

		if (vm.getTaskState() != null) {
			throw new TaskNotFinishedException();
		}
		if (((VMResourceImpl) vm).server.getStatus() != Server.Status.SHUTOFF) {
			throw new VMStatusException("The status of vm is not shut off.",
					"虚拟机的状态不是关闭的，不能启动。");
		}

		ServerApi serverApi = novaApi.getServerApi(region);
		serverApi.start(vm.getId());

		try {
			Server server = null;
			while (true) {
				server = serverApi.get(vm.getId());
				if (server == null
						|| Server.Status.ACTIVE == server.getStatus()
						|| server.getStatus() == Status.ERROR) {
					break;
				}
				Thread.sleep(1000);
			}
		} catch (InterruptedException e) {
			throw new PollingInterruptedException(e);
		}
	}

	@Override
	public void stopSync(String region, VMResource vm)
			throws PollingInterruptedException, RegionNotFoundException,
			TaskNotFinishedException, VMStatusException {
		checkRegion(region);

		if (vm.getTaskState() != null) {
			throw new TaskNotFinishedException();
		}
		Status currentServerStatus = ((VMResourceImpl) vm).server.getStatus();
		if (currentServerStatus != Server.Status.ACTIVE
				&& currentServerStatus != Server.Status.ERROR) {
			throw new VMStatusException("The status of vm is not stoppable.",
					"虚拟机的状态不是可关闭的，不能关闭。");
		}

		ServerApi serverApi = novaApi.getServerApi(region);
		serverApi.stop(vm.getId());

		try {
			Server server = null;
			while (true) {
				server = serverApi.get(vm.getId());
				if (server == null
						|| Server.Status.SHUTOFF == server.getStatus()
						|| server.getStatus() == Status.ERROR) {
					break;
				}
				Thread.sleep(1000);
			}
		} catch (InterruptedException e) {
			throw new PollingInterruptedException(e);
		}
	}

	@Override
	public int totalNumber() {
		int total = 0;
		Set<String> regions = getRegions();
		for (String region : regions) {
			ServerApi serverApi = novaApi.getServerApi(region);
			total += serverApi.list().concat().toList().size();
		}
		return total;
	}

	public List<FloatingIP> listFloatingIPs(String region)
			throws RegionNotFoundException, APINotAvailableException {
		checkRegion(region);

		Optional<FloatingIPApi> floatingIPApiOptional = novaApi
				.getFloatingIPApi(region);
		if (!floatingIPApiOptional.isPresent()) {
			throw new APINotAvailableException(FloatingIPApi.class);
		}
		FloatingIPApi floatingIPApi = floatingIPApiOptional.get();
		return floatingIPApi.list().toList();
	}

	@Override
	public Map<Integer, Map<Integer, Map<Integer, FlavorResource>>> groupFlavorResources(
			String region) throws OpenStackException {
		checkRegion(region);

		FlavorApi flavorApi = novaApi.getFlavorApi(region);
		List<Flavor> resources = flavorApi.listInDetail().concat().toList();

		Map<Integer, Map<Integer, Map<Integer, FlavorResource>>> flavorResources = new HashMap<Integer, Map<Integer, Map<Integer, FlavorResource>>>();
		for (Flavor resource : resources) {
			FlavorResource flavorResource = new FlavorResourceImpl(region,
					resource);

			Map<Integer, Map<Integer, FlavorResource>> vcpusFlavorResources = flavorResources
					.get(flavorResource.getVcpus());
			if (vcpusFlavorResources == null) {
				vcpusFlavorResources = new HashMap<Integer, Map<Integer, FlavorResource>>();
				flavorResources.put(flavorResource.getVcpus(),
						vcpusFlavorResources);
			}

			Map<Integer, FlavorResource> vcpusRamFlavorResources = vcpusFlavorResources
					.get(flavorResource.getRam());
			if (vcpusRamFlavorResources == null) {
				vcpusRamFlavorResources = new HashMap<Integer, FlavorResource>();
				vcpusFlavorResources.put(flavorResource.getRam(),
						vcpusRamFlavorResources);
			}

			if (vcpusRamFlavorResources.get(flavorResource.getDisk()) != null) {
				throw new OpenStackException("There are repeated flavors.",
						"存在重复的规格");
			} else {
				vcpusRamFlavorResources.put(flavorResource.getDisk(),
						flavorResource);
			}
		}

		return flavorResources;
	}

}
