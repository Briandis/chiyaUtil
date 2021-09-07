package com.chiya.authority;

import java.util.Collection;

import com.chiya.bit.BitUtil;

/**
 * 请求方式工具类
 * 
 * @author Brian
 *
 */
public class Method {
	public final static byte GET = 0b0000_0001;
	public final static byte POST = 0b0000_0010;
	public final static byte PUT = 0b0000_0100;
	public final static byte DELETE = 0b0000_1000;
	public final static byte HEAD = 0b0001_0000;
	public final static byte CONNECT = 0b0010_0000;
	public final static byte OPTIONS = 0b0100_0000;
	public final static byte TRACE = ~0b0111_1111;

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
