/*
 * @Title: Configuration.java
 * @Package com.letv.log.util
 * @Description: TODO
 * @author xufei1 <xufei1@letv.com>
 * @date 2012-12-9 下午3:31:22
 * @version V1.0
 *
 * Modification History:  
 * Date         Author      Version     Description  
 * -------------------------------------------------------------- 
 * 2012-12-9                          
 */
package com.letv.log.util;

import java.util.ResourceBundle;


/** 
 * <p>读取属性文件方法</p>
 * 
 * @author xufei1 <xufei1@letv.com>
 * Create at:2012-12-9 下午3:31:22
 */
public class Configuration {
	private static Configuration configuration = null;
	private static ResourceBundle resourceBundle = null;
	private static final String CONFIG_FILE = "letvLogConfig";

	private Configuration() {
		resourceBundle = ResourceBundle.getBundle(CONFIG_FILE);
	}

	public synchronized static Configuration getInstance() {
		if (null == configuration) {
			configuration = new Configuration();
		}

		return (configuration);
	}

	public String getValue(String key) {
		String result = null;		
		try{
		
			result = resourceBundle.getString(key);
					
				}catch(Exception e){
//					e.printStackTrace();
		}
				return result;
	}
}
