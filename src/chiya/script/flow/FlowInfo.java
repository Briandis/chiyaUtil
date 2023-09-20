package chiya.script.flow;

import java.util.function.Function;

/**
 * 流信息
 * 
 * @author chiya
 *
 */
public class FlowInfo<D> {
	/** 流顺序 */
	private double index = 0;
	/** 流类型 */
	private String type;
	/** 流数据 */
	private D data;

	/**
	 * 获取流顺序
	 * 
	 * @return 流顺序
	 */
	public double getIndex() {
		return index;
	}

	/**
	 * 设置流顺序
	 * 
	 * @param index 流顺序
	 */
	public void setIndex(double index) {
		this.index = index;
	}

	/**
	 * 链式添加流顺序
	 * 
	 * @param index 流顺序
	 * @return 对象本身
	 */
	public FlowInfo<D> chainIndex(double index) {
		setIndex(index);
		return this;
	}

	/**
	 * 获取流类型
	 * 
	 * @return 流类型
	 */
	public String getType() {
		return type;
	}

	/**
	 * 设置流类型
	 * 
	 * @param type 流类型
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * 链式添加流类型
	 * 
	 * @param type 流类型
	 * @return 对象本身
	 */
	public FlowInfo<D> chainType(String type) {
		setType(type);
		return this;
	}

	/**
	 * 获取流数据
	 * 
	 * @return 流数据
	 */
	public D getData() {
		return data;
	}

	/**
	 * 获取data或者创建data
	 * 
	 * @param function 默认构建方法
	 * @return 自身
	 */
	public D getDataOrSet(Function<FlowInfo<D>, D> function) {
		if (data == null) { setData(function.apply(this)); }
		return data;
	}

	/**
	 * 设置流数据
	 * 
	 * @param data 流数据
	 */
	public void setData(D data) {
		this.data = data;
	}

	/**
	 * 链式添加流数据
	 * 
	 * @param data 流数据
	 * @return 对象本身
	 */
	public FlowInfo<D> chainData(D data) {
		setData(data);
		return this;
	}

}
