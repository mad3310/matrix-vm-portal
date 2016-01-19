package com.letv.portal.vo.cloudvm.form.volume_snapshot;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

import com.letv.lcp.cloudvm.constants.ValidationRegex;

/**
 * Created by zhouxianguang on 2015/11/4.
 */
public class VolumeSnapshotCreateForm {
    private String region;
    private String volumeId;
    private String name;
    private String description;

    @NotBlank
    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    @NotBlank
    public String getVolumeId() {
        return volumeId;
    }

    public void setVolumeId(String volumeId) {
        this.volumeId = volumeId;
    }

    @Pattern(regexp = ValidationRegex.name, message = ValidationRegex.nameMessage)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
