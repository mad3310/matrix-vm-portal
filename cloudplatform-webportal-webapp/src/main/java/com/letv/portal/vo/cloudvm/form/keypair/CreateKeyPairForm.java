package com.letv.portal.vo.cloudvm.form.keypair;

import com.letv.portal.service.openstack.util.constants.ValidationRegex;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Pattern;

/**
 * Created by zhouxianguang on 2015/11/4.
 */
public class CreateKeyPairForm {
    private String region;
    private String name;

    @NotBlank
    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    @NotBlank
    @Pattern(regexp = ValidationRegex.keyPairName, message = ValidationRegex.keyPairNameMessage)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
