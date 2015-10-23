package com.letv.portal.service.cloudvm;

import com.letv.common.paging.impl.Page;
import com.letv.portal.model.cloudvm.CloudvmImage;
import com.letv.portal.model.cloudvm.CloudvmImageType;
import com.letv.portal.service.IBaseService;

import java.util.List;

/**
 * Created by zhouxianguang on 2015/10/8.
 */
public interface ICloudvmImageService extends IBaseService<CloudvmImage> {
    CloudvmImage selectImageByImageId(String region, String imageId);

    List<CloudvmImage> selectImageByName(String region, String name, Page page);

    int countImageByName(String region, String name);

    void insertImage(CloudvmImage cloudvmImage);

}
