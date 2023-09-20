package chiya.script.match;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import chiya.core.base.string.StringUtil;
import chiya.script.parser.ChiyaTokenParser;

/**
 * 匹配解析器
 * 
 * @author chiya
 *
 */
public class MatchParser {
	/** 匹配的因子信息 */
	private List<MatchRule> listRule = new ArrayList<>();
	/** 匹配索引树 */
	private MatchTree startTree = new MatchTree();
	/** 结尾匹配树 */
	private HashMap<Integer, String> endTree = new HashMap<>();

	/**
	 * 添加规则
	 * 
	 * @param status     匹配的标记状态
	 * @param start      开始字符
	 * @param end        结束字符
	 * @param nextParser 下一层解析
	 * @param selfMark   自身标记
	 * @param countStart 进一步匹配时需要计数的起始字符
	 * @param countEnd   进一步匹配时需要计数的结束字符
	 */
	public void addRule(String status, String start, String end, ChiyaTokenParser nextParser, String selfMark, String countStart, String countEnd) {
		MatchRule matchRule = new MatchRule()
			.chainStatus(status)
			.chainStart(start)
			.chainEnd(end)
			.chainNextParser(nextParser)
			.chainSelfMark(selfMark)
			.chainCountStart(countStart)
			.chainCountEnd(countEnd);
		listRule.add(matchRule);
		startTree.add(listRule.size() - 1, start, false);
		// 如果包含结尾，则进行匹配
		if (!StringUtil.isNullOrZero(end)) { endTree.put(listRule.size() - 1, end); }
	}

	/**
	 * 匹配
	 * 
	 * @param index      字符串起始的值
	 * @param sourceCode 原字符串
	 * @return 匹配到的列表
	 */
	public List<MatchResult> match(int index, String sourceCode) {
		// 先重置树
		startTree.reset();
		List<MatchResult> result = new ArrayList<>();
		// 后续匹配队列
		List<CountMatch> judgeList = new ArrayList<>();
		List<CountMatch> nextList = new ArrayList<>();

		boolean isOver = false;
		// 迭代循环
		while (index < sourceCode.length()) {
			char nowChar = sourceCode.charAt(index);
			// 对需要判断的列表做处理，需要的情况是对结尾符合的判断
			for (CountMatch judgeMatch : judgeList) {
				int judgeResult = judgeMatch.prefixEnd(nowChar);
				if (judgeResult == 1) {
					result.add(new MatchResult().chainData(judgeMatch.getData()).chainEndIndex(index).chainMatchRule(judgeMatch.getRule()));
				} else if (judgeResult == 0) {
					// 进一步匹配
					nextList.add(judgeMatch);
				}
			}
			judgeList.clear();
			judgeList.addAll(nextList);
			nextList.clear();

			if (!isOver) {
				TreeNode<Character, Integer> treeNode = startTree.each(nowChar);
				// 如果返回null或者子项没有则说明已经匹配结束了
				if (treeNode == null || treeNode.getChildren().isEmpty()) { isOver = true; }
				if (treeNode != null) {
					for (int key : treeNode.getNodeData()) {
						// 如果key存在于结束字符索引中，说明要进后续匹配
						if (endTree.containsKey(key)) {
							// 构造匹配对象
							judgeList.add(new CountMatch(listRule.get(key)));
						} else {
							result.add(new MatchResult().chainMatchRule(listRule.get(key)).chainEndIndex(index));
						}
					}
				}
			}
			if (isOver && judgeList.isEmpty()) { break; }
			index++;
		}
		return result;
	}

}
