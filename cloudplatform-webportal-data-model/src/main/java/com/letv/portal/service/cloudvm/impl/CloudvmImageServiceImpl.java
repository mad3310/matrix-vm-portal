package com.letv.portal.service.cloudvm.impl;

import com.letv.common.dao.IBaseDao;
import com.letv.common.exception.ValidateException;
import com.letv.common.paging.impl.Page;
import com.letv.portal.dao.cloudvm.ICloudvmImageDao;
import com.letv.portal.model.cloudvm.CloudvmImage;
import com.letv.portal.model.cloudvm.CloudvmImageType;
import com.letv.portal.service.cloudvm.ICloudvmImageService;
import com.letv.portal.service.impl.BaseServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhouxianguang on 2015/10/8.
 */
@Service
public class CloudvmImageServiceImpl extends BaseServiceImpl<CloudvmImage>
        implements ICloudvmImageService {

    @Resource
    private ICloudvmImageDao cloudvmImageDao;

    public CloudvmImageServiceImpl() {
        super(CloudvmImage.class);
    }

    @Override
    public IBaseDao<CloudvmImage> getDao() {
        return cloudvmImageDao;
    }

    @Override
    public CloudvmImage selectImageByImageId(String region, String imageId) {
        if (StringUtils.isEmpty(region)) {
            throw new ValidateException("地域不能为空");
        }
        if (StringUtils.isEmpty(imageId)) {
            throw new ValidateException("镜像ID不能为空");
        }
        Map<String, Object> paras = new HashMap<String, Object>();
        paras.put("region", region);
        paras.put("imageId", imageId);
        paras.put("imageType", CloudvmImageType.IMAGE);
        List<CloudvmImage> resultList = selectByMap(paras);
        if (!resultList.isEmpty()) {
            return resultList.get(0);
        }
        return null;
    }

    @Override
    public List<CloudvmImage> selectImageByName(String region, String name, Page page) {
        if (StringUtils.isEmpty(region)) {
            throw new ValidateException("地域不能为空");
        }
        Map<String, Object> paras = new HashMap<String, Object>();
        paras.put("region", region);
        if (StringUtils.isNotEmpty(name)) {
            paras.put("name", name);
        }
        if (page != null) {
            paras.put("page", page);
        }
        paras.put("imageType", CloudvmImageType.IMAGE);
        return cloudvmImageDao.selectByMap(paras);
    }

    @Override
    public int selectCountImageByName(String region, String name) {
        if (StringUtils.isEmpty(region)) {
            throw new ValidateException("地域不能为空");
        }
        Map<String, Object> paras = new HashMap<String, Object>();
        paras.put("region", region);
        if (StringUtils.isNotEmpty(name)) {
            paras.put("name", name);
        }
        paras.put("imageType", CloudvmImageType.IMAGE);
        return cloudvmImageDao.selectByMapCount(paras);
    }

    @Override
    public void insertImage(CloudvmImage cloudvmImage) {
        cloudvmImage.setImageType(CloudvmImageType.IMAGE);
        super.insert(cloudvmImage);
    }

    @Override
    public CloudvmImage selectVmSnapshotByVmSnapshotId(long tenantId, String region, String vmSnapshotId) {
        if (StringUtils.isEmpty(region)) {
            throw new ValidateException("地域不能为空");
        }
        if (StringUtils.isEmpty(vmSnapshotId)) {
            throw new ValidateException("虚拟机快照ID不能为空");
        }
        Map<String, Object> paras = new HashMap<String, Object>();
        paras.put("tenantId", tenantId);
        paras.put("region", region);
        paras.put("imageId", vmSnapshotId);
        paras.put("imageType", CloudvmImageType.SNAPSHOT);
        List<CloudvmImage> resultList = selectByMap(paras);
        if (!resultList.isEmpty()) {
            return resultList.get(0);
        }
        return null;
    }

    @Override
    public List<CloudvmImage> selectVmSnapshotForSync(Long minId, Page page) {
        Map<String, Object> paras = new HashMap<String, Object>();
        if (minId != null) {
            paras.put("id", minId);
        }
        if (page != null) {
            paras.put("page", page);
        }
        paras.put("imageType", CloudvmImageType.SNAPSHOT);
        return cloudvmImageDao.selectByMapForSync(paras);
    }

    @Override
    public List<CloudvmImage> selectVmSnapshotByName(long tenantId, String region, String name, Page page) {
        if (StringUtils.isEmpty(region)) {
            throw new ValidateException("地域不能为空");
        }
        Map<String, Object> paras = new HashMap<String, Object>();
        paras.put("tenantId", tenantId);
        paras.put("region", region);
        paras.put("imageType", CloudvmImageType.SNAPSHOT);
        if (StringUtils.isNotEmpty(name)) {
            paras.put("name", name);
        }
        if (page != null) {
            paras.put("page", page);
        }
        return cloudvmImageDao.selectByMap(paras);
    }

    @Override
    public int selectCountVmSnapshotByName(long tenantId, String region, String name) {
        if (StringUtils.isEmpty(region)) {
            throw new ValidateException("地域不能为空");
        }
        Map<String, Object> paras = new HashMap<String, Object>();
        paras.put("tenantId", tenantId);
        paras.put("region", region);
        paras.put("imageType", CloudvmImageType.SNAPSHOT);
        if (StringUtils.isNotEmpty(name)) {
            paras.put("name", name);
        }
        return cloudvmImageDao.selectByMapCount(paras);
    }

    @Override
    public void insertVmSnapshot(CloudvmImage cloudvmImage) {
        cloudvmImage.setImageType(CloudvmImageType.SNAPSHOT);
        super.insert(cloudvmImage);
    }

    @Override
    public List<CloudvmImage> selectVmSnapshotByServerId(long tenantId, String region, String serverId) {
        if (StringUtils.isEmpty(region)) {
            throw new ValidateException("地域不能为空");
        }
        if (StringUtils.isEmpty(serverId)) {
            throw new ValidateException("虚拟机ID不能为空");
        }
        Map<String, Object> paras = new HashMap<String, Object>();
        paras.put("tenantId", tenantId);
        paras.put("region", region);
        paras.put("serverId", serverId);
        paras.put("imageType", CloudvmImageType.SNAPSHOT);
        return cloudvmImageDao.selectByMap(paras);
    }

    @Override
    public CloudvmImage getImageOrVmSnapshot(String region, String imageId) {
        if (StringUtils.isEmpty(region)) {
            throw new ValidateException("地域不能为空");
        }
        if (StringUtils.isEmpty(imageId)) {
            return null;
        }
        Map<String, Object> paras = new HashMap<String, Object>();
        paras.put("region", region);
        paras.put("imageId", imageId);
        List<CloudvmImage> cloudvmImages = cloudvmImageDao.selectByMap(paras);
        if (!cloudvmImages.isEmpty()) {
            return cloudvmImages.get(0);
        }
        return null;
    }

    @Override
    public List<CloudvmImage> selectAllImageOrVmSnapshot(long tenantId, String region) {
        if (StringUtils.isEmpty(region)) {
            throw new ValidateException("地域不能为空");
        }
        Map<String, Object> paras = new HashMap<String, Object>();
        paras.put("region", region);
        paras.put("tenantId", tenantId);
        paras.put("imageType", CloudvmImageType.IMAGE);
        return cloudvmImageDao.selectAllImageOrVmSnapshot(paras);
    }
}
