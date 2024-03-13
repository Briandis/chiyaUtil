package chiya.script.parser;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import chiya.core.base.loop.Loop;
import chiya.core.base.string.ChiyaString;
import chiya.core.base.string.StringUtil;
import chiya.script.match.MatchParser;
import chiya.script.match.MatchResult;
import chiya.script.token.ChiyaToken;

/**
 * Token解析器
 * 
 * @author chiya
 *
 */
public class ChiyaTokenParser {

	/** 匹配器 */
	private MatchParser matchParser = new MatchParser();

	/**
	 * 添加token
	 * 
	 * @param tokenType token类型
	 * @param args      值
	 */
	public void addToken(String tokenType, String... args) {
		Loop.forEach(args, str -> {
			matchParser.addRule(tokenType, str, null, null, null, null, null);
		});
	}

	/**
	 * 添加组合token
	 * 
	 * @param tokenType  token类型
	 * @param start      起始字符
	 * @param end        结束字符串
	 * @param nextParser 下一层解析
	 * @param selfMark   自身标记
	 * @param countStart 计数的开始字符串
	 * @param countEnd   计数的结束字符串
	 */
	public void addCombination(String tokenType, String start, String end, ChiyaTokenParser nextParser, String selfMark, String countStart, String countEnd) {
		matchParser.addRule(tokenType, start, end, nextParser, selfMark, countStart, countEnd);
	}

	/**
	 * 添加组合token
	 * 
	 * @param tokenType token类型
	 * @param start     起始字符
	 * @param end       结束字符串
	 */
	public void addCombination(String tokenType, String start, String end) {
		matchParser.addRule(tokenType, start, end, null, null, null, null);
	}

	/**
	 * 生成Token序列
	 * 
	 * @param sourceCode 源码
	 * @param anyType    任意token的名称
	 * @param skipType   跳过的token
	 * @return token列表
	 */
	public List<ChiyaToken> toToken(String sourceCode, String anyType, String... skipType) {
		return toToken(sourceCode, anyType, 0, skipType);
	}

	/**
	 * 生成Token序列
	 * 
	 * @param sourceCode 源码
	 * @param anyType    任意token的名称
	 * @param lineCount  行坐标信息
	 * @param skipType   跳过的token
	 * @return token列表
	 */
	public List<ChiyaToken> toToken(String sourceCode, String anyType, int lineCount, String... skipType) {
		HashSet<String> skipSet = new HashSet<>();
		if (skipType != null) { Loop.forEach(skipType, data -> skipSet.add(data)); }

		List<ChiyaToken> listToken = new ArrayList<>();

		int nowIndex = 0;
		int endIndex = 0;
		int startIndex = 0;
		int lineStart = 0;
		String nowTokenType = "";

		ChiyaString anyData = new ChiyaString();
		while (nowIndex < sourceCode.length()) {
			List<MatchResult> listMatchResult = matchParser.match(nowIndex, sourceCode);
			if (listMatchResult.isEmpty()) {
				anyData.append(sourceCode.charAt(nowIndex));
			} else {
				lineStart = lineCount;
				// 如果any的值存在，先构建token
				if (!anyData.isEmpty()) {
					listToken.add(
						new ChiyaToken()
							.chainType(anyType)
							.chainData(anyData.toString())
							.chainCharIndex(nowIndex - 1)
							.chainCharLineStart(lineStart)
							.chainCharLineEnd(lineCount)
					);
					anyData.clear();
				}
				// 获取最后一个，最后一个肯定是所有匹配项中最长的
				MatchResult lastMatchResult = listMatchResult.get(listMatchResult.size() - 1);
				// 备份最后结尾下标，防止递归丢失
				endIndex = lastMatchResult.getEndIndex();

				// 统计字符出现的行数量
				for (int charIndex = 0; charIndex < endIndex - nowIndex + 1; charIndex++) {
					if (sourceCode.charAt(charIndex + nowIndex) == '\n') { lineCount++; }
				}
				// 如果匹配到的类型不是需要跳过的，则构建token
				if (!skipSet.contains(lastMatchResult.getMatchRule().getStatus())) {
					// 如果存在下一层解析
					if (lastMatchResult.getMatchRule().getNextParser() != null) {
						// 先进行计算，递归解析中会将该对象状态重置
						startIndex = endIndex - lastMatchResult.getData().length() - 1;
						// 当前类型
						nowTokenType = lastMatchResult.getMatchRule().getStatus();
						if (StringUtil.isNullOrZero(lastMatchResult.getMatchRule().getSelfMark())) {
							// 如果有标记备份标记
							nowTokenType = lastMatchResult.getMatchRule().getSelfMark();
						}
						// 递归
						List<ChiyaToken> tempList = lastMatchResult.getMatchRule().getNextParser().toToken(
							lastMatchResult.getData(),
							lastMatchResult.getMatchRule().getStatus(),
							lineCount,
							skipType
						);
						// 重新计算开始坐标
						listToken.add(new ChiyaToken().chainType(nowTokenType).chainStart(lastMatchResult.getMatchRule().getStart()).chainCharIndex(startIndex));
						for (ChiyaToken token : tempList) {
							// 重新计算递归坐标，多层递归会跟随累计，从而保证坐标正确
							token.setCharIndex(startIndex + token.getCharIndex() + 1);
						}
						listToken.addAll(tempList);
						// 结尾坐标
						listToken.add(new ChiyaToken().chainType(nowTokenType).chainEnd(lastMatchResult.getMatchRule().getEnd()).chainCharIndex(endIndex));
					} else {
						// 如果不存在递归解析
						listToken.add(
							new ChiyaToken()
								.chainType(lastMatchResult.getMatchRule().getStatus())
								.chainStart(lastMatchResult.getMatchRule().getStart())
								.chainEnd(lastMatchResult.getMatchRule().getEnd())
								.chainData(lastMatchResult.getData())
								.chainCharIndex(lastMatchResult.getEndIndex())
						);
					}
					listToken.get(listToken.size() - 1)
						.chainCharLineStart(lineStart)
						.chainCharLineEnd(sourceCode.charAt(nowIndex) == '\n' ? lineCount - 1 : lineCount);
				}
				nowIndex = lastMatchResult.getEndIndex();
			}
			nowIndex += 1;
		}
		// 如果最后还有参与的字符，则进行最后处理
		if (!anyData.isEmpty()) {
			listToken.add(
				new ChiyaToken()
					.chainType(anyType)
					.chainData(anyData.toString())
					.chainCharIndex(nowIndex - 1)
					.chainCharLineStart(lineCount)
					.chainCharLineEnd(lineCount)
			);
			anyData.clear();
		}
		return listToken;
	}

}
