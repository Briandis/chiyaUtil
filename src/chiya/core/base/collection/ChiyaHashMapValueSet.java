package chiya.core.base.collection;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.function.BiConsumer;

/**
 * 线程安全的MAP<T,SET<E>>结构
 * 
 * @author chiya
 *
 * @param <T> MAP的key
 * @param <E> 内部Set的Key
 */
public class ChiyaHashMapValueSet<T, E> {
	/**
	 * 基础容器
	 */
	private ConcurrentHashMap<T, ConcurrentSkipListSet<E>> concurrentHashMap = new ConcurrentHashMap<T, ConcurrentSkipListSet<E>>();

	/**
	 * 判断是否存在值
	 * 
	 * @param key    Map的key
	 * @param setKey Set的key
	 * @return true 存在/false 不存在
	 */
	public boolean contains(T key, E setKey) {
		return concurrentHashMap.containsKey(key) ? concurrentHashMap.get(key).contains(setKey) : false;
	}

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
	 * 添加
	 * 
	 * @param key    Map的key
	 * @param setKey Set的key
	 */
	public void put(T key, E setKey) {
		concurrentHashMap.computeIfAbsent(key, k -> new ConcurrentSkipListSet<E>()).add(setKey);
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
	 * @return ConcurrentSkipListSet<E>
	 */
	public ConcurrentSkipListSet<E> remove(T key) {
		return concurrentHashMap.remove(key);
	}

	/**
	 * 移除map中的set值
	 * 
	 * @param key    Map的key
	 * @param setKey Set的key
	 * @return true 移除了值/false 不存在值
	 */
	public boolean remove(T key, E setKey) {
		return concurrentHashMap.containsKey(key) ? concurrentHashMap.get(key).remove(setKey) : false;
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
	 * 获取set的大小
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
	 * @return ConcurrentSkipListSet<E>
	 */
	public ConcurrentSkipListSet<E> get(T key) {
		return concurrentHashMap.get(key);
	}

	/**
	 * 返回Entry
	 * 
	 * @return Set<Map.Entry<T, ConcurrentSkipListSet<E>>>
	 */
	public Set<Map.Entry<T, ConcurrentSkipListSet<E>>> entrySet() {
		return concurrentHashMap.entrySet();
	}

	/**
	 * 迭代方法
	 * 
	 * @param action (k,v)->function的表达式
	 */
	public void forEach(BiConsumer<? super T, ? super ConcurrentSkipListSet<E>> action) {
		concurrentHashMap.forEach(action);
	}

	/**
	 * 获取一个已有的Set，如何没有就获取新的
	 * 
	 * @param key 键
	 * @return ConcurrentSkipListSet<E>
	 */
	public ConcurrentSkipListSet<E> getOrNewValue(T key) {
		return concurrentHashMap.computeIfAbsent(key, k -> new ConcurrentSkipListSet<>());
	}
}
