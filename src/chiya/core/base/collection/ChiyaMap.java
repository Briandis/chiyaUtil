package chiya.core.base.collection;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;

/**
 * 线程安全的MAP<T,MAP<K,V>>
 * 
 * @author chiya
 *
 * @param <T> 外层Key
 * @param <K> 内层Key
 * @param <V> 内层Value
 */
public class ChiyaMap<T, K, V> {
	/**
	 * 基础容器
	 */
	private ConcurrentHashMap<T, ConcurrentHashMap<K, V>> concurrentHashMap = new ConcurrentHashMap<T, ConcurrentHashMap<K, V>>();

	/**
	 * 判断是否存在值
	 * 
	 * @param key Map的key
	 * @return true:存在/false:不存在
	 */
	public boolean contains(T key) {
		return concurrentHashMap.containsKey(key);
	}

	/**
	 * 判断是否存在值
	 * 
	 * @param key      Map的key
	 * @param valueKey 内部Map的key
	 * @return true:存在/false:不存在
	 */
	public boolean contains(T key, K valueKey) {
		return concurrentHashMap.containsKey(key) ? concurrentHashMap.get(key).containsKey(valueKey) : false;
	}

	/**
	 * 添加
	 * 
	 * @param key      Map的key
	 * @param valueKey 内层map的key
	 * @param value    值
	 */
	public void put(T key, K valueKey, V value) {
		concurrentHashMap.computeIfAbsent(key, k -> new ConcurrentHashMap<K, V>()).put(valueKey, value);
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
	 * @return ConcurrentHashMap<K, V>
	 */
	public ConcurrentHashMap<K, V> remove(T key) {
		return concurrentHashMap.remove(key);
	}

	/**
	 * 移除内部MAP的key
	 * 
	 * @param key      Map的key
	 * @param valueKey 内层Map的key
	 * @return value 值
	 */
	public V remove(T key, K valueKey) {
		V result = concurrentHashMap.containsKey(key) ? concurrentHashMap.get(key).remove(valueKey) : null;
		// 如果已经为空了，则删除根节点
		if (concurrentHashMap.get(key).isEmpty()) { concurrentHashMap.remove(key); }
		return result;
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
	 * 获取内部map大小
	 * 
	 * @param key:Map的key
	 * @return 内部Map大小
	 */
	public int size(T key) {
		return concurrentHashMap.containsKey(key) ? concurrentHashMap.get(key).size() : 0;
	}

	/**
	 * 获取Map
	 * 
	 * @param key Map的key
	 * @return ConcurrentHashMap<K, V>
	 */
	public ConcurrentHashMap<K, V> get(T key) {
		return concurrentHashMap.get(key);
	}

	/**
	 * 获取Map中的值
	 * 
	 * @param key      Map的key
	 * @param valueKey 内层map的key
	 * @return Map中的值
	 */
	public V get(T key, K valueKey) {
		return concurrentHashMap.containsKey(key) ? concurrentHashMap.get(key).get(valueKey) : null;
	}

	/**
	 * 返回Entry
	 * 
	 * @return Set<Map.Entry<T, ConcurrentHashMap<K, V>>>
	 */
	public Set<Map.Entry<T, ConcurrentHashMap<K, V>>> entrySet() {
		return concurrentHashMap.entrySet();
	}

	/**
	 * 返回Entry
	 * 
	 * @param key
	 * @return Set<Map.Entry<K, V>>
	 */
	public Set<Map.Entry<K, V>> entrySet(String key) {
		ConcurrentHashMap<K, V> map = concurrentHashMap.get(key);
		return map == null ? null : map.entrySet();
	}

	/**
	 * 迭代方法
	 * 
	 * @param action (k,v)->function的表达式
	 */
	public void forEach(BiConsumer<? super T, ? super ConcurrentHashMap<K, V>> action) {
		concurrentHashMap.forEach(action);
	}

	/**
	 * 迭代方法
	 * 
	 * @param key    要迭代的key
	 * @param action (k,v)->function的表达式
	 */
	public void forEach(String key, BiConsumer<? super K, ? super V> action) {
		ConcurrentHashMap<K, V> map = concurrentHashMap.get(key);
		if (map != null) { map.forEach(action); }
	}

	/**
	 * 获取一个已有的Map，如何没有就获取新的
	 * 
	 * @param key 键
	 * @return ConcurrentHashMap<K, V>
	 */
	public ConcurrentHashMap<K, V> getOrNewValue(T key) {
		return concurrentHashMap.computeIfAbsent(key, k -> new ConcurrentHashMap<K, V>());
	}

}
