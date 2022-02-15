package chiya.core.base.thread;

import java.util.HashSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

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

	/** 内存释放标记 */
	private volatile static boolean claerFlag = false;
	/** 上一次内存回收时间 */
	private volatile static long lastTime = System.currentTimeMillis();
	/** 清理间隔,5分钟一次 */
	private static long claerTime = 1000;
	/** 内存回收同步锁 */
	private static final Lock LOCK = new ReentrantLock();
	/** 内存回收线程标记,减少访问锁普通情况下访问锁次数，但依然能出现极端情况全部通过 */
	private static long claerThreadId = 0;

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
	public static void claer() {
		getMap().clear();
	}

	/**
	 * 获取当前线程自己的MAP
	 * 
	 * @return ConcurrentHashMap
	 */
	private static ConcurrentHashMap<String, Object> getMap() {
		if (threadThisMap == null) {
			synchronized (ThreadSpace.class) {
				if (threadThisMap == null) { threadThisMap = new ConcurrentHashMap<String, ConcurrentHashMap<String, Object>>(); }
			}

		}
		// 自动检查是否需要回收内存
		long nowTime = System.currentTimeMillis();
		if (claerFlag = lastTime + claerTime < nowTime) {
			claerThreadId = ThreadUtil.getThreadId();
			autoTimeClear();
		}

		String threadName = ThreadUtil.getThreadName();
		if (threadThisMap.get(threadName) == null) {
			synchronized (threadThisMap) {
				if (threadThisMap.get(threadName) == null) { threadThisMap.put(threadName, new ConcurrentHashMap<String, Object>()); }
			}
		}
		return threadThisMap.get(threadName);
	}

	/**
	 * 清除线程释放后多余占用空间
	 */
	private static void autoTimeClear() {
		if (claerFlag && claerThreadId == ThreadUtil.getThreadId()) {
			if (LOCK.tryLock()) {
				if (claerFlag) {
					ThreadGroup threadGroup = Thread.currentThread().getThreadGroup();
					Thread threads[] = new Thread[threadGroup.activeCount()];
					threadGroup.enumerate(threads);
					HashSet<String> hashSet = new HashSet<String>();
					for (Thread thread : threads) { if (thread != null) { hashSet.add(thread.getName()); } }
					threadThisMap.entrySet().removeIf(entry -> !hashSet.contains(entry.getKey()));
					lastTime = System.currentTimeMillis();
					claerFlag = false;
				}
				LOCK.unlock();
			}
		}
	}

}
