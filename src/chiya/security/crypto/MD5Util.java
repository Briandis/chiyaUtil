package chiya.security.crypto;

import java.math.BigInteger;
import java.security.MessageDigest;

/**
 * MD5工具类
 * 
 * @author chiya
 *
 */
public class MD5Util {
	/**
	 * 获取MD5值
	 * 
	 * @param input 输入字符
	 * @return MD5值
	 */
	public static String getMD5(String input) {
		return getMD5(input.getBytes());
	}

	/**
	 * 获取MD5值
	 * 
	 * @param input 输入字符
	 * @return MD5值
	 */
	public static String getMD5(byte[] input) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] messageDigest = md.digest(input);
			BigInteger no = new BigInteger(1, messageDigest);
			String hashtext = no.toString(16);
			while (hashtext.length() < 32) {
				hashtext = "0" + hashtext;
			}
			return hashtext;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
