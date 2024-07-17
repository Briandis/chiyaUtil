package chiya.security.crypto;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import javax.crypto.Cipher;

import chiya.core.base.io.FileUtil;

public class RSAUtil {

	/**
	 * 使用公钥加密数据
	 * 
	 * @param publicKeyString 公钥
	 * @param plainText       数据
	 * @return 加密后端数据
	 */
	public static String publicKeyEncrypt(String publicKeyString, String plainText) {
		try {
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			byte[] keyBytes = Base64.getDecoder().decode(publicKeyString);
			X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
			PublicKey publicKey = keyFactory.generatePublic(spec);
			// 初始化Cipher对象进行加密
			Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
			cipher.init(Cipher.ENCRYPT_MODE, publicKey);
			// 加密数据
			byte[] encryptedBytes = cipher.doFinal(plainText.getBytes());
			// 将加密后的字节数组转换为Base64编码的字符串
			return Base64.getEncoder().encodeToString(encryptedBytes);
		} catch (Exception e) {
			return null;
		}

	}

	/**
	 * 生成公钥和私钥
	 * 
	 * @param length 长度
	 */
	public static KeyInfo createRSAKey(int length) {
		try {
			KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
			keyPairGenerator.initialize(length); // 指定密钥长度
			KeyPair keyPair = keyPairGenerator.generateKeyPair();
			PublicKey publicKey = keyPair.getPublic();
			PrivateKey privateKey = keyPair.getPrivate();
			return new KeyInfo().chainPublicKey(publicKey).chainPrivateKey(privateKey);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 生成公钥和私钥
	 * 
	 * @param length 长度
	 */
	public static KeyInfo createRSAKey() {
		return createRSAKey(2048);
	}

	/**
	 * 创建并保存公钥和私钥
	 * 
	 * @param publicKeyPath  公钥
	 * @param privateKeyPath 私钥
	 * @param length         长度
	 */
	public static void cteateSavePublicAndPivateKey(String publicKeyPath, String privateKeyPath, int length) {
		KeyInfo keyInfo = createRSAKey(length);
		FileUtil.writeText(publicKeyPath, keyInfo.getPublicKeyString());
		FileUtil.writeText(privateKeyPath, keyInfo.getPrivateKeyString());
	}

	/**
	 * 创建并保存公钥和私钥
	 * 
	 * @param publicKeyPath  公钥
	 * @param privateKeyPath 私钥
	 */
	public static void cteateSavePublicAndPivateKey(String publicKeyPath, String privateKeyPath) {
		cteateSavePublicAndPivateKey(publicKeyPath, privateKeyPath, 2048);
	}

	/**
	 * 使用钥匙进行加密
	 * 
	 * @param key          公钥或私钥
	 * @param inputStream  输入流
	 * @param outputStream 输出流
	 */
	public static void encrypt(Key key, InputStream inputStream, OutputStream outputStream) {
		try {
			Cipher cipher = Cipher.getInstance("RSA");
			cipher.init(Cipher.ENCRYPT_MODE, key);

			byte[] buffer = new byte[245]; // RSA加密块大小限制
			int bytesRead;
			while ((bytesRead = inputStream.read(buffer)) != -1) {
				byte[] encryptedBytes = cipher.doFinal(buffer, 0, bytesRead);
				outputStream.write(encryptedBytes);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 使用钥匙进行解密
	 * 
	 * @param key          公钥或私钥
	 * @param inputStream  输入流
	 * @param outputStream 输出流
	 */
	public static void decrypt(Key key, InputStream inputStream, OutputStream outputStream) {

		try {
			Cipher cipher = Cipher.getInstance("RSA");
			cipher.init(Cipher.DECRYPT_MODE, key);
			byte[] buffer = new byte[256]; // RSA解密块大小
			int bytesRead;
			while ((bytesRead = inputStream.read(buffer)) != -1) {
				byte[] decryptedBytes = cipher.doFinal(buffer, 0, bytesRead);
				outputStream.write(decryptedBytes);
			}
			inputStream.close();
			outputStream.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	/**
	 * 公钥加密
	 * 
	 * @param publicKey   公钥或私钥
	 * @param inputData   输入数据
	 * @param isPublicKey 是否公钥还
	 * @param isEncrypt   是否是加密
	 * @return 加密后数据
	 */
	public static byte[] rsa(String key, byte[] inputData, boolean isPublicKey, boolean isEncrypt) {
		ByteArrayInputStream inputStream = new ByteArrayInputStream(inputData);
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		Key anykey = isPublicKey ? KeyInfo.publickeyToObject(key) : KeyInfo.privateKeyToObject(key);
		if (isEncrypt) {
			encrypt(anykey, inputStream, outputStream);
		} else {
			decrypt(anykey, inputStream, outputStream);
		}

		byte[] data = outputStream.toByteArray();
		try {
			inputStream.close();
			outputStream.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return data;
	}

	/**
	 * 公钥加密
	 * 
	 * @param publicKey 公钥字符串
	 * @param inputData 原始输入数据
	 * @return 加密后数据
	 */
	public static byte[] encryptPublicKey(String publicKey, String inputData) {
		return rsa(publicKey, inputData.getBytes(), true, true);
	}

	/**
	 * 私钥加密
	 * 
	 * @param privateKey 私钥字符串
	 * @param inputData  输入数据
	 * @return 加密后数据
	 */
	public static byte[] encryptPrivateKey(String privateKey, String inputData) {
		return rsa(privateKey, inputData.getBytes(), false, true);
	}

	/**
	 * 公钥加密
	 * 
	 * @param publicKey 公钥字符串
	 * @param inputData 原始输入数据
	 * @return 加密后数据
	 */
	public static String encryptPublicKeyBase64(String publicKey, String inputData) {
		byte[] data = rsa(publicKey, inputData.getBytes(), true, true);
		return Base64.getEncoder().encodeToString(data);
	}

	/**
	 * 私钥加密
	 * 
	 * @param privateKey 私钥字符串
	 * @param inputData  输入数据
	 * @return 加密后数据
	 */
	public static String encryptPrivateKeyBase64(String privateKey, String inputData) {
		byte[] data = rsa(privateKey, inputData.getBytes(), false, true);
		return Base64.getEncoder().encodeToString(data);
	}

	/**
	 * 公钥解密
	 * 
	 * @param publicKey 公钥字符串
	 * @param inputData 输入数据
	 * @return 加密后数据
	 */
	public static String decryptPublicKey(String publicKey, byte[] inputData) {
		byte[] data = rsa(publicKey, inputData, true, false);
		return new String(data);
	}

	/**
	 * 私钥解密
	 * 
	 * @param privateKey 私钥字符串
	 * @param inputData  输入数据
	 * @return 加密后数据
	 */
	public static String decryptPrivateKey(String privateKey, byte[] inputData) {
		byte[] data = rsa(privateKey, inputData, false, false);
		return new String(data);
	}

	/**
	 * 公钥解密
	 * 
	 * @param publicKey 公钥字符串
	 * @param inputData 输入数据
	 * @return 加密后数据
	 */
	public static String decryptPublicKey(String publicKey, String inputData) {
		byte[] data = rsa(publicKey, Base64.getDecoder().decode(inputData), true, false);
		return new String(data);
	}

	/**
	 * 私钥解密
	 * 
	 * @param privateKey 私钥字符串
	 * @param inputData  输入数据
	 * @return 加密后数据
	 */
	public static String decryptPrivateKey(String privateKey, String inputData) {
		byte[] data = rsa(privateKey, Base64.getDecoder().decode(inputData), false, false);
		return new String(data);
	}
}
