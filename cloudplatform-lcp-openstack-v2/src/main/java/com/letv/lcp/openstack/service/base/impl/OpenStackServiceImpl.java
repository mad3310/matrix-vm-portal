package com.letv.lcp.openstack.service.base.impl;

import java.text.MessageFormat;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.jclouds.ContextBuilder;
import org.jclouds.openstack.neutron.v2.NeutronApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.SchedulingTaskExecutor;
import org.springframework.stereotype.Service;

import com.letv.common.email.ITemplateMessageSender;
import com.letv.common.session.SessionServiceImpl;
import com.letv.lcp.openstack.constants.Constants;
import com.letv.lcp.openstack.exception.OpenStackException;
import com.letv.lcp.openstack.model.conf.OpenStackConf;
import com.letv.lcp.openstack.model.user.OpenStackTenant;
import com.letv.lcp.openstack.model.user.OpenStackUser;
import com.letv.lcp.openstack.service.base.IOpenStackService;
import com.letv.lcp.openstack.service.base.OpenStackServiceGroup;
import com.letv.lcp.openstack.service.cronjobs.IImageSyncService;
import com.letv.lcp.openstack.service.cronjobs.IVmSyncService;
import com.letv.lcp.openstack.service.cronjobs.IVolumeSyncService;
import com.letv.lcp.openstack.service.erroremail.IErrorEmailService;
import com.letv.lcp.openstack.service.event.IEventPublishService;
import com.letv.lcp.openstack.service.jclouds.IApiService;
import com.letv.lcp.openstack.service.jclouds.impl.ApiServiceImpl;
import com.letv.lcp.openstack.service.local.ILocalCommonQuotaSerivce;
import com.letv.lcp.openstack.service.local.ILocalImageService;
import com.letv.lcp.openstack.service.local.ILocalNetworkService;
import com.letv.lcp.openstack.service.local.ILocalRcCountService;
import com.letv.lcp.openstack.service.local.ILocalVolumeService;
import com.letv.lcp.openstack.service.password.IPasswordService;
import com.letv.lcp.openstack.service.resource.IResourceService;
import com.letv.lcp.openstack.service.session.IOpenStackSession;
import com.letv.lcp.openstack.service.session.impl.OpenStackSessionImpl;
import com.letv.lcp.openstack.util.ExceptionUtil;
import com.letv.lcp.openstack.util.internal.UserExists;
import com.letv.lcp.openstack.util.internal.UserRegister;
import com.letv.portal.model.cloudvm.CloudvmCluster;
import com.letv.portal.model.cloudvm.CloudvmRegion;
import com.letv.portal.model.common.UserModel;
import com.letv.portal.model.common.UserVo;
import com.letv.portal.service.cloudvm.ICloudvmClusterService;
import com.letv.portal.service.cloudvm.ICloudvmFlavorService;
import com.letv.portal.service.cloudvm.ICloudvmRegionService;
import com.letv.portal.service.cloudvm.ICloudvmServerService;
import com.letv.portal.service.cloudvm.ICloudvmVolumeService;
import com.letv.portal.service.common.IUserService;

/**
 * Created by zhouxianguang on 2015/6/8.
 */
@Service("openStackService")
public class OpenStackServiceImpl implements IOpenStackService {

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
	private IPasswordService passwordService;

	@Autowired
	private ICloudvmRegionService cloudvmRegionService;
	@Autowired
	private ICloudvmClusterService cloudvmClusterService;

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
	private IErrorEmailService errorEmailService;

	@Autowired
	private IVmSyncService vmSyncService;

	@Autowired
	private IApiService apiService;

	@Autowired
	private IUserService userService;

	@Autowired
	private ILocalVolumeService localVolumeService;

	@Autowired
	private IVolumeSyncService volumeSyncService;

	@Autowired
	private ICloudvmVolumeService cloudvmVolumeService;

	@Autowired
	private ILocalImageService localImageService;

	@Autowired
	private IImageSyncService imageSyncService;

	@Autowired
	private ILocalRcCountService localRcCountService;

	@Autowired
	private IEventPublishService eventPublishService;

	@Autowired
	private ILocalCommonQuotaSerivce localCommonQuotaSerivce;

	@Autowired
	private ILocalNetworkService localNetworkService;

    @Autowired
    private IResourceService resourceService;

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
	public IOpenStackSession createSession(long userVoUserId, String email,
			String userName) {
		OpenStackUser openStackUser = new OpenStackUser(new OpenStackTenant(userVoUserId, email));
		openStackUser.setUserName(userName);
		openStackUser.setUserEmail(email);
		openStackUser.setApplyUserId(userVoUserId);
//		openStackUser.setFirstLogin(false);
		openStackUser.setInternalUser(false);
		
		openStackConf.setPublicEndpoint(publicEndpoint);
		openStackConf.setAdminEndpoint(adminEndpoint);

		return new OpenStackSessionImpl(
				openStackConf, openStackUser);
	}
	
	@Override
	public IOpenStackSession createSession(long userId) {
		UserVo userVo = userService.getUcUserById(userId);
        String email = userVo.getEmail();
        String userName = userVo.getUsername();
        return this.createSession(userId, email, userName);
	}
	
	@Override
	public IOpenStackSession createSession(Long userId, Long tenantId) {
		CloudvmCluster vmCluster = cloudvmClusterService.selectById(tenantId);
		UserModel user = this.userService.getUserById(userId);
		return this.createSession(userId, user.getEmail(), user.getUserName(), vmCluster);
	}
	
	
	@Override
	public IOpenStackSession createAdminSession(Long userId, Long tenantId) {
		CloudvmCluster vmCluster = cloudvmClusterService.selectById(tenantId);
		UserModel user = this.userService.getUserById(userId);
		return createOpenStackSession(userId, user.getEmail(), user.getUserName(), vmCluster, true);
	}
	
	
	@Override
	public IOpenStackSession createSession(Long applyUserId, String applyUserEmail, String applyUserName, CloudvmCluster vmCluster) {
		return createOpenStackSession(applyUserId, applyUserEmail, applyUserName, vmCluster, false);
	}
	
	/**
	  * @Title: createOpenStackSession
	  * @Description: 根据申请人及创建用户创建openStackSession
	  * @param applyUserId 申请人id
	  * @param applyUserEmail 申请人邮箱
	  * @param applyUserName 申请人用户名
	  * @param vmCluster 创建用户信息（一个地域包含创建用户，管理员用户）
	  * @param isAdmin 是否是管理员用户
	  * @return IOpenStackSession   
	  * @throws 
	  * @author lisuxiao
	  * @date 2016年3月2日 上午11:36:49
	  */
	private IOpenStackSession createOpenStackSession(Long applyUserId, String applyUserEmail, String applyUserName, 
			CloudvmCluster vmCluster, boolean isAdmin) {
		OpenStackTenant openStackTenant = null;
		if(isAdmin) {
			openStackTenant = new OpenStackTenant(vmCluster.getId(),
					vmCluster.getAdminTenantName(), vmCluster.getAdminTenantPassword(), vmCluster.getAdminProjectName());
		} else {
			openStackTenant = new OpenStackTenant(vmCluster.getId(),
					vmCluster.getTenantName(), vmCluster.getTenantPassword(), vmCluster.getProjectName());
		}
		OpenStackUser openStackUser = new OpenStackUser(openStackTenant);
		openStackUser.setUserName(applyUserName);
		openStackUser.setUserEmail(applyUserEmail);
		openStackUser.setApplyUserId(applyUserId);
		
		if(vmCluster.getAdminEndpoint()!=null) {
			openStackConf.setAdminEndpoint(vmCluster.getAdminEndpoint());
		}
		if(vmCluster.getPublicEndpoint()!=null) {
			openStackConf.setPublicEndpoint(vmCluster.getPublicEndpoint());
		}
		return new OpenStackSessionImpl(openStackConf, openStackUser);
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
		UserExists userExists = new UserExists(openStackConf.getPublicEndpoint(),tenantName,password,tenantName);
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

        UserExists userExists = new UserExists(openStackConf.getPublicEndpoint(), tenantName, password, tenantName);
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

	@Override
	public OpenStackConf getOpenStackConfDefault() {
		OpenStackConf openStackConf = new OpenStackConf();
		openStackConf.setPublicEndpoint(publicEndpoint);
		openStackConf.setAdminEndpoint(adminEndpoint);
		openStackConf.setUserRegisterToken(userRegisterToken);
		openStackConf.setRouterGatewayBandWidth(Integer.parseInt(routerGatewayBandWidth));
		openStackConf.setBasicUserName(basicUserName);
		return openStackConf;
	}
}
