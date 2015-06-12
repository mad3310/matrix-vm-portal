package com.letv.portal.service.openstack.impl;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.text.MessageFormat;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import com.letv.portal.service.openstack.internal.UserExists;
import org.jclouds.ContextBuilder;
import org.jclouds.logging.slf4j.config.SLF4JLoggingModule;
import org.jclouds.openstack.keystone.v2_0.KeystoneApi;
import org.jclouds.openstack.keystone.v2_0.domain.User;
import org.jclouds.openstack.keystone.v2_0.features.UserApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableSet;
import com.google.inject.Module;
import com.letv.portal.service.openstack.OpenStackService;
import com.letv.portal.service.openstack.OpenStackSession;
import com.letv.portal.service.openstack.exception.OpenStackException;
import com.letv.portal.service.openstack.internal.UserRegister;
import com.letv.portal.service.openstack.password.PasswordService;

/**
 * Created by zhouxianguang on 2015/6/8.
 */
@Service("openStackService")
public class OpenStackServiceImpl implements OpenStackService {

    @Value("openstack.keystone.host")
    private String keystoneHost;

    @Value("openstack.keystone.version")
    private String keystoneVersion;

    @Value("openstack.public.port")
    private String publicPort;

    @Value("openstack.admin.port")
    private String adminPort;

    @Value("openstack.protocol")
    private String protocol;

    @Value("openstack.user.register.token")
    private String userRegisterToken;

    private String publicEndpoint;

    private String adminEndpoint;

    @Autowired
    private PasswordService passwordService;

    @PostConstruct
    public void open() {
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
