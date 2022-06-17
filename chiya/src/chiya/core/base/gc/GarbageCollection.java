package chiya.core.base.gc;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import chiya.core.base.function.Function;

/**
 * 垃圾回收
 * 
 * @author chiya
 */
public class GarbageCollection {
	/** 过期自动回收锁 */
	private Lock lock;
	/** 默认间隔，回收机制才用某个业务线程中断回收后执行业务 */
	private int timeInterval = 1000 * 60 * 5;
	/** 最后清理时间 */
	private volatile long lastClearTime = System.currentTimeMillis();
	/** 清除的方法 */
	private Function function;

	/**
	 * 默认构造方法，需要传入待执行的任务
	 * 
	 * @param function 回收方法
	 */
	public GarbageCollection(Function function) {
		setFunction(function);
		lock = new ReentrantLock();
	}

	/**
	 * 默认构造方法，需要传入待执行的任务
	 * 
	 * @param function     回收方法
	 * @param timeInterval 触发间隔
	 */
	public GarbageCollection(Function function, int timeInterval) {
		setFunction(function);
		setTimeInterval(timeInterval);
		lock = new ReentrantLock();
	}

	/**
	 * 获取清理间隔
	 *
	 * @return 清理间隔
	 */
	public int getTimeInterval() {
		return timeInterval;
	}

	/**
	 * 设置清理间隔
	 *
	 * @param timeInterval 要设置的 清理间隔
	 */
	public void setTimeInterval(int timeInterval) {
		this.timeInterval = timeInterval;
	}

	/**
	 * 设置回收方法
	 *
	 * @param function 回收方法
	 */
	public void setFunction(Function function) {
		this.function = function;
	}

	/**
	 * 变动时触发
	 */
	public void recycle() {
		long nowTime = System.currentTimeMillis();
		if (lastClearTime + timeInterval < nowTime) {
			if (lock.tryLock()) {
				try {
					if (lastClearTime + timeInterval < nowTime) {
						function.task();
						lastClearTime = nowTime;
					}
				} finally {
					lock.unlock();
				}
			}
		}
	}

}
