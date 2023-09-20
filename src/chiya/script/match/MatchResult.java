package chiya.script.match;

/**
 * 匹配返回结果
 * 
 * @author chiya
 *
 */
public class MatchResult {
	/** 当前的匹配规则 */
	private MatchRule matchRule;
	/** 结束的下标 */
	private int endIndex;
	/** 区间字符串 */
	private String data;

	/**
	 * 获取当前的匹配规则
	 * 
	 * @return 当前的匹配规则
	 */
	public MatchRule getMatchRule() {
		return matchRule;
	}

	/**
	 * 设置当前的匹配规则
	 * 
	 * @param matchRule 当前的匹配规则
	 */
	public void setMatchRule(MatchRule matchRule) {
		this.matchRule = matchRule;
	}

	/**
	 * 链式添加当前的匹配规则
	 * 
	 * @param matchRule 当前的匹配规则
	 * @return 对象本身
	 */
	public MatchResult chainMatchRule(MatchRule matchRule) {
		setMatchRule(matchRule);
		return this;
	}

	/**
	 * 获取结束的下标
	 * 
	 * @return 结束的下标
	 */
	public int getEndIndex() {
		return endIndex;
	}

	/**
	 * 设置结束的下标
	 * 
	 * @param endIndex 结束的下标
	 */
	public void setEndIndex(int endIndex) {
		this.endIndex = endIndex;
	}

	/**
	 * 链式添加结束的下标
	 * 
	 * @param endIndex 结束的下标
	 * @return 对象本身
	 */
	public MatchResult chainEndIndex(int endIndex) {
		setEndIndex(endIndex);
		return this;
	}

	/**
	 * 获取区间字符串
	 * 
	 * @return 区间字符串
	 */
	public String getData() {
		return data;
	}

	/**
	 * 设置区间字符串
	 * 
	 * @param data 区间字符串
	 */
	public void setData(String data) {
		this.data = data;
	}

	/**
	 * 链式添加区间字符串
	 * 
	 * @param data 区间字符串
	 * @return 对象本身
	 */
	public MatchResult chainData(String data) {
		setData(data);
		return this;
	}

}
