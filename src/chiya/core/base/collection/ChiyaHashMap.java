package chiya.core.base.collection;

import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;

/**
 * 线程安全的MAP<K,V>
 * 
 * @author chiya
 *
 * @param <K> Key
 * @param <V> Value
 */
public class ChiyaHashMap<K, V> {
	/** 基础容器 */
	private ConcurrentHashMap<K, V> concurrentHashMap = new ConcurrentHashMap<K, V>();

	/**
	 * 判断是否存在值
	 * 
	 * @param key Map的key
	 * @return true:存在/false:不存在
	 */
	public boolean contains(K key) {
		return concurrentHashMap.containsKey(key);
	}

	/**
	 * 添加
	 * 
	 * @param key   Map的key
	 * @param value 值
	 */
	public void put(K key, V value) {
		concurrentHashMap.put(key, value);
	}

	/**
	 * 清除全部内容
	 */
	public void remove() {
		concurrentHashMap.clear();
	}

	/**
	 * 移除key
	 * 
	 * @param key Map的key
	 * @return V 存储的对象
	 */
	public V remove(K key) {
		return concurrentHashMap.remove(key);
	}

	/**
	 * 获取map大小
	 * 
	 * @return 大小
	 */
	public int size() {
		return concurrentHashMap.size();
	}

	/**
	 * 获取Map中的值
	 * 
	 * @param key Map的key
	 * @return Map中的值
	 */
	public V get(K key) {
		return concurrentHashMap.get(key);
	}

	/**
	 * 返回Entry
	 * 
	 * @return Set<V>
	 */
	public Set<Entry<K, V>> entrySet() {
		return concurrentHashMap.entrySet();
	}

	/**
	 * 迭代方法
	 * 
	 * @param action (k,v)->function的表达式
	 */
	public void forEach(BiConsumer<? super K, ? super V> action) {
		concurrentHashMap.forEach(action);
	}

	/**
	 * 获取对象，如果不存在则构建后再获取
	 * 
	 * @param key     键
	 * @param classes 要构建的对象的class
	 * @return 存储的对象
	 */
	public V getAndPut(K key, Class<V> classes) {
		return ContainerUtil.getValueOrPut(concurrentHashMap, key, classes);
	}

}
