package com.letv.portal.service.openstack.cronjobs.impl;

import com.google.common.base.Optional;
import com.letv.common.exception.MatrixException;
import com.letv.portal.model.cloudvm.CloudvmImage;
import com.letv.portal.model.cloudvm.CloudvmImageStatus;
import com.letv.portal.service.cloudvm.ICloudvmImageLinkService;
import com.letv.portal.service.cloudvm.ICloudvmImagePropertyService;
import com.letv.portal.service.cloudvm.ICloudvmImageService;
import com.letv.portal.service.openstack.OpenStackService;
import com.letv.portal.service.openstack.cronjobs.ImageSyncService;
import com.letv.portal.service.openstack.cronjobs.impl.cache.SyncLocalApiCache;
import com.letv.portal.service.openstack.impl.OpenStackConf;
import com.letv.portal.service.openstack.impl.OpenStackServiceImpl;
import com.letv.portal.service.openstack.local.service.LocalImageService;
import com.letv.portal.service.openstack.password.PasswordService;
import com.letv.portal.service.openstack.resource.manager.impl.Checker;
import com.letv.portal.service.openstack.util.Contants;
import com.letv.portal.service.openstack.util.ExceptionUtil;
import com.letv.portal.service.openstack.util.ThreadUtil;

import org.jclouds.ContextBuilder;
import org.jclouds.openstack.glance.v1_0.GlanceApi;
import org.jclouds.openstack.glance.v1_0.domain.ImageDetails;
import org.jclouds.openstack.glance.v1_0.features.ImageApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

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

    @Autowired
    private LocalImageService localImageService;

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
                    for (@SuppressWarnings("unused") ImageDetails imageDetails : imageApi.listInDetail().concat().toList()) {

                    }
                }
            } finally {
                glanceApi.close();
            }
        } catch (Exception e) {
            ExceptionUtil.throwMatrixException(e);
        }
    }

    public void syncStatus(final List<CloudvmImage> cloudvmImages, final Checker<ImageDetails> checker) {
        ThreadUtil.asyncExec(new Runnable() {
            @Override
            public void run() {
                SyncLocalApiCache apiCache = new SyncLocalApiCache();
                try {
                    List<CloudvmImage> unFinishedImages = new LinkedList<CloudvmImage>();
                    unFinishedImages.addAll(cloudvmImages);
                    while (!unFinishedImages.isEmpty()) {
                        for (CloudvmImage cloudvmImage : unFinishedImages
                                .toArray(new CloudvmImage[0])) {
                            ImageDetails image = apiCache.getApi(cloudvmImage.getTenantId(),
                                    GlanceApi.class)
                                    .getImageApi(
                                            cloudvmImage.getRegion()).get(cloudvmImage.getImageId());
                            if (checker.check(image)) {
                                unFinishedImages.remove(cloudvmImage);
                                if (image != null) {
                                    CloudvmImage lastedCloudvmImage = cloudvmImageService.selectById(cloudvmImage.getId());
                                    lastedCloudvmImage.setStatus(CloudvmImageStatus.valueOf(image.getStatus().name()));
                                    Optional<Long> sizeOptional = image.getSize();
                                    if (sizeOptional.isPresent()) {
                                        lastedCloudvmImage.setSize(sizeOptional.get());
                                    }
                                    cloudvmImageService.update(lastedCloudvmImage);
                                }
                            }
                        }
                        Thread.sleep(1000);
                    }
                } catch (Exception e) {
                    ExceptionUtil.logAndEmail(e);
                } finally {
                    apiCache.close();
                }
            }
        });
    }

    @Override
    public void syncStatus(CloudvmImage cloudvmImage, Checker<ImageDetails> checker) {
        List<CloudvmImage> cloudvmImages = new LinkedList<CloudvmImage>();
        cloudvmImages.add(cloudvmImage);
        syncStatus(cloudvmImages, checker);
    }

    @Override
    public void cleanServerIdAfterServerDeleted(long tenantId, String region, String serverId) {
        List<CloudvmImage> cloudvmImages = cloudvmImageService.selectVmSnapshotByServerId(tenantId, region, serverId);
        for (CloudvmImage cloudvmImage : cloudvmImages) {
            cloudvmImage.setServerName(null);
            cloudvmImage.setServerId(null);
            cloudvmImageService.update(cloudvmImage);
        }
    }
}
