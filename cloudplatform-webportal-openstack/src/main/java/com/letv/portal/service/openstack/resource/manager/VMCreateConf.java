package com.letv.portal.service.openstack.resource.manager;

import org.jclouds.openstack.nova.v2_0.options.CreateServerOptions;

/**
 * Created by zhouxianguang on 2015/6/8.
 */
public class VMCreateConf {
	// 区域
	public String region;
	// 名字
	public String name;
	// 镜像
	public String imageRef;
	// 配额
	public String flavorRef;
	public CreateServerOptions[] options;
}
