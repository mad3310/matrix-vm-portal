package com.letv.common.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.util.Calendar;
import java.util.List;
import java.util.StringTokenizer;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;

public class StringTool {

	private StringTool() {
	}

	/**
	 * 把数组中的字符串给你加上你想加的隔断,比如逗号.
	 * 
	 * @param list
	 * @param delim
	 * @return
	 */
	static public String getArrayToString(String[] array, String delim) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < array.length; i++) {
			if (i != 0) {
				sb.append(delim).append(array[i]);
			} else {
				sb.append(array[i]);
			}
		}
		return sb.toString();
	}

	/**
	 * 把list中的字符串给你加上你想加的隔断,比如逗号.
	 * 
	 * @param list
	 * @param delim
	 * @return
	 */
	static public String getListToString(List<String> list, String delim) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < list.size(); i++) {
			if (i == 0) {
				sb.append(list.get(i));
			} else {
				sb.append(delim).append(list.get(i));
			}
		}
		return sb.toString();
	}

	/**
	 * 把ip地址变成数字。
	 * 
	 * @param ip
	 * @return
	 */
	public static long IPToLong(String ip) {
		long result = 0;
		String[] ip_feild = ip.split("\\.");
		try {
			for (int i = 0; i < ip_feild.length; i++) {
				result += Long.parseLong(ip_feild[i]) << (8 * (3 - i));
			}
		} catch (Exception e) {
		}
		return result;
	}

	/**
	 * 将字符串用MD5加密 主要用户密码加密和数据唯一性验证
	 * 
	 * @param password
	 *            String
	 * @return String
	 */
	public static String EncodedByMD5(String password) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(password.getBytes());
			return byte2string(md.digest());
		} catch (Exception e) {
			return password;
		}
	}

	/**
	 * 二进制转字符串 给EncodedByMD5用的.
	 * 
	 * @param b
	 *            byte[]
	 * @return String
	 */
	private static String byte2string(byte[] b) {
		StringBuilder hs = new StringBuilder(100);
		for (int n = 0; n < b.length; n++) {
			hs.append(byte2fex(b[n]));
		}
		return hs.toString();
	}

	/**
	 * 将byte转成16Fex 给EncodedByMD5用的.
	 * 
	 * @param ib
	 *            byte
	 * @return String
	 */
	private static String byte2fex(byte ib) {
		char[] Digit = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a',
				'b', 'c', 'd', 'e', 'f' };
		char[] ob = new char[2];
		ob[0] = Digit[(ib >>> 4) & 0X0F];
		ob[1] = Digit[ib & 0X0F];
		String s = new String(ob);
		return s;
	}

	/**
	 * 全部替换字符串里符合指定内容的为其它内容，与String类中不同，它使用的不是正则表达式的。
	 * 
	 * @param String
	 *            strOriginal,原字符串内容
	 * @param String
	 *            strOld, 需要替换的内容
	 * @param String
	 *            strNew, 用来替换的内容
	 * @return String, 字符串替换后的内容
	 */
	public static String replace(String strOriginal, String strOld,
			String strNew) {
		int i = 0;
		StringBuilder strBuffer = new StringBuilder(strOriginal);
		while ((i = strOriginal.indexOf(strOld, i)) >= 0) {
			strBuffer.delete(i, i + strOld.length());
			strBuffer.insert(i, strNew);
			i = i + strNew.length();
			strOriginal = strBuffer.toString();
		}
		return strOriginal;
	}

	public static String formatDbInStr(String str) {
		StringBuilder strBuffer = new StringBuilder();
		if (str != null) {
			String[] array = str.split(",");
			int j = 0;
			for (String s : array) {
				if (StringUtils.isNotBlank(s) && StringUtils.isNumeric(s)) {
					if (j > 0) {
						strBuffer.append(",");
					}
					strBuffer.append(s);
					j++;
				}
			}
		}
		return strBuffer.toString();
	}

	/**
	 * 返回某长度的字符串 中文算俩，英文算一个，
	 * 
	 * @param str
	 * @param length
	 * @throws UnsupportedEncodingException
	 */
	public static String getStringLen(String str, int length)
			throws UnsupportedEncodingException {
		// System.out.println(str.getBytes().length);
		int n = 0;
		int m = 0;
		for (int i = 0; i < str.length(); i++) {
			String s = "" + str.charAt(i);
			if (s.getBytes("gbk").length == 2) {
				n += 2;
			} else {
				n++;
			}
			if (n > length) {
				m = i;
				break;
			}
		}
		if (m == 0)
			return str;
		else
			return str.substring(0, m);

	}

	/**
	 * 是否含有中文
	 * 
	 * @param str
	 * @return
	 */

	public static boolean includeChinese(String str) {
		if (StringUtils.isEmpty(str)) {
			return false;
		}

		for (int i = 0; i < str.length(); i++) {
			char ch = str.charAt(i);
			if (isChinese(ch)) {
				return true;
			}
		}
		return false;

	}

	/**
	 * 这个char是否是中文，注意，只能判断一个char哦。
	 * 
	 * @param ch
	 * @return
	 */
	public static boolean isChinese(char ch) {
		Character.UnicodeBlock ub = Character.UnicodeBlock.of(ch);

		if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
				|| ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
				|| ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
				|| ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
				|| ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
				|| ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
			return true;
		}

		return false;
	}

	/**
	 * 创建32位UUID随机码。
	 * 
	 * @return
	 */
	public static String createUUID() {
		return UUID.randomUUID().toString().replace("-", "");
	}

	/**
	 * 通过时间戳产生随机码,全是数字,此时间戳在此服务器不会重复.
	 * 
	 * @return
	 */
	public static synchronized String createKey() {
		String key = String.valueOf(System.currentTimeMillis());
		try {
			Thread.currentThread().sleep(1);
		} catch (InterruptedException e) {
			// do nothing
		}
		return key;
	}

}
