package com.letv.portal.model.cloudvm.lcp;

import com.letv.common.model.BaseModel;
import com.letv.portal.enumeration.cloudvm.CloudvmNetworkStatusEnum;

public class CloudvmPublicNetworkModel extends BaseModel{

    private static final long serialVersionUID = 1302396074647557995L;

    private String name;
    private String region;
    private String networkInstanceId;
    private CloudvmNetworkStatusEnum status;//网络状态
    private String carrierName;//线路名称
    
    private Integer bandWidth;//带宽
    private String publicIp;//公网ip
    private Long tenantId;//租户ID
    
	public String getCarrierName() {
		return carrierName;
	}
	public void setCarrierName(String carrierName) {
		this.carrierName = carrierName;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public String getNetworkInstanceId() {
		return networkInstanceId;
	}
	public void setNetworkInstanceId(String networkInstanceId) {
		this.networkInstanceId = networkInstanceId;
	}
	public CloudvmNetworkStatusEnum getStatus() {
		return status;
	}
	public void setStatus(CloudvmNetworkStatusEnum status) {
		this.status = status;
	}
	public Integer getBandWidth() {
		return bandWidth;
	}
	public void setBandWidth(Integer bandWidth) {
		this.bandWidth = bandWidth;
	}
	public String getPublicIp() {
		return publicIp;
	}
	public void setPublicIp(String publicIp) {
		this.publicIp = publicIp;
	}
	public Long getTenantId() {
		return tenantId;
	}
	public void setTenantId(Long tenantId) {
		this.tenantId = tenantId;
	}
    
}
