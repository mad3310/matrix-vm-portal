package com.letv.portal.service.openstack.impl;

import com.letv.common.email.ITemplateMessageSender;
import com.letv.portal.service.cloudvm.ICloudvmRegionService;

public class OpenStackServiceGroup {
	private ICloudvmRegionService cloudvmRegionService;
	private ITemplateMessageSender defaultEmailSender;

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

}
