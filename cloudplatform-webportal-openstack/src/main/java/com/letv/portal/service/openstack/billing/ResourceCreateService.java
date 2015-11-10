package com.letv.portal.service.openstack.billing;

import com.letv.common.exception.MatrixException;
import com.letv.portal.service.openstack.billing.listeners.*;
import com.letv.portal.service.openstack.resource.FlavorResource;
import com.letv.portal.service.openstack.resource.VolumeTypeResource;

/**
 * Created by zhouxianguang on 2015/9/21.
 */
public interface ResourceCreateService {
    void createVm(long userId, String reqParaJson, VmCreateListener vmCreateListener, Object listenerUserData) throws MatrixException;

    CheckResult checkVmCreatePara(String reqParaJson);

    @Deprecated
    FlavorResource getFlavor(long userId, String region, String flavorId) throws MatrixException;

    void createVolume(long userId, String reqParaJson, VolumeCreateListener listener, Object listenerUserData) throws MatrixException;

    CheckResult checkVolumeCreatePara(String reqParaJson);

    @Deprecated
    VolumeTypeResource getVolumeType(long userId, String region, String volumeTypeId) throws MatrixException;

    void createFloatingIp(long userId, String reqParaJson, FloatingIpCreateListener listener, Object listenerUserData) throws MatrixException;

    CheckResult checkFloatingIpCreatePara(String reqParaJson);

    void createRouter(long userId, String reqParaJson, RouterCreateListener listener, Object listenerUserData) throws MatrixException;

    CheckResult checkRouterCreatePara(String reqParaJson);

    void createVmSnapshot(long userId, String reqParaJson, VmSnapshotCreateListener listener, Object listenerUserData) throws MatrixException;

    CheckResult checkVmSnapshotCreatePara(String reqParaJson);
}
