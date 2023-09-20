package chiya.script.match;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 树节点
 * 
 * @author chiya
 *
 */
public class TreeNode<T, V> {
	/** 子节点 */
	private HashMap<T, TreeNode<T, V>> children = new HashMap<>();
	/** 包含的节点 */
	private List<V> nodeData = new ArrayList<>();

	/**
	 * 添加一个索引节点
	 * 
	 * @param key 索引
	 * @return 该索引所在的索引节点
	 */
	public TreeNode<T, V> createKey(T key) {
		return children.computeIfAbsent(key, k -> new TreeNode<T, V>());
	}

	/**
	 * 添加值
	 * 
	 * @param data 值
	 */
	public void add(V data) {
		nodeData.add(data);
	}

	/**
	 * 判断key存不存在
	 * 
	 * @param key 键
	 * @return true:存在/false:不存在
	 */
	public boolean containsKey(T key) {
		return children.containsKey(key);
	}

	/**
	 * 获取子层节点
	 * 
	 * @param key 键
	 * @return 子层节点
	 */
	public TreeNode<T, V> nextChildren(T key) {
		return children.get(key);
	}

	/**
	 * 获取子节点
	 * 
	 * @return 子节点
	 */
	public HashMap<T, TreeNode<T, V>> getChildren() {
		return children;
	}

	/**
	 * 设置子节点
	 * 
	 * @param children 子节点
	 */
	public void setChildren(HashMap<T, TreeNode<T, V>> children) {
		this.children = children;
	}

	/**
	 * 链式添加子节点
	 * 
	 * @param children 子节点
	 * @return 对象本身
	 */
	public TreeNode<T, V> chainChildren(HashMap<T, TreeNode<T, V>> children) {
		setChildren(children);
		return this;
	}

	/**
	 * 获取包含的节点
	 * 
	 * @return 包含的节点
	 */
	public List<V> getNodeData() {
		return nodeData;
	}

	/**
	 * 设置包含的节点
	 * 
	 * @param nodeData 包含的节点
	 */
	public void setNodeData(List<V> nodeData) {
		this.nodeData = nodeData;
	}

	/**
	 * 链式添加包含的节点
	 * 
	 * @param nodeData 包含的节点
	 * @return 对象本身
	 */
	public TreeNode<T, V> chainNodeData(List<V> nodeData) {
		setNodeData(nodeData);
		return this;
	}

}
