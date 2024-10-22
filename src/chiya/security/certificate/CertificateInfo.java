package chiya.security.certificate;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import chiya.core.base.string.StringUtil;

/**
 * 签名信息
 * 
 * @author chiya
 *
 */
public class CertificateInfo {

	/** 有效时间 */
	private long effectiveTime = 0;
	/** 上下文 */
	private String context;

	/**
	 * 创建载荷
	 * 
	 * @param data          数据
	 * @param effectiveTime 有效时间
	 * @return 载荷
	 */
	public static String createLoad(String data, int effectiveTime) {
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
	 * 创建载荷
	 * 
	 * @return 载荷
	 */
	public String createLoad() {
		try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			DataOutputStream dataOutputStream = new DataOutputStream(outputStream)) {
			dataOutputStream.writeLong(effectiveTime);
			dataOutputStream.writeBytes(context);
			return StringUtil.encoderBASE64(outputStream.toByteArray());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 解析上下文
	 * 
	 * @param context 上下文
	 * @return 签名信息
	 */
	public static CertificateInfo parser(String context) {
		try (ByteArrayInputStream contextByte = new ByteArrayInputStream(StringUtil.decoderBASE64ToByte(context));
			DataInputStream contextData = new DataInputStream(contextByte)) {
			return new CertificateInfo()
				.chainEffectiveTime(contextData.readLong())
				.chainContext(new String(contextData.readAllBytes()));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

	}

	/**
	 * 检查是否超出有消息
	 * 
	 * @return true:已超出/false:未超出
	 */
	public boolean checkTimeOut() {
		return System.currentTimeMillis() > effectiveTime;
	}

	/**
	 * 获取有效时间
	 * 
	 * @return 有效时间
	 */
	public long getEffectiveTime() {
		return effectiveTime;
	}

	/**
	 * 设置有效时间
	 * 
	 * @param effectiveTime 有效时间
	 */
	public void setEffectiveTime(long effectiveTime) {
		this.effectiveTime = effectiveTime;
	}

	/**
	 * 链式添加有效时间
	 * 
	 * @param effectiveTime 有效时间
	 * @return 对象本身
	 */
	public CertificateInfo chainEffectiveTime(long effectiveTime) {
		setEffectiveTime(effectiveTime);
		return this;
	}

	/**
	 * 获取上下文
	 * 
	 * @return 上下文
	 */
	public String getContext() {
		return context;
	}

	/**
	 * 设置上下文
	 * 
	 * @param context 上下文
	 */
	public void setContext(String context) {
		this.context = context;
	}

	/**
	 * 链式添加上下文
	 * 
	 * @param context 上下文
	 * @return 对象本身
	 */
	public CertificateInfo chainContext(String context) {
		setContext(context);
		return this;
	}
}
