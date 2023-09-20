package chiya.core.base.pack;

/**
 * 泛型包装类
 * 
 * @author chiya
 *
 */
public class LongPack extends ObjectPack<Long> {
	public LongPack() {
		setData(0l);
	}

	/**
	 * 初始化方法
	 * 
	 * @param data 默认值
	 */
	public LongPack(long data) {
		setData(data);
	}

	/**
	 * 获取并增加多少
	 * 
	 * @param value 值
	 * @return 增加前的值
	 */
	public long getAndAdd(long value) {
		long temp = getData();
		setData(temp + value);
		return temp;
	}

	/**
	 * 增加并获取值
	 * 
	 * @param value 增加的值
	 * @return 增加后的值
	 */
	public long addAndGet(long value) {
		long temp = getData() + value;
		setData(temp);
		return temp;
	}

	/**
	 * 获取并自增
	 * 
	 * @return 自增前的值
	 */
	public long getAndIncrement() {
		return getAndAdd(1);
	}

	/**
	 * 自增并获取
	 * 
	 * @return 自增后的值
	 */
	public long incrementAndGet() {
		return addAndGet(1);
	}

	/**
	 * 获取并自减
	 * 
	 * @return 自减前的值
	 */
	public long getAndDecrement() {
		return getAndAdd(-1);
	}

	/**
	 * 自减并获取
	 * 
	 * @return 自减后的值
	 */
	public long decrementAndGet() {
		return addAndGet(-1);
	}

}
