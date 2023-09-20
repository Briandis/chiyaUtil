package chiya.core.base.cache;

import java.util.concurrent.ConcurrentHashMap;

import chiya.core.base.function.GenericNoParamFunction;
import chiya.core.base.gc.GarbageCollection;

/**
 * key-value结构计时缓存，如果超出时间未访问，则会被回收
 * 
 * @author brain
 *
 */
public class MapTimingCache<K, V> {

	/** 缓存容器 */
	private ConcurrentHashMap<K, TimeEntity<V>> concurrentHashMap = new ConcurrentHashMap<>();
	/** 垃圾回收 */
	private GarbageCollection garbageCollection;
	/** 统计间隔，毫秒，默认一分钟 */
	private int timeInterval = 1000 * 60;

	/** 初始化 */
	public void init() {
		garbageCollection = new GarbageCollection(() -> {
			long nowTime = System.currentTimeMillis();
			concurrentHashMap.entrySet().removeIf(
				entry -> entry.getValue().getLastTime() + timeInterval < nowTime
			);
		});
	}

	/** 默认构造方法，清理间隔1分钟 */
	public MapTimingCache() {
		init();
	}

	/**
	 * 构造方法
	 * 
	 * @param timeInterval 失效间隔毫秒
	 */
	public MapTimingCache(int timeInterval) {
		this.timeInterval = timeInterval;
		init();
	}

	/**
	 * 链式存入自身
	 * 
	 * @param key   键
	 * @param value 值
	 * @return 自身
	 */
	public MapTimingCache<K, V> put(K key, V value) {
		concurrentHashMap.computeIfAbsent(key, k -> new TimeEntity<V>(value)).setData(value);
		garbageCollection.recycle();
		return this;
	}

	/**
	 * 获取缓存中的或者构建一个
	 * 
	 * @param key   键
	 * @param value 值
	 * @return 缓存中的或者构建的对象
	 */
	public V getOrNew(K key, GenericNoParamFunction<V> function) {
		garbageCollection.recycle();
		TimeEntity<V> timeEntity = concurrentHashMap.computeIfAbsent(key, k -> new TimeEntity<V>(function.getValue()));
		if (timeEntity.getLastTime() + timeInterval > System.currentTimeMillis()) {
			return timeEntity.getData();
		} else {
			timeEntity.setData(function.getValue());
			return timeEntity.getData();
		}
	}

	/**
	 * 获取存储的值
	 * 
	 * @param key 键
	 * @return 值
	 */
	public V get(K key) {
		garbageCollection.recycle();
		if (concurrentHashMap.containsKey(key)) {
			TimeEntity<V> timeEntity = concurrentHashMap.get(key);
			if (timeEntity.getLastTime() + timeInterval > System.currentTimeMillis()) { return timeEntity.getData(); }
		}
		return null;
	}

	/**
	 * 移除key
	 * 
	 * @param key 键
	 */
	public void remove(K key) {
		concurrentHashMap.remove(key);
	}

	/**
	 * 移除全部
	 */
	public void removeAll() {
		concurrentHashMap.clear();
	}
}
