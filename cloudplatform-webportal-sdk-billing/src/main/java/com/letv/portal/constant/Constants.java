package com.letv.portal.constant;

import java.util.HashMap;
import java.util.Map;

public class Constants {

	public static final String KAPTCHA_COOKIE_NAME = "captcha_cache_id_";

	public static final String PAY_URL = "http://api.zhifu.letv.com/pay/6";//支付宝支付接口&银行卡支付接口
	
	public static final String WX_URL = "http://api.zhifu.letv.com/pay/wxcommon/24";//微信下单
	
	public static final String QUERY_URL = "http://api.zhifu.letv.com/pay/querystat";//查询订单号接口
	
	public static final String SIGN_KEY = "6033bd29078a5e094bf53748238aae82";//支付验证所需key
	
	public static final String ALI_PAY_PATTERN = "1";//支付宝支付方式
	
	public static final String WX_PAY_PATTERN = "2";//微信支付方式
	
	public static final long PRODUCT_VM = 2;//云主机
	
	public static final long PRODUCT_VOLUME = 3;//云硬盘
	
	public static final long PRODUCT_FLOATINGIP = 4;//公网IP
	
	public static final long PRODUCT_ROUTER = 5;//路由器
	
	public static final String SERVICE_PROVIDER_OPENSTACK = "openstack";//服务提供方
	
	public static Map<Long, String> productInfo = new HashMap<Long, String>();//产品信息缓存

}
