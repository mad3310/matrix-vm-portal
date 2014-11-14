package com.letv.common.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DataFormat {
    private static SimpleDateFormat compactDate = new SimpleDateFormat(
            "yyyyMMdd");

    private static SimpleDateFormat ordDate = new SimpleDateFormat("yyyy-MM-dd");

    private static SimpleDateFormat ordSecond = new SimpleDateFormat(
            "yyyy-MM-dd HH:mm:ss");

    private static SimpleDateFormat compactHmSecond = new SimpleDateFormat(
            "HHmmss");

    public static String compactDate(Date srcDate) {
        return compactDate.format(srcDate);
    }

    public static String ordDate(Date srcDate) {
        return ordDate.format(srcDate);
    }

    public static String ordSecond(Date srcDate) {
        return ordSecond.format(srcDate);
    }

    public static String compactHmSecond(Date srcDate) {
        return compactHmSecond.format(srcDate);
    }

    public static SimpleDateFormat getCompactDate() {
        return compactDate;
    }

    public static void setCompactDate(SimpleDateFormat compactDate) {
        DataFormat.compactDate = compactDate;
    }

    public static SimpleDateFormat getCompactHmSecond() {
        return compactHmSecond;
    }

    public static void setCompactHmSecond(SimpleDateFormat compactHmSecond) {
        DataFormat.compactHmSecond = compactHmSecond;
    }

    public static SimpleDateFormat getOrdDate() {
        return ordDate;
    }

    public static void setOrdDate(SimpleDateFormat ordDate) {
        DataFormat.ordDate = ordDate;
    }

    public static SimpleDateFormat getOrdSecond() {
        return ordSecond;
    }

    public static void setOrdSecond(SimpleDateFormat ordSecond) {
        DataFormat.ordSecond = ordSecond;
    }
    
    public static String formatNumberToString(int number, int length, String prefix){
		String value = String.valueOf(number);
		StringBuilder sb = new StringBuilder();
		if(value.length() >= length)
			return sb.append(prefix).append(value).toString();
		sb.append(prefix);
		for(int i=0;i<(length-value.length());i++){
			sb.append("0");
		}
		return sb.append(value).toString();
	}
	
	public static int getNumberFromFormatedString(String value,int length, String prefix){
			String regstr= new StringBuilder(prefix)
			                   .append("[0-9]{")
			                   .append(String.valueOf(length))
			                   .append("}")
			                   .toString();
			if(value==null || !value.matches(regstr))return -1;
			return Integer.valueOf(value.substring(prefix.length()));
	}
	
	public static String formatBigDecimalToString(BigDecimal bd) {  
        if (bd == null) {  
            return "";  
        }  
        bd.setScale(2, RoundingMode.HALF_UP);  
        DecimalFormat format = new DecimalFormat("#,##0.00");  
        return format.format(bd);  
    }  

}
