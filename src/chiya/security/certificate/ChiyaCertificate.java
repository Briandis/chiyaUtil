package chiya.security.certificate;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import chiya.core.base.exception.Assert;
import chiya.core.base.string.StringUtil;

/**
 * 数字签名
 * 
 * @author chiya
 *
 */
public class ChiyaCertificate {

	/** 加密的key */
	public String key = "chiya~certificate";
	/** 有效时间 */
	public int effectiveTime = 1000 * 60 * 60 * 24 * 30;
	/** 是否需要有效期异常 */
	private boolean timeOut = false;

	/** 无参构造方法 */
	public ChiyaCertificate() {}

	/**
	 * 数字签名
	 * 
	 * @param key           eky
	 * @param effectiveTime 有效时间
	 * @param timeOut       超出有效期抛出异常
	 */
	public ChiyaCertificate(String key, int effectiveTime, boolean timeOut) {
		this.key = key;
		this.effectiveTime = effectiveTime;
		this.timeOut = timeOut;
	}

	/** 启用超时时间 */
	public void enableTimeOut() {
		timeOut = true;
	}

	/** 禁用超时时间 */
	public void disableTimeOut() {
		timeOut = false;
	}

	/**
	 * 生成凭证
	 * 
	 * @param data          数据
	 * @param effectiveTime 有效时间
	 * @return 凭证
	 */
	public String createCertificate(String data) {
		return createCertificate(data, effectiveTime);
	}

	/**
	 * 生成凭证
	 * 
	 * @param data          数据
	 * @param effectiveTime 有效时间
	 * @return 凭证
	 */
	public String createCertificate(String data, int effectiveTime) {
		// 构建载荷
		String context = createLoad(data, effectiveTime);
		// 签名
		String sign = StringUtil.encoderSHA256(key + context + key);
		// 生成令牌
		return createToken(sign, context);
	}

	/**
	 * 构建完整签名
	 * 
	 * @param sign    签名
	 * @param context 上下文
	 * @return token
	 */
	private String createToken(String sign, String context) {
		String data = sign + context;
		try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			DataOutputStream dataOutputStream = new DataOutputStream(outputStream)) {
			dataOutputStream.writeInt(sign.length());
			dataOutputStream.writeBytes(data);
			return StringUtil.encoderBASE64(outputStream.toByteArray());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

	}

	/**
	 * 创建载荷
	 * 
	 * @param data          数据
	 * @param effectiveTime 有效时间
	 * @return 载荷
	 */
	public String createLoad(String data, int effectiveTime) {
		try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			DataOutputStream dataOutputStream = new DataOutputStream(outputStream)) {
			long time = System.currentTimeMillis() + effectiveTime;
			dataOutputStream.writeLong(time);
			dataOutputStream.writeBytes(data);
			return StringUtil.encoderBASE64(outputStream.toByteArray());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 获取令牌中的数据
	 * 
	 * @param certificate 已经生成的令牌
	 * @return 数据·
	 */
	public CertificateInfo getData(String certificate) {
		try {
			ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(StringUtil.decoderBASE64ToByte(certificate));
			DataInputStream dataInputStream = new DataInputStream(byteArrayInputStream);
			// 解析结构
			int signSize = dataInputStream.readInt();
			String token = new String(dataInputStream.readAllBytes());
			dataInputStream.close();
			byteArrayInputStream.close();

			String sign = token.substring(0, signSize);
			String context = token.substring(signSize, token.length());
			// 校验
			String signString = StringUtil.encoderSHA256(key + context + key);
			Assert.isNotEqualString(signString, sign, "数字签名不一致");

			CertificateInfo certificateInfo = CertificateInfo.parser(context);
			if (timeOut) { Assert.isTrue(certificateInfo.checkTimeOut(), "签名已超出有效期"); }
			return certificateInfo;

		} catch (IOException e) {
			throw new RuntimeException(e);
		}

	}

}
