package com.letv.common.util;

import java.security.MessageDigest;

/**Program Name: PasswordEncoder <br>
 * Description:  提供MD5或SHA的salt加密 算法<br>
 * @author name: liuhao1 <br>
 * Written Date: 2014年10月15日 <br>
 * Modified By: <br>
 * Modified Date: <br>
 */
public class PasswordEncoder {

	private final static String[] hexDigits = { "0", "1", "2", "3", "4", "5",
			"6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };

	private static String encode(String rawPass,String salt,String algorithm) {
		String result = null;
		try {
			MessageDigest md = MessageDigest.getInstance(algorithm);
			// 加密后的字符串
			result = byteArrayToHexString(md.digest(mergePasswordAndSalt(
					rawPass,salt).getBytes("utf-8")));
		} catch (Exception ex) {
		}
		return result;
	}
	
	private static boolean isPasswordValid(String encPass, String rawPass,String salt,String algorithm) {
		String pass1 = "" + encPass;
		String pass2 = encode(rawPass,salt,algorithm);

		return pass1.equals(pass2);
	}

	private static String mergePasswordAndSalt(String password,String salt) {
		if (password == null) {
			password = "";
		}
		if ((salt == null) || "".equals(salt)) {
			return password;
		} else {
			return password + "{" + salt.toString() + "}";
		}
	}

	private static String byteArrayToHexString(byte[] b) {
		StringBuffer resultSb = new StringBuffer();
		for (int i = 0; i < b.length; i++) {
			resultSb.append(byteToHexString(b[i]));
		}
		return resultSb.toString();
	}

	private static String byteToHexString(byte b) {
		int n = b;
		if (n < 0)
			n = 256 + n;
		int d1 = n / 16;
		int d2 = n % 16;
		return hexDigits[d1] + hexDigits[d2];
	}
	
	
	/**Methods Name: encode <br>
	 * Description: 提供MD5的salt加密<br>
	 * @author name: liuhao1
	 * @param rawPass  未加密字符串
	 * @param salt 加密盐
	 * @return 加密后的字符串
	 */
	public static String md5Encode(String rawPass,String salt) {
		return encode(rawPass, salt, "MD5");
	}
	
	/**Methods Name: isPasswordValid4MD5 <br>
	 * Description: 提供MD5的salt加密对比<br>
	 * @author name: liuhao1
	 * @param encPass  加密后的字符串
	 * @param rawPass  未加密的字符串
	 * @param salt  加密盐
	 * @return
	 */
	public static boolean isPasswordValid4MD5(String encPass, String rawPass,String salt) {
		return isPasswordValid(encPass, rawPass, salt,"MD5");

	}
	
	
	/**Methods Name: shaEncode <br>
	 * Description: 提供SHA的salt加密<br>
	 * @author name: liuhao1
	 * @param rawPass 未加密的字符串
	 * @param salt 加密盐
	 * @return 加密后的字符串
	 */
	public static String shaEncode(String rawPass,String salt) {
		return encode(rawPass, salt, "SHA");
	}
	
	/**Methods Name: isPasswordValid4SHA <br>
	 * Description: 提供SHA的salt加密对比<br>
	 * @author name: liuhao1
	 * @param encPass 加密后的字符串
	 * @param rawPass 未加密的字符串
	 * @param salt 加密盐
	 * @return
	 */
	public static boolean isPasswordValid4SHA(String encPass, String rawPass,String salt) {
		return isPasswordValid(encPass, rawPass, salt,"SHA");

	}

}