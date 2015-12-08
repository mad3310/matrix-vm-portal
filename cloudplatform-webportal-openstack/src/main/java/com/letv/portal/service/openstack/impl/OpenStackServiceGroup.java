package com.letv.portal.service.openstack.impl;

import com.letv.portal.service.IUserService;
import com.letv.portal.service.cloudvm.*;
import com.letv.portal.service.openstack.OpenStackService;
import com.letv.portal.service.openstack.billing.event.service.EventPublishService;
import com.letv.portal.service.openstack.cronjobs.ImageSyncService;
import com.letv.portal.service.openstack.cronjobs.VmSyncService;
import com.letv.portal.service.openstack.cronjobs.VolumeSyncService;
import com.letv.portal.service.openstack.jclouds.service.ApiService;
import com.letv.portal.service.openstack.local.service.LocalCommonQuotaSerivce;
import com.letv.portal.service.openstack.local.service.LocalImageService;
import com.letv.portal.service.openstack.local.service.LocalNetworkService;
import com.letv.portal.service.openstack.local.service.LocalRcCountService;
import com.letv.portal.service.openstack.local.service.LocalVolumeService;

import com.letv.portal.service.openstack.resource.service.ResourceService;
import org.springframework.scheduling.SchedulingTaskExecutor;

import com.letv.common.email.ITemplateMessageSender;
import com.letv.common.session.SessionServiceImpl;
import com.letv.portal.service.openstack.erroremail.ErrorEmailService;
import com.letv.portal.service.openstack.password.PasswordService;

public class OpenStackServiceGroup {
	private ICloudvmRegionService cloudvmRegionService;
	private ITemplateMessageSender defaultEmailSender;
	private PasswordService passwordService;
	private SessionServiceImpl sessionService;
	// private ICloudvmVmCountService cloudvmVmCountService;
	private SchedulingTaskExecutor threadPoolTaskExecutor;
	private ErrorEmailService errorEmailService;
	private ICloudvmFlavorService cloudvmFlavorService;
	private ICloudvmServerService cloudvmServerService;
	private VmSyncService vmSyncService;
	private ApiService apiService;
	private IUserService userService;
	private LocalVolumeService localVolumeService;
	private VolumeSyncService volumeSyncService;
	private ICloudvmVolumeService cloudvmVolumeService;
	private LocalImageService localImageService;
	private ImageSyncService imageSyncService;
	private LocalRcCountService localRcCountService;
	private EventPublishService eventPublishService;
	private LocalCommonQuotaSerivce localCommonQuotaSerivce;
	private LocalNetworkService localNetworkService;
	private ResourceService resourceService;
	private OpenStackService openStackService;

	public void setUserService(IUserService userService) {
		this.userService = userService;
	}

	public IUserService getUserService() {
		return userService;
	}

	public void setApiService(ApiService apiService) {
		this.apiService = apiService;
	}

	public ApiService getApiService() {
		return apiService;
	}

	public ICloudvmRegionService getCloudvmRegionService() {
		return cloudvmRegionService;
	}

	public void setCloudvmRegionService(
			ICloudvmRegionService cloudvmRegionService) {
		this.cloudvmRegionService = cloudvmRegionService;
	}

	public ITemplateMessageSender getDefaultEmailSender() {
		return defaultEmailSender;
	}

	public void setDefaultEmailSender(ITemplateMessageSender defaultEmailSender) {
		this.defaultEmailSender = defaultEmailSender;
	}

	public PasswordService getPasswordService() {
		return passwordService;
	}

	public void setPasswordService(PasswordService passwordService) {
		this.passwordService = passwordService;
	}

	public SessionServiceImpl getSessionService() {
		return sessionService;
	}

	public void setSessionService(SessionServiceImpl sessionService) {
		this.sessionService = sessionService;
	}

	// public ICloudvmVmCountService getCloudvmVmCountService() {
	// return cloudvmVmCountService;
	// }
	//
	// public void setCloudvmVmCountService(
	// ICloudvmVmCountService cloudvmVmCountService) {
	// this.cloudvmVmCountService = cloudvmVmCountService;
	// }

	public void setThreadPoolTaskExecutor(
			SchedulingTaskExecutor threadPoolTaskExecutor) {
		this.threadPoolTaskExecutor = threadPoolTaskExecutor;
	}

	public SchedulingTaskExecutor getThreadPoolTaskExecutor() {
		return threadPoolTaskExecutor;
	}

	public void setErrorEmailService(ErrorEmailService errorEmailService) {
		this.errorEmailService = errorEmailService;
	}

	public ErrorEmailService getErrorEmailService() {
		return errorEmailService;
	}

	public ICloudvmFlavorService getCloudvmFlavorService() {
		return cloudvmFlavorService;
	}

	public void setCloudvmFlavorService(
			ICloudvmFlavorService cloudvmFlavorService) {
		this.cloudvmFlavorService = cloudvmFlavorService;
	}

	public ICloudvmServerService getCloudvmServerService() {
		return cloudvmServerService;
	}

	public void setCloudvmServerService(
			ICloudvmServerService cloudvmServerService) {
		this.cloudvmServerService = cloudvmServerService;
	}

	public void setVmSyncService(VmSyncService vmSyncService) {
		this.vmSyncService = vmSyncService;
	}

	public VmSyncService getVmSyncService() {
		return vmSyncService;
	}

	public void setLocalVolumeService(LocalVolumeService localVolumeService) {
		this.localVolumeService = localVolumeService;
	}

	public LocalVolumeService getLocalVolumeService() {
		return localVolumeService;
	}

	public void setVolumeSyncService(VolumeSyncService volumeSyncService) {
		this.volumeSyncService = volumeSyncService;
	}

	public VolumeSyncService getVolumeSyncService() {
		return volumeSyncService;
	}

	public void setCloudvmVolumeService(
			ICloudvmVolumeService cloudvmVolumeService) {
		this.cloudvmVolumeService = cloudvmVolumeService;
	}

	public ICloudvmVolumeService getCloudvmVolumeService() {
		return cloudvmVolumeService;
	}

	public void setLocalImageService(LocalImageService localImageService) {
		this.localImageService = localImageService;
	}

	public LocalImageService getLocalImageService() {
		return localImageService;
	}

	public void setImageSyncService(ImageSyncService imageSyncService) {
		this.imageSyncService = imageSyncService;
	}

	public ImageSyncService getImageSyncService() {
		return imageSyncService;
	}

	public void setLocalRcCountService(LocalRcCountService localRcCountService) {
		this.localRcCountService = localRcCountService;
	}

	public LocalRcCountService getLocalRcCountService() {
		return localRcCountService;
	}

	public void setEventPublishService(EventPublishService eventPublishService) {
		this.eventPublishService = eventPublishService;
	}

	public EventPublishService getEventPublishService() {
		return eventPublishService;
	}

	public void setLocalCommonQuotaSerivce(
			LocalCommonQuotaSerivce localCommonQuotaSerivce) {
		this.localCommonQuotaSerivce = localCommonQuotaSerivce;
	}

	public LocalCommonQuotaSerivce getLocalCommonQuotaSerivce() {
		return localCommonQuotaSerivce;
	}

	public void setLocalNetworkService(LocalNetworkService localNetworkService) {
		this.localNetworkService = localNetworkService;
	}
	
	public LocalNetworkService getLocalNetworkService() {
		return localNetworkService;
	}

	public void setResourceService(ResourceService resourceService) {
		this.resourceService = resourceService;
	}

	public ResourceService getResourceService() {
		return resourceService;
	}

	public OpenStackService getOpenStackService() {
		return openStackService;
	}

	public void setOpenStackService(OpenStackService openStackService) {
		this.openStackService = openStackService;
	}
}
