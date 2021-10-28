package chiya.core.base.time;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import chiya.core.base.thread.ThreadMapUtil;

/**
 * 计数工具，用于解决固定间隔次数
 * 
 * @author Brian
 * @param <T>
 *
 */
public class CountTimeUtil {

	/**
	 * 计数块，每个不同的key，将用链式列队进行存储
	 */
	private ConcurrentHashMap<String, ArrayBlockingQueue<Long>> concurrentHashMap = new ConcurrentHashMap<String, ArrayBlockingQueue<Long>>();
	/**
	 * 默认总次数
	 */
	private int maxCount = 10;
	/**
	 * 默认间隔，回收机制才用某个业务线程中断回收后执行业务
	 */
	private int timeInterval = 1000 * 60 * 5;

	/**
	 * 清除失效key时间间隔
	 */
	private int claerLossKeyTime = 1000;
	/**
	 * 最后一次自动回收key时间
	 */
	private volatile long lastClaerTime = System.currentTimeMillis();
	/**
	 * 自动回收锁
	 */
	private Lock lock = new ReentrantLock();

	public CountTimeUtil() {}

	public CountTimeUtil(int maxCount) {
		setMaxCount(maxCount);
	}

	public CountTimeUtil(int maxCount, int timeInterval) {
		setMaxCount(maxCount);
		setTimeInterval(timeInterval);
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
	 * 自动回收失效key间隔
	 * 
	 * @param claerLossKeyTime 自动回收间隔，毫秒
	 */
	public void setClaerLossKeyTime(int claerLossKeyTime) {
		this.claerLossKeyTime = claerLossKeyTime < 1 ? 1000 * 60 * 5 : claerLossKeyTime;
	}

	/**
	 * 放入KEY，自动会根据时间间隔次数配置进行检查
	 * 
	 * @param key key
	 * @return true:在规定次数内/false:超出次数
	 */
	public boolean put(String key) {
		long nowTime = System.currentTimeMillis();

		// 自动回收key，如果有线程占获取锁，则由该线程来执行，其余线继续执行业务
		if (lastClaerTime + claerLossKeyTime < nowTime) {
			if (lock.tryLock()) {
				if (lastClaerTime + claerLossKeyTime < nowTime) {
					autoTimeClaer();
					lastClaerTime = nowTime;
					System.out.println(ThreadMapUtil.getThreadName() + "回收了失效key");
				}

				lock.unlock();
			}
		}

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
	 * @param key
	 * @return count:次数
	 */
	public int get(String key) {
		return concurrentHashMap.get(key) != null ? concurrentHashMap.get(key).size() : 0;
	}

	/**
	 * 手动检查过期次数并移除
	 * 
	 * @param key
	 */
	public void check(String key) {
		ArrayBlockingQueue<Long> queue = concurrentHashMap.get(key);
		if (queue != null) { queue.removeIf(time -> time + timeInterval < System.currentTimeMillis()); }
	}

	/**
	 * 自动回收失效key
	 */
	private void autoTimeClaer() {
		long nowTime = System.currentTimeMillis();
		concurrentHashMap.entrySet().removeIf(entry -> entry.getValue().stream().allMatch(l -> l + claerLossKeyTime < nowTime));
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
