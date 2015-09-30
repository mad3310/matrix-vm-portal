package com.letv.portal.model.cloudvm;

import com.letv.common.model.BaseModel;

/**
 * Created by zhouxianguang on 2015/9/30.
 */
public class CloudvmImageProperty extends BaseModel {
    private static final long serialVersionUID = -5513524268603348442L;
    private String region;
    private String imageId;
    private String key;
    private String value;

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
