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

	public static String CREATE_BUCKET_API_RESPONSE_SUCCESS = "202";

	public static String CREATE_REBALANCE_STATUS_RESPONSE_SUCCESS = "none";

	public static String PYTHON_API_RESULT_SUCCESS = "000000";

	public static String PYTHON_API_CHECK_CONTAINER_RUNNING = "<running>";

	public static String DB_USER_TYPE_MANAGER = "manager";

	public static String MCLUSTER_INIT_STATUS_RUNNING = "running";

}
