package chiya.graph;

import java.util.List;

/**
 * 区块执行任务
 * 
 * @author chiya
 *
 */
public class GraphBlockTask {

	/** 区块名称 */
	private String blockName;
	/** 起始节点 */
	private String nodeStart;
	/** 终点节点 */
	private String nodeEnd;
	/** 找到的路径 */
	private List<String> path;
	/** 是否找到 */
	private boolean success;

	/**
	 * 获取区块名称
	 * 
	 * @return 区块名称
	 */
	public String getBlockName() {
		return blockName;
	}

	/**
	 * 设置区块名称
	 * 
	 * @param blockName 区块名称
	 */
	public void setBlockName(String blockName) {
		this.blockName = blockName;
	}

	/**
	 * 链式添加区块名称
	 * 
	 * @param blockName 区块名称
	 * @return 对象本身
	 */
	public GraphBlockTask chainBlockName(String blockName) {
		setBlockName(blockName);
		return this;
	}

	/**
	 * 获取起始节点
	 * 
	 * @return 起始节点
	 */
	public String getNodeStart() {
		return nodeStart;
	}

	/**
	 * 设置起始节点
	 * 
	 * @param nodeStart 起始节点
	 */
	public void setNodeStart(String nodeStart) {
		this.nodeStart = nodeStart;
	}

	/**
	 * 链式添加起始节点
	 * 
	 * @param nodeStart 起始节点
	 * @return 对象本身
	 */
	public GraphBlockTask chainNodeStart(String nodeStart) {
		setNodeStart(nodeStart);
		return this;
	}

	/**
	 * 获取终点节点
	 * 
	 * @return 终点节点
	 */
	public String getNodeEnd() {
		return nodeEnd;
	}

	/**
	 * 设置终点节点
	 * 
	 * @param nodeEnd 终点节点
	 */
	public void setNodeEnd(String nodeEnd) {
		this.nodeEnd = nodeEnd;
	}

	/**
	 * 链式添加终点节点
	 * 
	 * @param nodeEnd 终点节点
	 * @return 对象本身
	 */
	public GraphBlockTask chainNodeEnd(String nodeEnd) {
		setNodeEnd(nodeEnd);
		return this;
	}

	/**
	 * 获取找到的路径
	 * 
	 * @return 找到的路径
	 */
	public List<String> getPath() {
		return path;
	}

	/**
	 * 设置找到的路径
	 * 
	 * @param path 找到的路径
	 */
	public void setPath(List<String> path) {
		this.path = path;
	}

	/**
	 * 链式添加找到的路径
	 * 
	 * @param path 找到的路径
	 * @return 对象本身
	 */
	public GraphBlockTask chainPath(List<String> path) {
		setPath(path);
		return this;
	}

	/**
	 * 获取是否找到
	 * 
	 * @return 是否找到
	 */
	public boolean getSuccess() {
		return success;
	}

	/**
	 * 设置是否找到
	 * 
	 * @param success 是否找到
	 */
	public void setSuccess(boolean success) {
		this.success = success;
	}

	/**
	 * 链式添加是否找到
	 * 
	 * @param success 是否找到
	 * @return 对象本身
	 */
	public GraphBlockTask chainSuccess(boolean success) {
		setSuccess(success);
		return this;
	}

}
