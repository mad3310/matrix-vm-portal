package com.letv.lcp.openstack.service.base;

import org.springframework.scheduling.SchedulingTaskExecutor;

import com.letv.common.email.ITemplateMessageSender;
import com.letv.common.session.SessionServiceImpl;
import com.letv.lcp.openstack.service.cronjobs.IImageSyncService;
import com.letv.lcp.openstack.service.cronjobs.IVmSyncService;
import com.letv.lcp.openstack.service.cronjobs.IVolumeSyncService;
import com.letv.lcp.openstack.service.erroremail.IErrorEmailService;
import com.letv.lcp.openstack.service.event.IEventPublishService;
import com.letv.lcp.openstack.service.jclouds.IApiService;
import com.letv.lcp.openstack.service.local.ILocalCommonQuotaSerivce;
import com.letv.lcp.openstack.service.local.ILocalImageService;
import com.letv.lcp.openstack.service.local.ILocalNetworkService;
import com.letv.lcp.openstack.service.local.ILocalRcCountService;
import com.letv.lcp.openstack.service.local.ILocalVolumeService;
import com.letv.lcp.openstack.service.password.IPasswordService;
import com.letv.lcp.openstack.service.resource.IResourceService;
import com.letv.portal.service.cloudvm.ICloudvmFlavorService;
import com.letv.portal.service.cloudvm.ICloudvmRegionService;
import com.letv.portal.service.cloudvm.ICloudvmServerService;
import com.letv.portal.service.cloudvm.ICloudvmVolumeService;
import com.letv.portal.service.common.IUserService;



public class OpenStackServiceGroup {
	private ICloudvmRegionService cloudvmRegionService;
	private ITemplateMessageSender defaultEmailSender;
	private IPasswordService passwordService;
	private SessionServiceImpl sessionService;
	// private ICloudvmVmCountService cloudvmVmCountService;
	private SchedulingTaskExecutor threadPoolTaskExecutor;
	private IErrorEmailService errorEmailService;
	private ICloudvmFlavorService cloudvmFlavorService;
	private ICloudvmServerService cloudvmServerService;
	private IVmSyncService vmSyncService;
	private IApiService apiService;
	private IUserService userService;
	private ILocalVolumeService localVolumeService;
	private IVolumeSyncService volumeSyncService;
	private ICloudvmVolumeService cloudvmVolumeService;
	private ILocalImageService localImageService;
	private IImageSyncService imageSyncService;
	private ILocalRcCountService localRcCountService;
	private IEventPublishService eventPublishService;
	private ILocalCommonQuotaSerivce localCommonQuotaSerivce;
	private ILocalNetworkService localNetworkService;
	private IResourceService resourceService;
	private IOpenStackService openStackService;
	public ICloudvmRegionService getCloudvmRegionService() {
		return cloudvmRegionService;
	}
	public void setCloudvmRegionService(ICloudvmRegionService cloudvmRegionService) {
		this.cloudvmRegionService = cloudvmRegionService;
	}
	public ITemplateMessageSender getDefaultEmailSender() {
		return defaultEmailSender;
	}
	public void setDefaultEmailSender(ITemplateMessageSender defaultEmailSender) {
		this.defaultEmailSender = defaultEmailSender;
	}
	public IPasswordService getPasswordService() {
		return passwordService;
	}
	public void setPasswordService(IPasswordService passwordService) {
		this.passwordService = passwordService;
	}
	public SessionServiceImpl getSessionService() {
		return sessionService;
	}
	public void setSessionService(SessionServiceImpl sessionService) {
		this.sessionService = sessionService;
	}
	public SchedulingTaskExecutor getThreadPoolTaskExecutor() {
		return threadPoolTaskExecutor;
	}
	public void setThreadPoolTaskExecutor(
			SchedulingTaskExecutor threadPoolTaskExecutor) {
		this.threadPoolTaskExecutor = threadPoolTaskExecutor;
	}
	public IErrorEmailService getErrorEmailService() {
		return errorEmailService;
	}
	public void setErrorEmailService(IErrorEmailService errorEmailService) {
		this.errorEmailService = errorEmailService;
	}
	public ICloudvmFlavorService getCloudvmFlavorService() {
		return cloudvmFlavorService;
	}
	public void setCloudvmFlavorService(ICloudvmFlavorService cloudvmFlavorService) {
		this.cloudvmFlavorService = cloudvmFlavorService;
	}
	public ICloudvmServerService getCloudvmServerService() {
		return cloudvmServerService;
	}
	public void setCloudvmServerService(ICloudvmServerService cloudvmServerService) {
		this.cloudvmServerService = cloudvmServerService;
	}
	public IVmSyncService getVmSyncService() {
		return vmSyncService;
	}
	public void setVmSyncService(IVmSyncService vmSyncService) {
		this.vmSyncService = vmSyncService;
	}
	public IApiService getApiService() {
		return apiService;
	}
	public void setApiService(IApiService apiService) {
		this.apiService = apiService;
	}
	public IUserService getUserService() {
		return userService;
	}
	public void setUserService(IUserService userService) {
		this.userService = userService;
	}
	public ILocalVolumeService getLocalVolumeService() {
		return localVolumeService;
	}
	public void setLocalVolumeService(ILocalVolumeService localVolumeService) {
		this.localVolumeService = localVolumeService;
	}
	public IVolumeSyncService getVolumeSyncService() {
		return volumeSyncService;
	}
	public void setVolumeSyncService(IVolumeSyncService volumeSyncService) {
		this.volumeSyncService = volumeSyncService;
	}
	public ICloudvmVolumeService getCloudvmVolumeService() {
		return cloudvmVolumeService;
	}
	public void setCloudvmVolumeService(ICloudvmVolumeService cloudvmVolumeService) {
		this.cloudvmVolumeService = cloudvmVolumeService;
	}
	public ILocalImageService getLocalImageService() {
		return localImageService;
	}
	public void setLocalImageService(ILocalImageService localImageService) {
		this.localImageService = localImageService;
	}
	public IImageSyncService getImageSyncService() {
		return imageSyncService;
	}
	public void setImageSyncService(IImageSyncService imageSyncService) {
		this.imageSyncService = imageSyncService;
	}
	public ILocalRcCountService getLocalRcCountService() {
		return localRcCountService;
	}
	public void setLocalRcCountService(ILocalRcCountService localRcCountService) {
		this.localRcCountService = localRcCountService;
	}
	public IEventPublishService getEventPublishService() {
		return eventPublishService;
	}
	public void setEventPublishService(IEventPublishService eventPublishService) {
		this.eventPublishService = eventPublishService;
	}
	public ILocalCommonQuotaSerivce getLocalCommonQuotaSerivce() {
		return localCommonQuotaSerivce;
	}
	public void setLocalCommonQuotaSerivce(
			ILocalCommonQuotaSerivce localCommonQuotaSerivce) {
		this.localCommonQuotaSerivce = localCommonQuotaSerivce;
	}
	public ILocalNetworkService getLocalNetworkService() {
		return localNetworkService;
	}
	public void setLocalNetworkService(ILocalNetworkService localNetworkService) {
		this.localNetworkService = localNetworkService;
	}
	public IResourceService getResourceService() {
		return resourceService;
	}
	public void setResourceService(IResourceService resourceService) {
		this.resourceService = resourceService;
	}
	public IOpenStackService getOpenStackService() {
		return openStackService;
	}
	public void setOpenStackService(IOpenStackService openStackService) {
		this.openStackService = openStackService;
	}

}
