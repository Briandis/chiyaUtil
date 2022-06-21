package chiya.security;

import java.util.Collection;

import chiya.core.base.bit.BitUtil;

/**
 * 请求方式工具类
 * 
 * @author Brian
 *
 */
public class Method {
	/** GET请求方式，数值=1 */
	public final static byte GET = 0b0000_0001;
	/** POST请求方式，数值=2 */
	public final static byte POST = 0b0000_0010;
	/** PUT请求方式，数值=4 */
	public final static byte PUT = 0b0000_0100;
	/** DELETE请求方式，数值=8 */
	public final static byte DELETE = 0b0000_1000;
	/** HEAD请求方式，数值=16 */
	public final static byte HEAD = 0b0001_0000;
	/** CONNECT请求方式，数值=32 */
	public final static byte CONNECT = 0b0010_0000;
	/** OPTIONS请求方式，数值=64 */
	public final static byte OPTIONS = 0b0100_0000;
	/** TRACE请求方式，数值=128 */
	public final static byte TRACE = ~0b0111_1111;
	/** 全部的请求方式 */
	public final static String[] ALL_METHOD = { "GET", "POST", "PUT", "DELETE", "HEAD", "CONNECT", "OPTIONS", "TRACE" };

	/**
	 * 返回对应方法的byte位置值
	 * 
	 * @param method 请求方式
	 * @return byte
	 */
	public static byte getByte(String method) {
		if (method == null) { return 0; }
		switch (method.toUpperCase()) {
		case "GET":
			return GET;
		case "POST":
			return POST;
		case "PUT":
			return PUT;
		case "DELETE":
			return DELETE;
		case "HEAD":
			return HEAD;
		case "CONNECT":
			return CONNECT;
		case "OPTIONS":
			return OPTIONS;
		case "TRACE":
			return TRACE;
		default:
			return 0;
		}
	}

	/**
	 * 返回对应方法的偏移值
	 * 
	 * @param method 请求方式
	 * @return 偏移位置
	 */
	public static int getOffset(String method) {
		if (method == null) { return 0; }
		switch (method.toUpperCase()) {
		case "GET":
			return 0;
		case "POST":
			return 1;
		case "PUT":
			return 2;
		case "DELETE":
			return 3;
		case "HEAD":
			return 4;
		case "CONNECT":
			return 5;
		case "OPTIONS":
			return 6;
		case "TRACE":
			return 7;
		default:
			return -1;
		}
	}

	/**
	 * 根据下标索引获取请求方式
	 * 
	 * @param index 下标
	 * @return 请求方式，超出下标则为空
	 */
	public static String getMethod(int index) {
		if (index < 0 || index > 7) { return null; }
		return ALL_METHOD[index];
	}

	/**
	 * 根据二进制的请求方式，获取请求方式<br>
	 * 该方法只能获取一个
	 * 
	 * @param method 二进制的请求方式
	 * @return 请求方式
	 */
	public static String getMethod(byte method) {
		int index = method;
		for (int i = 0; i < 8; i++) {
			if ((index & 1) == 1) { return ALL_METHOD[i]; }
			index = index >> 1;
		}
		return null;
	}

	/**
	 * 根据二进制请求方式，获取对应数组的字符串
	 * 
	 * @param method 二进制请求方式
	 * @return 二进制字符串
	 */
	public static String[] getMethodArray(byte method) {
		int length = BitUtil.countBit(method);
		int count = 0;
		String value[] = new String[length];
		for (int i = 0; i < 8; i++) {
			if (BitUtil.macthBit(method, i)) {
				value[count] = ALL_METHOD[i];
				count++;
			}
		}
		return value;
	}

	/**
	 * 把多个请求方式转成byte
	 * 
	 * @param list 请求方式
	 * @return byte
	 */
	public static byte toByte(Collection<String> list) {
		byte b = 0;
		for (String string : list) {
			b = BitUtil.modifySetBit(b, getOffset(string));
		}
		return b;
	}

}
