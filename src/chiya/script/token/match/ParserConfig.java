package chiya.script.token.match;

import chiya.script.parser.ChiyaSyntaxParser;

/**
 * 解析配置
 * 
 * @author chiya
 *
 */
public class ParserConfig {
	/** 需要递归匹配 */
	private boolean needRecursion = false;
	/** 前缀匹配的Token放到外层 */
	private boolean prefixOutside = false;
	/** 后缀匹配的Token放到外层 */
	private boolean suffixOutside = false;
	/** 前缀继续进行匹配 */
	private boolean prefixMatch = false;
	/** 后缀继续匹配 */
	private boolean suffixMatch = false;
	/** 深层解析方式 */
	private ChiyaSyntaxParser nextParser;
	/** 深层递归时，生效层数，只有小于该层才匹配 */
	private int efficientLayer = -1;

	/**
	 * 获取深层递归时，生效层数，只有小于该层才匹配
	 * 
	 * @return 深层递归时，生效层数，只有小于该层才匹配
	 */
	public int getEfficientLayer() {
		return efficientLayer;
	}

	/**
	 * 设置深层递归时，生效层数，只有小于该层才匹配
	 * 
	 * @param efficientLayer 深层递归时，生效层数，只有小于该层才匹配
	 */
	public void setEfficientLayer(int efficientLayer) {
		this.efficientLayer = efficientLayer;
	}

	/**
	 * 链式添加深层递归时，生效层数，只有小于该层才匹配
	 * 
	 * @param efficientLayer 深层递归时，生效层数，只有小于该层才匹配
	 * @return 对象本身
	 */
	public ParserConfig chainEfficientLayer(int efficientLayer) {
		setEfficientLayer(efficientLayer);
		return this;
	}

	/**
	 * 获取需要递归匹配
	 * 
	 * @return 需要递归匹配
	 */
	public boolean getNeedRecursion() {
		return needRecursion;
	}

	/**
	 * 设置需要递归匹配
	 * 
	 * @param needRecursion 需要递归匹配
	 */
	public void setNeedRecursion(boolean needRecursion) {
		this.needRecursion = needRecursion;
	}

	/**
	 * 链式添加需要递归匹配
	 * 
	 * @param needRecursion 需要递归匹配
	 * @return 对象本身
	 */
	public ParserConfig chainNeedRecursion(boolean needRecursion) {
		setNeedRecursion(needRecursion);
		return this;
	}

	/**
	 * 获取前缀匹配的Token放到外层
	 * 
	 * @return 前缀匹配的Token放到外层
	 */
	public boolean getPrefixOutside() {
		return prefixOutside;
	}

	/**
	 * 设置前缀匹配的Token放到外层
	 * 
	 * @param prefixOutside 前缀匹配的Token放到外层
	 */
	public void setPrefixOutside(boolean prefixOutside) {
		this.prefixOutside = prefixOutside;
	}

	/**
	 * 链式添加前缀匹配的Token放到外层
	 * 
	 * @param prefixOutside 前缀匹配的Token放到外层
	 * @return 对象本身
	 */
	public ParserConfig chainPrefixOutside(boolean prefixOutside) {
		setPrefixOutside(prefixOutside);
		return this;
	}

	/**
	 * 获取后缀匹配的Token放到外层
	 * 
	 * @return 后缀匹配的Token放到外层
	 */
	public boolean getSuffixOutside() {
		return suffixOutside;
	}

	/**
	 * 设置后缀匹配的Token放到外层
	 * 
	 * @param suffixOutside 后缀匹配的Token放到外层
	 */
	public void setSuffixOutside(boolean suffixOutside) {
		this.suffixOutside = suffixOutside;
	}

	/**
	 * 链式添加后缀匹配的Token放到外层
	 * 
	 * @param suffixOutside 后缀匹配的Token放到外层
	 * @return 对象本身
	 */
	public ParserConfig chainSuffixOutside(boolean suffixOutside) {
		setSuffixOutside(suffixOutside);
		return this;
	}

	/**
	 * 获取前缀继续进行匹配
	 * 
	 * @return 前缀继续进行匹配
	 */
	public boolean getPrefixMatch() {
		return prefixMatch;
	}

	/**
	 * 设置前缀继续进行匹配
	 * 
	 * @param prefixMatch 前缀继续进行匹配
	 */
	public void setPrefixMatch(boolean prefixMatch) {
		this.prefixMatch = prefixMatch;
	}

	/**
	 * 链式添加前缀继续进行匹配
	 * 
	 * @param prefixMatch 前缀继续进行匹配
	 * @return 对象本身
	 */
	public ParserConfig chainPrefixMatch(boolean prefixMatch) {
		setPrefixMatch(prefixMatch);
		return this;
	}

	/**
	 * 获取后缀继续匹配
	 * 
	 * @return 后缀继续匹配
	 */
	public boolean getSuffixMatch() {
		return suffixMatch;
	}

	/**
	 * 设置后缀继续匹配
	 * 
	 * @param suffixMatch 后缀继续匹配
	 */
	public void setSuffixMatch(boolean suffixMatch) {
		this.suffixMatch = suffixMatch;
	}

	/**
	 * 链式添加后缀继续匹配
	 * 
	 * @param suffixMatch 后缀继续匹配
	 * @return 对象本身
	 */
	public ParserConfig chainSuffixMatch(boolean suffixMatch) {
		setSuffixMatch(suffixMatch);
		return this;
	}

	/**
	 * 获取深层解析方式
	 * 
	 * @return 深层解析方式
	 */
	public ChiyaSyntaxParser getNextParser() {
		return nextParser;
	}

	/**
	 * 设置深层解析方式
	 * 
	 * @param nextParser 深层解析方式
	 */
	public void setNextParser(ChiyaSyntaxParser nextParser) {
		this.nextParser = nextParser;
	}

	/**
	 * 链式添加深层解析方式
	 * 
	 * @param nextParser 深层解析方式
	 * @return 对象本身
	 */
	public ParserConfig chainNextParser(ChiyaSyntaxParser nextParser) {
		setNextParser(nextParser);
		return this;
	}

}
