package chiya.core.base.cache;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import chiya.core.base.function.ReturnListFunction;
import chiya.core.base.function.ValueGetFunction;
import chiya.core.base.thread.ThreadUtil;

/**
 * 基础key-value缓存结构
 * 
 * @author brain
 * @param <K> 唯一标识的类型
 * @param <V> 传入存储的对象类型
 */
public abstract class BaseKeyValueCache<K, V> {

	/** 获取对象唯一标识 */
	protected final ValueGetFunction<V, K> valueGetFunction;

	/** 缓存容器 */
	protected final ConcurrentHashMap<K, V> concurrentHashMap = new ConcurrentHashMap<>();

	/**
	 * 构造方法
	 * 
	 * @param valueGetFunction 获取对象唯一标识
	 */
	public BaseKeyValueCache(ValueGetFunction<V, K> valueGetFunction) {
		this.valueGetFunction = valueGetFunction;
	}

	/** 缓存更新状态位 */
	protected volatile boolean NEED_RELOAD = true;

	/**
	 * 更新标识符
	 */
	public final void needReload() {
		NEED_RELOAD = true;
	}

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
		if (value != null) { concurrentHashMap.put(valueGetFunction.get(value), value); }
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
		if (list != null) { list.forEach(obj -> add(obj)); }
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
		list.forEach(
			k -> {
				if (concurrentHashMap.containsKey(k)) { value.add(concurrentHashMap.get(k)); }
			}
		);
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
		for (int i = 0; i < array.length; i++) { if (concurrentHashMap.containsKey(array[i])) { value.add(concurrentHashMap.get(array[i])); } }
		return value;
	}

	/**
	 * 重新拉取全部数据
	 * 
	 * @param function 获取数据方法
	 */
	public void reacquire(ReturnListFunction<V> function) {
		ThreadUtil.doubleCheckLock(
			() -> NEED_RELOAD,
			this,
			() -> {
				remove();
				add(function.getList());
				NEED_RELOAD = false;
			}
		);
	}
}
