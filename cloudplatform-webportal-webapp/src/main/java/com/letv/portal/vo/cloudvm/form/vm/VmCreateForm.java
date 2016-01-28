package com.letv.portal.vo.cloudvm.form.vm;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

import com.letv.lcp.cloudvm.constants.ValidationRegex;
import com.letv.lcp.cloudvm.validation.Divisible;

public class VmCreateForm {
	private String region;
	private String name;

	private String flavorId;
	private Integer cpu;
	private Integer ram;

	private String imageId;

	private int volumeSize;
	private String volumeTypeId;

	private String adminPass;
	private int count;
	
	private Integer orderTime;
	private Long tenantId;
	
	private Long userId;
	
	private String adminEndpoint;//admin调用地址
	private String publicEndpoint;//public调用地址
	private String passwordSalt;//密码盐值

	public VmCreateForm() {
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
	public String getImageId() {
		return imageId;
	}

	public void setImageId(String imageId) {
		this.imageId = imageId;
	}

	public String getFlavorId() {
		return flavorId;
	}

	public void setFlavorId(String flavorId) {
		this.flavorId = flavorId;
	}

    @Min(0)
    @Max(2000)
	@Divisible(10)
	public int getVolumeSize() {
		return volumeSize;
	}

	public void setVolumeSize(int volumeSize) {
		this.volumeSize = volumeSize;
	}
	@NotBlank
	public String getVolumeTypeId() {
		return volumeTypeId;
	}

	public void setVolumeTypeId(String volumeTypeId) {
		this.volumeTypeId = volumeTypeId;
	}
	@NotNull
    public Integer getCpu() {
		return cpu;
	}

	public void setCpu(Integer cpu) {
		this.cpu = cpu;
	}
	@NotNull
	public Integer getRam() {
		return ram;
	}

	public void setRam(Integer ram) {
		this.ram = ram;
	}
	@NotBlank
	public String getAdminPass() {
		return adminPass;
	}

	public void setAdminPass(String adminPass) {
		this.adminPass = adminPass;
	}

    @Min(1)
    @Max(20)
	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
	@NotNull
	public Integer getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(Integer orderTime) {
		this.orderTime = orderTime;
	}
	@NotNull
	public Long getTenantId() {
		return tenantId;
	}

	public void setTenantId(Long tenantId) {
		this.tenantId = tenantId;
	}
	
	@NotBlank
	public String getAdminEndpoint() {
		return adminEndpoint;
	}

	@NotNull
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public void setAdminEndpoint(String adminEndpoint) {
		this.adminEndpoint = adminEndpoint;
	}
	@NotBlank
	public String getPublicEndpoint() {
		return publicEndpoint;
	}

	public void setPublicEndpoint(String publicEndpoint) {
		this.publicEndpoint = publicEndpoint;
	}

	@NotBlank
	public String getPasswordSalt() {
		return passwordSalt;
	}

	public void setPasswordSalt(String passwordSalt) {
		this.passwordSalt = passwordSalt;
	}


}
