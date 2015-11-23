package com.letv.portal.service.openstack.billing.impl;

import com.letv.common.exception.MatrixException;
import com.letv.common.session.Session;
import com.letv.common.session.SessionServiceImpl;
import com.letv.portal.model.UserVo;
import com.letv.portal.service.IUserService;
import com.letv.portal.service.openstack.OpenStackService;
import com.letv.portal.service.openstack.OpenStackSession;
import com.letv.portal.service.openstack.billing.BillingResource;
import com.letv.portal.service.openstack.billing.CheckResult;
import com.letv.portal.service.openstack.billing.ResourceCreateService;
import com.letv.portal.service.openstack.billing.listeners.*;
import com.letv.portal.service.openstack.erroremail.ErrorEmailService;
import com.letv.portal.service.openstack.exception.OpenStackException;
import com.letv.portal.service.openstack.impl.OpenStackSessionImpl;
import com.letv.portal.service.openstack.jclouds.service.ApiService;
import com.letv.portal.service.openstack.resource.*;
import com.letv.portal.service.openstack.resource.manager.FloatingIpCreateConf;
import com.letv.portal.service.openstack.resource.manager.RouterCreateConf;
import com.letv.portal.service.openstack.resource.manager.VmSnapshotCreateConf;
import com.letv.portal.service.openstack.resource.manager.VolumeCreateConf;
import com.letv.portal.service.openstack.resource.manager.impl.NetworkManagerImpl;
import com.letv.portal.service.openstack.resource.manager.impl.VMManagerImpl;
import com.letv.portal.service.openstack.resource.manager.impl.VolumeManagerImpl;
import com.letv.portal.service.openstack.resource.manager.impl.create.vm.VMCreateConf2;
import com.letv.portal.service.openstack.util.ExceptionUtil;
import com.letv.portal.service.openstack.util.JsonUtil;
import com.letv.portal.service.openstack.util.RandomUtil;
import com.letv.portal.service.openstack.validation.service.ValidationService;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.type.TypeReference;
import org.jclouds.openstack.cinder.v1.CinderApi;
import org.jclouds.openstack.glance.v1_0.GlanceApi;
import org.jclouds.openstack.neutron.v2.NeutronApi;
import org.jclouds.openstack.nova.v2_0.NovaApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.HashSet;
import java.util.Set;

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

    @Autowired
    private SessionServiceImpl sessionService;

    @Autowired
    private ValidationService validationService;

    private OpenStackSession createOpenStackSession(long userId) throws OpenStackException {
        UserVo userVo = userService.getUcUserById(userId);
        String email = userVo.getEmail();
        String userName = userVo.getUsername();
        OpenStackSession openStackSession = openStackService.createSession(userId, email, email, userName);
        openStackSession.init(null);
        return openStackSession;
    }

    private OpenStackSession getOpenStackSession() throws OpenStackException {
        Session session = sessionService.getSession();
        OpenStackSessionImpl openStackSession = null;
        if (session != null) {
            openStackSession = (OpenStackSessionImpl) session.getOpenStackSession();
            openStackSession.init(session);
        }

//        if (openStackSession != null) {
//            return openStackSession;
//        } else {
//            return createOpenStackSession(userId);
//        }

        return openStackSession;
    }

    private void processException(Exception e, String function, Long userId, String contextMessage) {
        logger.error(e.getMessage(), e);
        errorEmailService.sendExceptionEmail(e, function, userId, contextMessage);
//        Util.throwMatrixException(e);
    }

    @Async
    @Override
    public void createVm(long userId, String reqParaJson, VmCreateListener vmCreateListener, Object listenerUserData) throws MatrixException {
        try {
//            Session session = sessionService.getSession();
//            logger.info("ResourceCreateServiceImpl.createVm session = ", session);

            VMCreateConf2 vmCreateConf = JsonUtil.fromJson(reqParaJson, new TypeReference<VMCreateConf2>() {
            }, true);
            if (StringUtils.isEmpty(vmCreateConf.getSharedNetworkId())) {
                vmCreateConf.setBindFloatingIp(false);
            }
            validationService.validate(vmCreateConf);

            OpenStackSession openStackSession = createOpenStackSession(userId);

            openStackSession.getVMManager().createForBilling(userId, vmCreateConf, vmCreateListener, listenerUserData);

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
    public CheckResult checkVmCreatePara(String reqParaJson) {
        try {
            VMCreateConf2 vmCreateConf = JsonUtil.fromJson(reqParaJson, new TypeReference<VMCreateConf2>() {
            }, true);
            if (StringUtils.isEmpty(vmCreateConf.getSharedNetworkId())) {
                vmCreateConf.setBindFloatingIp(false);
            }
            validationService.validate(vmCreateConf);
            OpenStackSession openStackSession = getOpenStackSession();
            openStackSession.getVMManager().checkCreate2(vmCreateConf);
            return new CheckResult();
        } catch (Exception e) {
            return new CheckResult(ExceptionUtil.getUserMessage(e));
        }
    }

    @Override
    public Set<Class<? extends BillingResource>> getResourceTypesOfVmCreatePara(String reqParaJson) throws MatrixException {
        Set<Class<? extends BillingResource>> resourceTypes = new HashSet<Class<? extends BillingResource>>();
        resourceTypes.add(VMResource.class);
        try {
            VMCreateConf2 vmCreateConf = JsonUtil.fromJson(reqParaJson, new TypeReference<VMCreateConf2>() {
            }, true);
            if (StringUtils.isEmpty(vmCreateConf.getSharedNetworkId())) {
                vmCreateConf.setBindFloatingIp(false);
            }
            if (vmCreateConf.getBindFloatingIp()) {
                resourceTypes.add(FloatingIpResource.class);
            }
            if (vmCreateConf.getVolumeSize() > 0) {
                resourceTypes.add(VolumeResource.class);
            }
            return resourceTypes;
        } catch (Exception ex) {
            ExceptionUtil.throwMatrixException(ex);
        }
        return resourceTypes;
    }

    @Override
    public FlavorResource getFlavor(long userId, String region, String flavorId) throws MatrixException {
        try {
            OpenStackSession openStackSession = getOpenStackSession();
            return openStackSession.getVMManager().getFlavorResource(region, flavorId);
        } catch (OpenStackException e) {
            throw e.matrixException();
        }
    }

    @Async
    @Override
    public void createVolume(long userId, String reqParaJson, VolumeCreateListener listener, Object listenerUserData) throws MatrixException {
        try {
            VolumeCreateConf volumeCreateConf = JsonUtil.fromJson(reqParaJson, new TypeReference<VolumeCreateConf>() {
            }, true);
            validationService.validate(volumeCreateConf);
            final String sessionId = RandomUtil.generateRandomSessionId();
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

    @Override
    public CheckResult checkVolumeCreatePara(String reqParaJson) {
        try {
            VolumeCreateConf volumeCreateConf = JsonUtil.fromJson(reqParaJson, new TypeReference<VolumeCreateConf>() {
            }, true);
            validationService.validate(volumeCreateConf);
            OpenStackSession openStackSession = getOpenStackSession();
            openStackSession.getVolumeManager().checkCreate(volumeCreateConf);
            return new CheckResult();
        } catch (Exception e) {
            return new CheckResult(ExceptionUtil.getUserMessage(e));
        }
    }

    @Override
    public VolumeTypeResource getVolumeType(long userId, String region, String volumeTypeId) throws MatrixException {
        try {
            OpenStackSession openStackSession = getOpenStackSession();
            return openStackSession.getVolumeManager().getVolumeTypeResource(region, volumeTypeId);
        } catch (OpenStackException e) {
            throw e.matrixException();
        }
    }

    @Async
    @Override
    public void createFloatingIp(long userId, String reqParaJson, FloatingIpCreateListener listener, Object listenerUserData) throws MatrixException {
        try {
            FloatingIpCreateConf floatingIpCreateConf = JsonUtil.fromJson(reqParaJson, new TypeReference<FloatingIpCreateConf>() {
            }, true);
            validationService.validate(floatingIpCreateConf);
            final String sessionId = RandomUtil.generateRandomSessionId();
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

    @Override
    public CheckResult checkFloatingIpCreatePara(String reqParaJson) {
        try {
            FloatingIpCreateConf floatingIpCreateConf = JsonUtil.fromJson(reqParaJson, new TypeReference<FloatingIpCreateConf>() {
            }, true);
            validationService.validate(floatingIpCreateConf);
            OpenStackSession openStackSession = getOpenStackSession();
            openStackSession.getNetworkManager().checkCreateFloatingIp(floatingIpCreateConf);
            return new CheckResult();
        } catch (Exception e) {
            return new CheckResult(ExceptionUtil.getUserMessage(e));
        }
    }

    @Async
    @Override
    public void createRouter(long userId, String reqParaJson, RouterCreateListener listener, Object listenerUserData) throws MatrixException {
        try {
            RouterCreateConf routerCreateConf = JsonUtil.fromJson(reqParaJson, new TypeReference<RouterCreateConf>() {
            }, true);
            validationService.validate(routerCreateConf);
            final String sessionId = RandomUtil.generateRandomSessionId();
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

    @Override
    public CheckResult checkRouterCreatePara(String reqParaJson) {
        try {
            RouterCreateConf routerCreateConf = JsonUtil.fromJson(reqParaJson, new TypeReference<RouterCreateConf>() {
            }, true);
            validationService.validate(routerCreateConf);
            OpenStackSession openStackSession = getOpenStackSession();
            openStackSession.getNetworkManager().checkCreateRouter(routerCreateConf);
            return new CheckResult();
        } catch (Exception e) {
            return new CheckResult(ExceptionUtil.getUserMessage(e));
        }
    }

    @Async
    @Override
    public void createVmSnapshot(long userId, String reqParaJson, VmSnapshotCreateListener listener, Object listenerUserData) throws MatrixException {
        try {
            VmSnapshotCreateConf vmSnapshotCreateConf = JsonUtil.fromJson(reqParaJson, new TypeReference<VmSnapshotCreateConf>() {
            }, true);
            validationService.validate(vmSnapshotCreateConf);
            final String sessionId = RandomUtil.generateRandomSessionId();
            final OpenStackSession openStackSession = createOpenStackSession(userId);
            try {
                NovaApi novaApi = apiService.getNovaApi(userId, sessionId);
                GlanceApi glanceApi = apiService.getGlanceApi(userId, sessionId);
                ((VMManagerImpl) openStackSession.getVMManager()).createImageFromVm(novaApi, glanceApi, vmSnapshotCreateConf, listener, listenerUserData);
            } finally {
                apiService.clearCache(userId, sessionId);
            }
        } catch (Exception e) {
            processException(e, "计费调用创建虚拟机快照", userId, reqParaJson);
        }
    }

    @Override
    public CheckResult checkVmSnapshotCreatePara(String reqParaJson) {
        try {
            VmSnapshotCreateConf vmSnapshotCreateConf = JsonUtil.fromJson(reqParaJson, new TypeReference<VmSnapshotCreateConf>() {
            }, true);
            validationService.validate(vmSnapshotCreateConf);
            OpenStackSession openStackSession = getOpenStackSession();
            openStackSession.getVMManager().checkCreateImageFromVm(vmSnapshotCreateConf);
            return new CheckResult();
        } catch (Exception e) {
            return new CheckResult(ExceptionUtil.getUserMessage(e));
        }
    }
}
