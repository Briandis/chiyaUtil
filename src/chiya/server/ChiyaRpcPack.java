package chiya.server;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

/**
 * RPC报文结构<br>
 * 0-4byte为校验位字符，负责对其<br>
 * 5-9字节为头部结构大小<br>
 * 10-14字节为数据大小<br>
 * 剩余为数据段
 * 
 * @author chiya
 *
 */
public class ChiyaRpcPack {

	/** RPC头部包 */
	private ChiyaRpcHead chiyaRpcHead;
	/** RPC数据 */
	private byte[] data;
	/** IP */
	private String ip;

	/**
	 * 获取RPC头部包
	 * 
	 * @return RPC头部包
	 */
	public ChiyaRpcHead getChiyaRpcHead() {
		return chiyaRpcHead;
	}

	/**
	 * 设置RPC头部包
	 * 
	 * @param chiyaRpcHead RPC头部包
	 */
	public void setChiyaRpcHead(ChiyaRpcHead chiyaRpcHead) {
		this.chiyaRpcHead = chiyaRpcHead;
	}

	/**
	 * 链式添加RPC头部包
	 * 
	 * @param chiyaRpcHead RPC头部包
	 * @return 对象本身
	 */
	public ChiyaRpcPack chainChiyaRpcHead(ChiyaRpcHead chiyaRpcHead) {
		setChiyaRpcHead(chiyaRpcHead);
		return this;
	}

	/**
	 * 获取RPC数据
	 * 
	 * @return RPC数据
	 */
	public byte[] getData() {
		return data;
	}

	/**
	 * 设置RPC数据
	 * 
	 * @param data RPC数据
	 */
	public void setData(byte[] data) {
		this.data = data;
	}

	/**
	 * 链式添加RPC数据
	 * 
	 * @param data RPC数据
	 * @return 对象本身
	 */
	public ChiyaRpcPack chainData(byte[] data) {
		setData(data);
		return this;
	}

	/**
	 * 获取IP
	 * 
	 * @return IP
	 */
	public String getIp() {
		return ip;
	}

	/**
	 * 设置IP
	 * 
	 * @param ip IP
	 */
	public void setIp(String ip) {
		this.ip = ip;
	}

	/**
	 * 链式添加IP
	 * 
	 * @param ip IP
	 * @return 对象本身
	 */
	public ChiyaRpcPack chainIp(String ip) {
		setIp(ip);
		return this;
	}

	/**
	 * 转化
	 * 
	 * @return 字节数组
	 */
	public byte[] toByte() {
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		try {
			byteArrayOutputStream.write("chiya".getBytes());
			byte[] head = chiyaRpcHead.toPack().getBytes();
			byteArrayOutputStream.write(ByteBuffer.allocate(4).putInt(head.length).array());
			byteArrayOutputStream.write(ByteBuffer.allocate(4).putInt(data != null ? data.length : 0).array());
			byteArrayOutputStream.write(head);
			if (data != null) { byteArrayOutputStream.write(data); }

		} catch (IOException e) {}
		return byteArrayOutputStream.toByteArray();
	}
}
