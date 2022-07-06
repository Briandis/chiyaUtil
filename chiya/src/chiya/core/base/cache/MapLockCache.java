package chiya.core.base.cache;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import chiya.core.base.function.ReturnListFunction;
import chiya.core.base.function.ValueGetFunction;
import chiya.core.base.loop.Loop;
import chiya.core.base.thread.ThreadUtil;

/**
 * key-value结构缓存，没有回收机制，主要场景是少量不改变的数据<br>
 * 包含一个读写锁
 * 
 * @author brain
 * @param <K> 唯一标识的类型
 * @param <V> 传入存储的对象类型
 */
public class MapLockCache<K, V> {

	/** 获取对象唯一标识 */
	private ValueGetFunction<V, K> valueGetFunction;
	/** 缓存容器 */
	private ConcurrentHashMap<K, V> concurrentHashMap = null;
	/** 读写锁 */
	private ReentrantReadWriteLock reentrantReadWriteLock = null;

	/**
	 * 构造方法
	 * 
	 * @param valueGetFunction 获取对象唯一标识
	 */
	public MapLockCache(ValueGetFunction<V, K> valueGetFunction) {
		this.valueGetFunction = valueGetFunction;
		concurrentHashMap = new ConcurrentHashMap<>();
		reentrantReadWriteLock = new ReentrantReadWriteLock();
	}

	/** 缓存更新状态位 */
	public volatile boolean NEED_UPDATE = true;

	/**
	 * 移除缓存中数据
	 * 
	 * @param kye 键
	 */
	public void remove(K key) {
		reentrantReadWriteLock.writeLock().lock();
		try {
			concurrentHashMap.remove(key);
		}
		finally {
			reentrantReadWriteLock.writeLock().unlock();
		}
	}

	/**
	 * 添加对象
	 * 
	 * @param value 缓存数据
	 */
	public void add(V value) {
		if (value != null) {
			reentrantReadWriteLock.writeLock().lock();
			try {
				concurrentHashMap.put(valueGetFunction.get(value), value);
			}
			finally {
				reentrantReadWriteLock.writeLock().unlock();
			}
		}
	}

	/**
	 * 添加对象
	 * 
	 * @param key   缓存的key
	 * @param value 缓存数据
	 */
	public void add(K key, V value) {
		if (key != null && value != null) {
			reentrantReadWriteLock.writeLock().lock();
			try {
				concurrentHashMap.put(key, value);
			}
			finally {
				reentrantReadWriteLock.writeLock().unlock();
			}
		}
	}

	/**
	 * 添加列表进行缓存
	 * 
	 * @param list 列表
	 */
	public void add(List<V> list) {
		if (list != null) {
			reentrantReadWriteLock.writeLock().lock();
			try {
				list.forEach(obj -> add(obj));
			}
			finally {
				reentrantReadWriteLock.writeLock().unlock();
			}
		}
	}

	/**
	 * 删除所有缓存
	 */
	public void remove() {
		reentrantReadWriteLock.writeLock().lock();
		try {
			concurrentHashMap.clear();
		}
		finally {
			reentrantReadWriteLock.writeLock().unlock();
		}

	}

	/**
	 * 获取一个对象
	 * 
	 * @param key 键
	 * @return Object 缓存的数据
	 */
	public V get(K key) {
		reentrantReadWriteLock.readLock().lock();
		try {
			return concurrentHashMap.get(key);
		}
		finally {
			reentrantReadWriteLock.readLock().unlock();
		}

	}

	/**
	 * 获取全部缓存数据
	 * 
	 * @return List<V> 全部缓存的对象
	 */
	public List<V> get() {
		reentrantReadWriteLock.readLock().lock();
		try {
			return new ArrayList<>(concurrentHashMap.values());
		}
		finally {
			reentrantReadWriteLock.readLock().unlock();
		}
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
		ThreadUtil.lock(
			reentrantReadWriteLock.readLock(),
			() -> {
				list.forEach(
					k -> {
						if (concurrentHashMap.containsKey(k)) { value.add(concurrentHashMap.get(k)); }
					}
				);
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
		ThreadUtil.lock(
			reentrantReadWriteLock.readLock(),
			() -> {
				Loop.forEach(
					array,
					o -> {
						if (concurrentHashMap.containsKey(o)) { value.add(concurrentHashMap.get(o)); }
					}
				);
			}
		);
		return value;
	}

	/**
	 * 重新拉取全部数据
	 * 
	 * @param function 获取数据方法
	 */
	public void reacquire(ReturnListFunction<V> function) {
		ThreadUtil.doubleCheckLock(
			() -> NEED_UPDATE,
			reentrantReadWriteLock.readLock(),
			() -> {
				remove();
				add(function.getList());
				NEED_UPDATE = false;
			}
		);
	}
}
