package com.letv.portal.service.openstack.impl;

import java.text.MessageFormat;

import javax.annotation.PostConstruct;

import com.google.common.base.Optional;
import com.letv.portal.service.IUserService;
import com.letv.portal.service.cloudvm.*;
import com.letv.portal.service.openstack.billing.event.service.EventPublishService;
import com.letv.portal.service.openstack.cronjobs.ImageSyncService;
import com.letv.portal.service.openstack.cronjobs.VmSyncService;
import com.letv.portal.service.openstack.cronjobs.VolumeSyncService;
import com.letv.portal.service.openstack.exception.APINotAvailableException;
import com.letv.portal.service.openstack.internal.UserExists;
import com.letv.portal.service.openstack.internal.UserRegister;
import com.letv.portal.service.openstack.jclouds.service.ApiService;
import com.letv.portal.service.openstack.jclouds.service.impl.ApiServiceImpl;
import com.letv.portal.service.openstack.local.service.LocalCommonQuotaSerivce;
import com.letv.portal.service.openstack.local.service.LocalImageService;
import com.letv.portal.service.openstack.local.service.LocalNetworkService;
import com.letv.portal.service.openstack.local.service.LocalRcCountService;
import com.letv.portal.service.openstack.local.service.LocalVolumeService;
import com.letv.portal.service.openstack.util.CollectionUtil;
import com.letv.portal.service.openstack.util.constants.Constants;
import com.letv.portal.service.openstack.util.ExceptionUtil;
import com.letv.portal.service.openstack.util.ThreadUtil;
import com.letv.portal.service.openstack.util.function.Function;
import com.letv.portal.service.openstack.util.function.Function1;

import org.jclouds.ContextBuilder;
import org.jclouds.openstack.neutron.v2.NeutronApi;
import org.jclouds.openstack.neutron.v2.domain.*;
import org.jclouds.openstack.neutron.v2.extensions.SecurityGroupApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.SchedulingTaskExecutor;
import org.springframework.stereotype.Service;

import com.letv.common.email.ITemplateMessageSender;
import com.letv.common.session.SessionServiceImpl;
import com.letv.portal.service.openstack.OpenStackService;
import com.letv.portal.service.openstack.OpenStackSession;
import com.letv.portal.service.openstack.erroremail.ErrorEmailService;
import com.letv.portal.service.openstack.exception.OpenStackException;
import com.letv.portal.service.openstack.password.PasswordService;

/**
 * Created by zhouxianguang on 2015/6/8.
 */
@Service("openStackService")
public class OpenStackServiceImpl implements OpenStackService {

	@Value("${openstack.keystone.host}")
	private String keystoneHost;

	@Value("${openstack.keystone.version}")
	private String keystoneVersion;

	@Value("${openstack.keystone.public.port}")
	private String publicPort;

	@Value("${openstack.keystone.admin.port}")
	private String adminPort;

	@Value("${openstack.keystone.protocol}")
	private String protocol;

	@Value("${openstack.keystone.user.register.token}")
	private String userRegisterToken;

//	@Value("${openstack.neutron.network.global.public.id}")
//	private String globalPublicNetworkId;
//
//	@Value("${openstack.neutron.network.global.shared.id}")
//	private String globalSharedNetworkId;

//	@Value("${openstack.neutron.network.user.private.name}")
//	private String userPrivateNetworkName;
//
//	@Value("${openstack.neutron.network.user.private.subnet.name}")
//	private String userPrivateNetworkSubnetName;
//
//	@Value("${openstack.neutron.network.user.private.subnet.cidr}")
//	private String userPrivateNetworkSubnetCidr;
//
//	@Value("${openstack.neutron.router.user.private.name}")
//	private String userPrivateRouterName;

	@Value("${openstack.neutron.router.gateway.bandWidth}")
	private String routerGatewayBandWidth;

	@Value("${openstack.keystone.basic.user.name}")
	private String basicUserName;

	private String publicEndpoint;

	private String adminEndpoint;

	private OpenStackConf openStackConf;

	@Autowired
	private PasswordService passwordService;

	@Autowired
	private ICloudvmRegionService cloudvmRegionService;

	@Autowired
	private ITemplateMessageSender defaultEmailSender;

	@Autowired
	private SessionServiceImpl sessionService;

//	@Autowired
//	private ICloudvmVmCountService cloudvmVmCountService;

	@Autowired
	private ICloudvmFlavorService cloudvmFlavorService;

	@Autowired
	private ICloudvmServerService cloudvmServerService;

	@Autowired
	private SchedulingTaskExecutor threadPoolTaskExecutor;

	@Autowired
	private ErrorEmailService errorEmailService;

	@Autowired
	private VmSyncService vmSyncService;

	@Autowired
	private ApiService apiService;

	@Autowired
	private IUserService userService;

	@Autowired
	private LocalVolumeService localVolumeService;

	@Autowired
	private VolumeSyncService volumeSyncService;

	@Autowired
	private ICloudvmVolumeService cloudvmVolumeService;

	@Autowired
	private LocalImageService localImageService;

	@Autowired
	private ImageSyncService imageSyncService;

	@Autowired
	private LocalRcCountService localRcCountService;

	@Autowired
	private EventPublishService eventPublishService;

	@Autowired
	private LocalCommonQuotaSerivce localCommonQuotaSerivce;
	
	@Autowired
	private LocalNetworkService localNetworkService;

	private OpenStackServiceGroup openStackServiceGroup;

	private static OpenStackServiceImpl INSTANCE;

	public OpenStackServiceImpl() {
		INSTANCE=this;
	}

	@PostConstruct
	public void open() {
		publicEndpoint = MessageFormat.format("{0}://{1}:{2}/v{3}/", protocol,
				keystoneHost, publicPort, keystoneVersion);
		adminEndpoint = MessageFormat.format("{0}://{1}:{2}/v{3}/", protocol,
				keystoneHost, adminPort, keystoneVersion);
		openStackConf = new OpenStackConf();
		openStackConf.setPublicEndpoint(publicEndpoint);
		openStackConf.setAdminEndpoint(adminEndpoint);
		openStackConf.setUserRegisterToken(userRegisterToken);
		openStackConf.setRouterGatewayBandWidth(Integer.parseInt(routerGatewayBandWidth));
		openStackConf.setBasicUserName(basicUserName);

		openStackServiceGroup = new OpenStackServiceGroup();
		openStackServiceGroup.setCloudvmRegionService(cloudvmRegionService);
		openStackServiceGroup.setDefaultEmailSender(defaultEmailSender);
		openStackServiceGroup.setPasswordService(passwordService);
		openStackServiceGroup.setSessionService(sessionService);
//		openStackServiceGroup.setCloudvmVmCountService(cloudvmVmCountService);
		openStackServiceGroup.setCloudvmFlavorService(cloudvmFlavorService);
		openStackServiceGroup.setCloudvmServerService(cloudvmServerService);
		openStackServiceGroup.setThreadPoolTaskExecutor(threadPoolTaskExecutor);
		openStackServiceGroup.setErrorEmailService(errorEmailService);
		openStackServiceGroup.setVmSyncService(vmSyncService);
		openStackServiceGroup.setApiService(apiService);
		openStackServiceGroup.setUserService(userService);
		openStackServiceGroup.setLocalVolumeService(localVolumeService);
		openStackServiceGroup.setVolumeSyncService(volumeSyncService);
		openStackServiceGroup.setCloudvmVolumeService(cloudvmVolumeService);
		openStackServiceGroup.setLocalImageService(localImageService);
		openStackServiceGroup.setImageSyncService(imageSyncService);
		openStackServiceGroup.setLocalRcCountService(localRcCountService);
		openStackServiceGroup.setEventPublishService(eventPublishService);
		openStackServiceGroup.setLocalCommonQuotaSerivce(localCommonQuotaSerivce);
		openStackServiceGroup.setLocalNetworkService(localNetworkService);
	}

	@Override
	public OpenStackSession createSession(long userVoUserId, String userId, String email,
			String userName) throws OpenStackException {
		OpenStackUser openStackUser = new OpenStackUser();
		openStackUser.setUserVoUserId(userVoUserId);
		openStackUser.setUserId(userId);
		openStackUser.setEmail(email);
		openStackUser.setUserName(userName);
//		openStackUser.setFirstLogin(false);
		openStackUser.setInternalUser(false);

		return new OpenStackSessionImpl(
				openStackConf, openStackUser);
	}

	@Override
	public boolean isUserExists(String email,String password) throws OpenStackException {
		UserExists userExists = new UserExists(openStackConf.getPublicEndpoint(), email,
				password);
		return userExists.run();
	}

	@Override
	public void registerUser(String email, String password) throws OpenStackException {
		UserRegister userRegister = new UserRegister(openStackConf.getAdminEndpoint(), email, password, email,
				openStackConf.getUserRegisterToken());
		userRegister.run();
	}

	@Override
	public void registerUserIfNotExists(String email, String password) throws OpenStackException {
		if (!isUserExists(email, password)) {
			registerUser(email, password);
		}
	}

	@Override
	public OpenStackUser registerAndInitUserIfNotExists(long userVoUserId, String userName, String email, String password) throws OpenStackException {
        OpenStackUser openStackUser = new OpenStackUser();
        openStackUser.setUserVoUserId(userVoUserId);
        openStackUser.setEmail(email);
        openStackUser.setPassword(password);
        openStackUser.setUserId(email);
        openStackUser.setUserName(userName);
        if (openStackUser.getEmail().endsWith("@letv.com")) {
            openStackUser.setInternalUser(true);
        }

        UserExists userExists = new UserExists(openStackConf.getPublicEndpoint(), email,
                password);
        boolean isUserExists = userExists.run();
        if (isUserExists) {
            openStackUser.setTenantId(userExists.getTenantId());
        } else {
            UserRegister userRegister = new UserRegister(openStackConf.getAdminEndpoint(), openStackUser.getUserId(), openStackUser.getPassword(), openStackUser.getEmail(),
                    openStackConf.getUserRegisterToken());
            userRegister.run();
            userExists = new UserExists(openStackConf.getPublicEndpoint(), email,
                    password);
            userExists.run();
            openStackUser.setTenantId(userExists.getTenantId());

            initResourcesForUser(openStackUser);
        }
        return openStackUser;
    }

    private void initResourcesForUser(OpenStackUser openStackUser) throws OpenStackException {
        try {
            final NeutronApi neutronApi = ContextBuilder
                    .newBuilder(ApiServiceImpl.apiToProvider.get(NeutronApi.class))
                    .endpoint(openStackConf.getPublicEndpoint())
                    .credentials(
                            openStackUser.getUserId() + ":"
                                    + openStackUser.getUserId(),
                            openStackUser.getPassword()).modules(Constants.jcloudsContextBuilderModules)
                    .buildApi(NeutronApi.class);
            try {
                ThreadUtil.concurrentFilter(CollectionUtil.toList(neutronApi.getConfiguredRegions()), new Function1<Void, String>() {
                    @Override
                    public Void apply(String region) throws Exception {
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

                        Rule pingRule = null, sshRule = null;
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
                            ThreadUtil.concurrentRunAndWait(new Function<Void>() {
                                @Override
                                public Void apply() {
                                    securityGroupApi.create(Rule.CreateRule
                                            .createBuilder(RuleDirection.INGRESS,
                                                    defaultSecurityGroupRef.getId())
                                            .ethertype(RuleEthertype.IPV4)
                                            .protocol(RuleProtocol.ICMP)
                                            .remoteIpPrefix("0.0.0.0/0").portRangeMax(255).portRangeMin(0).build());
									return null;
                                }
                            }, new Function<Void>() {
                                @Override
                                public Void apply() {
                                    securityGroupApi.create(Rule.CreateRule
                                            .createBuilder(RuleDirection.INGRESS,
                                                    defaultSecurityGroupRef.getId())
                                            .ethertype(RuleEthertype.IPV4)
                                            .protocol(RuleProtocol.TCP).portRangeMin(22)
                                            .portRangeMax(22).remoteIpPrefix("0.0.0.0/0").build());
									return null;
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
                        return null;
                    }
                });
            } finally {
                neutronApi.close();
            }
        } catch (Exception ex) {
            ExceptionUtil.throwException(ex);
        }
    }

//	@Override
//	public OpenStackSession createSessionForSync(long userVoUserId) throws OpenStackException {
//		UserVo userVo = userService.getUcUserById(userVoUserId);
//		String email = userVo.getEmail();
//		String userName = userVo.getUsername();
//		OpenStackSession openStackSession = this.createSession(userVoUserId, email, email, userName);
//		openStackSession.init(null);
//		return openStackSession;
//	}

	public static OpenStackServiceGroup getOpenStackServiceGroup() {
		return INSTANCE.openStackServiceGroup;
	}

    public static OpenStackConf getOpenStackConf() {
        return INSTANCE.openStackConf;
    }

    public static String createCredentialsIdentity(String email) {
        return email + ":" + email;
    }

	public static String createOpenStackUserId(String email){
		return email;
	}
}
