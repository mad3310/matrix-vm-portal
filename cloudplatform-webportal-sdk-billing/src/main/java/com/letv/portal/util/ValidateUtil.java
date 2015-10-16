package com.letv.portal.util;


/**
 * 验证工具类
 * @author lisuxiao
 *
 */
public class ValidateUtil {
	
	//判断是否是整数
	public static boolean isNumeric(String s){
	   if((s != null)&&(s.trim()!="")) {
		   return s.matches("^[0-9]*$");
	   } else {
		   return false;
	   }
	}
}
