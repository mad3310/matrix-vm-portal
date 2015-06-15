package com.letv.portal.service.openstack.impl;

import com.letv.common.util.ConfigUtil;
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

    private String publicEndpoint;

    private String adminEndpoint;

    @Autowired
    private PasswordService passwordService;

    @PostConstruct
    public void open() {
        ConfigUtil.class.getName();
        publicEndpoint = MessageFormat.format("{0}://{1}:{2}/v{3}/", protocol, keystoneHost, publicPort, keystoneVersion);
        adminEndpoint = MessageFormat.format("{0}://{1}:{2}/v{3}/", protocol, keystoneHost, adminPort, keystoneVersion);
    }

    @Override
    public OpenStackSession createSession(String userId, String email)
            throws OpenStackException {
        try {
            final String password = passwordService.userIdToPassword(userId);
            if(!new UserExists(publicEndpoint,userId,password).run()){
                new UserRegister(adminEndpoint,userId,password,email,userRegisterToken).run();
                if(!new UserExists(publicEndpoint,userId,password).run()){
                    throw new OpenStackException(
                            "can not create openstack user:" + userId);
                }
            }
            return new OpenStackSessionImpl(publicEndpoint, userId, password);
        } catch (NoSuchAlgorithmException e) {
            throw new OpenStackException(e);
        }
    }

}
