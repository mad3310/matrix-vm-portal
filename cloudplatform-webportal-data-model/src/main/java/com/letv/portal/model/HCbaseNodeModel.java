package com.letv.portal.model;

import com.letv.common.model.BaseModel;

public class HCbaseNodeModel extends BaseModel {

	private static final long serialVersionUID = -4620055845479820238L;

	private String hostName; // 主机名称
	private String hostIp; // 主机ip

	// private Integer nodesNumber; //节点个数
	private String hostModel; // 主机型号
	private String cpuModel; // cpu型号
	private Integer coresNumber; // cpu核数
	private Integer memorySize; // 内存大小
	private Integer diskSize; // 磁盘大小
	private Integer diskUsed; // 磁盘使用量
	private String type; // 主机类型
	private Long id; // 主机id
	private String descn; // 主机描述
	// private HclusterModel hcluster; //物理机集群
	private String name; // 用户名
	private String password; // 密码
	private String hostNameAlias; // 别名

	private Integer status; // 状态:

}
