package chiya.script.match;

import java.util.ArrayList;
import java.util.List;

import chiya.core.base.loop.Loop;

/**
 * 索引树
 * 
 * @author chiya
 *
 */
public class MatchTree {

	/** 根节点 */
	private TreeNode<Character, Integer> root = new TreeNode<>();
	/** 匹配节点 */
	private TreeNode<Character, Integer> matchNode = root;

	/** 重置 */
	public void reset() {
		matchNode = root;
	}

	/**
	 * 构建索引
	 * 
	 * @param key            索引键
	 * @param startNode      开始的节点
	 * @param matchData      匹配的值
	 * @param needIgnoreCase 是否区分大小写
	 */
	public static void createIndex(int key, TreeNode<Character, Integer> startNode, String matchData, boolean needIgnoreCase) {
		List<TreeNode<Character, Integer>> nodeList = new ArrayList<>();
		List<TreeNode<Character, Integer>> nextNode = new ArrayList<>();
		// 起始节点
		nodeList.add(startNode);
		Loop.forEach(matchData, (nowChar, i) -> {
			nextNode.clear();
			nodeList.forEach(nowNode -> {
				// 忽略大小写情况
				if (needIgnoreCase) {
					nextNode.add(nowNode.createKey(Character.toLowerCase(nowChar)));
					nextNode.add(nowNode.createKey(Character.toUpperCase(nowChar)));
				} else {
					nextNode.add(nowNode.createKey(nowChar));
				}
			});
			// 因为存在大小写，所以需要用list装多个，最后对末端节点保存值
			nodeList.clear();
			nodeList.addAll(nextNode);
		});
		// 保存的是配置的下标
		nodeList.forEach(nowNode -> nowNode.add(key));

	}

	/**
	 * 添加
	 * 
	 * @param key            键
	 * @param startData      起始数据
	 * @param needIgnoreCase 需要忽略大小写
	 */
	public void add(int key, String startData, boolean needIgnoreCase) {
		createIndex(key, root, startData, needIgnoreCase);
	}

	/**
	 * 按照
	 * 
	 * @param nowChar 当前字符
	 * @return 迭代时的节点
	 */
	public TreeNode<Character, Integer> each(Character nowChar) {
		if (matchNode != null) { matchNode = matchNode.nextChildren(nowChar); }
		return matchNode;
	}

}
