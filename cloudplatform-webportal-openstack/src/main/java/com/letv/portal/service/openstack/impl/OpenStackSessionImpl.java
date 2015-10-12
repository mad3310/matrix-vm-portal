package com.letv.portal.service.openstack.impl;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.text.MessageFormat;

import com.letv.common.exception.MatrixException;
import com.letv.portal.model.UserVo;
import com.letv.portal.service.openstack.jclouds.service.ApiService;
import com.letv.portal.service.openstack.util.Ref;
import com.letv.portal.service.openstack.util.Util;
import org.jclouds.openstack.neutron.v2.NeutronApi;
import org.jclouds.openstack.neutron.v2.domain.Network;
import org.jclouds.openstack.neutron.v2.domain.Rule;
import org.jclouds.openstack.neutron.v2.domain.RuleDirection;
import org.jclouds.openstack.neutron.v2.domain.RuleEthertype;
import org.jclouds.openstack.neutron.v2.domain.RuleProtocol;
import org.jclouds.openstack.neutron.v2.domain.SecurityGroup;
import org.jclouds.openstack.neutron.v2.extensions.SecurityGroupApi;
import org.jclouds.openstack.neutron.v2.features.NetworkApi;

import com.google.common.base.Optional;
import com.google.common.io.Closeables;
import com.letv.common.session.Session;
import com.letv.common.session.SessionServiceImpl;
import com.letv.portal.service.openstack.OpenStackSession;
import com.letv.portal.service.openstack.exception.APINotAvailableException;
import com.letv.portal.service.openstack.exception.OpenStackException;
import com.letv.portal.service.openstack.internal.UserExists;
import com.letv.portal.service.openstack.internal.UserRegister;
import com.letv.portal.service.openstack.resource.manager.ImageManager;
import com.letv.portal.service.openstack.resource.manager.NetworkManager;
import com.letv.portal.service.openstack.resource.manager.VMManager;
import com.letv.portal.service.openstack.resource.manager.VolumeManager;
import com.letv.portal.service.openstack.resource.manager.impl.ImageManagerImpl;
import com.letv.portal.service.openstack.resource.manager.impl.NetworkManagerImpl;
import com.letv.portal.service.openstack.resource.manager.impl.VMManagerImpl;
import com.letv.portal.service.openstack.resource.manager.impl.VolumeManagerImpl;
import org.springframework.web.context.request.RequestContextHolder;

/**
 * Created by zhouxianguang on 2015/6/8.
 */
public class OpenStackSessionImpl implements OpenStackSession {
	private static final long serialVersionUID = 1L;

//	@SuppressWarnings("unused")
//	private OpenStackServiceGroup openStackServiceGroup;
//	@SuppressWarnings("unused")
	private OpenStackConf openStackConf;
//	@SuppressWarnings("unused")
	private OpenStackUser openStackUser;

	private ImageManagerImpl imageManager;
	private NetworkManagerImpl networkManager;
	private VMManagerImpl vmManager;
	private VolumeManagerImpl volumeManager;
	// private IdentityManagerImpl identityManager;

	// private Object imageManagerLock;
	// private Object networkManagerLock;
	// private Object vmManagerLock;

	private boolean isClosed;

	private boolean isInit;

	public OpenStackSessionImpl(){
	}

	public OpenStackSessionImpl(
			OpenStackConf openStackConf, OpenStackUser openStackUser)
			throws OpenStackException {
//		this.openStackServiceGroup = openStackServiceGroup;
		this.openStackConf = openStackConf;
		this.openStackUser = openStackUser;

		// this.imageManagerLock = new Object();
		// this.networkManagerLock = new Object();
		// this.vmManagerLock = new Object();

		// identityManager = new IdentityManagerImpl(openStackServiceGroup,
		// openStackConf, openStackUser);
		imageManager = new ImageManagerImpl(
				openStackConf, openStackUser);
		networkManager = new NetworkManagerImpl(
				openStackConf, openStackUser);
		volumeManager = new VolumeManagerImpl(
				openStackConf, openStackUser);
		vmManager = new VMManagerImpl(openStackConf,
				openStackUser);

		vmManager.setImageManager(imageManager);
		vmManager.setNetworkManager(networkManager);
		vmManager.setVolumeManager(volumeManager);
		// vmManager.setIdentityManager(identityManager);

		networkManager.setVmManager(vmManager);

		// volumeManager.setIdentityManager(identityManager);
		volumeManager.setVmManager(vmManager);

		isClosed = false;

		isInit = false;
	}

	@Override
	public void init() throws OpenStackException {
		init(null);
	}

	@Override
	public void init(final Session session) throws OpenStackException {
		if (!isInit) {
			isInit = true;
			initUserWithOutOpenStack();
			initUser();
			if (session!=null) {
				Util.concurrentRunAndWait(new Runnable() {
											  @Override
											  public void run() {
												  final long userId = session.getUserId();
												  final String openStackUserId = openStackUser.getUserId();
												  final String openStackUserPassword = openStackUser.getPassword();
												  final String sessionId = RequestContextHolder.currentRequestAttributes().getSessionId();
												  OpenStackServiceImpl.getOpenStackServiceGroup().getApiService().loadAllApiForCurrentSession(userId,sessionId,openStackUserId,openStackUserPassword);
											  }
										  },
						new Runnable() {
							@Override
							public void run() {
								OpenStackSessionImpl.this.initResources();
							}
						}
				);

				session.setOpenStackSession(this);
				final SessionServiceImpl sessionService = OpenStackServiceImpl.getOpenStackServiceGroup().getSessionService();
				sessionService.setSession(session, "CloudVm.OpenStack");
			}
		}else{
			if(session != null){
				final long userId = session.getUserId();
				final String openStackUserId = openStackUser.getUserId();
				final String openStackUserPassword = openStackUser.getPassword();
				final String sessionId = RequestContextHolder.currentRequestAttributes().getSessionId();
				OpenStackServiceImpl.getOpenStackServiceGroup().getApiService().loadAllApiForCurrentSession(userId,sessionId,openStackUserId,openStackUserPassword);
			}
		}
	}

	private void initUserWithOutOpenStack()throws OpenStackException{
		try{
			final String password = OpenStackServiceImpl.getOpenStackServiceGroup().getPasswordService().userIdToPassword(openStackUser.getUserId());
			openStackUser.setPassword(password);

			if (openStackUser.getEmail().endsWith("@letv.com")) {
				openStackUser.setInternalUser(true);
			}

			if (!openStackUser.getEmail().contains("@") || !openStackUser.getUserId().contains("@")) {
				throw new OpenStackException(MessageFormat.format("invalid user email or id,email:'{0}',userId:'{1}'.", openStackUser.getEmail(), openStackUser.getUserId()), "用户信息不合法");
			}

			openStackUser.setPrivateNetworkName(openStackConf
					.getUserPrivateNetworkName());
		} catch (NoSuchAlgorithmException e) {
			throw new OpenStackException("后台服务不可用", e);
		}
	}

	private void initUser() throws OpenStackException {
		UserExists userExists = new UserExists(openStackConf.getPublicEndpoint(), openStackUser.getUserId(),
				openStackUser.getPassword());
		if (!userExists.run()) {
			new UserRegister(openStackConf.getAdminEndpoint(), openStackUser.getUserId(), openStackUser.getPassword(), openStackUser.getEmail(),
					openStackConf.getUserRegisterToken()).run();
			userExists = new UserExists(openStackConf.getPublicEndpoint(), openStackUser.getUserId(), openStackUser.getPassword());
			if (!userExists.run()) {
				throw new OpenStackException(
						"can not create openstack user:" + openStackUser.getUserId(),
						"不能创建用户：" + openStackUser.getEmail());
			}
		}
		openStackUser.setTenantId(userExists.getTenantId());
	}

	private void initResources() {
		try {
			// if (openStackUser.getFirstLogin()) {
			final NeutronApi neutronApi = networkManager.openApi();
			try {
				for (final String region : neutronApi.getConfiguredRegions()) {
					final NetworkApi networkApi = neutronApi.getNetworkApi(region);

					Util.concurrentRunAndWait(new Runnable() {
						@Override
						public void run() {
							Network publicNetwork = networkManager.getPublicNetwork(neutronApi, region);
							// for (Network network : networkApi.list().concat().toList()) {
							// if ("__public_network".equals(network.getName())) {
							// publicNetwork = network;
							// break;
							// }
							// }
							if (publicNetwork == null) {
								throw new OpenStackException(
										"can not find public network under region: " + region,
										"后台服务异常").matrixException();
							}
							openStackUser.setPublicNetworkName(publicNetwork.getName());
						}
					}, new Runnable() {
						@Override
						public void run() {
							Optional<SecurityGroupApi> securityGroupApiOptional = neutronApi
									.getSecurityGroupApi(region);
							if (!securityGroupApiOptional.isPresent()) {
								throw new APINotAvailableException(SecurityGroupApi.class).matrixException();
							}
							final SecurityGroupApi securityGroupApi = securityGroupApiOptional.get();

							SecurityGroup defaultSecurityGroup = null;
							for (SecurityGroup securityGroup : securityGroupApi
									.listSecurityGroups().concat().toList()) {
								if ("default".equals(securityGroup.getName())) {
									defaultSecurityGroup = securityGroup;
									break;
								}
							}
							if (defaultSecurityGroup == null) {
								defaultSecurityGroup = securityGroupApi
										.create(SecurityGroup.CreateSecurityGroup
												.createBuilder().name("default").build());
							}

							Rule pingRule=null , sshRule=null;
							for (Rule rule : defaultSecurityGroup.getRules()) {
								if (pingRule != null && sshRule != null) {
									break;
								}
								if (pingRule == null) {
									if (rule.getDirection() == RuleDirection.INGRESS
											&& rule.getEthertype() == RuleEthertype.IPV4
											&& rule.getProtocol() == RuleProtocol.ICMP
											&& "0.0.0.0/0".equals(rule.getRemoteIpPrefix())) {
										pingRule = rule;
									}
								}
								if (sshRule == null) {
									if (rule.getDirection() == RuleDirection.INGRESS
											&& rule.getEthertype() == RuleEthertype.IPV4
											&& rule.getProtocol() == RuleProtocol.TCP
											&& "0.0.0.0/0".equals(rule.getRemoteIpPrefix())
											&& rule.getPortRangeMin() == 22
											&& rule.getPortRangeMax() == 22) {
										sshRule = rule;
									}
								}
							}

							if (pingRule == null && sshRule == null) {
								final SecurityGroup defaultSecurityGroupRef = defaultSecurityGroup;
								Util.concurrentRunAndWait(new Runnable() {
									@Override
									public void run() {
										securityGroupApi.create(Rule.CreateRule
												.createBuilder(RuleDirection.INGRESS,
														defaultSecurityGroupRef.getId())
												.ethertype(RuleEthertype.IPV4)
												.protocol(RuleProtocol.ICMP)
												.remoteIpPrefix("0.0.0.0/0").portRangeMax(255).portRangeMin(0).build());
									}
								}, new Runnable() {
									@Override
									public void run() {
										securityGroupApi.create(Rule.CreateRule
												.createBuilder(RuleDirection.INGRESS,
														defaultSecurityGroupRef.getId())
												.ethertype(RuleEthertype.IPV4)
												.protocol(RuleProtocol.TCP).portRangeMin(22)
												.portRangeMax(22).remoteIpPrefix("0.0.0.0/0").build());
									}
								});
							} else {
								if (pingRule == null) {
									securityGroupApi.create(Rule.CreateRule
											.createBuilder(RuleDirection.INGRESS,
													defaultSecurityGroup.getId())
											.ethertype(RuleEthertype.IPV4)
											.protocol(RuleProtocol.ICMP)
											.remoteIpPrefix("0.0.0.0/0").portRangeMax(255).portRangeMin(0).build());
								}
								if (sshRule == null) {
									securityGroupApi.create(Rule.CreateRule
											.createBuilder(RuleDirection.INGRESS,
													defaultSecurityGroup.getId())
											.ethertype(RuleEthertype.IPV4)
											.protocol(RuleProtocol.TCP).portRangeMin(22)
											.portRangeMax(22).remoteIpPrefix("0.0.0.0/0").build());
								}
							}
						}
					}, new Runnable() {
						@Override
						public void run() {
							if (openStackUser.getInternalUser() && !openStackConf.getGlobalSharedNetworkId().isEmpty()) {
								openStackUser.setSharedNetworkName(networkApi.get(
										openStackConf.getGlobalSharedNetworkId()).getName());
							}
						}
					});
				}
			} finally {
				try {
					neutronApi.close();
				} catch (IOException e) {
					throw new OpenStackException("后台错误", e);
				}
			}
		} catch (OpenStackException e) {
			throw new MatrixException("后台错误", e);
		}
		// }
	}

	@Override
	public ImageManager getImageManager() throws OpenStackException {
		// if (imageManager == null) {
		// synchronized (this.imageManagerLock) {
		// if (imageManager == null) {
		// imageManager = new ImageManagerImpl(endpoint, userId,
		// password);
		// }
		// }
		// }
//		init();
		return imageManager;
	}

	@Override
	public NetworkManager getNetworkManager() throws OpenStackException {
		// if (networkManager == null) {
		// synchronized (this.networkManagerLock) {
		// if (networkManager == null) {
		// networkManager = new NetworkManagerImpl(endpoint, userId,
		// password);
		// }
		// }
		// }
//		init();
		return networkManager;
	}

	@Override
	public VMManager getVMManager() throws OpenStackException {
		// if (vmManager == null) {
		// synchronized (this.vmManagerLock) {
		// if (vmManager == null) {
		// vmManager = new VMManagerImpl(endpoint, userId, password);
		// }
		// }
		// }
//		init();
		return vmManager;
	}

	@Override
	public VolumeManager getVolumeManager() throws OpenStackException {
//		init();
		return volumeManager;
	}

	@Override
	public boolean isClosed() {
		return isClosed;
	}

	@Override
	public boolean isAuthority() {
		return vmManager.isAuthority();
	}

	@Override
	public void close() throws IOException {
		isClosed = true;
		// if (identityManager != null) {
		// Closeables.close(identityManager, true);
		// }
		if (imageManager != null) {
			Closeables.close(imageManager, true);
		}
		if (networkManager != null) {
			Closeables.close(networkManager, true);
		}
		if (volumeManager != null) {
			Closeables.close(volumeManager, true);
		}
		if (vmManager != null) {
			Closeables.close(vmManager, true);
		}
	}

	public OpenStackUser getOpenStackUser() {
		return openStackUser;
	}

	// private NovaApi novaApi;
	// private String tenantName;
	// private String userName;
	// private String password;
	//
	// public OpenStackSessionImpl(String userName) {
	// novaApi = ContextBuilder.newBuilder(provider)
	// .endpoint("http://xxx.xxx.xxx.xxx:5000/v2.0/")
	// .credentials(identity, credential)
	// .modules(modules)
	// .buildApi(NovaApi.class);
	// }
	//
	// void close() {
	//
	// }
	//
	// Set<String> listRegions() {
	//
	// }
	//
}
