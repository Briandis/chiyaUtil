package chiya.security.crypto;

import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import javax.crypto.Cipher;

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

}
