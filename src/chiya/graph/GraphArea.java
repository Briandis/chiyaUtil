package chiya.graph;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.List;

import chiya.core.base.collection.ChiyaArray;
import chiya.core.base.collection.ChiyaSet;
import chiya.core.base.collection.ContainerUtil;
import chiya.core.base.pack.ObjectPack;

/**
 * 区块区域
 * 
 * @author chiya
 *
 */
public class GraphArea {
	/** 域信息索引 */
	private GraphBlockMap area = null;
	/** 区块信息 */
	private ConcurrentHashMap<String, GraphBlockMap> blockMap = new ConcurrentHashMap<>();
	/** 连接边 */
	private List<GraphSide> linkSide = new CopyOnWriteArrayList<>();
	/** 网格组连接点 */
	private ConcurrentHashMap<String, ChiyaSet<String, String>> groupLinkNode = new ConcurrentHashMap<>();

	/**
	 * 寻路
	 * 
	 * @param blockStart 起始区块
	 * @param nodeStart  起始节点
	 * @param blockEnd   结束区块
	 * @param nodeEnd    结束节点
	 * @return 任务组
	 */
	public List<GraphBlockTask> find(String blockStart, String nodeStart, String blockEnd, String nodeEnd) {
		List<GraphBlockTask> listTask = createTask(blockStart, nodeStart, blockEnd, nodeEnd);
		listTask.forEach(task -> {
			task.setPath(blockMap.get(task.getBlockName()).find(task.getNodeStart(), task.getNodeEnd()));
			task.setSuccess(true);
		});
		return listTask;
	}

	/**
	 * 查找路径
	 * 
	 * @param blockStart 起始区块
	 * @param nodeStart  起始节点
	 * @param blockEnd   结束区块
	 * @param nodeEnd    结束节点
	 * @return 任务组
	 */
	public List<GraphBlockTask> createTask(String blockStart, String nodeStart, String blockEnd, String nodeEnd) {
		GraphBlockMap from = blockMap.get(blockStart);
		GraphBlockMap to = blockMap.get(blockEnd);
		if (from == null || to == null) { return null; }

		// 查找路径
		List<String> blockPath = area.find(
			blockStart + ":" + from.groupIndex(nodeStart).getName(),
			blockEnd + ":" + from.groupIndex(nodeEnd).getName()
		);
		if (blockPath.isEmpty()) { return null; }

		ChiyaArray<GraphBlockTask> task = new ChiyaArray<>();
		if (blockPath.size() == 1) {
			// 如果长度是1，则说明起始和结束都是自身区块
			task.add(new GraphBlockTask().chainBlockName(blockStart).chainNodeStart(nodeStart).chainNodeEnd(nodeEnd));
			return task;
		}
		task.add(
			new GraphBlockTask()
				.chainBlockName(blockStart)
				.chainNodeStart(nodeStart)
		);

		ObjectPack<GraphSide> side = new ObjectPack<>();
		for (int i = 1; i < blockPath.size(); i++) {

			ChiyaSet<String, String> chiyaSet = groupLinkNode.get(blockPath.get(i - 1) + "<>" + blockPath.get(i));
			ConcurrentSkipListSet<String> set = chiyaSet.get(task.getLast().getNodeStart());

			if (set != null) {
				// 如果有值说明是在连接点上，直接使用该连接点
				task.getLast().setNodeEnd(task.getLast().getNodeStart());
				String end = ContainerUtil.getOne(set.iterator());
				task.add(
					new GraphBlockTask()
						.chainBlockName(blockPath.get(i).split(":")[0])
						.chainNodeStart(end)
				);

			} else {
				// 普通情况
				side.setData(null);
				ContainerUtil.forEachOne(chiyaSet.entrySet(), entry -> {
					side.setData(
						new GraphSide()
							.chainNodeFrom(entry.getKey())
							.chainNodeTo(ContainerUtil.getOne(entry.getValue()))
					);
				});

				if (side.getData() != null) {
					// 修改上一个路径点的结尾
					task.getLast().setNodeEnd(side.getData().getNodeFrom());
					// 创建新的期待你
					task.add(
						new GraphBlockTask()
							.chainBlockName(blockPath.get(i).split(":")[0])
							.chainNodeStart(side.getData().getNodeTo())
					);
				}
			}

		}
		// 终点在连接点上的情况
		ChiyaSet<String, String> chiyaSet = groupLinkNode.get(blockPath.get(task.size() - 2) + "<>" + blockPath.get(task.size() - 1));
		for (var entry : chiyaSet.entrySet()) {
			if (entry.getValue().contains(nodeEnd)) {
				task.getLast().chainNodeStart(nodeEnd).chainNodeEnd(nodeEnd);
				task.get(-2).setNodeEnd(entry.getKey());
				return task;
			}
		}
		task.getLast().setNodeEnd(nodeEnd);
		return task;

	}

	/**
	 * 构建区块信息
	 */
	public void createArea() {
		GraphBlockMap area = new GraphBlockMap();
		blockMap.forEach((blockName, block) -> {
			block.getGroupMap().forEach((groupName, group) -> {
				area.addNode(group.createNode().chainName(blockName + ":" + groupName));
			});
		});
		linkSide.forEach(side -> {
			GraphBlockMap from = blockMap.get(side.getBlockFrom());
			GraphBlockMap to = blockMap.get(side.getBlockTo());
			if (from == null || to == null) { return; }
			String groupFromName = from.groupIndex(side.getNodeFrom()).getName();
			String groupEndName = to.groupIndex(side.getNodeTo()).getName();
			String groupFrom = side.getBlockFrom() + ":" + groupFromName;
			String groupEnd = side.getBlockTo() + ":" + groupEndName;
			// 添加节点与节点的索引，单向的，一个区块可能由多个连接点构成，一个连接可能连接多个其他区块连接点
			groupLinkNode.computeIfAbsent(
				groupFrom + "<>" + groupEnd,
				key -> new ChiyaSet<>()
			).put(side.getNodeFrom(), side.getNodeTo());

			area.addSide(groupFrom, groupEnd);
		});
		area.createGroup();
		this.area = area;
	}

	/**
	 * 注册连接点
	 * 
	 * @param blockName1 区块名称
	 * @param linkNode1  连接点
	 * @param blockName2 区块名称2
	 * @param linkNode2  连接点
	 */
	public void registerLinkNode(String blockName1, String linkNode1, String blockName2, String linkNode2) {
		linkSide.add(
			new GraphSide()
				.chainBlockFrom(blockName1)
				.chainNodeFrom(linkNode1)
				.chainBlockTo(blockName2)
				.chainNodeTo(linkNode2)
		);
	}

	/**
	 * 注册连接点
	 * 
	 * @param blockName1  区块名称
	 * @param linkNode1   连接点
	 * @param blockName2  区块名称2
	 * @param linkNode2   连接点
	 * @param noDirection 是否我无向边
	 */
	public void registerLinkNode(String blockName1, String linkNode1, String blockName2, String linkNode2, boolean noDirection) {
		registerLinkNode(blockName1, linkNode1, blockName2, linkNode2);
		if (noDirection) { registerLinkNode(blockName2, linkNode2, blockName1, linkNode1); }
	}

	/**
	 * 注册区块
	 * 
	 * @param graphBlockMap 区块信息
	 */
	public void registerBlock(GraphBlockMap graphBlockMap) {
		blockMap.put(graphBlockMap.getName(), graphBlockMap);
	}

}
