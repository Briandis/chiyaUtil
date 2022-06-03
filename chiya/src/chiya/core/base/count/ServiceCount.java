package chiya.core.base.count;

import java.util.concurrent.atomic.LongAdder;

/**
 * 接口性能统计
 * 
 * @author brain
 *
 */
public class ServiceCount {

	/** 统计数量 */
	private LongAdder count = new LongAdder();
	/** 全部所用时间 */
	private LongAdder allTime = new LongAdder();

	/** 添加时间 */
	public void add(int time) {
		count.increment();
	}

	/**
	 * 获取平均时间
	 * 
	 * @return 平均时间
	 */
	public double averageTime() {
		return allTime.longValue() / count.longValue();
	}

	/**
	 * 重置计数
	 */
	public void clear() {
		count.reset();
		allTime.reset();
	}

	/**
	 * 获取原始计数器
	 * 
	 * @return 原子累加计数器
	 */
	public LongAdder getCount() {
		return count;
	}

	/**
	 * 获取原始全部时间计数器
	 * 
	 * @return 原子累加计数器
	 */
	public LongAdder getAllTime() {
		return allTime;
	}

}
