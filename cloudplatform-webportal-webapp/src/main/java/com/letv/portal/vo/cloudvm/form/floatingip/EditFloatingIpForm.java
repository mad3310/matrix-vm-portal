package com.letv.portal.vo.cloudvm.form.floatingip;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * Created by zhouxianguang on 2015/11/4.
 */
public class EditFloatingIpForm {
    private String region;
    private String floatingIpId;
    private String name;
    private Integer bandWidth;

    @NotBlank
    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    @NotBlank
    public String getFloatingIpId() {
        return floatingIpId;
    }

    public void setFloatingIpId(String floatingIpId) {
        this.floatingIpId = floatingIpId;
    }

    @NotBlank
    @Pattern(regexp = "^[a-zA-Zu4e00-u9fa5][^@/:=\\\\\"<>\\{\\[\\]\\}\\s]{2,128}$", message = "名称须为2-128个字符，以大小写字母或中文开头，不支持字符@/:=\\\"<>{[]}和空格")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @NotNull
    public Integer getBandWidth() {
        return bandWidth;
    }

    public void setBandWidth(Integer bandWidth) {
        this.bandWidth = bandWidth;
    }
}
