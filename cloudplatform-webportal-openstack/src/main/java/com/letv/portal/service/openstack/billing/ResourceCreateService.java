package com.letv.portal.service.openstack.billing;

import com.letv.common.exception.MatrixException;
import com.letv.portal.service.openstack.billing.listeners.FloatingIpCreateListener;
import com.letv.portal.service.openstack.billing.listeners.RouterCreateListener;
import com.letv.portal.service.openstack.billing.listeners.VmCreateListener;
import com.letv.portal.service.openstack.billing.listeners.VolumeCreateListener;
import com.letv.portal.service.openstack.resource.FlavorResource;

/**
 * Created by zhouxianguang on 2015/9/21.
 */
public interface ResourceCreateService {
    void createVm(long userId, String reqParaJson, VmCreateListener listener, Object listenerUserData) throws MatrixException;

    FlavorResource getFlavor(long userId, String region, String flavorId) throws MatrixException;

    void createVolume(long userId, String reqParaJson, VolumeCreateListener listener, Object listenerUserData) throws MatrixException;

    void createFloatingIp(long userId, String reqParaJson, FloatingIpCreateListener listener, Object listenerUserData) throws MatrixException;

    void createRouter(long userId, String reqParaJson, RouterCreateListener listener, Object listenerUserData) throws MatrixException;
}
