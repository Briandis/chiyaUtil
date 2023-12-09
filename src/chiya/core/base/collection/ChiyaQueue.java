package chiya.core.base.collection;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * 线程安全的MAP<T,Queue<V>>结构
 * 
 * @author chiya
 *
 * @param <T> MAP的key
 * @param <V> 内部Queue的值
 */
public class ChiyaQueue<T, V> {
	/** 基础容器 */
	private ConcurrentHashMap<T, LinkedBlockingQueue<V>> concurrentHashMap = new ConcurrentHashMap<T, LinkedBlockingQueue<V>>();

	/**
	 * 判断是否存在值
	 * 
	 * @param key Map的key
	 * @return true 存在/false 不存在
	 */
	public boolean contains(T key) {
		return concurrentHashMap.containsKey(key);
	}

	/**
	 * 判断是否存在值
	 * 
	 * @param key   Map的key
	 * @param value 队列中存储的值
	 * @return true 存在/false 不存在
	 */
	public boolean contains(T key, V value) {
		return concurrentHashMap.containsKey(key) ? concurrentHashMap.get(key).contains(value) : false;
	}

	/**
	 * 添加
	 * 
	 * @param key   Map的key
	 * @param value 队列中存储的值
	 */
	public void put(T key, V value) {
		concurrentHashMap.computeIfAbsent(key, k -> new LinkedBlockingQueue<V>()).add(value);
	}

	/**
	 * 清除全部内容
	 */
	public void remove() {
		concurrentHashMap.clear();
	}

	/**
	 * 移除键，并返回value
	 * 
	 * @param key Map的key
	 * @return LinkedBlockingQueue<V>
	 */
	public LinkedBlockingQueue<V> remove(T key) {
		return concurrentHashMap.remove(key);
	}

	/**
	 * 移除map中列队中的值
	 * 
	 * @param key   Map的key
	 * @param value 队列中存储的值
	 * @return true 移除了值/false 不存在值
	 */
	public boolean remove(T key, V value) {
		boolean result = concurrentHashMap.containsKey(key) ? concurrentHashMap.get(key).remove(value) : false;
		// 如果已经为空了，则删除根节点
		if (concurrentHashMap.get(key).isEmpty()) { concurrentHashMap.remove(key); }
		return result;
	}

	/**
	 * 获取当前map大小
	 * 
	 * @return 大小
	 */
	public int size() {
		return concurrentHashMap.size();
	}

	/**
	 * 获取队列的大小
	 * 
	 * @param key Map的key
	 * @return 大小
	 */
	public int size(T key) {
		return concurrentHashMap.containsKey(key) ? concurrentHashMap.get(key).size() : 0;
	}

	/**
	 * 获取Set
	 * 
	 * @param key Map的key
	 * @return LinkedBlockingQueue<V>
	 */
	public LinkedBlockingQueue<V> get(T key) {
		return concurrentHashMap.get(key);
	}

	/**
	 * 返回Entry
	 * 
	 * @return Set<Map.Entry<T, LinkedBlockingQueue<E>>>
	 */
	public Set<Map.Entry<T, LinkedBlockingQueue<V>>> entrySet() {
		return concurrentHashMap.entrySet();
	}

	/**
	 * 迭代方法
	 * 
	 * @param action (k,v)->function的表达式
	 */
	public void forEach(BiConsumer<? super T, ? super LinkedBlockingQueue<V>> action) {
		concurrentHashMap.forEach(action);
	}

	/**
	 * 迭代方法
	 * 
	 * @param key    要迭代的key
	 * @param action (k,v)->function的表达式
	 */
	public void forEach(String key, Consumer<? super V> action) {
		LinkedBlockingQueue<V> data = concurrentHashMap.get(key);
		if (data != null) { data.forEach(action); }
	}

	/**
	 * 获取一个已有的队列，如何没有就获取新的
	 * 
	 * @param key 键
	 * @return LinkedBlockingQueue<V>
	 */
	public LinkedBlockingQueue<V> getOrNewValue(T key) {
		return concurrentHashMap.computeIfAbsent(key, k -> new LinkedBlockingQueue<V>());
	}
}
