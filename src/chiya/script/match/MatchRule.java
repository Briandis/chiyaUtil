package chiya.script.match;

import chiya.script.parser.ChiyaTokenParser;

/**
 * 匹配规则
 * 
 * @author chiya
 *
 */
public class MatchRule {

	/** 起始字符 */
	private String start;
	/** 结束字符 */
	private String end;
	/** 状态 */
	private String status;
	/** 下一层解析器 */
	private ChiyaTokenParser nextParser;
	/** 自身标记 */
	private String selfMark;
	/** 需要计数的开始字符 */
	private String countStart;
	/** 需要计数的结束字符 */
	private String countEnd;

	/**
	 * 获取起始字符
	 * 
	 * @return 起始字符
	 */
	public String getStart() {
		return start;
	}

	/**
	 * 设置起始字符
	 * 
	 * @param start 起始字符
	 */
	public void setStart(String start) {
		this.start = start;
	}

	/**
	 * 链式添加起始字符
	 * 
	 * @param start 起始字符
	 * @return 对象本身
	 */
	public MatchRule chainStart(String start) {
		setStart(start);
		return this;
	}

	/**
	 * 获取结束字符
	 * 
	 * @return 结束字符
	 */
	public String getEnd() {
		return end;
	}

	/**
	 * 设置结束字符
	 * 
	 * @param end 结束字符
	 */
	public void setEnd(String end) {
		this.end = end;
	}

	/**
	 * 链式添加结束字符
	 * 
	 * @param end 结束字符
	 * @return 对象本身
	 */
	public MatchRule chainEnd(String end) {
		setEnd(end);
		return this;
	}

	/**
	 * 获取状态
	 * 
	 * @return 状态
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * 设置状态
	 * 
	 * @param status 状态
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * 链式添加状态
	 * 
	 * @param status 状态
	 * @return 对象本身
	 */
	public MatchRule chainStatus(String status) {
		setStatus(status);
		return this;
	}

	/**
	 * 获取下一层解析器
	 * 
	 * @return 下一层解析器
	 */
	public ChiyaTokenParser getNextParser() {
		return nextParser;
	}

	/**
	 * 设置下一层解析器
	 * 
	 * @param nextParser 下一层解析器
	 */
	public void setNextParser(ChiyaTokenParser nextParser) {
		this.nextParser = nextParser;
	}

	/**
	 * 链式添加下一层解析器
	 * 
	 * @param nextParser 下一层解析器
	 * @return 对象本身
	 */
	public MatchRule chainNextParser(ChiyaTokenParser nextParser) {
		setNextParser(nextParser);
		return this;
	}

	/**
	 * 获取自身标记
	 * 
	 * @return 自身标记
	 */
	public String getSelfMark() {
		return selfMark;
	}

	/**
	 * 设置自身标记
	 * 
	 * @param selfMark 自身标记
	 */
	public void setSelfMark(String selfMark) {
		this.selfMark = selfMark;
	}

	/**
	 * 链式添加自身标记
	 * 
	 * @param selfMark 自身标记
	 * @return 对象本身
	 */
	public MatchRule chainSelfMark(String selfMark) {
		setSelfMark(selfMark);
		return this;
	}

	/**
	 * 获取需要计数的开始字符
	 * 
	 * @return 需要计数的开始字符
	 */
	public String getCountStart() {
		return countStart;
	}

	/**
	 * 设置需要计数的开始字符
	 * 
	 * @param countStart 需要计数的开始字符
	 */
	public void setCountStart(String countStart) {
		this.countStart = countStart;
	}

	/**
	 * 链式添加需要计数的开始字符
	 * 
	 * @param countStart 需要计数的开始字符
	 * @return 对象本身
	 */
	public MatchRule chainCountStart(String countStart) {
		setCountStart(countStart);
		return this;
	}

	/**
	 * 获取需要计数的结束字符
	 * 
	 * @return 需要计数的结束字符
	 */
	public String getCountEnd() {
		return countEnd;
	}

	/**
	 * 设置需要计数的结束字符
	 * 
	 * @param countEnd 需要计数的结束字符
	 */
	public void setCountEnd(String countEnd) {
		this.countEnd = countEnd;
	}

	/**
	 * 链式添加需要计数的结束字符
	 * 
	 * @param countEnd 需要计数的结束字符
	 * @return 对象本身
	 */
	public MatchRule chainCountEnd(String countEnd) {
		setCountEnd(countEnd);
		return this;
	}
}