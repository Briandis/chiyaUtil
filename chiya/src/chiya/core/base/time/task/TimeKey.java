package chiya.core.base.time.task;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * 时间键
 * 
 * @param <K> 键类型
 * @author chiya
 */
public class TimeKey<K> implements Delayed {
	/** 创建时间 */
	private long createTime;
	/** 延迟时间 */
	private long timeOut;
	/** 对象标识 */
	private K key;

	/**
	 * 重写比较方法，与判断的O相比，-1小于，0等于，1大于
	 */
	@Override
	public int compareTo(Delayed o) {
		return (int) (this.getDelay(TimeUnit.MILLISECONDS) - o.getDelay(TimeUnit.MILLISECONDS));
	}

	/**
	 * 获取延迟时间
	 */
	@Override
	public long getDelay(TimeUnit unit) {
		return unit.convert((createTime + timeOut) - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
	}

	/**
	 * 构造方法
	 * 
	 * @param key        键
	 * @param createTime 创建时间
	 * @param timeOut    超时时间
	 */
	public TimeKey(K key, long createTime, long timeOut) {
		this.key = key;
		this.createTime = createTime;
		this.timeOut = timeOut;
	}

	/**
	 * 获取创建时间
	 *
	 * @return 创建时间
	 */
	public long getCreateTime() {
		return createTime;
	}

	/**
	 * 设置创建时间
	 *
	 * @param createTime 创建时间
	 */
	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

	/**
	 * 获取超时时间
	 *
	 * @return 超时时间
	 */
	public long getTimeOut() {
		return timeOut;
	}

	/**
	 * 设置超时时间
	 *
	 * @param timeOut 超时时间
	 */
	public void setTimeOut(long timeOut) {
		this.timeOut = timeOut;
	}

	/**
	 * 获取键
	 *
	 * @return 键
	 */
	public K getKey() {
		return key;
	}

	/**
	 * 设置键
	 *
	 * @param key 键
	 */
	public void setKey(K key) {
		this.key = key;
	}

}
