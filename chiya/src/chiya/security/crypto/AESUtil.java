package chiya.security.crypto;

import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * AUS解密工具
 * 
 * @author brain
 *
 */
public class AESUtil {
	/**
	 * base64解码
	 * 
	 * @param baseString BASE64字符串
	 * @return 解密后的byte二进制
	 * @throws Exception 解密失败异常
	 */
	public static byte[] base64StringDecoding(String baseString) throws Exception {
		return Base64.getDecoder().decode(baseString);
	}

	/**
	 * AES128 CBC模式解密
	 * 
	 * @param sSrc Base64字符串
	 * @param key  密钥
	 * @param ivs  向量
	 * @return 解密后字符串
	 */
	public static String AES128CBCStringDecoding(byte[] sSrc, String key, String ivs) {
		try {
			if (key == null) { return null; }
			if (key.length() != 16) { return null; }
			if (ivs != null && ivs.length() != 16) { return null; }
			byte[] raw = key.getBytes("ASCII");
			SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			IvParameterSpec iv = new IvParameterSpec(ivs.getBytes());
			cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
			byte[] original = cipher.doFinal(sSrc);
			String originalString = new String(original, "utf-8");
			return originalString;
		} catch (Exception ex) {
			return null;
		}
	}

	/**
	 * 解密
	 * 
	 * @param data 加密数据
	 * @param key  sessionKey
	 * @param iv   向量
	 * @return 解密后字符串
	 */
	public static String decrypt(String data, String key, String iv) {
		try {
			return AES128CBCStringDecoding(base64StringDecoding(data), key, iv);
		} catch (Exception ex) {
			return null;
		}
	}
}
