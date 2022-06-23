package chiya.core.base.count;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import chiya.core.base.collection.ContainerUtil;

/**
 * 计数工具
 * 
 * @author chiya
 */
public class CountMap<T> {

	/** 计数器 */
	private ConcurrentHashMap<T, AtomicLong> count = new ConcurrentHashMap<T, AtomicLong>();

	/**
	 * 获取或创建一个新的值
	 * 
	 * @param key 键
	 * @return 原子自增值
	 */
	private AtomicLong getOrNewValue(T key) {
		return ContainerUtil.getValueOrPut(count, key, AtomicLong.class);
	}

	/**
	 * 数量+1
	 * 
	 * @param key 键
	 */
	public long increment(T key) {
		return getOrNewValue(key).incrementAndGet();
	}

	/**
	 * 数量-1
	 * 
	 * @param key 键
	 */
	public long decrement(T key) {
		return getOrNewValue(key).decrementAndGet();
	}

	/**
	 * 获取计数器
	 *
	 * @return ConcurrentHashMap<T, AtomicLong> 计数器
	 */
	public ConcurrentHashMap<T, AtomicLong> getCount() {
		return count;
	}

	/**
	 * 重置所有为0
	 */
	public void reset() {
		count.forEach((key, value) -> value.set(0));
	}

	/**
	 * 重置某个特定key为0，如果这个key存在
	 * 
	 * @param key
	 */
	public void reset(T key) {
		if (count.containsKey(key)) { count.get(key).set(0); }
	}

	/**
	 * 获取值
	 * 
	 * @param key 键
	 * @return 值
	 */
	public long get(T key) {
		return getOrNewValue(key).intValue();
	}

	/**
	 * 删除key
	 * 
	 * @param key
	 * @return 原子自增对象
	 */
	public AtomicLong remove(T key) {
		return count.remove(key);
	}

	@Override
	public String toString() {
		return count.toString();
	}
}
