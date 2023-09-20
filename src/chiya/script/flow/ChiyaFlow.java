package chiya.script.flow;

import java.util.ArrayList;
import java.util.List;

import chiya.core.base.collection.ContainerUtil;
import chiya.core.base.number.NumberUtil;

/**
 * 流容器
 * 
 * @author chiya
 *
 */
public class ChiyaFlow<D> {

	/** 流信息 */
	protected final List<FlowInfo<D>> flow = new ArrayList<>();

	/**
	 * 添加流信息
	 * 
	 * @param index 下标
	 * @param type  类型
	 * @param data  数据
	 * @return 对应流信息
	 */
	public FlowInfo<D> addFlow(double index, String type, D data) {
		FlowInfo<D> flowInfo = new FlowInfo<D>().chainIndex(index).chainType(type).chainData(data);
		flow.add(flowInfo);
		return flowInfo;
	}

	/**
	 * 获取流并排序
	 * 
	 * @return 流
	 */
	public List<FlowInfo<D>> getFlow() {
		return ContainerUtil.listSort(flow, (a, b) -> NumberUtil.compareSize(a.getIndex(), b.getIndex()));
	}

}
