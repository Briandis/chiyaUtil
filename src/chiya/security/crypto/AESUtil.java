package chiya.security.crypto;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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
	/** 加密方式 */
	private static final String ALGORITHM = "AES";
	/** CTR方式 */
	private static final String TRANSFORMATION = "AES/CTR/NoPadding";

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
			SecretKeySpec skeySpec = new SecretKeySpec(key, ALGORITHM);
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

	/**
	 * 二进制文件加密
	 * 
	 * @param inputFile  文件保存路径
	 * @param outputFile 文件输出路径
	 * @param key        key
	 * @param iv         向量
	 */
	public static void encryptFile(String inputFile, String outputFile, String key, String iv) {
		fileAES(inputFile, outputFile, key, iv, false);
	}

	/**
	 * 二进制文件加密
	 * 
	 * @param inputFile  文件保存路径
	 * @param outputFile 文件输出路径
	 * @param key        key
	 */
	public static void encryptFile(String inputFile, String outputFile, String key) {
		fileAES(inputFile, outputFile, key, null, false);
	}

	/**
	 * 解密文件并生成二进制数组
	 * 
	 * @param inputFile  文件保存路径
	 * @param outputFile 文件输出路径
	 * @param key        key
	 * @param iv         向量
	 */
	public static void decryptFile(String inputFile, String outputFile, String key, String iv) {
		fileAES(inputFile, outputFile, key, iv, true);
	}

	/**
	 * 解密文件并生成二进制数组
	 * 
	 * @param inputFile  文件保存路径
	 * @param outputFile 文件输出路径
	 * @param key        key
	 */
	public static void decryptFile(String inputFile, String outputFile, String key) {
		fileAES(inputFile, outputFile, key, null, true);
	}

	/**
	 * 文件AES处理
	 * 
	 * @param inputFile  输入文件
	 * @param outputFile 输出文件
	 * @param key        key
	 * @param iv         向量
	 * @param isDecrypt  是否是解密模式
	 */
	public static void fileAES(String inputFile, String outputFile, String key, String iv, boolean isDecrypt) {
		try (FileInputStream fileInputStream = new FileInputStream(inputFile);
			FileOutputStream fileOutputStream = new FileOutputStream(outputFile)) {
			ctrModle(fileInputStream, fileOutputStream, key, iv, isDecrypt);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * CTR模式加密或解密
	 * 
	 * @param inputStream  输入流
	 * @param outputStream 输出流
	 * @param key          key
	 * @param iv           向量
	 * @param isDecrypt    是否是解密模式
	 */
	public static void ctrModle(InputStream inputStream, OutputStream outputStream, String key, String iv, boolean isDecrypt) {
		try {
			Cipher cipher = Cipher.getInstance(TRANSFORMATION);
			SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(), ALGORITHM);
			int model = isDecrypt ? Cipher.DECRYPT_MODE : Cipher.ENCRYPT_MODE;
			if (iv != null) {
				cipher.init(model, secretKeySpec, new IvParameterSpec(iv.getBytes()));
			} else {
				cipher.init(model, secretKeySpec);
			}
			byte[] buffer = new byte[8192];
			int read;
			// CTR模式处理
			while ((read = inputStream.read(buffer)) != -1) {
				byte[] decrypted = cipher.update(buffer, 0, read);
				if (decrypted != null) { outputStream.write(decrypted); }
				byte[] lastBlock = cipher.doFinal();
				if (lastBlock != null) { outputStream.write(lastBlock); }
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

}
