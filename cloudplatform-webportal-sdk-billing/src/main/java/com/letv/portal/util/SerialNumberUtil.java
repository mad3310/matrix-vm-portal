package com.letv.portal.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

/**
 * @Title: SerialNumberUtil.java
 * @Package com.letv.portal.util
 * @Description: 生产订单订阅的编码工具类
 * @author lisuxiao
 * @date 2015年9月11日 下午4:27:54
 */
public class SerialNumberUtil {
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
	private static Random r = new Random();
	
	/**
	  * @Title: getNumber
	  * @Description: 获取唯一编码(类型+年月日时分秒+3位random随机数+3位uuid随机数)
	  * @param type 类型
	  * @return String   
	  * @throws 
	  * @author lisuxiao
	  * @date 2015年9月11日 下午5:09:42
	  */
	public static String getNumber(int type) {
		StringBuffer sb = new StringBuffer();
		String uuid = UUID.randomUUID().toString();
		char[] c = uuid.toCharArray();
		String num = "";
		for(int i=r.nextInt(10); i<c.length; i++) {
			if(c[i]>='0' && c[i]<='9') {
				num += c[i];
				if(num.length()==3) {
					break;
				}
			}
		}
		sb.append(type).append(sdf.format(new Date())).append(r.nextInt(900)+100).append(num);
		return sb.toString();
	}
}
