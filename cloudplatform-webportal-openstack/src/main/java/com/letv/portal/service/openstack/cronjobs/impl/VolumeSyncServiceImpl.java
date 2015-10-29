package com.letv.portal.service.openstack.cronjobs.impl;

import com.letv.common.exception.MatrixException;
import com.letv.common.paging.impl.Page;
import com.letv.portal.model.cloudvm.CloudvmVolume;
import com.letv.portal.model.cloudvm.CloudvmVolumeStatus;
import com.letv.portal.service.cloudvm.ICloudvmVolumeService;
import com.letv.portal.service.openstack.cronjobs.VolumeSyncService;
import com.letv.portal.service.openstack.cronjobs.impl.cache.SyncLocalApiCache;
import com.letv.portal.service.openstack.erroremail.ErrorEmailService;
import com.letv.portal.service.openstack.erroremail.impl.ErrorMailMessageModel;
import com.letv.portal.service.openstack.exception.OpenStackException;
import com.letv.portal.service.openstack.exception.PollingInterruptedException;
import com.letv.portal.service.openstack.local.service.LocalVolumeService;
import com.letv.portal.service.openstack.resource.manager.RegionAndVmId;
import com.letv.portal.service.openstack.resource.manager.impl.Checker;
import com.letv.portal.service.openstack.util.Params;
import com.letv.portal.service.openstack.util.Util;
import org.apache.commons.lang3.StringUtils;
import org.jclouds.openstack.cinder.v1.CinderApi;
import org.jclouds.openstack.cinder.v1.domain.Volume;
import org.jclouds.openstack.nova.v2_0.NovaApi;
import org.jclouds.openstack.nova.v2_0.domain.Server;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by zhouxianguang on 2015/10/22.
 */
@Service
public class VolumeSyncServiceImpl extends AbstractSyncServiceImpl implements VolumeSyncService {

    @Autowired
    private ICloudvmVolumeService cloudvmVolumeService;

    @Autowired
    private LocalVolumeService localVolumeService;

    @Autowired
    private ErrorEmailService errorEmailService;

    @Override
    public void sync(int recordsPerPage) throws MatrixException {
        SyncLocalApiCache apiCache = new SyncLocalApiCache();
        try {
            Long minId = null;
            int currentPage = 1;
            List<CloudvmVolume> cloudvmVolumes = cloudvmVolumeService
                    .selectForSync(minId, new Page(currentPage, recordsPerPage));
            while (!cloudvmVolumes.isEmpty()) {
                for (final CloudvmVolume cloudvmVolume : cloudvmVolumes) {
                    CinderApi cinderApi = apiCache.getApi(cloudvmVolume.getTenantId(), CinderApi.class);
                    Volume volume = cinderApi.getVolumeApi(cloudvmVolume.getRegion()).get(cloudvmVolume.getVolumeId());
                    update(cloudvmVolume, volume);
                }
                minId = cloudvmVolumes.get(cloudvmVolumes.size() - 1).getId();
                currentPage++;
                cloudvmVolumes = cloudvmVolumeService.selectForSync(minId, new Page(currentPage, recordsPerPage));
            }
        } catch (OpenStackException e) {
            throw e.matrixException();
        } finally {
            apiCache.close();
        }
    }

    @Override
    public void syncStatus(final List<CloudvmVolume> cloudvmVolumes, final Checker<Volume>
            checker) {
        Util.concurrentRun(new Runnable() {
            @Override
            public void run() {
                SyncLocalApiCache apiCache = new SyncLocalApiCache();
                try {
                    List<CloudvmVolume> unFinishedVolumes = new LinkedList<CloudvmVolume>();
                    unFinishedVolumes.addAll(cloudvmVolumes);
                    while (!unFinishedVolumes.isEmpty()) {
                        for (CloudvmVolume cloudvmVolume : unFinishedVolumes
                                .toArray(new CloudvmVolume[0])) {
                            Volume volume = apiCache.getApi(cloudvmVolume.getTenantId(),
                                    CinderApi.class)
                                    .getVolumeApi(
                                            cloudvmVolume.getRegion()).get(cloudvmVolume.getVolumeId());
                            if(volume == null) {
                                unFinishedVolumes.remove(cloudvmVolume);
                            }else if (checker.check(volume)) {
                                unFinishedVolumes.remove(cloudvmVolume);
                                localVolumeService.update(cloudvmVolume.getTenantId(), cloudvmVolume
                                        .getTenantId(), cloudvmVolume.getRegion(), volume);
                            }
                        }
                        Thread.sleep(1000);
                    }
                } catch (Exception e) {
                    Util.logAndEmail(e);
                } finally {
                    apiCache.close();
                }
            }
        });
    }

    @Override
    public void syncStatus(CloudvmVolume cloudvmVolume, Checker<Volume>
            checker) {
        List<CloudvmVolume> cloudvmVolumes = new LinkedList<CloudvmVolume>();
        cloudvmVolumes.add(cloudvmVolume);
        syncStatus(cloudvmVolumes, checker);
    }

    private void update(CloudvmVolume cloudvmVolume, Volume volume) {
        if (volume == null) {
            if (cloudvmVolume.getStatus() != CloudvmVolumeStatus.NIL) {
                cloudvmVolume.setStatus(CloudvmVolumeStatus.NIL);
                cloudvmVolumeService.update(cloudvmVolume);
                errorEmailService.sendErrorEmail(
                        new ErrorMailMessageModel()
                                .requestUrl("功能：同步云硬盘")
                                .exceptionParams("volume.id=" + volume.getId())
                                .exceptionId("tenantId:" + cloudvmVolume.getTenantId())
                                .exceptionMessage("云硬盘状态为NIL")
                                .toMap());
            }
        } else {
            final String latestStatus = volume.getStatus().name();
            if (!StringUtils.equals(cloudvmVolume.getStatus().name(), latestStatus)) {
                if (cloudvmVolume.getStatus() != CloudvmVolumeStatus.WAITING_ATTACHING) {
                    cloudvmVolume.setStatus(CloudvmVolumeStatus.valueOf(latestStatus));
                    cloudvmVolumeService.update(cloudvmVolume);
                }
            }
        }
    }

    @Override
    public void syncStatusAfterServerDeleted(long tenantId, String region, String serverId) {
        List<CloudvmVolume> cloudvmVolumes = cloudvmVolumeService.selectByServerIdAndStatus(tenantId, region, serverId, null);
        List<CloudvmVolume> needSyncCloudvmVolumes = new LinkedList<CloudvmVolume>();
        for (CloudvmVolume cloudvmVolume : cloudvmVolumes) {
            if (cloudvmVolume.getStatus() != CloudvmVolumeStatus.NIL) {
                needSyncCloudvmVolumes.add(cloudvmVolume);
            }else{
                cloudvmVolume.setServerId(null);
                cloudvmVolumeService.update(cloudvmVolume);
            }
        }
        syncStatus(needSyncCloudvmVolumes, new Checker<Volume>() {
            @Override
            public boolean check(Volume volume) throws Exception {
                return true;
            }
        });
    }
}
