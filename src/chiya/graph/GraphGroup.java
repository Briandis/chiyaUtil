package chiya.graph;

import java.util.HashMap;

import chiya.core.base.collection.ContainerUtil;
import chiya.core.base.pack.DoublePack;
import chiya.core.base.random.RandomString;

/**
 * 网格组
 * 
 * @author chiya
 *
 */
public class GraphGroup {

	/** 网格组名称 */
	private String name;
	/** 包含节点 */
	private HashMap<String, GraphNode> node = new HashMap<>();
	/** 连接点 */
	private HashMap<String, GraphNode> linkNode = new HashMap<>();
	/** 网格组中心点X坐标 */
	private double indexX = 0;
	/** 网格组中心点Y坐标 */
	private double indexY = 0;
	/** 网格组中心点Z坐标 */
	private double indexZ = 0;

	/**
	 * 根据自身信息构建节点信息
	 * 
	 * @return 自身节点
	 */
	public GraphNode createNode() {
		return new GraphNode()
			.chainName(name)
			.chainIndexX(indexX)
			.chainIndexY(indexY)
			.chainIndexZ(indexZ);
	}

	/**
	 * 构建中心点坐标
	 */
	public void centerPoint() {
		DoublePack x = new DoublePack();
		DoublePack y = new DoublePack();
		DoublePack z = new DoublePack();
		node.forEach((nodeName, node) -> {
			x.getAndAdd(node.getIndexX());
			y.getAndAdd(node.getIndexY());
			z.getAndAdd(node.getIndexZ());
		});
		linkNode.forEach((nodeName, node) -> {
			x.getAndAdd(node.getIndexX());
			y.getAndAdd(node.getIndexY());
			z.getAndAdd(node.getIndexZ());
		});
		int count = node.size() + linkNode.size();
		if (count == 0) { return; }
		indexX = x.getData() / count;
		indexY = y.getData() / count;
		indexZ = z.getData() / count;
	}

	/**
	 * 添加节点
	 * 
	 * @param flag      是否是连接点
	 * @param graphNode 节点信息
	 */
	public void addNode(boolean flag, GraphNode graphNode) {
		if (flag) {
			addLinkNode(graphNode);
		} else {
			addNode(graphNode);
		}
	}

	/**
	 * 添加节点
	 * 
	 * @param graphNode 节点
	 */
	public void addNode(GraphNode graphNode) {
		node.put(graphNode.getName(), graphNode);
	}

	/**
	 * 添加连接点
	 * 
	 * @param graphNode 连接点
	 */
	public void addLinkNode(GraphNode graphNode) {
		linkNode.put(graphNode.getName(), graphNode);
	}

	/**
	 * 创建名称
	 * 
	 * @return 名称
	 */
	public String createName() {
		String name = null;
		if (!linkNode.isEmpty()) {
			name = ContainerUtil.getOne(linkNode.entrySet()).getKey();
		} else if (!node.isEmpty()) { name = ContainerUtil.getOne(node.entrySet()).getKey(); }
		return createName(name);
	}

	/**
	 * 创建一个名称
	 * 
	 * @param name 名称
	 * @return 创建好的名称
	 */
	public String createName(String name) {
		if (name == null) { name = RandomString.randomStringByUUID(); }
		setName(name);
		return name;
	}

	/**
	 * 获取网格组名称
	 * 
	 * @return 网格组名称
	 */
	public String getName() {
		return name;
	}

	/**
	 * 设置网格组名称
	 * 
	 * @param name 网格组名称
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 获取包含节点
	 * 
	 * @return 包含节点
	 */
	public HashMap<String, GraphNode> getNode() {
		return node;
	}

	/**
	 * 获取连接点
	 * 
	 * @return 连接点
	 */
	public HashMap<String, GraphNode> getLinkNode() {
		return linkNode;
	}

	/**
	 * 获取中心的X坐标
	 * 
	 * @return X坐标
	 */
	public double getIndexX() {
		return indexX;
	}

	/**
	 * 获取中心点Y坐标
	 * 
	 * @return Y坐标
	 */
	public double getIndexY() {
		return indexY;
	}

	/**
	 * 获取中心点Z坐标
	 * 
	 * @return Z坐标
	 */
	public double getIndexZ() {
		return indexZ;
	}

}
