package chiya.script.token.match;

import java.util.List;
import java.util.Map;

import chiya.script.token.ChiyaToken;

/**
 * 语法匹配
 * 
 * @author chiya
 *
 */
public class SyntaxMatch {

	/** 语法因子 */
	private SyntaxFactor syntaxFactor;
	/** 当前下标 */
	private int nowIndex = 0;
	/** 左边成对计数 */
	private int pairedStarCount = 0;
	/** 右边成对计数 */
	private int pairedEndCount = 0;
	/** 左边成对记录 */
	private boolean pariedFlag = false;
	/** 第一次标记 */
	private boolean firstFlag = true;

	public SyntaxMatch(SyntaxFactor syntaxFactor) {
		this.syntaxFactor = syntaxFactor;
	}

	/**
	 * 是否允许匹配到结尾
	 * 
	 * @return true:允许/false:不允许
	 */
	public boolean getAllowEnd() {
		return syntaxFactor.getLexicalLast() == null ? false : syntaxFactor.getLexicalLast().getAllowMatchNotEnd();
	}

	/**
	 * 获取当前下标
	 * 
	 * @return 下标
	 */
	public int getNowIndex() {
		return nowIndex;
	}

	/**
	 * 重置
	 */
	public void reset() {
		nowIndex = 0;
		pairedStarCount = 0;
		pairedEndCount = 0;
		pariedFlag = false;
		firstFlag = true;

	}

	/**
	 * 判断语法因子符合条件情况
	 * 
	 * @param nextToken 下一个token
	 * @return -1:不符合/0:前缀相同/1:完全相同
	 */
	public int prefix(ChiyaToken nextToken) {
		if (syntaxFactor.getNeedMatch()) {
			// 需要匹配的情况
			nowIndex++;
			boolean startFlag = syntaxFactor.tokenSameFirst(nextToken);
			boolean endFlag = syntaxFactor.tokenSameLast(nextToken);
			if (startFlag) {
				if (syntaxFactor.getNeedPaired()) { pairedStarCount++; }
				pariedFlag = true;
			}
			if (endFlag && syntaxFactor.getNeedPaired()) { pairedEndCount++; }
			// 只有匹配的值同为一个时，才会触发，所以要进行第一次忽略
			if (startFlag && endFlag && firstFlag) {
				firstFlag = false;
				return 0;
			}
			// 如果已经匹配到了前缀，则直接返回0，匹配到了后缀，且成对返回1，其余-1
			return pariedFlag ? pairedStarCount == pairedEndCount ? 1 : 0 : -1;
		} else {
			boolean isSame = syntaxFactor.tokenSame(nowIndex, nextToken);
			nowIndex++;
			boolean endFlag = nowIndex == syntaxFactor.getSyntaxLength();
			return isSame ? endFlag ? 1 : 0 : -1;
		}
	}

	/**
	 * 改变token类型
	 * 
	 * @param chiyaToken
	 */
	@SuppressWarnings("unchecked")
	public void changeType(List<ChiyaToken> listToken) {
		// 对类型替换迭代
		syntaxFactor.getTokenType().forEach((index, data) -> {
			// 支持负数下标索引
			if (index < 0) { index = listToken.size() + index; }
			// 如果是值则直接替换对应位置
			if (data instanceof String) { listToken.get(index).setType((String) data); }
			if (data instanceof Map) {
				Map<String, String> map = (Map<String, String>) data;
				String value = map.get(listToken.get(index).getType());
				if (value != null) { listToken.get(index).setType(value); }
			}
		});
	}

	/**
	 * 获取语法因子
	 * 
	 * @return 自身
	 */
	public SyntaxFactor getSyntaxFactor() {
		return syntaxFactor;
	}

}
