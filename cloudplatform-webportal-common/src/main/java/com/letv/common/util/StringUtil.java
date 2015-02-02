package com.letv.common.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.lang.StringUtils;

/**
 * Pinyin support
 * 
 * @author zxwu
 */
public final class StringUtil {
    private static final ThreadLocal<MessageDigest> DIGESTER_CONTEXT = new ThreadLocal<MessageDigest>() {
        protected synchronized MessageDigest initialValue() {
            try {
                return MessageDigest.getInstance("MD5");
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            }
        }
    };

    private static final char[] HEX_DIGITS = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd',
            'e', 'f' };

    /**
     * Constructor
     */
    private StringUtil() {
    }

    /**
	 * 通过文件二进制数据计算md5值
	 * 
	 * @param fileData
	 *            文件二进制数据
	 * @return md5
	 */
    public static String getMd5(byte[] fileData) {
        MessageDigest digester = DIGESTER_CONTEXT.get();
        digester.update(fileData);
        byte[] md5Bytes = digester.digest();

        int length = md5Bytes.length;
        char[] md5String = new char[length * 2];
        int k = 0;
        for (int i = 0; i < length; i++) {
            byte b = md5Bytes[i];
            md5String[k++] = HEX_DIGITS[b >>> 4 & 0xf];
            md5String[k++] = HEX_DIGITS[b & 0xf];
        }
        return new String(md5String);
    }

    /**
	 * 截取指定字节数的字符串，注意是按字节数截取
	 * 
	 * @param str
	 *            str
	 * @param length
	 *            length
	 * @return result
	 */
    public static String truncate(String str, int length) {
        if (!StringUtils.isEmpty(str) && length > 3) {
            try {
                byte[] b = str.getBytes("GBK");
                if (b.length > length) {
                    int counterOfDoubleByte = 0;
                    length -= 3;
                    for (int i = 0; i < length; i++) {
                        if (b[i] < 0) {
                            counterOfDoubleByte++;
                        }
                    }
                    if (counterOfDoubleByte % 2 == 0) {
                        str = new String(b, 0, length, "GBK") + "...";
                    } else {
                        str = new String(b, 0, length - 1, "GBK") + "...";
                    }
                }
            } catch (UnsupportedEncodingException e) {
                return str;
            }
        }
        return str;
    }
    
	
	/**
	 * <p>
	 * 判断obj是否值为空，若为null赋值""否则返回obj.toSring()
	 * </p>
	 * 
	 * @author xufei<xufei1@letv.com>
	 * @param obj
	 * @return str
	 */
	public static String objectToString(Object obj) {
		return null == obj ? "" : obj.toString();
	}
	/**
	 * 根据缓存中的key值和前缀截取ID
	 * @param key
	 * @param cacheKey
	 * @return
	 */
	public static String getIdFromCacheKey(String key,String cacheKey){
		String id="";
		if(cacheKey.indexOf(key) != -1){
			int start = cacheKey.lastIndexOf("_")+1;
			int end = cacheKey.length();
			id = cacheKey.substring(start, end);
		}
		return id;
	}
	
	/**Methods Name: transSqlCharacter <br>
	 * Description: sql like查询 替换特殊字符<br>
	 * @author name: liuhao1
	 * @param str
	 * @return
	 */
	public static String transSqlCharacter(String str) {
		if(!StringUtils.isEmpty(str)) {
			str = str.replace("_", "^_");
			str = str.replace("%", "^%");
		}
		return str;
	}
}
