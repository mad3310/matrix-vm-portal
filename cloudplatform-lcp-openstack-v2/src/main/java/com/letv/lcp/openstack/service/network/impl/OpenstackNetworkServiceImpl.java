package com.letv.lcp.openstack.service.network.impl;

import org.jclouds.openstack.neutron.v2.NeutronApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.letv.lcp.cloudvm.listener.FloatingIpCreateListener;
import com.letv.lcp.cloudvm.listener.RouterCreateListener;
import com.letv.lcp.cloudvm.model.network.FloatingIpCreateConf;
import com.letv.lcp.cloudvm.model.network.RouterCreateConf;
import com.letv.lcp.openstack.service.base.IOpenStackService;
import com.letv.lcp.openstack.service.erroremail.IErrorEmailService;
import com.letv.lcp.openstack.service.jclouds.IApiService;
import com.letv.lcp.openstack.service.manage.impl.NetworkManagerImpl;
import com.letv.lcp.openstack.service.network.IOpenstackNetworkService;
import com.letv.lcp.openstack.service.session.IOpenStackSession;
import com.letv.lcp.openstack.service.validation.IValidationService;
import com.letv.lcp.openstack.util.RandomUtil;

@Service
public class OpenstackNetworkServiceImpl implements IOpenstackNetworkService  {
	
	private static final Logger logger = LoggerFactory.getLogger(OpenstackNetworkServiceImpl.class);
	
	@Autowired
    private IValidationService validationService;
	@Autowired
    private IApiService apiService;
	@Autowired
    private IErrorEmailService errorEmailService;
	@Autowired
    private IOpenStackService openStackService;

	@Override
	public boolean delete(String id) {
		return false;
	}

	@Override
	public void createFloatingIp(long userId, FloatingIpCreateConf floatingIpCreateConf,
			FloatingIpCreateListener listener, Object listenerUserData) {
		try {
            validationService.validate(floatingIpCreateConf);
            final String sessionId = RandomUtil.generateRandomSessionId();
            final IOpenStackSession openStackSession = this.openStackService.createSession(userId);
            openStackSession.init(null);
            try {
                NeutronApi neutronApi = apiService.getNeutronApi(userId, sessionId);
                ((NetworkManagerImpl) openStackSession.getNetworkManager()).createFloatingIp(neutronApi, floatingIpCreateConf, listener, listenerUserData);
            } finally {
                apiService.clearCache(userId, sessionId);
            }
        } catch (Exception e) {
        	logger.error(e.getMessage(), e);
        	errorEmailService.sendExceptionEmail(e, "计费调用创建公网IP", userId, floatingIpCreateConf.toString());
        }
	}

	@Override
	public void createRouter(long userId, RouterCreateConf routerCreateConf,
			RouterCreateListener listener, Object listenerUserData) {
		try {
            validationService.validate(routerCreateConf);
            final String sessionId = RandomUtil.generateRandomSessionId();
            final IOpenStackSession openStackSession = this.openStackService.createSession(userId);
            openStackSession.init(null);
            try {
                NeutronApi neutronApi = apiService.getNeutronApi(userId, sessionId);
                ((NetworkManagerImpl) openStackSession.getNetworkManager()).createRouter(neutronApi, routerCreateConf, listener, listenerUserData);
            } finally {
                apiService.clearCache(userId, sessionId);
            }
        } catch (Exception e) {
        	logger.error(e.getMessage(), e);
        	errorEmailService.sendExceptionEmail(e, "计费调用创建路由", userId, routerCreateConf.toString());
        }
	}


}
