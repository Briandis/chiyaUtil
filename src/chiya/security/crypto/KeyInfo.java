package chiya.security.crypto;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

/**
 * 公钥、私钥对象
 * 
 * @author chiya
 *
 */
public class KeyInfo {
	/** 公钥字符串 */
	private String publicKeyString;
	/** 私钥字符串 */
	private String privateKeyString;
	/** 公钥 */
	private PublicKey publicKey;
	/** 私钥 */
	private PrivateKey privateKey;

	/**
	 * 获取公钥字符串
	 * 
	 * @return 公钥字符串
	 */
	public String getPublicKeyString() {
		return publicKeyString;
	}

	/**
	 * 设置公钥字符串
	 * 
	 * @param publicKeyString 公钥字符串
	 */
	public void setPublicKeyString(String publicKeyString) {
		this.publicKeyString = publicKeyString;
		// 转换对象
		publicKey = publickeyToObject(publicKeyString);
	}

	/**
	 * 公钥转换成对象
	 * 
	 * @param publicKeyString 公钥
	 * @return 公钥对象
	 */
	public static PublicKey publickeyToObject(String publicKeyString) {
		try {
			byte[] keyBytes = Base64.getDecoder().decode(publicKeyString);
			X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			return keyFactory.generatePublic(keySpec);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 链式添加公钥字符串
	 * 
	 * @param publicKeyString 公钥字符串
	 * @return 对象本身
	 */
	public KeyInfo chainPublicKeyString(String publicKeyString) {
		setPublicKeyString(publicKeyString);
		return this;
	}

	/**
	 * 获取私钥字符串
	 * 
	 * @return 私钥字符串
	 */
	public String getPrivateKeyString() {
		return privateKeyString;
	}

	/**
	 * 设置私钥字符串
	 * 
	 * @param privateKeyString 私钥字符串
	 */
	public void setPrivateKeyString(String privateKeyString) {
		this.privateKeyString = privateKeyString;
		// 转换对象
		privateKey = privateKeyToObject(privateKeyString);

	}

	/**
	 * 私钥转成对象
	 * 
	 * @param privateKeyString 私钥
	 * @return 私钥对象
	 */
	public static PrivateKey privateKeyToObject(String privateKeyString) {
		try {
			byte[] keyBytes = Base64.getDecoder().decode(privateKeyString);
			PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			return keyFactory.generatePrivate(keySpec);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 链式添加私钥字符串
	 * 
	 * @param privateKeyString 私钥字符串
	 * @return 对象本身
	 */
	public KeyInfo chainPrivateKeyString(String privateKeyString) {
		setPrivateKeyString(privateKeyString);
		return this;
	}

	/**
	 * 获取公钥
	 * 
	 * @return 公钥
	 */
	public PublicKey getPublicKey() {
		return publicKey;
	}

	/**
	 * 设置公钥
	 * 
	 * @param publicKey 公钥
	 */
	public void setPublicKey(PublicKey publicKey) {
		this.publicKey = publicKey;
		publicKeyString = Base64.getEncoder().encodeToString(publicKey.getEncoded());
	}

	/**
	 * 链式添加公钥
	 * 
	 * @param publicKey 公钥
	 * @return 对象本身
	 */
	public KeyInfo chainPublicKey(PublicKey publicKey) {
		setPublicKey(publicKey);
		return this;
	}

	/**
	 * 获取私钥
	 * 
	 * @return 私钥
	 */
	public PrivateKey getPrivateKey() {
		return privateKey;
	}

	/**
	 * 设置私钥
	 * 
	 * @param privateKey 私钥
	 */
	public void setPrivateKey(PrivateKey privateKey) {
		this.privateKey = privateKey;
		privateKeyString = Base64.getEncoder().encodeToString(privateKey.getEncoded());
	}

	/**
	 * 链式添加私钥
	 * 
	 * @param privateKey 私钥
	 * @return 对象本身
	 */
	public KeyInfo chainPrivateKey(PrivateKey privateKey) {
		setPrivateKey(privateKey);
		return this;
	}
}
