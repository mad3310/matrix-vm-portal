package com.letv.log.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public final class StringUtil {
    /**
     * <p>时间转换Date类型转换成String</p>
     * @author xufei<xufei1@letv.com>
     * @param date
     * @param formate
     * @return result
     */
    public static String dateToString(Date date,String format){
    	SimpleDateFormat sdf=new SimpleDateFormat(format);  
    	String result=sdf.format(date);  
    	
    	return result;
    }

    
}
