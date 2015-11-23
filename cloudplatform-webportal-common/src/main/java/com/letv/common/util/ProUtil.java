package com.letv.common.util;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ProUtil {
	private static Properties p = new Properties();  
	  
    /** 
     * 读取properties配置文件信息 
     */  
	
	
	static{ 
		try {  
			String filePath = System.getProperty("user.dir") + "/src/config/data.properties";
			InputStream in = new BufferedInputStream(new FileInputStream(filePath));  
	        p.load(in);
		} catch (IOException e) {  
            System.out.println("读取配置信息出错！");  
        }  
	}
	
	
    /** 
     * 根据key得到value的值 
     */  
    public static String getValue(String key)  
    {  
       
    	return p.getProperty(key);  
    }  
    
}
