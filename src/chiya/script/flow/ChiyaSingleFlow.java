package chiya.script.flow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import chiya.core.base.collection.ContainerUtil;
import chiya.core.base.number.NumberUtil;

/**
 * 流，索引具有唯一性，如果相同则划分到一起
 * 
 * @author chiya
 *
 */
public class ChiyaSingleFlow<D> {

	/** 语法索引 */
	private HashMap<Double, FlowInfo<D>> flow = new HashMap<>();

	/**
	 * 添加流
	 * 
	 * @param index 流索引
	 * @param type  类型
	 * @return 创建的流信息
	 */
	public FlowInfo<D> addFlow(double index, String type) {
		return flow.computeIfAbsent(index, key -> new FlowInfo<D>().chainIndex(index).chainType(type));
	}

	/**
	 * 获取索引
	 * 
	 * @param index 索引值
	 * @return 语法配置信息
	 */
	public FlowInfo<D> getIndex(double index) {
		return flow.get(index);
	}

	/**
	 * 获取流
	 * 
	 * @return 排序好的流信息
	 */
	public List<FlowInfo<D>> getFlow() {
		ArrayList<FlowInfo<D>> list = new ArrayList<>();
		// 按照索引排序
		ContainerUtil.listSort(
			new ArrayList<>(flow.entrySet()),
			(a, b) -> NumberUtil.compareSize(a.getKey(), b.getKey())
		).forEach(entry -> list.add(entry.getValue()));

		return list;
	}

	/**
	 * 判断key存不存在
	 * 
	 * @param key 键
	 * @return true:存在/false：不存在
	 */
	public boolean containsKey(double key) {
		return flow.containsKey(key);
	}

}
