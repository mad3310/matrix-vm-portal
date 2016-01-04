package com.letv.lcp.openstack.service.billing;

import java.util.Set;

import com.letv.common.exception.MatrixException;
import com.letv.lcp.cloudvm.listener.FloatingIpCreateListener;
import com.letv.lcp.cloudvm.listener.RouterCreateListener;
import com.letv.lcp.cloudvm.listener.VolumeCreateListener;
import com.letv.lcp.openstack.listener.VmCreateListener;
import com.letv.lcp.openstack.listener.VmSnapshotCreateListener;
import com.letv.lcp.openstack.model.billing.BillingResource;
import com.letv.lcp.openstack.model.billing.CheckResult;
import com.letv.lcp.openstack.model.compute.FlavorResource;
import com.letv.lcp.openstack.model.storage.VolumeTypeResource;

/**
 * Created by zhouxianguang on 2015/9/21.
 */
public interface IResourceCreateService {
    void createVm(long userId, String reqParaJson, VmCreateListener vmCreateListener, Object listenerUserData) throws MatrixException;

    CheckResult checkVmCreatePara(String reqParaJson);

    Set<Class<? extends BillingResource>> getResourceTypesOfVmCreatePara(String reqParaJson) throws MatrixException;

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
