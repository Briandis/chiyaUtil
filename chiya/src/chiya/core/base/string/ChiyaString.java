package chiya.core.base.string;

import java.util.Arrays;

/**
 * 可复用字符串<br>
 * 多线程下不安全
 */
public class ChiyaString {

	/** 字符缓存 */
	private char chars[];
	/** 默认大小 */
	private static final int SIZE = 32;
	/** 当前数值长度 */
	private int length = 0;
	/** 当前存储上限 */
	private int maxLength = 0;
	/** 重置次数 */
	private long resetCount = 0;
	/** 统计长度 */
	private long countLength = 0;

	/** 默认构造方法 */
	public ChiyaString() {
		chars = new char[SIZE];
	}

	/**
	 * 默认长度构造方法
	 * 
	 * @param len 默认长度
	 */
	public ChiyaString(int len) {
		if (len < 0) { len = SIZE; }
		chars = new char[len];
	}

	/**
	 * 根据长度扩容
	 * 
	 * @param len 预期长度
	 */
	private void expansion(long len) {
		if (maxLength == Integer.MAX_VALUE || len < maxLength || len < 0) { return; }
		if (len >= Integer.MAX_VALUE) {
			maxLength = Integer.MAX_VALUE;
			return;
		}
		maxLength = (int) (len * 1.5);
		chars = Arrays.copyOf(chars, maxLength);
	}

	/**
	 * 追加内容
	 * 
	 * @param value 要追加的字符串
	 */
	private void add(char value[]) {
		for (int i = 0; i < value.length; i++, length++) {
			chars[length] = value[i];
		}
	}

	/**
	 * 追加内容
	 * 
	 * @param value 要追加的字符串
	 */
	private void add(String value) {
		for (int i = 0; i < value.length(); i++, length++) {
			chars[length] = value.charAt(i);
		}
	}

	/**
	 * 追加内容
	 * 
	 * @param value 要追加的字符串
	 */
	private void add(char value) {
		chars[length] = value;
		length++;
	}

	/**
	 * 检查是否可用
	 * 
	 * @param len 长度
	 */
	private void check(int len) {
		int expected = len + length;
		if (len + length > maxLength) { expansion(expected); }
	}

	/**
	 * 添加字符串
	 * 
	 * @param value 字符串
	 * @return 自身
	 */
	public ChiyaString append(String value) {
		if (value != null) {
			check(value.length());
			add(value);
		}
		return this;
	}

	/**
	 * 添加字符串
	 * 
	 * @param value 字符串
	 * @return 自身
	 */
	public ChiyaString append(Object value) {
		if (value != null) { append(value.toString()); }
		return this;
	}

	/**
	 * 添加字符串
	 * 
	 * @param value[] 字符串
	 * @return 自身
	 */
	public ChiyaString append(char value[]) {
		if (value != null) {
			check(value.length);
			add(value);
		}
		return this;
	}

	/**
	 * 添加字符
	 * 
	 * @param value 字符
	 * @return 自身
	 */
	public ChiyaString append(char value) {
		check(length + 1);
		add(value);
		return this;
	}

	/**
	 * 添加一行
	 * 
	 * @return 自身
	 */
	public ChiyaString appendLine() {
		return append("\n");
	}

	/**
	 * 添加缩进
	 * 
	 * @return 自身
	 */
	public ChiyaString appendIndent() {
		return append("\t");
	}

	/** 清除内容 */
	public void clear() {
		resetCount++;
		if (length > countLength) {
			countLength = length;
			resetCount = 0;
		}
		// 如果重置次数大于阈值，并且历史最大空间小于使用空间的一半，则缩小1.5倍
		if (resetCount > SIZE && countLength < maxLength / 2) {
			chars = new char[(int) (maxLength * 1.5)];
			countLength = 0;
			resetCount = 0;
		}
		length = 0;
	}

	@Override
	public String toString() {
		return String.valueOf(chars, 0, length);
	}
}
