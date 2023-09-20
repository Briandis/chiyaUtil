package chiya.core.base.count;

import java.util.Set;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.LongAdder;
import java.util.function.BiConsumer;

import chiya.core.base.collection.ContainerUtil;

/**
 * 计数工具
 * 
 * @author chiya
 */
public class CountMap<T> {

	/** 计数器 */
	private ConcurrentHashMap<T, LongAdder> count = new ConcurrentHashMap<T, LongAdder>();

	/**
	 * 获取或创建一个新的值
	 * 
	 * @param key 键
	 * @return 原子累加类
	 */
	private LongAdder getOrNewValue(T key) {
		return ContainerUtil.getValueOrPut(count, key, LongAdder.class);
	}

	/**
	 * 数量+1
	 * 
	 * @param key 键
	 */
	public void increment(T key) {
		getOrNewValue(key).increment();
	}

	/**
	 * 数量-1
	 * 
	 * @param key 键
	 */
	public void decrement(T key) {
		getOrNewValue(key).decrement();
	}

	/**
	 * 获取计数器
	 *
	 * @return ConcurrentHashMap<T, LongAdder> 计数器
	 */
	public ConcurrentHashMap<T, LongAdder> getCount() {
		return count;
	}

	/**
	 * 重置所有为0
	 */
	public void reset() {
		count.forEach((key, value) -> value.reset());
	}

	/**
	 * 重置某个特定key为0，如果这个key存在
	 * 
	 * @param key
	 */
	public void reset(T key) {
		if (count.containsKey(key)) { count.get(key).reset(); }
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
	public LongAdder remove(T key) {
		return count.remove(key);
	}

	/**
	 * 删除全部
	 */
	public void remove() {
		count.clear();
		;
	}

	/**
	 * 返回Entry
	 * 
	 * @return Set<T>
	 */
	public Set<Entry<T, LongAdder>> entrySet() {
		return count.entrySet();
	}

	/**
	 * 迭代方法
	 * 
	 * @param action (k,v)->function的表达式
	 */
	public void forEach(BiConsumer<? super T, ? super LongAdder> action) {
		count.forEach(action);
	}

	@Override
	public String toString() {
		return count.toString();
	}
}
