package chiya.core.base.thread;

import java.util.HashSet;
import java.util.concurrent.ConcurrentHashMap;

import chiya.core.base.gc.GarbageCollection;

/**
 * 通用型线程存储空间
 * 
 * @author chiya
 *
 */
public class ChiyaSpace<K, V> {

	/** 存储内容 */
	private ConcurrentHashMap<String, ConcurrentHashMap<K, V>> threadMap = null;
	/** 垃圾回收期 */
	private GarbageCollection garbageCollection = null;

	/**
	 * 构造方法，会自动构造回收期，由下一次执行获取的线程执行回收
	 */
	public ChiyaSpace() {
		// 构建存储容器
		threadMap = new ConcurrentHashMap<String, ConcurrentHashMap<K, V>>();
		// 构建GC回收
		garbageCollection = new GarbageCollection(() -> {
			ThreadGroup threadGroup = Thread.currentThread().getThreadGroup();
			Thread threads[] = new Thread[threadGroup.activeCount()];
			threadGroup.enumerate(threads);
			// 存活的线程
			HashSet<String> hashSet = new HashSet<String>();
			for (Thread thread : threads) {
				if (thread != null) { hashSet.add(thread.getName()); }
			}
			// 如果线程没有存活，则回收
			threadMap.entrySet().removeIf(entry -> !hashSet.contains(entry.getKey()));
		});

	}

	/**
	 * 获取当前map中key的值
	 * 
	 * @param key 键
	 * @return Object
	 */
	public Object get(K key) {
		return getMap().get(key);
	}

	/**
	 * 当前线程Map中添加一个key、value
	 * 
	 * @param key   键
	 * @param value 值
	 */
	public void put(K key, V value) {
		getMap().put(key, value);
	}

	/**
	 * 如果值不存在，则插入，否则获取当前值
	 * 
	 * @param key   键
	 * @param value 值
	 * @return 当前持有的值/新插入的值
	 */
	public V computeIfAbsent(K key, V value) {
		return getMap().computeIfAbsent(key, k -> value);
	}

	/**
	 * 删除当前Map中对应key
	 * 
	 * @param key 键
	 */
	public void remove(K key) {
		getMap().remove(key);
	}

	/**
	 * 清除当前线程Map中所有的数据
	 */
	public void clear() {
		getMap().clear();
	}

	/**
	 * 获取当前线程自己的MAP
	 * 
	 * @return 当前线程持有的MAP
	 */
	private ConcurrentHashMap<K, V> getMap() {
		garbageCollection.recycle();
		return threadMap.computeIfAbsent(
			ThreadUtil.getThreadName(),
			k -> new ConcurrentHashMap<K, V>()
		);
	}

}
