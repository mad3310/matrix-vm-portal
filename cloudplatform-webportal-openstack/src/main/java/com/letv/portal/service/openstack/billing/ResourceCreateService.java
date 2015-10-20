package com.letv.portal.service.openstack.billing;

import com.letv.common.exception.MatrixException;
import com.letv.portal.service.openstack.billing.listeners.*;
import com.letv.portal.service.openstack.resource.FlavorResource;

/**
 * Created by zhouxianguang on 2015/9/21.
 */
public interface ResourceCreateService {
    void createVm(long userId, String reqParaJson, VmCreateListener listener, Object listenerUserData) throws MatrixException;

    CheckResult checkVmCreatePara(long userId, String reqParaJson);

    FlavorResource getFlavor(long userId, String region, String flavorId) throws MatrixException;

    void createVolume(long userId, String reqParaJson, VolumeCreateListener listener, Object listenerUserData) throws MatrixException;

    CheckResult checkVolumeCreatePara(long userId, String reqParaJson);

    void createFloatingIp(long userId, String reqParaJson, FloatingIpCreateListener listener, Object listenerUserData) throws MatrixException;

    CheckResult checkFloatingIpCreatePara(long userId, String reqParaJson);

    void createRouter(long userId, String reqParaJson, RouterCreateListener listener, Object listenerUserData) throws MatrixException;

    CheckResult checkRouterCreatePara(long userId, String reqParaJson);

    void createVmSnapshot(long userId, String reqParaJson, VmSnapshotCreateListener listener, Object listenerUserData) throws MatrixException;

    CheckResult checkVmSnapshotCreatePara(long userId, String reqParaJson);
}
