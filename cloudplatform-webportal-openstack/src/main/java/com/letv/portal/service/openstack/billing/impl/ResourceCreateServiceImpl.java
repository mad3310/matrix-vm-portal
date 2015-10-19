package com.letv.portal.service.openstack.billing.impl;

import com.letv.common.exception.MatrixException;
import com.letv.portal.model.UserVo;
import com.letv.portal.service.IUserService;
import com.letv.portal.service.openstack.OpenStackService;
import com.letv.portal.service.openstack.OpenStackSession;
import com.letv.portal.service.openstack.billing.ResourceCreateService;
import com.letv.portal.service.openstack.billing.listeners.*;
import com.letv.portal.service.openstack.erroremail.ErrorEmailService;
import com.letv.portal.service.openstack.exception.OpenStackException;
import com.letv.portal.service.openstack.jclouds.service.ApiService;
import com.letv.portal.service.openstack.resource.FlavorResource;
import com.letv.portal.service.openstack.resource.manager.FloatingIpCreateConf;
import com.letv.portal.service.openstack.resource.manager.RouterCreateConf;
import com.letv.portal.service.openstack.resource.manager.VmSnapshotCreateConf;
import com.letv.portal.service.openstack.resource.manager.VolumeCreateConf;
import com.letv.portal.service.openstack.resource.manager.impl.NetworkManagerImpl;
import com.letv.portal.service.openstack.resource.manager.impl.VMManagerImpl;
import com.letv.portal.service.openstack.resource.manager.impl.VolumeManagerImpl;
import com.letv.portal.service.openstack.resource.manager.impl.create.vm.VMCreateConf2;
import com.letv.portal.service.openstack.util.Util;
import org.codehaus.jackson.type.TypeReference;
import org.jclouds.openstack.cinder.v1.CinderApi;
import org.jclouds.openstack.neutron.v2.NeutronApi;
import org.jclouds.openstack.nova.v2_0.NovaApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * Created by zhouxianguang on 2015/9/21.
 */
@Service
public class ResourceCreateServiceImpl implements ResourceCreateService {

    private static final Logger logger = LoggerFactory.getLogger(ResourceCreateServiceImpl.class);

//    @Autowired
//    private SessionServiceImpl sessionService;

    @Autowired
    private OpenStackService openStackService;

    @Autowired
    private IUserService userService;

    @Autowired
    private ErrorEmailService errorEmailService;

    @Autowired
    private ApiService apiService;

    private OpenStackSession createOpenStackSession(long userId) throws OpenStackException {
        UserVo userVo = userService.getUcUserById(userId);
        String email = userVo.getEmail();
        String userName = userVo.getUsername();
        OpenStackSession openStackSession = openStackService.createSession(email, email, userName);
        openStackSession.init(null);
        return openStackSession;
    }

    private void processException(Exception e, String function, Long userId, String contextMessage) {
        logger.error(e.getMessage(), e);
        errorEmailService.sendExceptionEmail(e, function, userId, contextMessage);
        Util.throwMatrixException(e);
    }

    @Async
    @Override
    public void createVm(long userId, String reqParaJson, VmCreateListener listener, Object listenerUserData) throws MatrixException {
        try {
//            Session session = sessionService.getSession();
//            logger.info("ResourceCreateServiceImpl.createVm session = ", session);

            VMCreateConf2 vmCreateConf = Util.fromJson(reqParaJson, new TypeReference<VMCreateConf2>() {
            }, true);

            OpenStackSession openStackSession = createOpenStackSession(userId);

            openStackSession.getVMManager().createForBilling(userId, vmCreateConf, listener, listenerUserData);

//            List<ResourceLocator> resourceLocators = new LinkedList<ResourceLocator>();
//            for (VmCreateContext vmCreateContext : multiVmCreateContext.getVmCreateContexts()) {
//                resourceLocators.add(new ResourceLocator(multiVmCreateContext.getVmCreateConf().getRegion(), vmCreateContext.getServer().getId()));
//            }
//            return resourceLocators;
        } catch (Exception e) {
            processException(e, "计费调用创建云主机", userId, reqParaJson);
        }
    }

    @Override
    public FlavorResource getFlavor(long userId, String region, String flavorId) throws MatrixException {
        try {
            OpenStackSession openStackSession = createOpenStackSession(userId);
            return openStackSession.getVMManager().getFlavorResource(region, flavorId);
        } catch (OpenStackException e) {
            throw e.matrixException();
        }
    }

    @Async
    @Override
    public void createVolume(long userId, String reqParaJson, VolumeCreateListener listener, Object listenerUserData) throws MatrixException {
        try {
            VolumeCreateConf volumeCreateConf = Util.fromJson(reqParaJson, new TypeReference<VolumeCreateConf>() {
            }, true);
            final String sessionId = Util.generateRandomSessionId();
            final OpenStackSession openStackSession = createOpenStackSession(userId);
            try {
                CinderApi cinderApi = apiService.getCinderApi(userId, sessionId);
                ((VolumeManagerImpl) openStackSession.getVolumeManager()).create(cinderApi, volumeCreateConf, listener, listenerUserData);
            } finally {
                apiService.clearCache(userId, sessionId);
            }
        } catch (Exception e) {
            processException(e, "计费调用创建云硬盘", userId, reqParaJson);
        }
    }

    @Async
    @Override
    public void createFloatingIp(long userId, String reqParaJson, FloatingIpCreateListener listener, Object listenerUserData) throws MatrixException {
        try {
            FloatingIpCreateConf floatingIpCreateConf = Util.fromJson(reqParaJson, new TypeReference<FloatingIpCreateConf>() {
            }, true);
            final String sessionId = Util.generateRandomSessionId();
            final OpenStackSession openStackSession = createOpenStackSession(userId);
            try {
                NeutronApi neutronApi = apiService.getNeutronApi(userId, sessionId);
                ((NetworkManagerImpl) openStackSession.getNetworkManager()).createFloatingIp(neutronApi, floatingIpCreateConf, listener, listenerUserData);
            } finally {
                apiService.clearCache(userId, sessionId);
            }
        } catch (Exception e) {
            processException(e, "计费调用创建公网IP", userId, reqParaJson);
        }
    }

    @Async
    @Override
    public void createRouter(long userId, String reqParaJson, RouterCreateListener listener, Object listenerUserData) throws MatrixException {
        try {
            RouterCreateConf routerCreateConf = Util.fromJson(reqParaJson, new TypeReference<RouterCreateConf>() {
            }, true);
            final String sessionId = Util.generateRandomSessionId();
            final OpenStackSession openStackSession = createOpenStackSession(userId);
            try {
                NeutronApi neutronApi = apiService.getNeutronApi(userId, sessionId);
                ((NetworkManagerImpl) openStackSession.getNetworkManager()).createRouter(neutronApi, routerCreateConf, listener, listenerUserData);
            } finally {
                apiService.clearCache(userId, sessionId);
            }
        } catch (Exception e) {
            processException(e, "计费调用创建路由", userId, reqParaJson);
        }
    }

    @Async
    @Override
    public void createVmSnapshot(long userId, String reqParaJson, VmSnapshotCreateListener listener, Object listenerUserData) throws MatrixException {
        try {
            VmSnapshotCreateConf vmSnapshotCreateConf = Util.fromJson(reqParaJson, new TypeReference<VmSnapshotCreateConf>() {
            }, true);
            final String sessionId = Util.generateRandomSessionId();
            final OpenStackSession openStackSession = createOpenStackSession(userId);
            try {
                NovaApi novaApi = apiService.getNovaApi(userId, sessionId);
                ((VMManagerImpl) openStackSession.getVMManager()).createImageFromVm(novaApi, vmSnapshotCreateConf, listener, listenerUserData);
            } finally {
                apiService.clearCache(userId, sessionId);
            }
        } catch (Exception e) {
            processException(e, "计费调用创建虚拟机快照", userId, reqParaJson);
        }
    }
}
