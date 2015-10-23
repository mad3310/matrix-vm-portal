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
    public int countImageByName(String region, String name) {
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
}
