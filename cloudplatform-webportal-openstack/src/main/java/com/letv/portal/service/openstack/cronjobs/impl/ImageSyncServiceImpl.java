package com.letv.portal.service.openstack.cronjobs.impl;

import com.letv.common.exception.MatrixException;
import com.letv.portal.service.cloudvm.ICloudvmImageLinkService;
import com.letv.portal.service.cloudvm.ICloudvmImagePropertyService;
import com.letv.portal.service.cloudvm.ICloudvmImageService;
import com.letv.portal.service.openstack.OpenStackService;
import com.letv.portal.service.openstack.cronjobs.ImageSyncService;
import com.letv.portal.service.openstack.cronjobs.impl.cache.SyncLocalApiCache;
import com.letv.portal.service.openstack.exception.OpenStackException;
import com.letv.portal.service.openstack.impl.OpenStackConf;
import com.letv.portal.service.openstack.impl.OpenStackServiceImpl;
import com.letv.portal.service.openstack.password.PasswordService;
import com.letv.portal.service.openstack.util.Contants;
import com.letv.portal.service.openstack.util.Util;
import org.jclouds.ContextBuilder;
import org.jclouds.openstack.glance.v1_0.GlanceApi;
import org.jclouds.openstack.glance.v1_0.domain.ImageDetails;
import org.jclouds.openstack.glance.v1_0.features.ImageApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by zhouxianguang on 2015/10/8.
 */
@Service
public class ImageSyncServiceImpl extends AbstractSyncServiceImpl implements ImageSyncService {

    @Autowired
    private ICloudvmImageService cloudvmImageService;

    @Autowired
    private ICloudvmImagePropertyService cloudvmImagePropertyService;

    @Autowired
    private ICloudvmImageLinkService cloudvmImageLinkService;

    @Autowired
    private OpenStackService openStackService;

    @Autowired
    private PasswordService passwordService;

    @Override
    public void sync(int recordsPerPage) throws MatrixException {
        try {
            OpenStackConf openStackConf = OpenStackServiceImpl.getOpenStackConf();
            String basicUserName = openStackConf.getBasicUserName();
            String basicUserPassword = passwordService.userIdToPassword(basicUserName);
            openStackService.registerUserIfNotExists(basicUserName, basicUserPassword);

            GlanceApi glanceApi = ContextBuilder
                    .newBuilder("openstack-glance")
                    .endpoint(openStackConf.getPublicEndpoint())
                    .credentials(
                            OpenStackServiceImpl.createCredentialsIdentity(basicUserName),
                            basicUserPassword).modules(Contants.jcloudsContextBuilderModules)
                    .buildApi(GlanceApi.class);
            try {
                for (String region : glanceApi.getConfiguredRegions()) {
                    ImageApi imageApi = glanceApi.getImageApi(region);
                    for (ImageDetails imageDetails : imageApi.listInDetail().concat().toList()) {

                    }
                }
            } finally {
                glanceApi.close();
            }
        } catch (Exception e) {
            Util.throwMatrixException(e);
        }
    }
}
