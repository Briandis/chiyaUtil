package chiya.script.match;

import chiya.core.base.string.StringUtil;

/**
 * 计数匹配
 * 
 * @author chiya
 *
 */
public class CountMatch {

	/** 匹配规则 */
	private MatchRule matchRule;
	/** 字符缓存 */
	private StringBuilder cache = new StringBuilder();
	/** 起始字符计数 */
	private int startCount = 0;
	/** 结束字符计数 */
	private int endCount = 0;

	/** 匹配器 */
	private IterativeMatch endMatch;
	/** 计数开始匹配器 */
	private IterativeMatch countStartMatch;
	/** 计数结束匹配器 */
	private IterativeMatch countEndMatch;

	/**
	 * 构造方法
	 * 
	 * @param matchRule 匹配规则
	 */
	public CountMatch(MatchRule matchRule) {
		this.matchRule = matchRule;
		endMatch = new IterativeMatch(matchRule.getEnd());
		if (!StringUtil.isNullOrZero(matchRule.getCountStart())) { countStartMatch = new IterativeMatch(matchRule.getCountStart()); }
		if (!StringUtil.isNullOrZero(matchRule.getCountEnd())) { countEndMatch = new IterativeMatch(matchRule.getCountEnd()); }
	}

	/**
	 * 匹配结尾
	 * 
	 * @param nextChar 下一个字符
	 * @return -1完全不匹配,0只有前缀匹配了，1完全匹配
	 */
	public int prefixEnd(char nextChar) {
		if (StringUtil.isNullOrZero(matchRule.getEnd())) { return 1; }

		cache.append(nextChar);
		// 匹配
		if (countStartMatch != null && countStartMatch.match(nextChar) == 1) { startCount++; }
		if (countEndMatch != null && countEndMatch.match(nextChar) == 1) { endCount++; }

		if (endMatch.match(nextChar) == 1) {
			if (startCount == endCount) {
				cache.delete(cache.length() - matchRule.getEnd().length(), cache.length());
				return 1;
			}
		}
		return 0;
	}

	/**
	 * 获取缓存的值
	 * 
	 * @return 值
	 */
	public String getData() {
		return cache.toString();
	}

	/**
	 * 获取规则
	 * 
	 * @return 匹配规则
	 */
	public MatchRule getRule() {
		return matchRule;
	}

}
