package chiya.security.crypto;

import java.io.UnsupportedEncodingException;
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
	 */
	public static byte[] base64StringDecoding(String baseString) {
		return Base64.getDecoder().decode(baseString);
	}

	/**
	 * AES128 CBC模式解密 AES/CBC/PKCS5Padding
	 * 
	 * @param sSrc Base64字符串
	 * @param key  密钥
	 * @param ivs  向量
	 * @return 解密后字符串
	 */
	@Deprecated
	public static String AES128CBCStringDecoding(byte[] sSrc, String key, String ivs) {
		try {
			return decryptAES(
				sSrc,
				getCipher("AES/CBC/PKCS5Padding"),
				key.getBytes("ASCII"),
				ivs
			);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 通用AES解密
	 * 
	 * @param bytes  字节数组
	 * @param cipher 加密方式
	 * @param key    键
	 * @param ivs    向量
	 * @return 解密后的字符串
	 */
	public static String decryptAES(byte bytes[], Cipher cipher, byte key[], String ivs) {
		try {
			SecretKeySpec skeySpec = new SecretKeySpec(key, "AES");
			if (ivs != null) {
				cipher.init(Cipher.DECRYPT_MODE, skeySpec, new IvParameterSpec(ivs.getBytes()));
			} else {
				cipher.init(Cipher.DECRYPT_MODE, skeySpec);
			}
			return new String(cipher.doFinal(bytes), "utf-8");
		} catch (Exception ex) {
			return null;
		}
	}

	/**
	 * 通用AES解密
	 * 
	 * @param bytes  字节数组
	 * @param cipher 加密方式
	 * @param key    键
	 * @param ivs    向量
	 * @return 解密后的字符串
	 */
	public static String decryptAES(byte bytes[], String cipher, byte key[], String ivs) {
		return decryptAES(bytes, getCipher(cipher), key, ivs);
	}

	/**
	 * Base通用AES解密
	 * 
	 * @param context 加密的数据
	 * @param cipher  加密方式
	 * @param key     键
	 * @param ivs     向量
	 * @return 解密后的字符串
	 */
	public static String decryptAES(String context, String cipher, String key, String ivs) {
		return decryptAES(base64StringDecoding(context), getCipher(cipher), base64StringDecoding(key), ivs);
	}

	/**
	 * 解密 AES128 CBC模式解密 AES/CBC/PKCS5Padding
	 * 
	 * @param data 加密数据
	 * @param key  sessionKey
	 * @param iv   向量
	 * @return 解密后字符串
	 */
	public static String decrypt(String data, String key, String iv) {
		try {
			return decryptAES(
				base64StringDecoding(data),
				getCipher("AES/CBC/PKCS5Padding"),
				key.getBytes("ASCII"),
				iv
			);
		} catch (UnsupportedEncodingException e) {
			return null;
		}
	}

	/**
	 * 获取加密方式实例
	 * 
	 * @param string 加密方式
	 * @return Cipher
	 */
	public static Cipher getCipher(String string) {
		try {
			return Cipher.getInstance(string);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
