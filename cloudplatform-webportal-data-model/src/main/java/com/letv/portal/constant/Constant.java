package com.letv.portal.constant;

public class Constant {

	public static final String KAPTCHA_COOKIE_NAME = "captcha_cache_id_";
	public static int RESULT_SUCCESS = 1;
	public static int RESULT_ERROR = 0;

	public static int PAGE_SIZE = 10;

	// container node节点类型
	public static String MCLUSTER_NODE_TYPE_VIP_SUFFIX = "_vip";
	public static String MCLUSTER_NODE_TYPE_DATA_SUFFIX = "_data";

	// db审批状态
	public static String DB_AUDIT_STATUS_FALSE = "-1";

	public static String IS_DELETE_FALSE = "0";
	public static String IS_DELETE_TRUE = "1";

	public static Integer STATUS_DEFAULT = 0;
	public static Integer STATUS_OK = 1;
	public static Integer STATUS_BUILDDING = 2;
	public static Integer STATUS_BUILD_FAIL = 3;
	public static Integer STATUS_AUDIT_FAIL = 4;

	public static String DB_AUDIT_STATUS_TRUE_BUILD_NEW_MCLUSTER = "1";
	public static String DB_AUDIT_STATUS_TRUE_BUILD_OLD_MCLUSTER = "2";

	public static int MCLUSTER_CONTAINERS_COUNT = 4;

	public static Integer IPRESOURCE_STATUS_USERD = 1;

	public static String PYTHON_API_RESPONSE_SUCCESS = "200";
	//根据业务判断是否异常
	public static String PYTHON_API_RESPONSE_JUDGE  = "417";

	public static String CREATE_BUCKET_API_RESPONSE_SUCCESS = "202";

	public static String CREATE_REBALANCE_STATUS_RESPONSE_SUCCESS = "none";

	public static String PYTHON_API_RESULT_SUCCESS = "000000";

	public static String PYTHON_API_CHECK_CONTAINER_RUNNING = "<running>";

	public static String DB_USER_TYPE_MANAGER = "manager";

	public static String MCLUSTER_INIT_STATUS_RUNNING = "running";

	//是否查询mysql不变数据的开关
	public static boolean QUERY_MYSQL_CONSTANT_DATA = false;
	
	//创建云主机
	public static final String CREATE_OPENSTACK = "云主机";
	//启动云主机
	public static final String START_OPENSTACK = "启动云主机";
	//停止云主机
	public static final String STOP_OPENSTACK = "关机云主机";
	public static final String REBOOT_OPENSTACK = "重启云主机";
	public static final String MODIFY_PWD_OPENSTACK = "云主机修改密码";
	//绑定云硬盘
	public static final String ATTACH_VOLUME_OPENSTACK = "绑定云硬盘";
	public static final String DETACH_VOLUME_OPENSTACK = "卸载云硬盘";
	//创建快照
	public static final String SNAPSHOT_CREATE_OPENSTACK = "创建云主机快照";
	public static final String SNAPSHOT_DELETE_OPENSTACK = "删除云主机快照";
	//删除云主机
	public static final String DELETE_OPENSTACK = "删除云主机";
	//云主机绑定公网IP
	public static final String BINDED_FLOATINGIP_OPENSTACK = "云主机绑定公网IP";
	//云主机解绑公网IP
	public static final String UNBINDED_FLOATINGIP_OPENSTACK = "云主机解绑公网IP";
	
	//创建云硬盘
	public static final String CREATE_VOLUME = "云硬盘";
	//删除云硬盘
	public static final String DELETE_VOLUME = "删除云硬盘";
	//创建快照
	public static final String SNAPSHOT_CREATE_VOLUME = "创建云硬盘快照";
	public static final String SNAPSHOT_DELETE_VOLUME = "删除云硬盘快照";
	public static final String EDIT_VOLUME = "编辑云硬盘";
	
	//创建公网IP
	public static final String CREATE_FLOATINGIP = "公网IP";
	//删除公网IP
	public static final String DELETE_FLOATINGIP = "删除公网IP";
	//编辑公网IP
	public static final String EDIT_FLOATINGIP = "编辑公网IP";
	
	
	//创建路由器
	public static final String CREATE_ROUTER = "路由器";
	//删除路由器
	public static final String DELETE_ROUTER = "删除路由器";
	public static final String BINDED_FLOATINGIP_ROUTER = "路由器绑定公网IP";
	public static final String UNBINDED_FLOATINGIP_ROUTER = "路由器解绑公网IP";
	public static final String BINDED_SUBNET_ROUTER = "路由器关联子网";
	public static final String UNBINDED_SUBNET_ROUTER = "路由器解除关联子网";
	public static final String EDIT_ROUTER = "编辑路由器";
	
	public static final String CREATE_PRIVATE_NET = "创建私网";
	public static final String EDIT_PRIVATE_NET = "编辑私网";
	public static final String DELETE_PRIVATE_NET = "删除私网";
	public static final String CREATE_SUBNET = "创建子网";
	public static final String EDIT_SUBNET = "编辑子网";
	public static final String DELETE_SUBNET = "删除子网";
	
	public static final String CREATE_KEYPAIR = "创建密钥";
	public static final String DELETE_KEYPAIR = "删除密钥";
	
	public static final String SUBNET_ATTACH_VM = "子网添加云主机";
	public static final String SUBNET_DETACH_VM = "子网移除云主机";
	
	//无名称云产品统一为“未设置”
	public static final String NO_NAME = "未设置";
	//最近操作样式一
	public static final String STYLE_OPERATE_1 = "{0}=={1}";
	//最近操作样式二
	public static final String STYLE_OPERATE_2 = "{0}=-{1}";

}
