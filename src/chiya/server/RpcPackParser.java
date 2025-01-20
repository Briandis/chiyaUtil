package chiya.server;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.HashMap;

import chiya.core.base.function.VoidGenericFunction;
import chiya.core.base.io.ByteArray;
import chiya.core.base.number.NumberUtil;
import chiya.core.base.string.StringUtil;

/**
 * 报文解析
 * 
 * @author chiya
 *
 */
public class RpcPackParser {

	/** 标志位 */
	public static final byte[] checkFlag = "chiya".getBytes();

	/**
	 * 解析
	 * 
	 * @param client   客户端连接
	 * @param function
	 */
	public static void parser(Socket client, VoidGenericFunction<ChiyaRpcPack> function) {
		try {
			InputStream inputStream = client.getInputStream();
			ByteArray byteArray = new ByteArray();
			byte[] cache = new byte[1024 * 4];
			int readSize = 0;
			while (true) {
				// 如果当前缓存中数量，小于标志位和数量，才需要读取
				if (byteArray.getSize() < checkFlag.length + 8) {
					readSize = inputStream.read(cache);
					byteArray.append(cache, 0, readSize);
				}
				// 寻找校验位
				int check = byteArray.find(checkFlag);
				if (check != -1) {
					// 移除标志位，如果流混乱，则删除非标志位之前的数据
					byteArray.remove(checkFlag.length + check);
					// 如果只读到了标志位
					if (byteArray.getSize() < 2) {
						readSize = inputStream.read(cache);
						byteArray.append(cache, 0, readSize);
					}
					int headSize = java.nio.ByteBuffer.wrap(byteArray.remove(4)).getInt();
					int dataSize = java.nio.ByteBuffer.wrap(byteArray.remove(4)).getInt();
					// 直到读取完头部
					while (byteArray.getSize() < headSize) {
						readSize = inputStream.read(cache);
						byteArray.append(cache, 0, readSize);
					}
					ChiyaRpcPack rpcPack = new ChiyaRpcPack();
					// 头部解析
					rpcPack.setChiyaRpcHead(parserHead(byteArray.remove(headSize)));
					// 直到读取完数据
					while (byteArray.getSize() < dataSize) {
						readSize = inputStream.read(cache);
						byteArray.append(cache, 0, readSize);
					}
					rpcPack.setData(byteArray.remove(dataSize));
					function.execute(rpcPack);
				}
			}
		} catch (IOException e) {
//			throw new RuntimeException(e);
		} finally {
			try {
				client.close();
			} catch (IOException e1) {
				throw new RuntimeException(e1);
			}
		}

	}

	/**
	 * 头数据解析
	 * 
	 * @param data 头数据
	 * @return 解析后对象
	 */
	public static ChiyaRpcHead parserHead(byte[] data) {
		ChiyaRpcHead chiyaRpcHead = new ChiyaRpcHead();
		String headString = new String(data);
		HashMap<String, String> hashMap = new HashMap<>();
		for (String line : headString.split("\r\n")) {
			String[] lineArray = line.split("=", 2);
			if (lineArray.length == 2) { hashMap.put(lineArray[0].toLowerCase(), lineArray[1]); }
		}
		chiyaRpcHead.chainUrl(hashMap.get("url"))
			.chainType(hashMap.get("type"))
			.chainJson(StringUtil.eqString(hashMap.get("json").toLowerCase(), "true"))
			.chainKey(hashMap.get("key"))
			.chainPort(NumberUtil.parseInt(hashMap.get("port")))
			.chainResult(StringUtil.eqString(hashMap.get("result").toLowerCase(), "true"));
		return chiyaRpcHead;

	}
}
