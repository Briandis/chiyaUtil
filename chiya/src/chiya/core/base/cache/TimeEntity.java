package chiya.core.base.cache;

import java.util.concurrent.atomic.LongAdder;

/**
 * 时间辅助对象
 * 
 * @author brain
 *
 */
public class TimeEntity<O> {
	/** 存储的对象 */
	private O data;
	/** 最后一次访问时间 */
	private long lastTime = System.currentTimeMillis();
	/** 创建时间 */
	private long creatTime = System.currentTimeMillis();
	/** 访问次数 */
	private LongAdder count = new LongAdder();

	/**
	 * 构造方法
	 * 
	 * @param data 存储的对象
	 */
	public TimeEntity(O data) {
		this.data = data;
	}

	/**
	 * 获取存储的对象
	 * 
	 * @return 存储的对象
	 */
	public O getData() {
		lastTime = System.currentTimeMillis();
		count.increment();
		return data;
	}

	/**
	 * 获取最后访问时间
	 * 
	 * @return 访问时间的时间戳
	 */
	public long getLastTime() {
		return lastTime;
	}

	/**
	 * 获取创建时间
	 * 
	 * @return 创建的时间戳
	 */
	public long getCreatTime() {
		return creatTime;
	}

	/**
	 * 获取访问统计
	 * 
	 * @return LongAdder 原子自增对象
	 */
	public LongAdder getCount() {
		return count;
	}

	/**
	 * 存储对象
	 * 
	 * @param data 存储的对象
	 */
	public void setData(O data) {
		lastTime = System.currentTimeMillis();
		this.data = data;
	}

	@Override
	public String toString() {
		return data.toString();
	}

}
