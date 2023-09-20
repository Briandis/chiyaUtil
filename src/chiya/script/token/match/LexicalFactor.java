package chiya.script.token.match;

import java.util.HashSet;

import chiya.core.base.loop.Loop;
import chiya.script.token.ChiyaToken;

/**
 * 词法因子
 * 
 * @author chiya
 *
 */
public class LexicalFactor {

	/** 支持类型 */
	private HashSet<String> type = new HashSet<>();
	/** 开始字符匹配规则 */
	private HashSet<String> start = new HashSet<>();
	/** 中间字符匹配规则 */
	private HashSet<String> data = new HashSet<>();
	/** 结尾字符匹配规则 */
	private HashSet<String> end = new HashSet<>();
	/** 允许匹配没有结尾 */
	private boolean allowMatchNotEnd = false;

	public LexicalFactor() {}

	public LexicalFactor(boolean allowMatchNotEnd) {
		this.allowMatchNotEnd = allowMatchNotEnd;
	}

	/**
	 * 获取是否允许匹配忽略结尾
	 * 
	 * @return 允许/不允许
	 */
	public boolean getAllowMatchNotEnd() {
		return this.allowMatchNotEnd;
	}

	/**
	 * 添加所有数据进对应容器重
	 * 
	 * @param set   容器
	 * @param value 值
	 */
	private void addAll(HashSet<String> set, String... value) {
		if (value != null) { Loop.forEach(value, v -> set.add(v)); }
	}

	/**
	 * 支持的类型
	 * 
	 * @param value 类型
	 * @return 自身
	 */
	public LexicalFactor chainType(String... value) {
		addAll(type, value);
		return this;
	}

	/**
	 * 支持的开始字符
	 * 
	 * @param value 类型
	 * @return 自身
	 */
	public LexicalFactor chainStart(String... value) {
		addAll(start, value);
		return this;
	}

	/**
	 * 支持的中间字符
	 * 
	 * @param value 类型
	 * @return 自身
	 */
	public LexicalFactor chainData(String... value) {
		addAll(data, value);
		return this;
	}

	/**
	 * 支持的结束字符
	 * 
	 * @param value 类型
	 * @return 自身
	 */
	public LexicalFactor chainEnd(String... value) {
		addAll(end, value);
		return this;
	}

	/**
	 * 判定值是否在词法重，空集合即判定存在
	 * 
	 * @param set  集合
	 * @param data 值
	 * @return true:在/false：不在
	 */
	private static boolean containsValue(HashSet<String> set, String data) {
		return set.isEmpty() || set.contains(data);
	}

	/**
	 * 判断token是否和当前词法规则相同
	 * 
	 * @param token token
	 * @return 相同/不同
	 */
	public boolean tokenSame(ChiyaToken token) {
		return containsValue(type, token.getType()) &&
			containsValue(start, token.getStart()) &&
			containsValue(data, token.getData()) &&
			containsValue(end, token.getEnd());
	}

	/**
	 * 判断token是否和当前词法规则相同
	 * 
	 * @param lexicalFactor 词法因子
	 * @param token         token
	 * @return 相同/不同
	 */
	public static boolean tokenSame(LexicalFactor lexicalFactor, ChiyaToken token) {
		if (token == null || lexicalFactor == null) { return true; }

		return containsValue(lexicalFactor.type, token.getType()) &&
			containsValue(lexicalFactor.start, token.getStart()) &&
			containsValue(lexicalFactor.data, token.getData()) &&
			containsValue(lexicalFactor.end, token.getEnd());
	}
}
