package chiya.graph;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;

import chiya.core.base.collection.ChiyaMap;
import chiya.core.base.loop.Loop;
import chiya.core.base.number.IntervalPack;
import chiya.core.base.other.CoordinateUtil;
import chiya.core.base.string.StringUtil;

/**
 * 基础图结构
 * 
 * @author chiya
 *
 */
public class GraphBlockMap {
	/** 节点信息索引 */
	private ConcurrentHashMap<String, GraphNode> nodeMap = new ConcurrentHashMap<>();
	/** 边信息索引 */
	private ChiyaMap<String, String, GraphSide> sideMap = new ChiyaMap<>();
	/** 连接点 */
	private ConcurrentSkipListSet<String> linkNode = new ConcurrentSkipListSet<>();
	/** 网格组信息 */
	private ConcurrentHashMap<String, GraphGroup> groupMap = new ConcurrentHashMap<>();
	/** 区块名称 */
	private String name;
	/** 图结构更新 */
	private boolean mapUpdate = true;
	/** 节点反向索引网格组 */
	private ConcurrentHashMap<String, GraphGroup> nodeGroupIndex = new ConcurrentHashMap<>();

	/**
	 * 是否是连接点
	 * 
	 * @param nodeName 节点名称
	 * @return true:是/false:不是
	 */
	public boolean isLinkNode(String nodeName) {
		return linkNode.contains(nodeName);
	}

	/**
	 * 网格组索引
	 * 
	 * @param nodeName 网格组
	 * @return 网格组信息
	 */
	public GraphGroup groupIndex(String nodeName) {
		return nodeGroupIndex.get(nodeName);
	}

	/**
	 * 构建网格组
	 */
	public void createGroup() {
		groupMap.clear();
		nodeGroupIndex.clear();
		// 使用了的节点
		ConcurrentSkipListSet<String> isUseNode = new ConcurrentSkipListSet<>();
		ConcurrentHashMap<String, GraphNode> allNode = new ConcurrentHashMap<>();
		allNode.putAll(nodeMap);
		nodeMap.forEach((nodeName, graphNode) -> {
			// 被使用则退出
			if (isUseNode.contains(nodeName)) { return; }
			isUseNode.add(nodeName);

			// 构建网格组
			GraphGroup graphGroup = new GraphGroup();
			graphGroup.addNode(linkNode.contains(nodeName), graphNode);
			// 节点反向索引网格组
			nodeGroupIndex.put(nodeName, graphGroup);
			List<String> findNode = new ArrayList<>();
			// 下一个要查找的节点，对找到的节点作循环判断
			findNode.add(nodeName);
			while (!findNode.isEmpty()) {
				String nowNodeName = findNode.remove(findNode.size() - 1);
				isUseNode.add(nowNodeName);
				sideMap.forEach(nowNodeName, (nextNodeName, side) -> {
					if (!isUseNode.contains(nextNodeName) &&
						sideMap.contains(nextNodeName, nowNodeName)) {
						// 下一个节点未被使用，且下一个节点与当前节点连通
						findNode.add(nextNodeName);
						graphGroup.addNode(linkNode.contains(nextNodeName), nodeMap.get(nextNodeName));
						nodeGroupIndex.put(nextNodeName, graphGroup);
					}
				});
			}
			graphGroup.createName();
			graphGroup.centerPoint();
			groupMap.put(graphGroup.getName(), graphGroup);
		});
	}

	/**
	 * 添加节点
	 * 
	 * @param graphNode 节点
	 */
	public void addNode(GraphNode graphNode) {
		mapUpdate = true;
		nodeMap.put(graphNode.getName(), graphNode);
	}

	/**
	 * 添加节点
	 * 
	 * @param nodes 任意可迭代对象
	 */
	public void addNode(Iterable<GraphNode> nodes) {
		nodes.forEach(graphNode -> addNode(graphNode));
	}

	/**
	 * 添加边
	 * 
	 * @param graphSide 边
	 */
	public void addSide(GraphSide graphSide) {
		// 两个节点必须先行注册，才允许注册边
		if (nodeMap.containsKey(graphSide.getNodeFrom()) &&
			nodeMap.containsKey(graphSide.getNodeTo())) {

			sideMap.put(graphSide.getNodeFrom(), graphSide.getNodeTo(), graphSide);
			GraphNode start = nodeMap.get(graphSide.getNodeFrom());
			GraphNode target = nodeMap.get(graphSide.getNodeTo());
			// 计算路径代价
			graphSide.setCost(
				CoordinateUtil.distance(
					start.getIndexX(),
					start.getIndexY(),
					start.getIndexZ(),
					target.getIndexX(),
					target.getIndexY(),
					target.getIndexZ()
				)
			);
			// 标记结构更新
			mapUpdate = true;
		}
	}

	/**
	 * 添加边
	 * 
	 * @param graphSide   边
	 * @param noDirection 无向边
	 */
	public void addSide(GraphSide graphSide, boolean noDirection) {
		addSide(graphSide);
		if (noDirection) { addSide(graphSide.exchange()); }
	}

	/**
	 * 添加边
	 * 
	 * @param fromNode    从这个节点
	 * @param toNode      到这个节点
	 * @param noDirection 是否无向边
	 */
	public void addSide(String fromNode, String toNode, boolean noDirection) {
		addSide(
			new GraphSide().chainNodeFrom(fromNode).chainNodeTo(toNode),
			noDirection
		);
	}

	/**
	 * 添加边
	 * 
	 * @param fromNode 从这个节点
	 * @param toNode   到这个节点
	 */
	public void addSide(String fromNode, String toNode) {
		addSide(new GraphSide().chainNodeFrom(fromNode).chainNodeTo(toNode));
	}

	/**
	 * 添加节点
	 * 
	 * @param sides 任意可迭代对象
	 */
	public void addSide(Iterable<GraphSide> sides) {
		sides.forEach(graphSide -> addSide(graphSide));
	}

	/**
	 * 删除边边
	 * 
	 * @param fromNode    从这个节点
	 * @param toNode      到这个节点
	 * @param noDirection 是否无向边
	 */
	public void removeSide(String fromNode, String toNode) {
		mapUpdate = sideMap.remove(fromNode, toNode) == null ? mapUpdate : true;
	}

	/**
	 * 删除边边
	 * 
	 * @param fromNode    从这个节点
	 * @param toNode      到这个节点
	 * @param noDirection 是否无向边
	 */
	public void removeSide(String fromNode, String toNode, boolean noDirection) {
		removeSide(fromNode, toNode);
		if (noDirection) { removeSide(toNode, fromNode); }
	}

	/**
	 * 注册连接点
	 * 
	 * @param nodeName 连接点名称
	 */
	public void registerLinkNode(String nodeName) {
		linkNode.add(nodeName);
	}

	/**
	 * 注册连接点
	 * 
	 * @param linkNodes 一个或多个连接点名称
	 */
	public void registerLinkNode(String... linkNodes) {
		Loop.forEach(linkNodes, nodeName -> linkNode.add(nodeName));
	}

	/**
	 * 注册连接点
	 * 
	 * @param names 一个或多个连接点名称
	 */
	public void registerLinkNode(Iterable<String> names) {
		Loop.forEach(names, nodeName -> linkNode.add(nodeName));
	}

	/**
	 * 根据名称获取边
	 * 
	 * @param nodeFrom 从节点
	 * @param nodeTo   目标点
	 * @return 边信息
	 */
	public GraphSide getGraphSide(String nodeFrom, String nodeTo) {
		return sideMap.get(nodeFrom, nodeTo);
	}

	/**
	 * 查找最小代价节点，并删除
	 * 
	 * @param costMap 代价索引
	 * @return 找到的节点
	 */
	private String findMinCost(ConcurrentHashMap<String, Double> costMap) {
		String resultNode = null;
		Double minCost = null;
		for (Entry<String, Double> entry : costMap.entrySet()) {
			if (minCost == null || minCost > entry.getValue()) {
				minCost = entry.getValue();
				resultNode = entry.getKey();
			}
		}
		costMap.remove(resultNode);
		return resultNode;
	}

	/**
	 * 查找最小代价节点
	 * 
	 * @param nowNode    当前节点
	 * @param targetNode 目标节点
	 * @param isUseNode  使用的节点
	 * @param costMap    代价索引
	 */
	private String findMinCostNode(String nowNode, String targetNode, ConcurrentSkipListSet<String> isUseNode, ConcurrentHashMap<String, Double> costMap) {
		if (!sideMap.contains(nowNode)) { return null; }
		ConcurrentHashMap<String, GraphSide> nextNode = new ConcurrentHashMap<>();
		sideMap.forEach(nowNode, (nodeName, side) -> {
			if (!isUseNode.contains(nodeName)) { nextNode.put(nodeName, side); }
		});

		// 目标点相同，或者下一个就是目标点
		if (StringUtil.eqString(nowNode, targetNode) || nextNode.containsKey(targetNode)) { return targetNode; }
		// 没有可用相邻点
		if (nextNode.isEmpty()) { return null; }
		GraphNode target = nodeMap.get(targetNode);
		nextNode.forEach((nodeName, side) -> {
			GraphNode next = nodeMap.get(nodeName);
			costMap.put(
				nodeName,
				CoordinateUtil.distance(
					next.getIndexX(),
					next.getIndexY(),
					next.getIndexZ(),
					target.getIndexX(),
					target.getIndexY(),
					target.getIndexZ()
				) + side.getCost()
			);
		});
		return findMinCost(costMap);
	}

	/**
	 * 查找两点之间可通行路径
	 * 
	 * @param startNode  起始点
	 * @param targetNode 终点
	 */
	public List<String> find(String startNode, String targetNode) {
		// 查询的目标点必须存在
		if (!nodeMap.containsKey(startNode) || !nodeMap.containsKey(targetNode)) { return null; }
		// 寻找到的路径
		List<String> path = new ArrayList<String>();
		path.add(startNode);
		if (StringUtil.eqString(startNode, targetNode)) { return path; }
		String nowNode = startNode;
		// 使用了的节点
		ConcurrentSkipListSet<String> isUseNode = new ConcurrentSkipListSet<>();

		boolean notFind = true;
		ConcurrentHashMap<String, ConcurrentHashMap<String, Double>> costIndex = new ConcurrentHashMap<>();
		while (true) {
			isUseNode.add(nowNode);
			// 查找节点最小代价
			ConcurrentHashMap<String, Double> costMap = new ConcurrentHashMap<>();

			String nextNode = findMinCostNode(nowNode, targetNode, isUseNode, costMap);
			if (StringUtil.eqString(nextNode, targetNode)) {
				path.add(nextNode);
				break;
			}
			if (nextNode == null) {
				notFind = false;
				while (!path.isEmpty()) {
					String lastNode = path.remove(path.size() - 1);
					if (costIndex.containsKey(lastNode)) {
						nextNode = findMinCost(costIndex.get(lastNode));
						if (costIndex.get(lastNode).isEmpty()) { costIndex.remove(lastNode); }
						if (nextNode == null) { continue; }
						notFind = true;
						path.add(lastNode);
						path.add(nextNode);
					}
				}
				if (!notFind) { break; }
			} else {
				// 添加可用的节点
				if (!costMap.isEmpty()) { costIndex.put(nowNode, costMap); }
				path.add(nextNode);
			}
			nowNode = nextNode;
		}
		// 路径优化
		optimizationPath(path, costIndex);
		return notFind ? path : null;
	}

	/**
	 * 优化路径
	 * 
	 * @param path      当前路径
	 * @param costIndex 当前索引
	 */
	public void optimizationPath(List<String> path, ConcurrentHashMap<String, ConcurrentHashMap<String, Double>> costIndex) {
		if (!path.isEmpty()) {
			List<IntervalPack> newPath = new ArrayList<>();

			int lastStart = 0;
			boolean isNew = false;
			ConcurrentSkipListSet<String> isUseNode = new ConcurrentSkipListSet<>();
			// 构建节点与下标的关系
			ConcurrentHashMap<String, Integer> nodeIndex = new ConcurrentHashMap<>();
			for (int i = 0; i < path.size(); i++) {
				nodeIndex.put(path.get(i), i);
			}
			String nowNode = null;
			// 循环查询子节点
			for (int index = 0; index < path.size() - 1; index++) {
				nowNode = path.get(index);
				isUseNode.add(nowNode);
				for (var entry : sideMap.entrySet(nowNode)) {
					if (!StringUtil.eqString(entry.getValue().getNodeTo(), path.get(index + 1)) &&
						nodeIndex.containsKey(entry.getValue().getNodeTo()) &&
						index < nodeIndex.get(entry.getValue().getNodeTo())) {
						// 当前节点的指向下一个节点的信息不能使用过，并且下标要大于之前的，并且存在与之前的路径
						isNew = true;
						newPath.add(new IntervalPack().chainStart(lastStart).chainEnd(index + 1));
						lastStart = nodeIndex.get(entry.getValue().getNodeTo());
						index = lastStart;
						continue;
					}
				}
			}
			if (isNew) {
				newPath.add(new IntervalPack().chainStart(lastStart).chainEnd(path.size()));
				List<String> finalPath = new ArrayList<>();
				newPath.forEach(pack -> {
					finalPath.addAll(path.subList(pack.getStart(), pack.getEnd()));
				});
				path.clear();
				path.addAll(finalPath);

			}
		}

	}

	/**
	 * 获取区块名称
	 * 
	 * @return 区块名称
	 */
	public String getName() {
		return name;
	}

	/**
	 * 设置区块名称
	 * 
	 * @param name 区块名称
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 链式添加区块名称
	 * 
	 * @param name 区块名称
	 * @return 对象本身
	 */
	public GraphBlockMap chainName(String name) {
		setName(name);
		return this;
	}

	/**
	 * 获取网格组信息
	 * 
	 * @return 网格组信息
	 */
	public ConcurrentHashMap<String, GraphGroup> getGroupMap() {
		// 图结构更新，需要重新生成网格组
		if (mapUpdate) {
			mapUpdate = false;
			createGroup();
		}
		return groupMap;
	}

}
