package com.letv.portal.service.openstack.impl;

import java.text.MessageFormat;

import javax.annotation.PostConstruct;

import com.letv.portal.service.common.IUserService;
import com.letv.portal.service.cloudvm.*;
import com.letv.portal.service.openstack.OpenStackTenant;
import com.letv.portal.service.openstack.billing.event.service.EventPublishService;
import com.letv.portal.service.openstack.cronjobs.ImageSyncService;
import com.letv.portal.service.openstack.cronjobs.VmSyncService;
import com.letv.portal.service.openstack.cronjobs.VolumeSyncService;
import com.letv.portal.service.openstack.internal.UserExists;
import com.letv.portal.service.openstack.internal.UserRegister;
import com.letv.portal.service.openstack.jclouds.service.ApiService;
import com.letv.portal.service.openstack.jclouds.service.impl.ApiServiceImpl;
import com.letv.portal.service.openstack.local.service.LocalCommonQuotaSerivce;
import com.letv.portal.service.openstack.local.service.LocalImageService;
import com.letv.portal.service.openstack.local.service.LocalNetworkService;
import com.letv.portal.service.openstack.local.service.LocalRcCountService;
import com.letv.portal.service.openstack.local.service.LocalVolumeService;
import com.letv.portal.service.openstack.resource.service.ResourceService;
import com.letv.portal.service.openstack.util.constants.Constants;
import com.letv.portal.service.openstack.util.ExceptionUtil;

import org.apache.commons.lang3.StringUtils;
import org.jclouds.ContextBuilder;
import org.jclouds.openstack.neutron.v2.NeutronApi;
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

    @Autowired
    private ResourceService resourceService;

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
        openStackServiceGroup.setResourceService(resourceService);
		openStackServiceGroup.setOpenStackService(this);
	}

	@Override
	public OpenStackSession createSession(long userVoUserId, String email,
			String userName) {
		OpenStackUser openStackUser = new OpenStackUser(new OpenStackTenant(userVoUserId, email));
		openStackUser.setUserName(userName);
//		openStackUser.setFirstLogin(false);
		openStackUser.setInternalUser(false);

		return new OpenStackSessionImpl(
				openStackConf, openStackUser);
	}

	@Override
	public String getOpenStackTenantNameFromMatrixUser(long userVoUserId, String email) {
        assert StringUtils.isNotBlank(email);
        return email + "_" + userVoUserId;
    }

//	public static String getOpenStackTenantName(long userVoUserId, String email){
//		return getOpenStackServiceGroup().getOpenStackService().getOpenStackTenantNameFromMatrixUser(userVoUserId, email);
//	}

	@Override
	public boolean isUserExists(String tenantName, String password) throws OpenStackException {
		UserExists userExists = new UserExists(openStackConf.getPublicEndpoint(),tenantName,password);
		return userExists.run();
	}

	@Override
	public void registerUser(String tenantName, String password, String email) throws OpenStackException {
		UserRegister userRegister = new UserRegister(openStackConf.getAdminEndpoint(), openStackConf.getUserRegisterToken(), tenantName, password, email);
		userRegister.run();
	}

	@Override
	public void registerUserIfNotExists(String tenantName, String password, String email) throws OpenStackException {
		if (!isUserExists(tenantName, password)) {
			registerUser(tenantName, password, email);
		}
	}

	@Override
	public void registerAndInitUserIfNotExists(String tenantName, String password, String email) throws OpenStackException {
//        OpenStackUser openStackUser = new OpenStackUser(tenant);
//        openStackUser.setUserName(userName);
//        if (openStackUser.tenant.email.endsWith("@letv.com")) {
//            openStackUser.setInternalUser(true);
//        }

        UserExists userExists = new UserExists(openStackConf.getPublicEndpoint(), tenantName, password);
        boolean isUserExists = userExists.run();
        if (!isUserExists) {
            UserRegister userRegister = new UserRegister(openStackConf.getAdminEndpoint(),
                    openStackConf.getUserRegisterToken(), tenantName, password, email);
            userRegister.run();

            initResourcesForUser(tenantName, password);
        }
//        return openStackUser;
    }

    private void initResourcesForUser(String tenantName, String password) throws OpenStackException {
        try {
            final NeutronApi neutronApi = ContextBuilder
                    .newBuilder(ApiServiceImpl.apiToProvider.get(NeutronApi.class))
                    .endpoint(openStackConf.getPublicEndpoint())
                    .credentials(
                            OpenStackServiceImpl.createCredentialsIdentity(tenantName),
                            password).modules(Constants.jcloudsContextBuilderModules)
                    .buildApi(NeutronApi.class);
            try {
                resourceService.createDefaultSecurityGroupAndRule(neutronApi);
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

	public static String createCredentialsIdentity(String tenantName) {
		return tenantName + ":" + tenantName;
	}

	public static OpenStackServiceGroup getOpenStackServiceGroup() {
		return INSTANCE.openStackServiceGroup;
	}

    public static OpenStackConf getOpenStackConf() {
        return INSTANCE.openStackConf;
    }
}
