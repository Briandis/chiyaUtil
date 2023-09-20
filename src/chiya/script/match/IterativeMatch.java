package chiya.script.match;

import chiya.core.base.string.StringUtil;

/**
 * 迭代匹配
 * 
 * @author chiya
 *
 */
public class IterativeMatch {

	/** 数据 */
	private String data;
	/** 当前下标 */
	private int index = 0;

	/** 重置索引 */
	public void reset() {
		index = 0;
	}

	/**
	 * 狗杂方法
	 * 
	 * @param data 数据
	 */
	public IterativeMatch(String data) {
		this.data = data;
	}

	/**
	 * 匹配
	 * 
	 * @param nextChar 下一个字符
	 * @return -1完全不匹配,0只有前缀匹配了，1完全匹配
	 */
	public int match(char nextChar) {
		// 如果为null或者空，则默认相等
		if (StringUtil.isNullOrZero(data)) { return 1; }
		// 匹配当前字符
		// 不是前缀的字符直接重制返回
		if (data.charAt(index) != nextChar) {
			reset();
			return -1;
		}
		index++;
		// 如果相等则重置返回
		if (index == data.length()) {
			reset();
			return 1;
		}
		return 0;
	}

}
