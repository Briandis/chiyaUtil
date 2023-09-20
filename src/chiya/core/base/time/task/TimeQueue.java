package chiya.core.base.time.task;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.DelayQueue;
import chiya.core.base.collection.MapEnter;
import chiya.core.base.function.VoidGenericFunction;
import chiya.core.base.thread.ThreadTask;

/**
 * 延时队列
 * 
 * @author chiya
 */
public class TimeQueue<K, V> extends ThreadTask<V> {

	/** 延时队列 */
	private volatile DelayQueue<TimeKey<K>> delayQueue = new DelayQueue<TimeKey<K>>();
	/** 实际存储 */
	private ConcurrentHashMap<K, MapEnter<TimeKey<K>, V>> concurrentHashMap = new ConcurrentHashMap<K, MapEnter<TimeKey<K>, V>>();

	/**
	 * 构造方法，需要传入任务体
	 * 
	 * @param task 任务执法方法
	 */
	public TimeQueue(VoidGenericFunction<V> task) {
		super(task);
	}

	/**
	 * 添加
	 * 
	 * @param key        键
	 * @param value      值
	 * @param createTime 创建时间
	 * @param timeOut    超时时间
	 */
	public void put(K key, V value, long createTime, long timeOut) {
		TimeKey<K> timeKey = new TimeKey<K>(key, createTime, timeOut);
		concurrentHashMap.put(key, new MapEnter<TimeKey<K>, V>(timeKey, value));
		delayQueue.add(timeKey);
	}

	/**
	 * 添加
	 * 
	 * @param key     键
	 * @param value   值
	 * @param timeOut 超时时间
	 */
	public void put(K key, V value, long timeOut) {
		put(key, value, System.currentTimeMillis(), timeOut);
	}

	/**
	 * 移除
	 * 
	 * @param key 键
	 * @return 信息对象
	 */
	public MapEnter<TimeKey<K>, V> remove(K key) {
		MapEnter<TimeKey<K>, V> mapEnter = concurrentHashMap.remove(key);
		if (mapEnter != null) { delayQueue.remove(mapEnter.getKey()); }
		return mapEnter;
	}

	/**
	 * 移除全部
	 */
	public void remove() {
		concurrentHashMap.clear();
		delayQueue.clear();
	}

	/**
	 * 改变触发时间
	 * 
	 * @param key        键
	 * @param createTime 对象创建时间
	 * @param timeOut    延迟触发时间
	 */
	public void timeOutChange(K key, long createTime, long timeOut) {
		MapEnter<TimeKey<K>, V> mapEnter = concurrentHashMap.get(key);
		if (mapEnter != null) {
			delayQueue.remove(mapEnter.getKey());
			mapEnter.getKey().setCreateTime(createTime);
			mapEnter.getKey().setTimeOut(timeOut);
			delayQueue.add(mapEnter.getKey());
		}
	}

	/**
	 * 改变触发时间，基于当前时间戳延迟多少
	 * 
	 * @param key     键
	 * @param timeOut 延迟触发时间
	 */
	public void timeOutChange(K key, long timeOut) {
		timeOutChange(key, System.currentTimeMillis(), timeOut);
	}

	/**
	 * 添加或修改
	 * 
	 * @param key     键
	 * @param value   值
	 * @param timeOut 超时时间
	 */
	public void putOrChange(K key, V value, long timeOut) {
		MapEnter<TimeKey<K>, V> mapEnter = concurrentHashMap.get(key);
		if (mapEnter != null) {
			timeOutChange(key, timeOut);
		} else {
			put(key, value, timeOut);
		}
	}

	@Override
	public V next() throws Exception {
		TimeKey<K> timeShell = delayQueue.take();
		return concurrentHashMap.remove(timeShell.getKey()).getValue();
	}

	/**
	 * 获取当前延时队列中的所有key
	 * 
	 * @return 可迭代的set集合
	 */
	public Collection<K> getKeys() {
		return concurrentHashMap.keySet();
	}

}
