package com.letv.lcp.cloudvm.model.network;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

import com.letv.lcp.cloudvm.constants.ValidationRegex;

/**
 * Created by zhouxianguang on 2015/10/19.
 */
public class FloatingIpCreateConf {

	private String region;
    private String name;
    private String publicNetworkId;
    private Integer bandWidth;
    private Integer count;

    public FloatingIpCreateConf() {
    }

    @NotBlank
    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    @NotBlank
    @Pattern(regexp = ValidationRegex.name, message = ValidationRegex.nameMessage)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @NotBlank
    public String getPublicNetworkId() {
        return publicNetworkId;
    }

    public void setPublicNetworkId(String publicNetworkId) {
        this.publicNetworkId = publicNetworkId;
    }

    @NotNull
    @Min(1)
    @Max(50)
    public Integer getBandWidth() {
        return bandWidth;
    }

    public void setBandWidth(Integer bandWidth) {
        this.bandWidth = bandWidth;
    }

    @NotNull
    @Min(1)
    @Max(20)
    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
    
    @Override
   	public String toString() {
   		return "FloatingIpCreateConf [region=" + region + ", name=" + name
   				+ ", publicNetworkId=" + publicNetworkId + ", bandWidth="
   				+ bandWidth + ", count=" + count + "]";
   	}
}
