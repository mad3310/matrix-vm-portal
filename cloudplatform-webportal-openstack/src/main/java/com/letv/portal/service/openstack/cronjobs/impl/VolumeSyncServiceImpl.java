package com.letv.portal.service.openstack.cronjobs.impl;

import com.letv.common.exception.MatrixException;
import com.letv.common.paging.impl.Page;
import com.letv.portal.model.cloudvm.CloudvmVolume;
import com.letv.portal.model.cloudvm.CloudvmVolumeStatus;
import com.letv.portal.service.cloudvm.ICloudvmVolumeService;
import com.letv.portal.service.openstack.cronjobs.VolumeSyncService;
import com.letv.portal.service.openstack.cronjobs.impl.cache.SyncLocalApiCache;
import com.letv.portal.service.openstack.exception.OpenStackException;
import org.apache.commons.lang3.StringUtils;
import org.jclouds.openstack.cinder.v1.CinderApi;
import org.jclouds.openstack.cinder.v1.domain.Volume;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zhouxianguang on 2015/10/22.
 */
@Service
public class VolumeSyncServiceImpl extends AbstractSyncServiceImpl implements VolumeSyncService {

    @Autowired
    private ICloudvmVolumeService cloudvmVolumeService;

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

    private void update(CloudvmVolume cloudvmVolume, Volume volume) {
        if (volume == null) {
            if (cloudvmVolume.getStatus() != CloudvmVolumeStatus.NIL) {
                cloudvmVolume.setStatus(CloudvmVolumeStatus.NIL);
                cloudvmVolumeService.update(cloudvmVolume);
            }
        } else {
            final String latestStatus = volume.getStatus().name();
            if (!StringUtils.equals(cloudvmVolume.getStatus().name(), latestStatus)) {
                cloudvmVolume.setStatus(CloudvmVolumeStatus.valueOf(latestStatus));
                cloudvmVolumeService.update(cloudvmVolume);
            }
        }
    }
}
