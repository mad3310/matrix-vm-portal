package com.letv.common.util;

import java.util.Random;

/**Program Name: PasswordRandom <br>
 * Description:  随机生成密码串<br>
 * @author name: liuhao1 <br>
 * Written Date: 2014年11月12日 <br>
 * Modified By: <br>
 * Modified Date: <br>
 */
public class PasswordRandom {

	private final static char[] chars = { '0', '1', '2', '3', '4', '5','6', '7', '8', '9', 
		'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r','s', 't', 'u', 'v', 'w', 'x', 'y', 'z',
		'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R','S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
	private final static int charsSize = 62;
	private static int length = 8;

	
	/**Methods Name: genStr <br>
	 * Description: 随机生成customLength位密码，默认8位，包含数字、大小写字母<br>
	 * @author name: liuhao1
	 * @param customLength
	 * @return
	 */
	public static String genStr(Integer... customLength) {
		Random r = new Random();
		int j;
		char c;
		StringBuffer str = new StringBuffer();
		for (int i = 0; i < (customLength.length > 0?customLength[0]:length); i++) {
			j = r.nextInt(charsSize);
			c = chars[j];
			str.append(c);
		}
		return str.toString();
	}
	
	public static void main(String[] args) {
		for (int i = 0; i < 100000; i++) {
			System.out.println(genStr(2));
		}
	}

}