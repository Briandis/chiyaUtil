package chiya.script.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.function.Consumer;

import chiya.core.base.collection.ChiyaArray;
import chiya.core.base.loop.Loop;
import chiya.core.base.string.StringUtil;
import chiya.script.flow.ChiyaSingleFlow;
import chiya.script.flow.FlowInfo;
import chiya.script.token.ChiyaToken;
import chiya.script.token.match.ParserConfig;
import chiya.script.token.match.SyntaxFactor;
import chiya.script.token.match.SyntaxMatch;
import chiya.script.token.match.SyntaxRule;

/**
 * 语法解析
 * 
 * @author chiya
 *
 */
public class ChiyaSyntaxParser {
	/** 解析所用到的流配置 */
	private ChiyaSingleFlow<List<SyntaxRule>> chiyaFlow = new ChiyaSingleFlow<>();
	/** 关键字列表 */
	private HashSet<String> keyword = new HashSet<>();
	/** 匹配流模式标记 */
	private static final String FLOW_TOKEN_MATCH = "TOKEN_MATCH";
	/** 自定义流标记 */
	private static final String FLOW_CUSTOMIZE = "COSTOMIZE";
	/** 自定义流方法 */
	private HashMap<Object, Consumer<List<ChiyaToken>>> customizeMap = new HashMap<>();

	/**
	 * 添加工作流
	 * 
	 * @param index        执行的索引
	 * @param parserConfig 解析配置
	 * @param syntaxFactor 语法规则
	 */
	public void addFlow(double index, ParserConfig parserConfig, SyntaxFactor syntaxFactor) {
		chiyaFlow.addFlow(index, FLOW_TOKEN_MATCH)
			.getDataOrSet(f -> new ArrayList<>())
			.add(
				new SyntaxRule()
					.chainConfig(parserConfig)
					.chainSyntaxMatch(new SyntaxMatch(syntaxFactor))
			);
	}

	/**
	 * 自定义流
	 * 
	 * @param index    执行索引
	 * @param consumer 自定义方法
	 */
	public void customizeFlow(double index, Consumer<List<ChiyaToken>> consumer) {
		customizeMap.put(chiyaFlow.addFlow(index, FLOW_CUSTOMIZE), consumer);
	}

	/**
	 * 注册关键字
	 * 
	 * @param data 关键字
	 */
	public void registerKeyword(String... data) {
		if (data != null) { Loop.forEach(data, value -> keyword.add(value)); }
	}

	/**
	 * 标记关键字
	 * 
	 * @param listToken   token列表
	 * @param ignoreCase  是否区分大小写
	 * @param replaceType 替换类型
	 * @return token列表
	 */
	public List<ChiyaToken> markKeyword(List<ChiyaToken> listToken, boolean ignoreCase, String replaceType) {
		HashSet<String> set = new HashSet<>();
		// 如果不区分大小写，统一转小写
		keyword.forEach(value -> set.add(ignoreCase ? value.toLowerCase() : value));
		ChiyaToken.recursion(listToken, token -> {
			String nowData = token.getData();
			if (nowData != null && StringUtil.eqString(token.getType(), replaceType)) {
				if (ignoreCase) { nowData = nowData.toLowerCase(); }
				if (set.contains(nowData)) { token.setType("key:" + nowData); }
			}
		});

		return listToken;
	}

	/**
	 * 标记关键字
	 * 
	 * @param listToken  token列表
	 * @param ignoreCase 是否区分大小写
	 * @return token列表
	 */
	public List<ChiyaToken> markKeyword(List<ChiyaToken> listToken, boolean ignoreCase) {
		return markKeyword(listToken, ignoreCase, "any");
	}

	/**
	 * 标记关键字
	 * 
	 * @param listToken token列表
	 * @return token列表
	 */
	public List<ChiyaToken> markKeyword(List<ChiyaToken> listToken) {
		return markKeyword(listToken, false);
	}

	/**
	 * 获取匹配因子
	 * 
	 * @param listToken 迭代的token
	 * @param index     当前下标
	 * @param flowInfo  流信息
	 * @param layer     当前层数
	 * @return 匹配因子
	 */
	public SyntaxRule switchFactor(List<ChiyaToken> listToken, int index, FlowInfo<List<SyntaxRule>> flowInfo, int layer) {

		// 需要判断的队列
		List<SyntaxRule> judgeList = new ArrayList<>();
		List<SyntaxRule> nextList = new ArrayList<>();
		ChiyaArray<SyntaxRule> finshList = new ChiyaArray<>();

		flowInfo.getData().forEach(rule -> {
			int configLayer = rule.getConfig().getEfficientLayer();
			// 层数规则判断，可以减少有层数配置中无效的计算量
			if (configLayer == -1 || layer < configLayer) { judgeList.add(rule); }
		});

		// 每次匹配都要对所有匹配器重置
		judgeList.forEach(rule -> rule.getSyntaxMatch().reset());
		while (index < listToken.size()) {
			for (SyntaxRule rule : judgeList) {
				int x = rule.getSyntaxMatch().prefix(listToken.get(index));
				if (x == 0) { nextList.add(rule); }
				if (x == 1) { finshList.add(rule); }
			}
			judgeList.clear();
			judgeList.addAll(nextList);
			nextList.clear();
			// 没有药判断的队列时退出
			if (judgeList.isEmpty()) { break; }
			index++;
		}
		// 对允许匹配到结尾的进行处理
		judgeList.forEach(rule -> {
			if (rule.getSyntaxMatch().getAllowEnd()) { finshList.add(rule); }
		});
		// 按照最后匹配原则获取
		return finshList.getLast();
	}

	/**
	 * 更改为父级token
	 * 
	 * @param fatherIndex 父级下标
	 * @param listToken   列表
	 * @return 父级token指针
	 */
	private static ChiyaToken fatherToken(int fatherIndex, List<ChiyaToken> listToken) {
		// 改变token树
		ChiyaToken tempBranch = listToken.get(fatherIndex);
		listToken.forEach(token -> {
			if (token != tempBranch) {
				// 将除了自己以外的值全部移到自己的树下
				tempBranch.addTree(token);
			}
		});
		return tempBranch;
	}

	/**
	 * token解析模式
	 * 
	 * @param whileList  循环列表
	 * @param finishList 完成的列表
	 * @param flow       当前匹配流
	 * @param layer      当前层数
	 */
	private void tokenParser(List<ChiyaToken> whileList, List<ChiyaToken> finishList, FlowInfo<List<SyntaxRule>> flow, int layer) {
		int nowIndex = 0;
		finishList.clear();
		while (nowIndex < whileList.size()) {
			// 判断匹配项存不存在
			SyntaxRule syntaxRule = switchFactor(whileList, nowIndex, flow, layer);
			if (syntaxRule == null) {
				// 没有匹配项，进入下一次匹配
				finishList.add(whileList.get(nowIndex));
				nowIndex++;
			} else {

				SyntaxMatch syntaxMatch = syntaxRule.getSyntaxMatch();
				// 匹配器具有公用性质，关键数据需要进行备份，放置递归丢失
				SyntaxFactor syntaxFactor = syntaxMatch.getSyntaxFactor();
				int matchIndex = syntaxMatch.getNowIndex();
				boolean matchEndFlag = syntaxMatch.getAllowEnd();
				// 如果存在，则进行进一步处理
				ChiyaToken branch = new ChiyaToken().chainType(syntaxFactor.getType());
				// 需要递归场景
				if (syntaxRule.getConfig().getNeedRecursion()) {
					List<ChiyaToken> tempList = new ArrayList<>(whileList.subList(nowIndex, nowIndex + matchIndex));
					// 更改匹配到的类型
					syntaxMatch.changeType(tempList);
					// 计算要在内层迭代的节点信息
					int startIndex = 1;
					int endIndex = tempList.size() - 1;

					// 需要前缀匹配，则起始下标为0，与前缀放置外层冲突
					if (syntaxRule.getConfig().getPrefixMatch()) {
						startIndex = 0;
					} else {
						// 如果前缀放置外层，则直接放置
						if (syntaxRule.getConfig().getPrefixOutside()) {
							finishList.add(tempList.get(0));
						} else {
							// 否则放入树中
							branch.addTree(tempList.get(0));
						}
					}
					if (syntaxRule.getConfig().getSuffixMatch()) {
						// 后缀继续匹配，则下标就是匹配列队的大小
						endIndex = tempList.size();
					}
					// 递归解析，此处会公用语法解析器，需要注意
					branch.addTree(parser(tempList.subList(startIndex, endIndex), layer + 1));

					nowIndex = nowIndex + matchIndex - 1;
					if (syntaxRule.getConfig().getSuffixOutside() &&
						!syntaxRule.getConfig().getSuffixMatch() &&
						!matchEndFlag) {
						// 如果有放置外层，并且不需要后续匹配，且匹配未完成，直接放入完成队列
						finishList.add(branch);
					} else {
						if (!syntaxRule.getConfig().getSuffixMatch()) {
							// 如果没有继续后缀匹配
							branch.addTree(tempList.get(tempList.size() - 1));
						}
						// 如果有改变父级的特性
						whileList.set(
							nowIndex,
							syntaxFactor.getFatherIndex() != null ? fatherToken(syntaxFactor.getFatherIndex(), branch.getTokenTree()) : branch
						);
					}
				} else {
					// 非递归场景
					// 计算默认截取的下标
					int startIndex = nowIndex;
					int endIndex = nowIndex + matchIndex;
					// 优先更改类型
					syntaxMatch.changeType(whileList.subList(startIndex, endIndex));
					if (syntaxRule.getConfig().getPrefixOutside()) {
						finishList.add(whileList.get(startIndex));
						startIndex++;
					}
					if (syntaxRule.getConfig().getSuffixOutside()) {
						// 先减
						endIndex--;
					}
					// 向子节点中添加
					branch.addTree(whileList.subList(startIndex, endIndex));
					if (syntaxRule.getConfig().getSuffixOutside()) {
						// 此处再加入
						finishList.add(whileList.get(endIndex + 1));
					}
					nowIndex = nowIndex + matchIndex - 1;
					// 改变token树
					whileList.set(
						nowIndex,
						syntaxFactor.getFatherIndex() != null ? fatherToken(syntaxFactor.getFatherIndex(), branch.getTokenTree()) : branch
					);

				}
				branch
					.chainCharLineStart(branch.getTokenTree().get(0).getCharLineStart())
					.chainCharLineEnd(branch.getTokenTree().get(branch.getTokenTree().size() - 1).getCharLineEnd());
			}
		}
		whileList.clear();
		whileList.addAll(finishList);
	}

	/**
	 * 解析token
	 * 
	 * @param listToken token列表
	 * @param lyaer     层数
	 * @return 最终半AST的token列表
	 */
	private List<ChiyaToken> parser(List<ChiyaToken> listToken, int layer) {
		List<ChiyaToken> whileList = new ArrayList<>(listToken);
		List<ChiyaToken> finishList = new ArrayList<>();
		Loop.forEach(chiyaFlow.getFlow(), flow -> {
			switch (flow.getType()) {
				case FLOW_TOKEN_MATCH -> tokenParser(whileList, finishList, flow, layer);
				case FLOW_CUSTOMIZE -> customizeMap.get(flow).accept(whileList);
			}
		});

		return whileList;
	}

	/**
	 * 解析token
	 * 
	 * @param listToken token列表
	 * @return 最终半AST的token列表
	 */
	public List<ChiyaToken> parser(List<ChiyaToken> listToken) {
		return parser(listToken, 0);
	}

}
