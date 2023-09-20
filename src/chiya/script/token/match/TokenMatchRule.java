package chiya.script.token.match;

import chiya.core.base.string.StringUtil;
import chiya.script.token.ChiyaToken;

/**
 * token 匹配规则
 * 
 * @author chiya
 *
 */
public class TokenMatchRule {

	/** 起始字符 */
	private String start;
	/** 值 */
	private String data;
	/** 结尾字符 */
	private String end;

	public TokenMatchRule() {}

	public TokenMatchRule(String start) {
		this.start = start;
	}

	public TokenMatchRule(String start, String data) {
		this.start = start;
		this.data = data;
	}

	public TokenMatchRule(String start, String data, String end) {
		this.start = start;
		this.data = data;
		this.end = end;
	}

	public boolean isSame(ChiyaToken chiyaToken) {
		if (chiyaToken == null) { return true; }
		boolean startFlag = start == null || StringUtil.eqString(start, chiyaToken.getStart());
		boolean dataFlag = data == null || StringUtil.eqString(data, chiyaToken.getData());
		boolean endFlag = end == null || StringUtil.eqString(end, chiyaToken.getEnd());
		return startFlag && dataFlag && endFlag;
	}

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
	public TokenMatchRule chainStart(String start) {
		setStart(start);
		return this;
	}

	/**
	 * 获取值
	 * 
	 * @return 值
	 */
	public String getData() {
		return data;
	}

	/**
	 * 设置值
	 * 
	 * @param data 值
	 */
	public void setData(String data) {
		this.data = data;
	}

	/**
	 * 链式添加值
	 * 
	 * @param data 值
	 * @return 对象本身
	 */
	public TokenMatchRule chainData(String data) {
		setData(data);
		return this;
	}

	/**
	 * 获取结尾字符
	 * 
	 * @return 结尾字符
	 */
	public String getEnd() {
		return end;
	}

	/**
	 * 设置结尾字符
	 * 
	 * @param end 结尾字符
	 */
	public void setEnd(String end) {
		this.end = end;
	}

	/**
	 * 链式添加结尾字符
	 * 
	 * @param end 结尾字符
	 * @return 对象本身
	 */
	public TokenMatchRule chainEnd(String end) {
		setEnd(end);
		return this;
	}

}
