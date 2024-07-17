package chiya.security.crypto;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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
			cipher.init(model, secretKeySpec, new IvParameterSpec(iv != null ? iv.getBytes() : new byte[16]));
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

	/**
	 * 加密成BASE64
	 * 
	 * @param inputData 输入字符串
	 * @param key       key
	 * @param iv        向量
	 * @return BASE64编码的二进制数据
	 */
	public static String encryptToBase64(String inputData, String key, String iv) {
		byte[] data = aes(inputData.getBytes(), key, iv, false);
		return Base64.getEncoder().encodeToString(data);
	}

	/**
	 * 加密成BASE64
	 * 
	 * @param inputData 输入字符串
	 * @param key       key
	 * @return BASE64编码的二进制数据
	 */
	public static String encryptToBase64(String inputData, String key) {
		return encryptToBase64(inputData, key, null);
	}

	/**
	 * AES加密
	 * 
	 * @param inputData 输入字符串
	 * @param key       key
	 * @param iv        向量
	 * @return 二进制数据
	 */
	public static byte[] encrypt(String inputData, String key, String iv) {
		return aes(inputData.getBytes(), key, iv, false);
	}

	/**
	 * AES加密
	 * 
	 * @param inputData 输入字符串
	 * @param key       key
	 * @return 二进制数据
	 */
	public static byte[] encrypt(String inputData, String key) {
		return encrypt(inputData, key, null);
	}

	/**
	 * AES解密
	 * 
	 * @param inputData 输入数据
	 * @param key       key
	 * @param iv        向量
	 * @return 解密后数据
	 */
	public static String decrypt(String inputData, String key, String iv) {
		byte[] data = aes(Base64.getDecoder().decode(inputData), key, iv, true);
		return new String(data);
	}

	/**
	 * AES解密
	 * 
	 * @param inputData 文件保存路径
	 * @param key       key
	 * @return 解密后数据
	 */
	public static String decrypt(String inputData, String key) {
		return decrypt(inputData, key, null);
	}

	/**
	 * AES解密
	 * 
	 * @param inputData 文件保存路径
	 * @param key       key
	 * @param iv        向量
	 * @return 解密后数据
	 */
	public static String decrypt(byte[] inputData, String key, String iv) {
		byte[] data = aes(inputData, key, iv, true);
		return new String(data);
	}

	/**
	 * AES解密
	 * 
	 * @param inputData 文件保存路径
	 * @param key       key
	 * @return 解密后数据
	 */
	public static String decrypt(byte[] inputData, String key) {
		return decrypt(inputData, key, null);
	}

	/**
	 * AES处理
	 * 
	 * @param inputData 输入输入
	 * @param key       key
	 * @param iv        向量
	 * @param isDecrypt 是否是解密模式
	 * @return 二进制数组
	 */
	public static byte[] aes(byte[] inputData, String key, String iv, boolean isDecrypt) {
		try (InputStream inputStream = new ByteArrayInputStream(inputData);
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
			ctrModle(inputStream, outputStream, key, iv, isDecrypt);
			return outputStream.toByteArray();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
