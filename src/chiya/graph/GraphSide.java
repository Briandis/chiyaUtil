package chiya.graph;

/**
 * 图中的边
 * 
 * @author chiya
 *
 */
public class GraphSide {
	/** 从节点 */
	private String nodeFrom;
	/** 目标节点 */
	private String nodeTo;
	/** 代价 */
	private double cost = 0;
	/** 从区块 */
	private String blockFrom;
	/** 从节点 */
	private String blockTo;

	/**
	 * 节点互换
	 * 
	 * @return 新节点
	 */
	public GraphSide exchange() {
		return new GraphSide()
			.chainCost(cost)
			.chainNodeTo(nodeFrom)
			.chainNodeFrom(nodeTo)
			.chainBlockFrom(blockTo)
			.chainNodeFrom(nodeTo)
			.chainBlockTo(blockFrom)
			.chainNodeTo(nodeFrom);
	}

	/**
	 * 获取从节点
	 * 
	 * @return 从节点
	 */
	public String getNodeFrom() {
		return nodeFrom;
	}

	/**
	 * 设置从节点
	 * 
	 * @param nodeFrom 从节点
	 */
	public void setNodeFrom(String nodeFrom) {
		this.nodeFrom = nodeFrom;
	}

	/**
	 * 链式添加从节点
	 * 
	 * @param nodeFrom 从节点
	 * @return 对象本身
	 */
	public GraphSide chainNodeFrom(String nodeFrom) {
		setNodeFrom(nodeFrom);
		return this;
	}

	/**
	 * 获取目标节点
	 * 
	 * @return 目标节点
	 */
	public String getNodeTo() {
		return nodeTo;
	}

	/**
	 * 设置目标节点
	 * 
	 * @param nodeTo 目标节点
	 */
	public void setNodeTo(String nodeTo) {
		this.nodeTo = nodeTo;
	}

	/**
	 * 链式添加目标节点
	 * 
	 * @param nodeTo 目标节点
	 * @return 对象本身
	 */
	public GraphSide chainNodeTo(String nodeTo) {
		setNodeTo(nodeTo);
		return this;
	}

	/**
	 * 获取代价
	 * 
	 * @return 代价
	 */
	public double getCost() {
		return cost;
	}

	/**
	 * 设置代价
	 * 
	 * @param cost 代价
	 */
	public void setCost(double cost) {
		this.cost = cost;
	}

	/**
	 * 链式添加代价
	 * 
	 * @param cost 代价
	 * @return 对象本身
	 */
	public GraphSide chainCost(double cost) {
		setCost(cost);
		return this;
	}

	/**
	 * 获取从区块
	 * 
	 * @return 从区块
	 */
	public String getBlockFrom() {
		return blockFrom;
	}

	/**
	 * 设置从区块
	 * 
	 * @param blockFrom 从区块
	 */
	public void setBlockFrom(String blockFrom) {
		this.blockFrom = blockFrom;
	}

	/**
	 * 链式添加从区块
	 * 
	 * @param blockFrom 从区块
	 * @return 对象本身
	 */
	public GraphSide chainBlockFrom(String blockFrom) {
		setBlockFrom(blockFrom);
		return this;
	}

	/**
	 * 获取从节点
	 * 
	 * @return 从节点
	 */
	public String getBlockTo() {
		return blockTo;
	}

	/**
	 * 设置从节点
	 * 
	 * @param blockTo 从节点
	 */
	public void setBlockTo(String blockTo) {
		this.blockTo = blockTo;
	}

	/**
	 * 链式添加从节点
	 * 
	 * @param blockTo 从节点
	 * @return 对象本身
	 */
	public GraphSide chainBlockTo(String blockTo) {
		setBlockTo(blockTo);
		return this;
	}

}
