package com.letv.portal.service.openstack.impl;

import com.letv.common.email.ITemplateMessageSender;
import com.letv.common.util.ConfigUtil;
import com.letv.portal.service.cloudvm.ICloudvmRegionService;
import com.letv.portal.service.openstack.OpenStackService;
import com.letv.portal.service.openstack.OpenStackSession;
import com.letv.portal.service.openstack.exception.OpenStackException;
import com.letv.portal.service.openstack.internal.UserExists;
import com.letv.portal.service.openstack.internal.UserRegister;
import com.letv.portal.service.openstack.password.PasswordService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

import java.security.NoSuchAlgorithmException;
import java.text.MessageFormat;

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

	@Value("${openstack.neutron.network.global.public.id}")
	private String globalPublicNetworkId;

	@Value("${openstack.neutron.network.global.shared.id}")
	private String globalSharedNetworkId;

	@Value("${openstack.neutron.network.user.private.name}")
	private String userPrivateNetworkName;

	@Value("${openstack.neutron.network.user.private.subnet.name}")
	private String userPrivateNetworkSubnetName;

	@Value("${openstack.neutron.network.user.private.subnet.cidr}")
	private String userPrivateNetworkSubnetCidr;

	@Value("${openstack.neutron.router.user.private.name}")
	private String userPrivateRouterName;

	private String publicEndpoint;

	private String adminEndpoint;

	private OpenStackConf openStackConf;

	@Autowired
	private PasswordService passwordService;

	@Autowired
	private ICloudvmRegionService cloudvmRegionService;

	@Autowired
	private ITemplateMessageSender defaultEmailSender;

	private OpenStackServiceGroup openStackServiceGroup;

	@PostConstruct
	public void open() {
		ConfigUtil.class.getName();
		publicEndpoint = MessageFormat.format("{0}://{1}:{2}/v{3}/", protocol,
				keystoneHost, publicPort, keystoneVersion);
		adminEndpoint = MessageFormat.format("{0}://{1}:{2}/v{3}/", protocol,
				keystoneHost, adminPort, keystoneVersion);
		openStackConf = new OpenStackConf();
		openStackConf.setGlobalPublicNetworkId(globalPublicNetworkId);
		openStackConf.setGlobalSharedNetworkId(globalSharedNetworkId);
		openStackConf.setPublicEndpoint(publicEndpoint);
		openStackConf.setUserPrivateNetworkName(userPrivateNetworkName);
		openStackConf
				.setUserPrivateNetworkSubnetCidr(userPrivateNetworkSubnetCidr);
		openStackConf
				.setUserPrivateNetworkSubnetName(userPrivateNetworkSubnetName);
		openStackConf.setUserPrivateRouterName(userPrivateRouterName);

		openStackServiceGroup = new OpenStackServiceGroup();
		openStackServiceGroup.setCloudvmRegionService(cloudvmRegionService);
		openStackServiceGroup.setDefaultEmailSender(defaultEmailSender);
	}

	@Override
	public OpenStackSession createSession(String userId, String email,
			String userName) throws OpenStackException {
		try {
			OpenStackUser openStackUser = new OpenStackUser();
			openStackUser.setUserId(userId);
			openStackUser.setEmail(email);
			openStackUser.setUserName(userName);
			openStackUser.setFirstLogin(false);
			openStackUser.setInternalUser(false);

			final String password = passwordService.userIdToPassword(userId);
			openStackUser.setPassword(password);

			UserExists userExists = new UserExists(publicEndpoint, userId,
					password);
			if (!userExists.run()) {
				openStackUser.setFirstLogin(true);
				new UserRegister(adminEndpoint, userId, password, email,
						userRegisterToken).run();
				userExists = new UserExists(publicEndpoint, userId, password);
				if (!userExists.run()) {
					throw new OpenStackException(
							"can not create openstack user:" + userId,
							"不能创建用户：" + email);
				}
			}
			openStackUser.setTenantId(userExists.getTenantId());
			if (email.endsWith("@letv.com")) {
				openStackUser.setInternalUser(true);
			}
			return new OpenStackSessionImpl(openStackServiceGroup,
					openStackConf, openStackUser);
		} catch (NoSuchAlgorithmException e) {
			throw new OpenStackException("后台服务不可用", e);
		}
	}

}
