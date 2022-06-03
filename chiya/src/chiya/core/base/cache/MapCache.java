package chiya.core.base.cache;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import chiya.core.base.function.GetUniqueFunction;

/**
 * key-value结构缓存，没有回收机制，主要场景是少量不改变的数据
 * 
 * @author brain
 *
 */
public class MapCache<K, V> {

	/** 获取对象唯一标识 */
	private GetUniqueFunction<K, V> getUniqueFunction;

	/**
	 * 构造方法
	 * 
	 * @param genericsReturnFunction 获取对象唯一标识
	 */
	public MapCache(GetUniqueFunction<K, V> genericsReturnFunction) {
		this.getUniqueFunction = genericsReturnFunction;
	}

	/** 缓存容器 */
	private ConcurrentHashMap<K, V> concurrentHashMap = new ConcurrentHashMap<>();

	/** 缓存更新状态位 */
	public volatile boolean NEED_UPDATE = true;

	/**
	 * 移除缓存中数据
	 * 
	 * @param kye 键
	 */
	public void remove(K key) {
		concurrentHashMap.remove(key);
	}

	/**
	 * 添加对象
	 * 
	 * @param value 缓存数据
	 */
	public void add(V value) {
		if (value != null) { concurrentHashMap.put(getUniqueFunction.task(value), value); }
	}

	/**
	 * 添加对象
	 * 
	 * @param key   缓存的key
	 * @param value 缓存数据
	 */
	public void add(K key, V value) {
		if (key != null && value != null) { concurrentHashMap.put(key, value); }
	}

	/**
	 * 添加列表进行缓存
	 * 
	 * @param list 列表
	 */
	public void add(List<V> list) {
		list.forEach(obj -> add(obj));
	}

	/**
	 * 删除所有缓存
	 */
	public void remove() {
		concurrentHashMap.clear();
	}

	/**
	 * 获取一个对象
	 * 
	 * @param key 键
	 * @return Object 缓存的数据
	 */
	public V get(K key) {
		return concurrentHashMap.get(key);
	}

	/**
	 * 获取全部缓存数据
	 * 
	 * @return List<V> 全部缓存的对象
	 */
	public List<V> get() {
		return new ArrayList<>(concurrentHashMap.values());
	}

	/**
	 * 获取指定范围的数据
	 * 
	 * @param list 唯一标识列表
	 * @return List<V> 找到的缓存对象
	 */
	public List<V> get(Collection<K> list) {
		if (list == null) { return null; }
		List<V> value = new ArrayList<>();
		list.forEach(k -> {
			if (concurrentHashMap.containsKey(k)) { value.add(concurrentHashMap.get(k)); }
		});
		return value;
	}

	/**
	 * 获取指定范围的数据
	 * 
	 * @param array 唯一标识列表
	 * @return List<V> 找到的缓存对象
	 */
	public List<V> get(K[] array) {
		if (array == null) { return null; }
		List<V> value = new ArrayList<>();
		for (int i = 0; i < array.length; i++) {
			if (concurrentHashMap.containsKey(array[i])) { value.add(concurrentHashMap.get(array[i])); }
		}
		return value;
	}
}
