package com.letv.lcp.openstack.service.storage.impl;

import org.jclouds.openstack.cinder.v1.CinderApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.letv.lcp.cloudvm.listener.VolumeCreateListener;
import com.letv.lcp.cloudvm.model.storage.VolumeCreateConf;
import com.letv.lcp.openstack.service.base.IOpenStackService;
import com.letv.lcp.openstack.service.erroremail.IErrorEmailService;
import com.letv.lcp.openstack.service.jclouds.IApiService;
import com.letv.lcp.openstack.service.manage.impl.VolumeManagerImpl;
import com.letv.lcp.openstack.service.session.IOpenStackSession;
import com.letv.lcp.openstack.service.storage.IOpenstackStorageService;
import com.letv.lcp.openstack.service.validation.IValidationService;
import com.letv.lcp.openstack.util.RandomUtil;

@Service
public class OpenstackStorageServiceImpl implements IOpenstackStorageService  {
	
	private static final Logger logger = LoggerFactory.getLogger(OpenstackStorageServiceImpl.class);
	
	@Autowired
    private IValidationService validationService;
	@Autowired
    private IApiService apiService;
	@Autowired
    private IErrorEmailService errorEmailService;
	@Autowired
    private IOpenStackService openStackService;

	@Override
	public void create(Long userId, VolumeCreateConf storage, VolumeCreateListener listener, Object listenerUserData) {
		try {
            validationService.validate(storage);
            final String sessionId = RandomUtil.generateRandomSessionId();
            final IOpenStackSession openStackSession = this.openStackService.createSession(userId);
            openStackSession.init(null);
            try {
                CinderApi cinderApi = apiService.getCinderApi(userId, sessionId);
                ((VolumeManagerImpl) openStackSession.getVolumeManager()).create(cinderApi, storage, listener, listenerUserData);
            } finally {
                apiService.clearCache(userId, sessionId);
            }
        } catch (Exception e) {
        	logger.error(e.getMessage(), e);
        	errorEmailService.sendExceptionEmail(e, "计费调用创建云硬盘", userId, storage.toString());
        }
	};
	
	
	@Override
	public boolean delete(String id) {
		// TODO Auto-generated method stub
		return false;
	}


}
