package com.letv.lcp.openstack.service.cronjobs.impl;

import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.jclouds.openstack.cinder.v1.CinderApi;
import org.jclouds.openstack.cinder.v1.domain.Volume;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.letv.common.exception.MatrixException;
import com.letv.common.paging.impl.Page;
import com.letv.lcp.openstack.exception.OpenStackException;
import com.letv.lcp.openstack.model.erroremail.ErrorMailMessageModel;
import com.letv.lcp.openstack.model.user.OpenStackUser;
import com.letv.lcp.openstack.service.cronjobs.IVolumeSyncService;
import com.letv.lcp.openstack.service.erroremail.IErrorEmailService;
import com.letv.lcp.openstack.service.local.ILocalVolumeService;
import com.letv.lcp.openstack.service.manage.check.Checker;
import com.letv.lcp.openstack.util.ExceptionUtil;
import com.letv.lcp.openstack.util.ThreadUtil;
import com.letv.lcp.openstack.util.cache.SyncLocalApiCache;
import com.letv.lcp.openstack.util.cache.UserApiCache;
import com.letv.lcp.openstack.util.function.Function0;
import com.letv.portal.model.cloudvm.CloudvmVolume;
import com.letv.portal.model.cloudvm.CloudvmVolumeStatus;
import com.letv.portal.service.cloudvm.ICloudvmVolumeService;

/**
 * Created by zhouxianguang on 2015/10/22.
 */
@Service
public class VolumeSyncServiceImpl extends AbstractSyncServiceImpl implements IVolumeSyncService {

    @Autowired
    private ICloudvmVolumeService cloudvmVolumeService;

    @Autowired
    private ILocalVolumeService localVolumeService;

    @Autowired
    private IErrorEmailService errorEmailService;

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
    public void syncVolumeCreated(final OpenStackUser openStackUser, final String region, final List<Volume> volumes
            , final Checker<Volume> checker) {
        if (volumes != null && !volumes.isEmpty()) {
            ThreadUtil.asyncExec(new Function0<Void>() {
                @Override
                public Void apply() throws Exception {
                    for (Volume volume : volumes) {
                        localVolumeService.create(openStackUser.getApplyUserId(), openStackUser.getApplyUserId(), region, volume);
                    }

                    UserApiCache apiCache = new UserApiCache(openStackUser.tenant);
                    try {
                        List<Volume> unFinishedVolumes = new LinkedList<Volume>();
                        unFinishedVolumes.addAll(volumes);
                        while (!unFinishedVolumes.isEmpty()) {
                            for (Volume volume : unFinishedVolumes
                                    .toArray(new Volume[0])) {
                                Volume latestVolume = apiCache.getApi(CinderApi.class)
                                        .getVolumeApi(
                                                region).get(volume.getId());
                                if (latestVolume == null) {
                                    unFinishedVolumes.remove(volume);
                                } else if (checker.check(latestVolume)) {
                                    unFinishedVolumes.remove(volume);
                                    localVolumeService.update(openStackUser.getApplyUserId(), openStackUser.getApplyUserId(), region, latestVolume);
                                }
                            }
                            Thread.sleep(1000);
                        }
                    } catch (Exception e) {
                        ExceptionUtil.logAndEmail(e);
                    } finally {
                        apiCache.close();
                    }
                    return null;
                }
            });
        }
    }

    @Override
    public void syncStatus(final List<CloudvmVolume> cloudvmVolumes, final Checker<Volume>
            checker) {
        if (cloudvmVolumes != null && !cloudvmVolumes.isEmpty()) {
            ThreadUtil.asyncExec(new Function0<Void>() {
                @Override
                public Void apply() {
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
                                if (volume == null) {
                                    unFinishedVolumes.remove(cloudvmVolume);
                                } else if (checker.check(volume)) {
                                    unFinishedVolumes.remove(cloudvmVolume);
                                    localVolumeService.update(cloudvmVolume.getTenantId(), cloudvmVolume
                                            .getTenantId(), cloudvmVolume.getRegion(), volume);
                                }
                            }
                            Thread.sleep(1000);
                        }
                    } catch (Exception e) {
                        ExceptionUtil.logAndEmail(e);
                    } finally {
                        apiCache.close();
                    }
                    return null;
                }
            });
        }
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
                                .exceptionParams("volume.id=" + cloudvmVolume.getVolumeId())
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
            if (!ArrayUtils.contains(CloudvmVolumeStatus.matrixStatus, cloudvmVolume.getStatus())) {
                needSyncCloudvmVolumes.add(cloudvmVolume);
            }
            cloudvmVolume.setServerId(null);
            cloudvmVolume.setServerName(null);
            cloudvmVolumeService.update(cloudvmVolume);
        }
        syncStatus(needSyncCloudvmVolumes, new Checker<Volume>() {
            @Override
            public boolean check(Volume volume) throws Exception {
                return true;
            }
        });
    }
}
