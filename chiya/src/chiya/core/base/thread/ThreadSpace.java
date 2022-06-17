package chiya.core.base.thread;

import java.util.HashSet;
import java.util.concurrent.ConcurrentHashMap;

import chiya.core.base.gc.GarbageCollection;

/**
 * 线程存储空间工具库
 * 
 * @author chiya
 */
public class ThreadSpace {
	/** 私有化构造方法 */
	private ThreadSpace() {}

	// 懒加载模式，只有使用到了，才创建存储对象
	/** 存储内容 */
	private static ConcurrentHashMap<String, ConcurrentHashMap<String, Object>> threadThisMap = null;
	/** 垃圾回收期 */
	private static GarbageCollection garbageCollection;
	/** 需要初始化标记 */
	private static Boolean needInit = true;

	/**
	 * 获取当前map中key的值
	 * 
	 * @param key 键
	 * @return Object
	 */
	public static Object get(String key) {
		return getMap().get(key);
	}

	/**
	 * 当前线程Map中添加一个key、value
	 * 
	 * @param <T>
	 * @param key   key
	 * @param value value
	 */
	public static <T> void put(String key, T value) {
		getMap().put(key, value);
	}

	/**
	 * 删除当前Map中对应key
	 * 
	 * @param key
	 */
	public static void remove(String key) {
		getMap().remove(key);
	}

	/**
	 * 清除当前线程Map中所有的数据
	 */
	public static void clear() {
		getMap().clear();
	}

	/**
	 * 获取当前线程自己的MAP
	 * 
	 * @return ConcurrentHashMap
	 */
	private static ConcurrentHashMap<String, Object> getMap() {
		ThreadUtil.conditionLock(
			() -> needInit,
			ThreadSpace.class,
			() -> {
				// 构建存储容器
				threadThisMap = new ConcurrentHashMap<String, ConcurrentHashMap<String, Object>>();
				// 构建GC回收
				garbageCollection = new GarbageCollection(() -> {
					ThreadGroup threadGroup = Thread.currentThread().getThreadGroup();
					Thread threads[] = new Thread[threadGroup.activeCount()];
					threadGroup.enumerate(threads);
					// 存货的线程
					HashSet<String> hashSet = new HashSet<String>();
					for (Thread thread : threads) {
						if (thread != null) { hashSet.add(thread.getName()); }
					}
					// 如果线程没有存活，则回收
					threadThisMap.entrySet().removeIf(entry -> !hashSet.contains(entry.getKey()));
				});
				needInit = false;
			}
		);
		garbageCollection.recycle();

		String threadName = ThreadUtil.getThreadName();
		ThreadUtil.conditionLock(
			() -> threadThisMap.get(threadName) == null,
			threadThisMap,
			() -> threadThisMap.put(threadName, new ConcurrentHashMap<String, Object>())
		);

		return threadThisMap.get(threadName);
	}

}
