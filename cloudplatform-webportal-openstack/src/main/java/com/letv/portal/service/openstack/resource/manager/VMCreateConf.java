package com.letv.portal.service.openstack.resource.manager;

import com.letv.portal.service.openstack.resource.FlavorResource;
import com.letv.portal.service.openstack.resource.ImageResource;
import com.letv.portal.service.openstack.resource.NetworkResource;

import java.util.List;

/**
 * Created by zhouxianguang on 2015/6/8.
 */
public class VMCreateConf {

	private String name;
	private ImageResource imageResource;
	private FlavorResource flavorResource;
	private List<NetworkResource> networkResources;
	private String adminPass;
	private boolean bindFloatingIP;

	public VMCreateConf() {
	}

	public VMCreateConf(String name, ImageResource imageResource,
			FlavorResource flavorResource,
			List<NetworkResource> networkResources, String adminPass,
			boolean bindFloatingIP) {
		this.name = name;
		this.imageResource = imageResource;
		this.flavorResource = flavorResource;
		this.networkResources = networkResources;
		this.adminPass = adminPass;
		this.bindFloatingIP = bindFloatingIP;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ImageResource getImageResource() {
		return imageResource;
	}

	public void setImageResource(ImageResource imageResource) {
		this.imageResource = imageResource;
	}

	public FlavorResource getFlavorResource() {
		return flavorResource;
	}

	public void setFlavorResource(FlavorResource flavorResource) {
		this.flavorResource = flavorResource;
	}

	public void setNetworkResources(List<NetworkResource> networkResources) {
		this.networkResources = networkResources;
	}

	public List<NetworkResource> getNetworkResources() {
		return networkResources;
	}

	public String getAdminPass() {
		return adminPass;
	}

	public void setAdminPass(String adminPass) {
		this.adminPass = adminPass;
	}

	public boolean getBindFloatingIP() {
		return bindFloatingIP;
	}

	public void setBindFloatingIP(boolean bindFloatingIP) {
		this.bindFloatingIP = bindFloatingIP;
	}

	// // 区域
	// public String region;
	// // 名字
	// public String name;
	// // 镜像
	// public String imageRef;
	// // 配额
	// public String flavorRef;
	// public CreateServerOptions[] options;
}
