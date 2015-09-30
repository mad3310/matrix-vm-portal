package com.letv.portal.model.cloudvm;

import com.letv.common.model.BaseModel;

import java.util.Date;

/**
 * Created by zhouxianguang on 2015/9/30.
 */
public class CloudvmImage extends BaseModel{

    private static final long serialVersionUID = -8649677463047922945L;

    private String region;
    private String imageId;
    private String name;
    private String containerFormat;
    private String diskFormat;
    private Long size;
    private String checkSum;
    private Long minDisk;
    private Long minRam;
    private String location;
    private String owner;
    private Date updatedAt;
    private Date createdAt;
    private Date deletedAt;
    private String status;
    private Boolean isPublic;
//    private

}
