package com.letv.lcp.cloudvm.dispatch;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.stereotype.Service;

import com.letv.lcp.cloudvm.enumeration.InstanceTypeEnum;
import com.letv.lcp.cloudvm.enumeration.ServiceTypeEnum;

@Service("dispatchCenter")
public class DispatchCenter extends ApplicationObjectSupport {
	
	@Value("${dispatch.service.provider}")
	private String provider;
	
	/**
	  * @Title: getServiceByStrategy
	  * @Description: 获取相应service类实例
	  * @param serviceType 服务类型
	  * @param instanceType 实例类型
	  * @return Object   
	  * @throws 
	  * @author lisuxiao
	  * @date 2016年1月5日 上午11:06:24
	  */
	public Object getServiceByStrategy(ServiceTypeEnum serviceType, InstanceTypeEnum instanceType) {
		if(null==instanceType) {//实例类型为空时,采用默认的实例
			String[] providers = provider.split(",");
			for (InstanceTypeEnum instance : InstanceTypeEnum.values()) {
				if(providers[0].equals(instance.getName())) {
					instanceType = instance;
				}
			}
		}
		String serviceName = instanceType.getName()+serviceType.getName();
		return getApplicationContext().getBean(serviceName);
	}
	
	/**
	  * @Title: getServiceByStrategy
	  * @Description: 获取相应service类实例
	  * @param serviceType 服务类型
	  * @return Object   
	  * @throws 
	  * @author lisuxiao
	  * @date 2016年1月5日 上午10:19:38
	  */
	public Object getServiceByStrategy(ServiceTypeEnum serviceType) {
		String[] providers = provider.split(",");
		for (InstanceTypeEnum instanceType : InstanceTypeEnum.values()) {
			if(providers[0].equals(instanceType.getName())) {
				return getServiceByStrategy(serviceType, instanceType);
			}
		}
		return new Object();
	}

}
