package com.letv.lcp.openstack.service.billing.impl;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.type.TypeReference;
import org.jclouds.openstack.cinder.v1.CinderApi;
import org.jclouds.openstack.glance.v1_0.GlanceApi;
import org.jclouds.openstack.neutron.v2.NeutronApi;
import org.jclouds.openstack.nova.v2_0.NovaApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.letv.common.exception.MatrixException;
import com.letv.common.session.Session;
import com.letv.common.session.SessionServiceImpl;
import com.letv.lcp.cloudvm.listener.FloatingIpCreateListener;
import com.letv.lcp.cloudvm.listener.RouterCreateListener;
import com.letv.lcp.cloudvm.listener.VolumeCreateListener;
import com.letv.lcp.cloudvm.model.network.FloatingIpCreateConf;
import com.letv.lcp.cloudvm.model.network.RouterCreateConf;
import com.letv.lcp.cloudvm.model.storage.VolumeCreateConf;
import com.letv.lcp.openstack.exception.OpenStackException;
import com.letv.lcp.openstack.listener.VmCreateListener;
import com.letv.lcp.openstack.listener.VmSnapshotCreateListener;
import com.letv.lcp.openstack.model.billing.BillingResource;
import com.letv.lcp.openstack.model.billing.CheckResult;
import com.letv.lcp.openstack.model.compute.FlavorResource;
import com.letv.lcp.openstack.model.compute.VMResource;
import com.letv.lcp.openstack.model.conf.VmSnapshotCreateConf;
import com.letv.lcp.openstack.model.network.FloatingIpResource;
import com.letv.lcp.openstack.model.storage.VolumeResource;
import com.letv.lcp.openstack.model.storage.VolumeTypeResource;
import com.letv.lcp.openstack.service.base.IOpenStackService;
import com.letv.lcp.openstack.service.billing.IResourceCreateService;
import com.letv.lcp.openstack.service.erroremail.IErrorEmailService;
import com.letv.lcp.openstack.service.jclouds.IApiService;
import com.letv.lcp.openstack.service.manage.impl.NetworkManagerImpl;
import com.letv.lcp.openstack.service.manage.impl.VMManagerImpl;
import com.letv.lcp.openstack.service.manage.impl.VolumeManagerImpl;
import com.letv.lcp.openstack.service.session.IOpenStackSession;
import com.letv.lcp.openstack.service.session.impl.OpenStackSessionImpl;
import com.letv.lcp.openstack.service.task.createvm.VMCreateConf2;
import com.letv.lcp.openstack.service.validation.IValidationService;
import com.letv.lcp.openstack.util.ExceptionUtil;
import com.letv.lcp.openstack.util.JsonUtil;
import com.letv.lcp.openstack.util.RandomUtil;
import com.letv.portal.model.common.UserVo;
import com.letv.portal.service.common.IUserService;

/**
 * Created by zhouxianguang on 2015/9/21.
 */
@Service
public class ResourceCreateServiceImpl implements IResourceCreateService {

    private static final Logger logger = LoggerFactory.getLogger(ResourceCreateServiceImpl.class);

//    @Autowired
//    private SessionServiceImpl sessionService;

    @Autowired
    private IOpenStackService openStackService;

    @Autowired
    private IUserService userService;

    @Autowired
    private IErrorEmailService errorEmailService;

    @Autowired
    private IApiService apiService;

    @Autowired
    private SessionServiceImpl sessionService;

    @Autowired
    private IValidationService validationService;

    private IOpenStackSession createOpenStackSession(long userId) throws OpenStackException {
        UserVo userVo = userService.getUcUserById(userId);
        String email = userVo.getEmail();
        String userName = userVo.getUsername();
        IOpenStackSession openStackSession = openStackService.createSession(userId, email, userName);
        openStackSession.init(null);
        return openStackSession;
    }

    private IOpenStackSession getOpenStackSession() throws OpenStackException {
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

//    @Async
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

            IOpenStackSession openStackSession = createOpenStackSession(userId);

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
            IOpenStackSession openStackSession = getOpenStackSession();
            openStackSession.getVMManager().checkCreate2(vmCreateConf);
            return new CheckResult();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
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
            IOpenStackSession openStackSession = getOpenStackSession();
            return openStackSession.getVMManager().getFlavorResource(region, flavorId);
        } catch (OpenStackException e) {
            throw e.matrixException();
        }
    }

//    @Async
    @Override
    public void createVolume(long userId, String reqParaJson, VolumeCreateListener listener, Object listenerUserData) throws MatrixException {
        try {
            VolumeCreateConf volumeCreateConf = JsonUtil.fromJson(reqParaJson, new TypeReference<VolumeCreateConf>() {
            }, true);
            validationService.validate(volumeCreateConf);
            final String sessionId = RandomUtil.generateRandomSessionId();
            final IOpenStackSession openStackSession = createOpenStackSession(userId);
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
            IOpenStackSession openStackSession = getOpenStackSession();
            openStackSession.getVolumeManager().checkCreate(volumeCreateConf);
            return new CheckResult();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new CheckResult(ExceptionUtil.getUserMessage(e));
        }
    }

    @Override
    public VolumeTypeResource getVolumeType(long userId, String region, String volumeTypeId) throws MatrixException {
        try {
            IOpenStackSession openStackSession = getOpenStackSession();
            return openStackSession.getVolumeManager().getVolumeTypeResource(region, volumeTypeId);
        } catch (OpenStackException e) {
            throw e.matrixException();
        }
    }

//    @Async
    @Override
    public void createFloatingIp(long userId, String reqParaJson, FloatingIpCreateListener listener, Object listenerUserData) throws MatrixException {
        try {
            FloatingIpCreateConf floatingIpCreateConf = JsonUtil.fromJson(reqParaJson, new TypeReference<FloatingIpCreateConf>() {
            }, true);
            validationService.validate(floatingIpCreateConf);
            final String sessionId = RandomUtil.generateRandomSessionId();
            final IOpenStackSession openStackSession = createOpenStackSession(userId);
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
            IOpenStackSession openStackSession = getOpenStackSession();
            openStackSession.getNetworkManager().checkCreateFloatingIp(floatingIpCreateConf);
            return new CheckResult();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new CheckResult(ExceptionUtil.getUserMessage(e));
        }
    }

//    @Async
    @Override
    public void createRouter(long userId, String reqParaJson, RouterCreateListener listener, Object listenerUserData) throws MatrixException {
        try {
            RouterCreateConf routerCreateConf = JsonUtil.fromJson(reqParaJson, new TypeReference<RouterCreateConf>() {
            }, true);
            validationService.validate(routerCreateConf);
            final String sessionId = RandomUtil.generateRandomSessionId();
            final IOpenStackSession openStackSession = createOpenStackSession(userId);
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
            IOpenStackSession openStackSession = getOpenStackSession();
            openStackSession.getNetworkManager().checkCreateRouter(routerCreateConf);
            return new CheckResult();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new CheckResult(ExceptionUtil.getUserMessage(e));
        }
    }

//    @Async
    @Override
    public void createVmSnapshot(long userId, String reqParaJson, VmSnapshotCreateListener listener, Object listenerUserData) throws MatrixException {
        try {
            VmSnapshotCreateConf vmSnapshotCreateConf = JsonUtil.fromJson(reqParaJson, new TypeReference<VmSnapshotCreateConf>() {
            }, true);
            validationService.validate(vmSnapshotCreateConf);
            final String sessionId = RandomUtil.generateRandomSessionId();
            final IOpenStackSession openStackSession = createOpenStackSession(userId);
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
            IOpenStackSession openStackSession = getOpenStackSession();
            openStackSession.getVMManager().checkCreateImageFromVm(vmSnapshotCreateConf);
            return new CheckResult();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new CheckResult(ExceptionUtil.getUserMessage(e));
        }
    }
}
