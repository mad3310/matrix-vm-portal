package com.letv.portal.service.openstack.impl;

import com.letv.portal.service.IUserService;
import com.letv.portal.service.cloudvm.ICloudvmFlavorService;
import com.letv.portal.service.cloudvm.ICloudvmServerService;
import com.letv.portal.service.openstack.cronjobs.VmSyncService;
import com.letv.portal.service.openstack.jclouds.service.ApiService;
import com.letv.portal.service.openstack.jclouds.service.impl.ApiServiceImpl;
import org.springframework.scheduling.SchedulingTaskExecutor;

import com.letv.common.email.ITemplateMessageSender;
import com.letv.common.session.SessionServiceImpl;
import com.letv.portal.service.cloudvm.ICloudvmRegionService;
import com.letv.portal.service.cloudvm.ICloudvmVmCountService;
import com.letv.portal.service.openstack.erroremail.ErrorEmailService;
import com.letv.portal.service.openstack.password.PasswordService;

public class OpenStackServiceGroup {
	private ICloudvmRegionService cloudvmRegionService;
	private ITemplateMessageSender defaultEmailSender;
	private PasswordService passwordService;
	private SessionServiceImpl sessionService;
	private ICloudvmVmCountService cloudvmVmCountService;
	private SchedulingTaskExecutor threadPoolTaskExecutor;
	private ErrorEmailService errorEmailService;
	private ICloudvmFlavorService cloudvmFlavorService;
	private ICloudvmServerService cloudvmServerService;
	private VmSyncService vmSyncService;
	private ApiService apiService;
	private IUserService userService;

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

	public ICloudvmVmCountService getCloudvmVmCountService() {
		return cloudvmVmCountService;
	}

	public void setCloudvmVmCountService(
			ICloudvmVmCountService cloudvmVmCountService) {
		this.cloudvmVmCountService = cloudvmVmCountService;
	}

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

	public void setCloudvmFlavorService(ICloudvmFlavorService cloudvmFlavorService) {
		this.cloudvmFlavorService = cloudvmFlavorService;
	}

	public ICloudvmServerService getCloudvmServerService() {
		return cloudvmServerService;
	}

	public void setCloudvmServerService(ICloudvmServerService cloudvmServerService) {
		this.cloudvmServerService = cloudvmServerService;
	}

	public void setVmSyncService(VmSyncService vmSyncService) {
		this.vmSyncService = vmSyncService;
	}

	public VmSyncService getVmSyncService() {
		return vmSyncService;
	}
}
