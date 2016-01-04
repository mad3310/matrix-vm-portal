package com.letv.lcp.cloudvm.dispatch;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.stereotype.Service;

@Service
public class DispatchCenter extends ApplicationObjectSupport {
	
	@Value("${dispatch.service.provider}")
	private String provider;
	
	public Object getServiceByStrategy(String serviceType, String instanceType) {
		String[] providers = provider.split(",");
		//根据数据库中配置的实现类获取服务名称
		String serviceName = "openstackComputeServiceImpl";
		return getApplicationContext().getBean(serviceName);
	}

}
