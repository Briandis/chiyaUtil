package chiya.core.base.time;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;

import chiya.core.base.gc.GarbageCollection;

/**
 * 计数工具，用于解决固定间隔次数
 * 
 * @author chiya
 */
public class CountTimeUtil {

	/** 计数块，每个不同的key，将用链式列队进行存储 */
	private ConcurrentHashMap<String, ArrayBlockingQueue<Long>> concurrentHashMap;
	/** 默认总次数 */
	private int maxCount = 10;
	/** 统计间隔,毫秒 */
	private int timeInterval = 1000;
	/** 垃圾回收 */
	private GarbageCollection garbageCollection;

	/** 初始化 */
	private void init() {
		concurrentHashMap = new ConcurrentHashMap<String, ArrayBlockingQueue<Long>>();
		garbageCollection = new GarbageCollection(() -> {
			long nowTime = System.currentTimeMillis();
			concurrentHashMap.entrySet().removeIf(entry -> entry.getValue().stream().allMatch(lastTime -> lastTime + timeInterval < nowTime));
		});
	}

	/** 默认构造方法 */
	public CountTimeUtil() {
		init();
	}

	/**
	 * 最大值的构造方法
	 * 
	 * @param maxCount 最大数值
	 */
	public CountTimeUtil(int maxCount) {
		setMaxCount(maxCount);
		init();
	}

	/**
	 * 最大值和间隔的构造方法
	 * 
	 * @param maxCount     最大值
	 * @param timeInterval 间隔，毫秒
	 */
	public CountTimeUtil(int maxCount, int timeInterval) {
		setMaxCount(maxCount);
		setTimeInterval(timeInterval);
		init();
	}

	/**
	 * 设置总次数，底层会创建对应空间的数组，过大需要重新考虑系数
	 * 
	 * @param maxCount 最大次数
	 */
	public void setMaxCount(int maxCount) {
		this.maxCount = maxCount < 1 ? 10 : maxCount;
	}

	/**
	 * 次数失效间隔
	 * 
	 * @param timeInterval 时间间隔，毫秒
	 */
	public void setTimeInterval(int timeInterval) {
		this.timeInterval = timeInterval < 1 ? 1000 : timeInterval;
	}

	/**
	 * 放入KEY，自动会根据时间间隔次数配置进行检查
	 * 
	 * @param key 键
	 * @return true:在规定次数内/false:超出次数
	 */
	public boolean put(String key) {
		long nowTime = System.currentTimeMillis();
		garbageCollection.recycle();
		ArrayBlockingQueue<Long> queue = concurrentHashMap.get(key);
		if (queue == null) {
			synchronized (concurrentHashMap) {
				if (queue == null) {
					queue = new ArrayBlockingQueue<>(maxCount);
					concurrentHashMap.put(key, queue);
				}
			}
		}
		check(key);
		return queue.offer(nowTime);
	}

	/**
	 * 获取key当前次数
	 * 
	 * @param key 键
	 * @return count:次数
	 */
	public int get(String key) {
		return concurrentHashMap.get(key) != null ? concurrentHashMap.get(key).size() : 0;
	}

	/**
	 * 手动检查过期次数并移除
	 * 
	 * @param key 键
	 */
	public void check(String key) {
		ArrayBlockingQueue<Long> queue = concurrentHashMap.get(key);
		if (queue != null) { queue.removeIf(time -> time + timeInterval < System.currentTimeMillis()); }
	}

	/**
	 * 移除并返回key中的最终次数
	 * 
	 * @param key
	 * @return count:列队中的数量
	 */
	public int remove(String key) {
		ArrayBlockingQueue<Long> queue = concurrentHashMap.remove(key);
		return queue != null ? queue.size() : 0;
	}

}
